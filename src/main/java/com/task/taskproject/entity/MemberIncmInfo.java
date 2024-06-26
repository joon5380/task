package com.task.taskproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author : 최대준
 * @ClassName : MemberIncmInfo
 * @Description : Entity MEMBERINCMINFO 사용자 소득 및 공제정보 테이블
 * @Since : 2024. 06. 26.
 */
@NoArgsConstructor
@Entity
@Getter
@Table(name = "memberincminfo")
public class MemberIncmInfo {
    @Id
    @Column(nullable = false, scale = 20, unique = true)
    private String userId; // 아이디

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private double totalIncmAmt; // 종합소득금액

    @Column(nullable = false)
    private double taxDeduction; // 세액공제

    @Builder
    private MemberIncmInfo(String userId, String name, double totalIncmAmt, double taxDeduction) {
        this.userId = userId;
        this.name = name;
        this.totalIncmAmt = totalIncmAmt;
        this.taxDeduction = taxDeduction;
    }
}
