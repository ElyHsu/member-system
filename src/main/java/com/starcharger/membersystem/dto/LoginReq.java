package com.starcharger.membersystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登入上行
 */
@Data
public class LoginReq {

    /** Email，作為登入帳號 */
    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式錯誤")
    private String email;

    /** 密碼 */
    @NotBlank(message = "密碼不可為空")
    private String password;
}
