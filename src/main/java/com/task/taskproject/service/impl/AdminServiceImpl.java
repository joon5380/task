package com.task.taskproject.service.impl;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.dto.member.response.MemberInfoResponse;
import com.task.taskproject.repository.MemberRepository;
import com.task.taskproject.service.AdminService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author : 최대준
 * @ClassName : AdminServiceImpl
 * @Description : 관리자용 API 구현체
 * @Since : 2024. 06. 24.
 */
@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MemberInfoResponse> getAdminList() {
        return memberRepository.findAllByType(MemberType.ADMIN)
                .stream()
                .map(MemberInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MemberInfoResponse> getUserList() {
        return memberRepository.findAllByType(MemberType.USER)
                .stream()
                .map(MemberInfoResponse::from)
                .toList();
    }
}
