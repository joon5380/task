package com.task.taskproject.service.impl;

import com.task.taskproject.entity.Member;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.service.SzsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("SzsService")
public class SzsServiceImpl implements SzsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public String test(Map<String, Object> content) {
        // 주민등록번호 앞,뒤자리 분리하여 뒷자리만 암호화 준비?
        Member me = new Member();
        me.setUserId((String) content.get("userId"));
        me.setPassword((String) content.get("password"));
        me.setName((String) content.get("name"));
        me.setRegNo((String) content.get("regNo"));
        memberRepository.save(me);
        return null;
    }
}
