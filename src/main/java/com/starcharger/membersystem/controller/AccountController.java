package com.starcharger.membersystem.controller;

import com.starcharger.membersystem.dto.*;
import com.starcharger.membersystem.service.LoginService;
import com.starcharger.membersystem.service.TokenService;
import com.starcharger.membersystem.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final RegisterService registerService;
    private final TokenService tokenService;
    private final LoginService loginService;

    /**
     * 會員註冊
     */
    @Operation(summary = "會員註冊")
    @PostMapping("/register")
    public ResponseEntity<CommonResp<RegisterResp>> register(@Valid @RequestBody RegisterReq registerReq) {
        return ResponseEntity.ok(CommonResp.success(registerService.register(registerReq)));
    }

    /**
     * 帳號開通
     */
    @Operation(summary = "帳號開通")
    @PostMapping("/activate")
    public ResponseEntity<CommonResp<ActivateResp>> activate(@Valid @RequestBody ActivateReq activateReq) {
        return ResponseEntity.ok(CommonResp.success(tokenService.verifyToken(activateReq)));
    }

    /**
     * 重新寄送 Token
     */
    @Operation(summary = "重新寄送 Token")
    @PostMapping("/resend-token")
    public ResponseEntity<CommonResp<ResendTokenResp>> resendToken(
            @Valid @RequestBody ResendTokenReq req) {
        return ResponseEntity.ok(CommonResp.success(tokenService.resendToken(req)));
    }

    /**
     * 登入
     */
    @Operation(summary = "登入")
    @PostMapping("/login")
    public ResponseEntity<CommonResp<LoginResp>> login(@Valid @RequestBody LoginReq req) {
        LoginResp resp = loginService.login(req);
        return ResponseEntity.ok(CommonResp.success(resp));
    }

    /**
     * 驗證 otp
     */
    @Operation(summary = "驗證 otp")
    @PostMapping("/verify-otp")
    public ResponseEntity<CommonResp<VerifyOtpResp>> verifyLoginOtp(
            @Valid @RequestBody VerifyOtpReq verifyOtpReq) {
        return ResponseEntity.ok(CommonResp.success(tokenService.verifyLoginOtp(verifyOtpReq)));
    }

    /**
     * 查詢最後登入時間
     */
    @Operation(summary = "查詢最後登入時間")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/last-login")
    public ResponseEntity<CommonResp<LastLoginResp>> queryLastLogin(
            @Parameter(hidden = true)
            @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(CommonResp.success(loginService.queryLastLogin(authorization)));
    }
}
