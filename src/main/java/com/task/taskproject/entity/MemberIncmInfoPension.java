package com.task.taskproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author : 최대준
 * @ClassName : MemberIncmInfoPension
 * @Description : Entity MEMBERINCMINFOPENSION 국민연금소득공제 테이블
 * @Since : 2024. 06. 26.
 */
@NoArgsConstructor
@Entity
@Getter
@Table(name = "memberincminfopension")
public class MemberIncmInfoPension {
    @EmbeddedId
    private UserIdYearMonthKey complexKey; // 복합키

    @Column(nullable = false)
    private double deductible; // 공제액

    @Builder
    private MemberIncmInfoPension(String userId, String deductYear, String deductMonth, double deductible) {
        this.complexKey = new UserIdYearMonthKey(userId, deductYear, deductMonth);
        this.deductible = deductible;
    }
}
