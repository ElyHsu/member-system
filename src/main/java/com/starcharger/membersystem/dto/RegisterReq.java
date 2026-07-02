package com.starcharger.membersystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 註冊會員上行
 */
@Data
public class RegisterReq {
    /**
     * Email，登入帳號
     */
    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式錯誤")
    private String email;

    /**
     * 密碼
     */
    @NotBlank(message = "密碼不可為空")
    @Size(min = 8, message = "密碼至少需要 8 碼")
    private String password;

    /**
     * 使用者名稱
     */
    @NotBlank(message = "使用者名稱不可為空")
    @Size(min = 2, message = "使用者名稱至少需要 2 個字")
    private String name;

}
