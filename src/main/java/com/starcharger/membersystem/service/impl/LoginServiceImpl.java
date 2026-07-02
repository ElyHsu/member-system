package com.starcharger.membersystem.service.impl;

import com.starcharger.membersystem.common.AccountStatus;
import com.starcharger.membersystem.dto.LastLoginResp;
import com.starcharger.membersystem.dto.LoginReq;
import com.starcharger.membersystem.dto.LoginResp;
import com.starcharger.membersystem.entity.Account;
import com.starcharger.membersystem.repository.AccountRepository;
import com.starcharger.membersystem.service.JwtService;
import com.starcharger.membersystem.service.LoginService;
import com.starcharger.membersystem.service.MailService;
import com.starcharger.membersystem.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 登入服務
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MailService mailService;
    private final JwtService jwtService;

    @Override
    public LoginResp login(LoginReq loginReq) {

        Account account = accountRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("帳號或密碼錯誤"));

        if (!passwordEncoder.matches(loginReq.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("帳號或密碼錯誤");
        }

        String status = account.getStatus();
        if (!AccountStatus.ACTIVE.equals(status)) {
            throw new IllegalArgumentException("帳號尚未開通或無法登入");
        }

        String newOtp = tokenService.createLoginOtp(account.getAccountId());

        String email = account.getEmail();
        mailService.sendTokenMail(email, account.getUserName(), newOtp);

        return LoginResp.builder()
                .email(email)
                .status(status)
                .message("帳密驗證成功，已寄送 OTP 至 Email")
                .otp(newOtp)
                .build();
    }

    @Override
    public LastLoginResp queryLastLogin(String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("JWT 無效");
        }

        String jwtToken = authorization.substring(7);

        Long accountId = jwtService.getAccountId(jwtToken);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("會員帳號不存在"));

        return LastLoginResp.builder()
                .email(account.getEmail())
                .lastLoginTime(account.getLastLoginTime())
                .message("查詢成功")
                .build();
    }
}