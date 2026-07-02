package com.starcharger.membersystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 重發 token 上行
 */
@Data
public class ResendTokenReq {

    /** Email，登入帳號 */
    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式錯誤")
    private String email;
}
