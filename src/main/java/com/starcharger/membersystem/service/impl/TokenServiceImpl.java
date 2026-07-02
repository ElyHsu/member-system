package com.starcharger.membersystem.service.impl;

import com.starcharger.membersystem.common.AccountStatus;
import com.starcharger.membersystem.dto.*;
import com.starcharger.membersystem.entity.Account;
import com.starcharger.membersystem.entity.EmailToken;
import com.starcharger.membersystem.repository.AccountRepository;
import com.starcharger.membersystem.repository.EmailTokenRepository;
import com.starcharger.membersystem.service.JwtService;
import com.starcharger.membersystem.service.MailService;
import com.starcharger.membersystem.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import java.security.SecureRandom;

/**
 * Token 管理服務
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final EmailTokenRepository emailTokenRepository;
    private final AccountRepository accountRepository;
    private final MailService mailService;
    private final JwtService jwtService;

    @Override
    public String createActivationToken(Long accountId) {
        emailTokenRepository.deleteByAccountId(accountId);

        LocalDateTime now = LocalDateTime.now();
        // 36 字元識別碼
        String token = UUID.randomUUID().toString();

        EmailToken emailToken = EmailToken.builder()
                .accountId(accountId)
                .token(token)
                .createdTime(now)
                .expiredTime(now.plusMinutes(30))
                .usedTime(null)
                .build();

        emailTokenRepository.save(emailToken);
        return token;
    }

    @Override
    public ActivateResp verifyToken(ActivateReq req) {
        EmailToken emailToken = validateToken(req.getToken(), "Token 驗證失敗");

        Account account = accountRepository.findById(emailToken.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("會員不存在"));

        if (AccountStatus.ACTIVE.equals(account.getStatus())) {
            throw new IllegalArgumentException("帳號已開通");
        }

        LocalDateTime now = LocalDateTime.now();

        account.setStatus(AccountStatus.ACTIVE);
        account.setUpdatedTime(now);
        accountRepository.save(account);

        usedToken(emailToken, now);

        return ActivateResp.builder()
                .email(account.getEmail())
                .status(account.getStatus())
                .message("帳號開通成功")
                .build();
    }

    @Override
    public ResendTokenResp resendToken(ResendTokenReq resendTokenReq) {
        Account account = accountRepository.findByEmail(resendTokenReq.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("會員帳號不存在"));

        String status = account.getStatus();
        if (AccountStatus.ACTIVE.equals(status)) {
            return ResendTokenResp.builder()
                    .email(account.getEmail())
                    .status(status)
                    .message("帳號已開通，不需重新寄送 Token")
                    .build();
        }

        if (AccountStatus.LOCKED.equals(status)) {
            throw new IllegalArgumentException("帳號已鎖定，請聯絡客服");
        }

        if (!AccountStatus.PENDING.equals(status)) {
            throw new IllegalArgumentException("帳號狀態異常");
        }

        String newToken = createActivationToken(account.getAccountId());

        mailService.sendTokenMail(account.getEmail(), account.getUserName(), newToken);

        return ResendTokenResp.builder()
                .email(account.getEmail())
                .status(status)
                .message("Token 已重新寄送，請至 Email 收取開通信")
                .activationToken(newToken)
                .build();
    }

    @Override
    public String createLoginOtp(Long accountId) {
        emailTokenRepository.deleteByAccountId(accountId);

        LocalDateTime now = LocalDateTime.now();
        //6位數純數字
        String otp = String.format("%06d", SECURE_RANDOM.nextInt(1_000_000));

        EmailToken emailToken = EmailToken.builder()
                .accountId(accountId)
                .token(otp)
                .createdTime(now)
                .expiredTime(now.plusMinutes(5))
                .usedTime(null)
                .build();

        emailTokenRepository.save(emailToken);
        return otp;
    }

    @Override
    public VerifyOtpResp verifyLoginOtp(VerifyOtpReq req) {
        Account account = accountRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("OTP 驗證失敗"));

        if (!AccountStatus.ACTIVE.equals(account.getStatus())) {
            throw new IllegalArgumentException("帳號尚未開通或無法登入");
        }

        EmailToken emailToken = validateToken(req.getOtp(), "OTP 驗證失敗");

        if (!account.getAccountId().equals(emailToken.getAccountId())) {
            throw new IllegalArgumentException("OTP 驗證失敗");
        }

        LocalDateTime now = LocalDateTime.now();

        account.setLastLoginTime(now);
        account.setUpdatedTime(now);
        accountRepository.save(account);

        usedToken(emailToken, now);
        String jwtToken = jwtService.generateToken(
                account.getAccountId(),
                account.getEmail()
        );

        return VerifyOtpResp.builder()
                .email(account.getEmail())
                .jwtToken(jwtToken)
                .message("登入成功")
                .build();
    }

    /**
     * 檢查是否為有效 token
     */
    private EmailToken validateToken(String token, String errorMessage) {
        EmailToken emailToken = emailTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));

        if (emailToken.getUsedTime() != null) {
            throw new IllegalArgumentException(errorMessage);
        }

        if (LocalDateTime.now().isAfter(emailToken.getExpiredTime())) {
            throw new IllegalArgumentException(errorMessage);
        }

        return emailToken;
    }

    /**
     * 標記 token 為已使用
     */
    private void usedToken(EmailToken emailToken, LocalDateTime now) {
        emailToken.setUsedTime(now);
        emailTokenRepository.save(emailToken);
    }
}