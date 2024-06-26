package com.task.taskproject.repository;

import com.task.taskproject.common.MemberType;
import com.task.taskproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : 최대준
 * @InterfaceName : MemberRepository
 * @Description : Member DB 데이터 조작 메서드 처리
 * @Since : 2024. 06. 24.
 */
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserId(String userId);
    List<Member> findAllByType(MemberType type);
}
