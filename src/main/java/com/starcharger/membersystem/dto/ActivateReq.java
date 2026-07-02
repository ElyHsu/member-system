package com.starcharger.membersystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 帳號開通上行
 */
@Data
public class ActivateReq {

    /** Email 開通信驗證碼 */
    @NotBlank(message = "驗證碼不可為空")
    private String token;

}
