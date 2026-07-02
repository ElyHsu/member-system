package com.starcharger.membersystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * OTP 驗證下行
 */
@Data
@Builder
public class VerifyOtpResp {

    /**
     * Email
     */
    private String email;

    /**
     * 回應訊息
     */
    private String message;

    /** JWT Token */
    private String jwtToken;
}