package com.task.taskproject.entity;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.dto.member.request.MemberUpdateRequest;
import com.task.taskproject.dto.signUp.request.SignUpRequest;
import com.task.taskproject.utils.CipherUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * @Author : 최대준
 * @ClassName : Member
 * @Description : Entity MEMBER 회원 테이블
 * @Since : 2024. 06. 25.
 */
@PropertySource("classpath:jwt.yml")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @Column(nullable = false, scale = 20, unique = true)
    private String userId; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String regNo; // 주민등록번호

    @Enumerated(EnumType.STRING) // Enum 클래스 값 그대로 DB 저장
    private MemberType type; // 신분구분 (Admin, User)

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static Member from(SignUpRequest request, PasswordEncoder encoder, String encryptionKey) {
        CipherUtil util = new CipherUtil(encryptionKey);
        try {
            return Member.builder()
                    .userId(request.userId())
                    .password(encoder.encode(request.password())) // 단방향 암호화
                    .name(request.name())
                    .regNo(util.encryptString(request.regNo())) // 양방향 암호화
                    .type(MemberType.USER)
                    .build();
        } catch (InvalidKeyException e) {
            throw new NoSuchElementException("암호화 작업 중 문제가 발생했습니다.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new NoSuchElementException("암호화 작업 중 문제가 발생했습니다.");
        }
    }

    @Builder
    private Member(String userId, String password, String name, String regNo, MemberType type) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
        this.type = type;
    }

    public void update(MemberUpdateRequest newMember, PasswordEncoder encoder) {
        this.password = newMember.newPassword() == null || newMember.newPassword().isBlank() ? this.password : encoder.encode(newMember.newPassword());
        this.name = newMember.name();
        this.regNo = newMember.regNo();
    }
}
