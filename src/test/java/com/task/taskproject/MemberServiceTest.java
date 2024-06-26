package com.task.taskproject;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.dto.member.request.MemberUpdateRequest;
import com.task.taskproject.dto.member.response.MemberDeleteResponse;
import com.task.taskproject.dto.member.response.MemberInfoResponse;
import com.task.taskproject.dto.member.response.MemberUpdateResponse;
import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.service.MemberService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MemberServiceTest {
    @Resource(name = "MemberService")
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    void existMemberTest() {
        // given
        Member target = memberRepository.save(Member.builder()
                .userId("user1234")
                .password(encoder.encode("1234"))
                .name("최대준")
                .regNo("990424-1000000")
                .type(MemberType.USER)
                .build()
        );
        // when
        MemberInfoResponse response = memberService.getMemberInfo(target.getUserId());
        // then
        assertThat(response.userId()).isEqualTo(target.getUserId());
        assertThat(response.name()).isEqualTo(target.getName());
        assertThat(response.regNo()).isEqualTo(target.getRegNo());
        assertThat(response.type()).isEqualTo(target.getType());
    }

    @Test
    void notExistMemberTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> memberService.getMemberInfo("user1234"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    void deleteMemberTest() {
        // given
        Member target = memberRepository.save(Member.builder()
                .userId("user1234")
                .password(encoder.encode("1234"))
                .name("최대준")
                .regNo("990424-1000000")
                .type(MemberType.USER)
                .build()
        );
        // when
        MemberDeleteResponse response = memberService.deleteMember(target.getUserId());
        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).isEmpty();
        assertThat(response.result()).isEqualTo(true);
    }

    @Test
    void updateMemberTest() {
        // given
        Member target = memberRepository.save(Member.builder()
                .userId("user1234")
                .password(encoder.encode("1234"))
                .name("최대준")
                .regNo("990424-1000000")
                .type(MemberType.USER)
                .build()
        );
        // when
        MemberUpdateRequest request = new MemberUpdateRequest("1234", "4321", "홍길동", "990424-2000000");
        MemberUpdateResponse response = memberService.updateMember(target.getUserId(), request);
        // then
        assertThat(response.result()).isEqualTo(true);
        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.regNo()).isEqualTo("990424-2000000");

        Member savedTarget = memberRepository.findAll().get(0);
        assertThat(encoder.matches("4321", savedTarget.getPassword())).isEqualTo(true);
    }
}
