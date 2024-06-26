package com.task.taskproject.repository;

import com.task.taskproject.entity.MemberIncmInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author : 최대준
 * @InterfaceName : MemberIncmInfoRepository
 * @Description : MEMBERINCMINFO DB 데이터 조작 메서드 처리
 * @Since : 2024. 06. 25.
 */
public interface MemberIncmInfoRepository extends JpaRepository<MemberIncmInfo, String> {
    MemberIncmInfo findByUserId(String userId);
}
