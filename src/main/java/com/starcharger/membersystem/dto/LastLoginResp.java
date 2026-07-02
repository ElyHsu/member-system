package com.starcharger.membersystem.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查詢最後登入時間下行
 */
@Data
@Builder
public class LastLoginResp {

    /**
     * Email
     */
    private String email;

    /**
     * 最後登入時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    /**
     * 回應訊息
     */
    private String message;
}
