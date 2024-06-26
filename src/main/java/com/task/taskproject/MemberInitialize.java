package com.task.taskproject;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.security.TokenProvider;
import com.task.taskproject.utils.CipherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;

/**
 * @Author : 최대준
 * @ClassName : MemberInitialize
 * @Description : 임시 관리자 최초 입력
 * @Since : 2024. 06. 24.
 */
@RequiredArgsConstructor
@Component
public class MemberInitialize implements ApplicationRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public void run(ApplicationArguments args) {
        CipherUtil util = new CipherUtil(tokenProvider.getEncryptionKey());
        try {
            memberRepository.save(Member.builder()
                    .userId("admin")
                    .password(encoder.encode("joon5380"))
                    .name("최대준")
                    .regNo(util.encryptString("990424-1000000"))
                    .type(MemberType.ADMIN) // 관리자 추가 기능은 별도로 없어 최초 입력 필요
                    .build()
            );
        } catch (InvalidKeyException e) {
            throw new NoSuchElementException("암호화 작업 중 문제가 발생했습니다.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new NoSuchElementException("암호화 작업 중 문제가 발생했습니다.");
        }
    }
}
