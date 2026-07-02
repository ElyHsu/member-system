package com.starcharger.membersystem.service;

import com.starcharger.membersystem.dto.LastLoginResp;
import com.starcharger.membersystem.dto.LoginReq;
import com.starcharger.membersystem.dto.LoginResp;

/**
 * 登入服務
 */
public interface LoginService {

    /**
     * 帳密登入，成功後寄送 OTP
     *
     * @param loginReq email  密碼
     * @return LoginResp
     */
    LoginResp login(LoginReq loginReq);

    /**
     * 查詢最後登入時間
     *
     * @param authorization jwt
     * @return LastLoginResp
     */
    LastLoginResp queryLastLogin(String authorization);
}
