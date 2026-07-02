package com.starcharger.membersystem.service;

/**
 * JWT
 */
public interface JwtService {

    /**
     * 產生 JWT
     *
     * @param accountId 會員ID
     * @param email 信箱
     * @return LoginResp
     */
    String generateToken(Long accountId, String email);

    /**
     * 從 JWT 取得帳號 ID
     *
     * @param jwtToken  token
     * @return 會員ID
     */
    Long getAccountId(String jwtToken);
}
