package com.task.taskproject.service.impl;

import com.task.taskproject.dto.signIn.request.SignInRequest;
import com.task.taskproject.dto.signIn.response.SignInResponse;
import com.task.taskproject.dto.signUp.request.SignUpRequest;
import com.task.taskproject.dto.signUp.response.SignUpResponse;
import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.security.TokenProvider;
import com.task.taskproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : 최대준
 * @ClassName : AuthServiceImpl
 * @Description : 회원가입 및 로그인 API 구현체
 * @Since : 2024. 06. 24.
 */
@Service("AuthService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    @Override
    public SignUpResponse register(SignUpRequest request) {
        // 회원가입 시 사용자 검증 추가
        List<Map<String, String>> dataList = new ArrayList<>();

        // 데이터 추가
        Map<String, String> person1 = new HashMap<>();
        person1.put("이름", "동탁");
        person1.put("주민등록번호", "921108-1582816");
        dataList.add(person1);

        Map<String, String> person2 = new HashMap<>();
        person2.put("이름", "관우");
        person2.put("주민등록번호", "681108-1582816");
        dataList.add(person2);

        Map<String, String> person3 = new HashMap<>();
        person3.put("이름", "손권");
        person3.put("주민등록번호", "890601-2455116");
        dataList.add(person3);

        Map<String, String> person4 = new HashMap<>();
        person4.put("이름", "유비");
        person4.put("주민등록번호", "790411-1656116");
        dataList.add(person4);

        Map<String, String> person5 = new HashMap<>();
        person5.put("이름", "조조");
        person5.put("주민등록번호", "810326-2715702");
        dataList.add(person5);

        // 입력받은 이름과 주민등록번호를 기준으로 데이터 리스트에서 검색
        boolean found = false;
        for (Map<String, String> person : dataList) {
            String name = person.get("이름");
            String jumin = person.get("주민등록번호");

            if (request.name().equals(name) && request.regNo().equals(jumin)) {
                found = true;
                break;
            }
        }

        if (!found) throw new IllegalArgumentException("가입 가능한 회원이 아닙니다.");
        if (memberRepository.findByUserId(request.userId()).isPresent()) throw new IllegalArgumentException("이미 사용중인 아이디입니다.");

        Member member = memberRepository.save(Member.from(request, encoder, tokenProvider.getEncryptionKey()));
        try {
            memberRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        return SignUpResponse.from(member);
    }

    @Transactional(readOnly = true)
    @Override
    public SignInResponse login(SignInRequest request) {
        Member member = memberRepository.findByUserId(request.userId())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        String token = tokenProvider.createToken(String.format("%s:%s", member.getUserId(), member.getType()));
        return new SignInResponse(member.getName(), member.getType(), token);
    }
}
