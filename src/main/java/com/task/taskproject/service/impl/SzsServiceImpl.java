package com.task.taskproject.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.taskproject.dto.incomeInfo.response.IncomeInfoCardResponse;
import com.task.taskproject.dto.incomeInfo.response.IncomeInfoRefundResponse;
import com.task.taskproject.dto.incomeInfo.response.IncomeInfoResponse;
import com.task.taskproject.dto.member.response.MemberInfoResponse;
import com.task.taskproject.entity.MemberIncmInfo;
import com.task.taskproject.entity.MemberIncmInfoCard;
import com.task.taskproject.entity.MemberIncmInfoPension;
import com.task.taskproject.repository.MemberIncmInfoCardRepository;
import com.task.taskproject.repository.MemberIncmInfoPensionRepository;
import com.task.taskproject.repository.MemberIncmInfoRepository;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.security.TokenProvider;
import com.task.taskproject.service.SzsService;
import com.task.taskproject.utils.CipherUtil;
import com.task.taskproject.utils.SzsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @Author : 최대준
 * @ClassName : SzsServiceImpl
 * @Description : 스크래핑 및 결정세액 API 구현체
 * @Since : 2024. 06. 25.
 */
@Service("SzsService")
public class SzsServiceImpl implements SzsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberIncmInfoRepository memberIncmInfoRepository;

    @Autowired
    private MemberIncmInfoPensionRepository memberIncmInfoPensionRepository;

    @Autowired
    private MemberIncmInfoCardRepository memberIncmInfoCardRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SzsProvider szsProvider;

    @Transactional
    @Override
    public String postScrap(String userId) {
        MemberInfoResponse content = memberRepository.findByUserId(userId)
                .map(MemberInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            /** Scraping API 통신 */
            URL url = new URL(szsProvider.getScrapingUrl());
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-API-KEY", szsProvider.getXApiKey());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(30000); // 30초
            connection.setReadTimeout(30000); // 30초
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            CipherUtil util = new CipherUtil(tokenProvider.getEncryptionKey());
            String jsonInputString = "{\"name\": \"" + content.name() + "\", \"regNo\": \"" + util.decryptString(content.regNo()) + "\"}";
            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInputString.getBytes();
            os.write(input, 0, input.length);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            /** API Response Data Format Parsing */
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> jsonMap = objectMapper.readValue(response.toString(), Map.class);
            // MemberIncmInfo Entity Parsing
            IncomeInfoResponse incomeTaxInfo = objectMapper.convertValue(jsonMap.get("data"), IncomeInfoResponse.class);

            // MemberIncmInfoPension Entity Parsing
            List<MemberIncmInfoPension> mList = incomeTaxInfo.deductions().getPensionDeductions().stream().map(x -> x.of(userId)).collect(Collectors.toList());

            // MemberIncmInfoCard Entity Parsing
            IncomeInfoCardResponse creditCardDeduction = incomeTaxInfo.deductions().getCreditCardDeduction();
            List<MemberIncmInfoCard> cardList = creditCardDeduction.cardIncomeList().stream().map(x -> creditCardDeduction.from(userId, x)).collect(Collectors.toList());

            // Entity DB Insert or Update
            memberIncmInfoRepository.save(incomeTaxInfo.of(userId));
            memberIncmInfoPensionRepository.saveAll(mList);
            memberIncmInfoCardRepository.saveAll(cardList);
        } catch (JsonMappingException e) {
            throw new NoSuchElementException("Json Parsing 작업 중 문제가 발생했습니다.");
        } catch (IOException e) {
            throw new NoSuchElementException("Scraping API 통신 중 문제가 발생했습니다.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new NoSuchElementException("복호화 작업 중 문제가 발생했습니다.");
        } catch (InvalidKeyException e) {
            throw new NoSuchElementException("복호화 작업 중 문제가 발생했습니다.");
        } finally {
            try {
                if(reader != null) reader.close();
                if(connection != null) connection.disconnect();
            } catch (IOException e) {
                throw new IllegalArgumentException("Scraping API 통신 중 문제가 발생했습니다.");
            }
        }
        return userId;
    }

    @Transactional
    @Override
    public IncomeInfoRefundResponse postRefund(String userId) {
        MemberInfoResponse content = memberRepository.findByUserId(userId)
                .map(MemberInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        MemberIncmInfo memberIncmInfo = memberIncmInfoRepository.findByUserId(userId); // 종합소득금액, 세액공제 select
        List<MemberIncmInfoPension> memberIncmInfoPension = memberIncmInfoPensionRepository.findAllByComplexKeyUserId(userId); // 국민연금 select
        List<MemberIncmInfoCard> memberIncmInfoCard = memberIncmInfoCardRepository.findAllByComplexKeyUserId(userId); // 신용카드소득공제 select

        double sumPension = memberIncmInfoPension.stream().mapToDouble(obj -> Math.round(obj.getDeductible())).sum(); // 국민연금 총합
        double sumCard = memberIncmInfoCard.stream().mapToDouble(obj -> Math.round(obj.getDeductible())).sum(); // 신용카드소득공제 총합

        double taxBase = memberIncmInfo.getTotalIncmAmt() - (sumPension + sumCard); // 과세표준 = 종합소득금액 - 소득공제(국민연금 총합 + 신용카드소득공제 총합)

        double tax; // 산출세액
        if (taxBase <= 14000000) {
            tax = taxBase * 0.06;
        } else if (taxBase <= 50000000) {
            tax = 840000 + (taxBase - 14000000) * 0.15;
        } else if (taxBase <= 88000000) {
            tax = 6240000 + (taxBase - 50000000) * 0.24;
        } else if (taxBase <= 150000000) {
            tax = 15360000 + (taxBase - 88000000) * 0.35;
        } else if (taxBase <= 300000000) {
            tax = 37060000 + (taxBase - 150000000) * 0.38;
        } else if (taxBase <= 500000000) {
            tax = 94060000 + (taxBase - 300000000) * 0.4;
        } else {
            tax = 174060000 + (taxBase - 500000000) * 0.42;
        }

        double resultTaxAmount = Math.round(tax) - memberIncmInfo.getTaxDeduction(); // 결정세액 = 산출세액 - 세액공제

        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return new IncomeInfoRefundResponse(decimalFormat.format(resultTaxAmount));
    }
}
