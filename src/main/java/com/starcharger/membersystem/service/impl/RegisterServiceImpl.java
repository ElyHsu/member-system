package com.starcharger.membersystem.service.impl;

import com.starcharger.membersystem.common.AccountStatus;
import com.starcharger.membersystem.dto.RegisterReq;
import com.starcharger.membersystem.dto.RegisterResp;
import com.starcharger.membersystem.entity.Account;
import com.starcharger.membersystem.repository.AccountRepository;
import com.starcharger.membersystem.service.MailService;
import com.starcharger.membersystem.service.RegisterService;
import com.starcharger.membersystem.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 註冊
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MailService mailService;

    public RegisterResp register(RegisterReq registerReq) {
        if (accountRepository.existsByEmail(registerReq.getEmail())) {
            return RegisterResp.builder()
                    .success(false)
                    .message("Email 已被註冊")
                    .build();
        }

        LocalDateTime now = LocalDateTime.now();

        Account account = Account.builder()
                .email(registerReq.getEmail())
                .password(passwordEncoder.encode(registerReq.getPassword()))
                .userName(registerReq.getName())
                .status(AccountStatus.PENDING)
                .createdTime(now)
                .updatedTime(now)
                .build();

        accountRepository.save(account);

        //生成token
        String newToken = tokenService.createActivationToken(account.getAccountId());
        mailService.sendTokenMail(
                account.getEmail(),
                account.getUserName(),
                newToken
        );

        return RegisterResp.builder()
                .success(true)
                .message("註冊成功，請至 Email 收取OTP開通帳戶")
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .status(account.getStatus())
                .activationToken(newToken)
                .build();

    }
}
