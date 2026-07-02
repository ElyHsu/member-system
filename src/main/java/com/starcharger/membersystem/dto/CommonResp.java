package com.starcharger.membersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * API 共用回應格式
 */
@Data
@Builder
@AllArgsConstructor
public class CommonResp<T> {

    /** 回應代碼 */
    private String code;

    /** 回應訊息 */
    private String message;

    /** 回應資料 */
    private T data;

    public static <T> CommonResp<T> success(T data) {
        return CommonResp.<T>builder()
                .code("0000")
                .message("成功")
                .data(data)
                .build();
    }

    public static <T> CommonResp<T> fail(String code, String message) {
        return CommonResp.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
}