package com.starcharger.membersystem.exception;

import com.starcharger.membersystem.dto.CommonResp;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全域例外處理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 處理驗證錯誤
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResp<Void>> handleValidationException(MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + "：" + error.getDefaultMessage())
                .orElse("上行電文格式錯誤");

        return ResponseEntity.ok(CommonResp.fail("E001", errorMessage));
    }

    /**
     * 處理業務邏輯錯誤(不同業務邏輯錯誤給予不同的code方便前端辨識訊息，此為範例)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResp<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("系統發生 IllegalArgumentException:{}", e.getMessage());
        return ResponseEntity.ok(CommonResp.fail("E002", e.getMessage()));
    }

    /**
     * 未帶入JWT Token
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CommonResp<Object>> handleMissingRequestHeaderException(
            MissingRequestHeaderException e) {
        log.error("未帶入JWT Token", e);
        return ResponseEntity.ok(
                CommonResp.fail("E003", "請提供 JWT Token"));
    }

    /**
     * JWT Token 無效或已過期
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CommonResp<Object>> handleJwtException(JwtException e) {
        log.error("JWT Token 無效或已過期", e);
        return ResponseEntity.ok(
                CommonResp.fail("E003", "JWT Token 無效或已過期")
        );
    }

    /**
     * 處理其他未預期錯誤
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResp<Void>> handleException(Exception e) {
        log.error("系統發生未預期錯誤:", e);
        return ResponseEntity.ok(CommonResp.fail("9999", "系統忙碌中，請稍後再試"));
    }
}
