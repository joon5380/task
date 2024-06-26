package com.task.taskproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author : 최대준
 * @ClassName : MemberIncmInfoCard
 * @Description : Entity MEMBERINCMINFOCARD 신용카드소득공제 테이블
 * @Since : 2024. 06. 26.
 */
@NoArgsConstructor
@Entity
@Getter
@Table(name = "memberincminfocard")
public class MemberIncmInfoCard {
    @EmbeddedId
    private UserIdYearMonthKey complexKey; // 복합키

    @Column(nullable = false)
    private double deductible; // 공제액

    @Builder
    private MemberIncmInfoCard(String userId, String deductYear, String deductMonth, double deductible) {
        this.complexKey = new UserIdYearMonthKey(userId, deductYear, deductMonth);
        this.deductible = deductible;
    }
}
