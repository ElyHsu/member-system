package com.starcharger.membersystem.repository;

import com.starcharger.membersystem.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Email 驗證碼資料 Repository
 */
@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

    /**
     * 依 token 查詢 token 資料
     * @param token token
     *
     * @return Optional<EmailToken> 或 Optional.empty()
     */
    Optional<EmailToken> findByToken(String token);

    /**
     * 避免一個帳號有很多開通信的 token
     *
     * @param accountId 會員帳號
     * @return 刪除筆數
     */
    void deleteByAccountId(Long accountId);
}
