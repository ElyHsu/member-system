package com.starcharger.membersystem.service;

/**
 * 寄信
 */
public interface MailService {

    /**
     * 寄送開通otp信
     *
     * @param email    信箱
     * @param userName 使用者名稱
     * @param token    token
     */
    void sendTokenMail(String email, String userName, String token);

}
