package com.starcharger.membersystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 會員資料
 */
@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     * 會員帳號
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    /**
     * Email，作為登入帳號
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * BCrypt 密碼
     */
    @Column(nullable = false)
    private String password;

    /**
     * 使用者名稱
     */
    @Column(nullable = false, length = 50)
    private String userName;

    /**
     * 帳號狀態：PENDING / ACTIVE / LOCKED
     */
    @Column(nullable = false, length = 20)
    private String status;

    /**
     * 最後登入時間
     */
    private LocalDateTime lastLoginTime;

    /**
     * 建立時間
     */
    @Column(nullable = false)
    private LocalDateTime createdTime;

    /**
     * 更新時間
     */
    @Column(nullable = false)
    private LocalDateTime updatedTime;
}
