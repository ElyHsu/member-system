package com.starcharger.membersystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 登入下行
 */
@Data
@Builder
public class LoginResp {

    /**
     * 信箱
     */
    private String email;

    /**
     * 帳號狀態
     */
    private String status;

    /**
     * 回應訊息
     */
    private String message;

    /**
     * Demo 用，正式環境不會回傳
     */
    private String otp;
}
