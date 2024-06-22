package com.task.taskproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @Column(nullable = false)
    private String userId; // 사용자 아이디

    @Column(nullable = false)
    private String password; // 사용자 비밀번호

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Column(nullable = false)
    private String regNo; // 사용자 주민등록번호
}
