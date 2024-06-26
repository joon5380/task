package com.task.taskproject.service;

import com.task.taskproject.dto.member.response.MemberInfoResponse;
import java.util.List;

/**
 * @Author : 최대준
 * @InterfaceName : AdminService
 * @Description : 관리자용 API 구현체 인터페이스
 * @Since : 2024. 06. 24.
 */
public interface AdminService {
    List<MemberInfoResponse> getAdminList();
    List<MemberInfoResponse> getUserList();
}
