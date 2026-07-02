package com.starcharger.membersystem.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 帳號開通下行
 */
@Data
@Builder
public class ActivateResp {

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
     * 回應訊息
     */
    private String message;

}
