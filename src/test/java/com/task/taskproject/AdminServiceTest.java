package com.task.taskproject;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.dto.member.response.MemberInfoResponse;
import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.service.AdminService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminServiceTest {
    @Resource(name = "AdminService")
    private AdminService adminService;

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
    void findAllMember() {
        // given
        memberRepository.save(Member.builder()
                .userId("user123")
                .password(encoder.encode("1234"))
                .name("최대준")
                .regNo("990424-1000000")
                .type(MemberType.USER)
                .build()
        );
        memberRepository.save(Member.builder()
                .userId("user456")
                .password(encoder.encode("1234"))
                .name("홍길동")
                .regNo("990424-2000000")
                .type(MemberType.USER)
                .build()
        );
        memberRepository.save(Member.builder()
                .userId("user789")
                .password(encoder.encode("1234"))
                .name("삼쩜삼")
                .regNo("970111-1000000")
                .type(MemberType.USER)
                .build()
        );
        // when
        List<MemberInfoResponse> memberList = adminService.getUserList();
        // then
        assertThat(memberList).hasSize(3);
        assertThat(memberList.get(0).userId()).isEqualTo("user123");
        assertThat(memberList.get(0).name()).isEqualTo("최대준");
        assertThat(memberList.get(0).regNo()).isEqualTo("990424-1000000");
        assertThat(memberList.get(0).type()).isEqualTo(MemberType.USER);

        assertThat(memberList.get(1).userId()).isEqualTo("user456");
        assertThat(memberList.get(1).name()).isEqualTo("홍길동");
        assertThat(memberList.get(1).regNo()).isEqualTo("990424-2000000");
        assertThat(memberList.get(1).type()).isEqualTo(MemberType.USER);

        assertThat(memberList.get(2).userId()).isEqualTo("user789");
        assertThat(memberList.get(2).name()).isEqualTo("삼쩜삼");
        assertThat(memberList.get(2).regNo()).isEqualTo("970111-1000000");
        assertThat(memberList.get(2).type()).isEqualTo(MemberType.USER);
    }

    @Test
    void findAllAdmin() {
        // given
        memberRepository.save(Member.builder()
                .userId("user123")
                .password(encoder.encode("1234"))
                .name("최대준")
                .regNo("990424-1000000")
                .type(MemberType.ADMIN)
                .build()
        );
        memberRepository.save(Member.builder()
                .userId("user456")
                .password(encoder.encode("1234"))
                .name("홍길동")
                .regNo("990424-2000000")
                .type(MemberType.ADMIN)
                .build()
        );
        memberRepository.save(Member.builder()
                .userId("user789")
                .password(encoder.encode("1234"))
                .name("삼쩜삼")
                .regNo("970111-1000000")
                .type(MemberType.ADMIN)
                .build()
        );
        // when
        List<MemberInfoResponse> memberList = adminService.getAdminList();
        // then
        assertThat(memberList).hasSize(3);
        assertThat(memberList.get(0).userId()).isEqualTo("user123");
        assertThat(memberList.get(0).name()).isEqualTo("최대준");
        assertThat(memberList.get(0).regNo()).isEqualTo("990424-1000000");
        assertThat(memberList.get(0).type()).isEqualTo(MemberType.ADMIN);

        assertThat(memberList.get(1).userId()).isEqualTo("user456");
        assertThat(memberList.get(1).name()).isEqualTo("홍길동");
        assertThat(memberList.get(1).regNo()).isEqualTo("990424-2000000");
        assertThat(memberList.get(1).type()).isEqualTo(MemberType.ADMIN);

        assertThat(memberList.get(2).userId()).isEqualTo("user789");
        assertThat(memberList.get(2).name()).isEqualTo("삼쩜삼");
        assertThat(memberList.get(2).regNo()).isEqualTo("970111-1000000");
        assertThat(memberList.get(2).type()).isEqualTo(MemberType.ADMIN);
    }
}
