package com.task.taskproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author : 최대준
 * @ClassName : UserIdYearMonthKey
 * @Description : 복합키 객체 (MEMBERINCMINFOCARD, MEMBERINCMINFOPENSION 테이블 활용)
 * @Since : 2024. 06. 26.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class UserIdYearMonthKey implements Serializable {
    @Column(nullable = false)
    private String userId; // 사용자 아이디

    @Column(nullable = false)
    private String deductYear; // 공제연도

    @Column(nullable = false)
    private String deductMonth; // 공제월
}
