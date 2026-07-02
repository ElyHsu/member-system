package com.starcharger.membersystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 查詢最後登入時間請求上行
 */
@Data
public class LastLoginReq {

    /** Email，作為登入帳號 */
    @NotBlank(message = "Email 不可為空")
    @Email(message = "Email 格式錯誤")
    private String email;
}
