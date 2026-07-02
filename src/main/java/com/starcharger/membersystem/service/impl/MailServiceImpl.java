package com.starcharger.membersystem.service.impl;

import com.starcharger.membersystem.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 信箱
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendTokenMail(String email, String userName, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@starcharger-demo.com");
            message.setTo(email);
            message.setSubject("會員系統驗證信");

            StringBuffer sb = new StringBuffer();
            sb.append("您好 ").append(userName).append("，\n\n")
                    .append("歡迎試用我們的會員服務 \n")
                    .append("您的驗證碼為：\n")
                    .append(token).append("\n\n")
                    .append("請回到系統完成驗證。");
            message.setText(sb.toString());

            mailSender.send(message);
            log.info("Email 已送出至 {}", email);

        } catch (Exception e) {
            log.error("Email 寄送失敗", e);
            throw new IllegalStateException("Email 寄送失敗");
        }
    }
}