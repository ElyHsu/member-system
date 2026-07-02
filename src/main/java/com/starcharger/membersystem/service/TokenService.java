package com.starcharger.membersystem.service;
import com.starcharger.membersystem.dto.*;

/**
 * Token 相關服務
 */
public interface TokenService {
    /**
     * 建立帳號開通 Token
     *
     * @param accountId 會員帳號
     * @return Token
     */
    String createActivationToken(Long accountId);

    /**
     * 驗證 Token 是否有效
     *
     * @param activateReq 帳號開通上行
     * @return ActivateResp
     */
    ActivateResp verifyToken(ActivateReq activateReq);

    /**
     * 重新寄送 Token
     *
     * @param resendTokenReq 重發 token 上行
     * @return ResendTokenResp
     */
    ResendTokenResp resendToken(ResendTokenReq resendTokenReq);

    /**
     * 建立登入 OTP
     *
     * @param accountId 會員帳號
     * @return otp
     */
    String createLoginOtp(Long accountId);

    /**
     * 驗證 OTP
     *
     * @param verifyOtpReq OTP 驗證上行
     * @return VerifyOtpResp
     */
    VerifyOtpResp verifyLoginOtp(VerifyOtpReq verifyOtpReq);

}
