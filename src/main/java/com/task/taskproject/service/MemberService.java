package com.task.taskproject.service;

import com.task.taskproject.dto.member.request.MemberUpdateRequest;
import com.task.taskproject.dto.member.response.MemberDeleteResponse;
import com.task.taskproject.dto.member.response.MemberInfoResponse;
import com.task.taskproject.dto.member.response.MemberUpdateResponse;

/**
 * @Author : 최대준
 * @InterfaceName : MemberService
 * @Description : 회원용 API 구현체 인터페이스
 * @Since : 2024. 06. 24.
 */
public interface MemberService {
    MemberInfoResponse getMemberInfo(String userId);
    MemberDeleteResponse deleteMember(String userId);
    MemberUpdateResponse updateMember(String userId, MemberUpdateRequest request);
}
