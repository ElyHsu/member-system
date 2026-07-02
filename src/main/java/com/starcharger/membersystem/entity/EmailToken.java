package com.starcharger.membersystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 驗證碼資料
 */
@Entity
@Table(name = "email_tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailToken {
    /**
     * 系統流水號
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 會員帳號
     */
    @Column(nullable = false)
    private Long accountId;

    /**
     * 開通Token或OTP
     */
    @Column(nullable = false, unique = true, length = 100)
    private String token;

    /**
     * 到期時間
     */
    @Column(nullable = false)
    private LocalDateTime expiredTime;

    /**
     * 使用時間，null 表示尚未使用
     */
    private LocalDateTime usedTime;

    /**
     * 建立時間
     */
    @Column(nullable = false)
    private LocalDateTime createdTime;
}
