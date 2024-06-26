package com.task.taskproject.repository;

import com.task.taskproject.entity.MemberIncmInfoCard;
import com.task.taskproject.entity.UserIdYearMonthKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author : 최대준
 * @InterfaceName : MemberIncmInfoCardRepository
 * @Description : MEMBERINCMINFOCARD DB 데이터 조작 메서드 처리
 * @Since : 2024. 06. 25.
 */
public interface MemberIncmInfoCardRepository extends JpaRepository<MemberIncmInfoCard, UserIdYearMonthKey> {
    List<MemberIncmInfoCard> findAllByComplexKeyUserId(String userId);
}
