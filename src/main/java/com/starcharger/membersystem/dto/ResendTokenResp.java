package com.starcharger.membersystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 重發 token 下行
 */
@Data
@Builder
public class ResendTokenResp {

    /** Email */
    private String email;

    /** 帳號狀態 */
    private String status;

    /** 回應訊息 */
    private String message;

    /**
     * Demo 用，正式環境不會出現
     */
    @Schema(description = "Demo 用Token，正式環境不會回傳")
    private String activationToken;
}
