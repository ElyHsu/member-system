package com.starcharger.membersystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * OTP 驗證上行
 */
@Data
public class VerifyOtpReq {

    /** Email，登入帳號 */
    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式錯誤")
    private String email;

    /** OTP 驗證碼 */
    @NotBlank(message = "OTP 不可為空")
    private String otp;
}
