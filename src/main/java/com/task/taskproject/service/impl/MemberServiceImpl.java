package com.task.taskproject.service.impl;

import com.task.taskproject.dto.member.request.MemberUpdateRequest;
import com.task.taskproject.dto.member.response.MemberDeleteResponse;
import com.task.taskproject.dto.member.response.MemberInfoResponse;
import com.task.taskproject.dto.member.response.MemberUpdateResponse;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.service.MemberService;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author : 최대준
 * @ClassName : MemberServiceImpl
 * @Description : 회원용 API 구현체
 * @Since : 2024. 06. 24.
 */
@Service("MemberService")
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional(readOnly = true)
    @Override
    public MemberInfoResponse getMemberInfo(String userId) {
        return memberRepository.findByUserId(userId)
                .map(MemberInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    @Transactional
    @Override
    public MemberDeleteResponse deleteMember(String userId) {
        if (!memberRepository.existsById(userId)) return new MemberDeleteResponse(false);
        memberRepository.deleteById(userId);
        return new MemberDeleteResponse(true);
    }

    @Transactional
    @Override
    public MemberUpdateResponse updateMember(String userId, MemberUpdateRequest request) {
        return memberRepository.findById(userId)
                .filter(member -> encoder.matches(request.password(), member.getPassword()))
                .map(member -> {
                    member.update(request, encoder);
                    return MemberUpdateResponse.of(true, member);
                })
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }
}
