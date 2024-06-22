package com.task.taskproject;

import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TaskProjectApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void contextLoads() {
//        memberRepository.save(Member.builder()
//                .userId("아이디")
//                .password("비밀번호")
//                .name("이름")
//                .regNo("주민등록번호")
//        );

        //when
        List<Member> postsList = memberRepository.findAll();
    }

}
