package com.task.taskproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "memberincminfo")
public class MemberIncmInfo {
    @Id
    @Column(nullable = false)
    private String userId; // 사용자 아이디

    @Column(nullable = false)
    private String totalIncmAmt; // 종합소득금액

    @Column(nullable = false)
    private String a; // 소득공제: 국민연금
}
