package com.starcharger.membersystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 註冊會員下行
 */
@Data
@Builder
public class RegisterResp {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 回應訊息
     */
    private String message;

    /**
     * 會員帳號
     */
    private Long accountId;

    /**
     * 信箱
     */
    private String email;

    /**
     * 帳號狀態
     */
    private String status;

    /**
     * Demo 用，正式環境不會回傳
     */
    private String activationToken;
}
