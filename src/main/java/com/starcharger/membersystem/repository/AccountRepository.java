package com.starcharger.membersystem.repository;

import com.starcharger.membersystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 會員帳號 Repository
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * 依 Email 查詢會員帳號
     */
    Optional<Account> findByEmail(String email);

    /**
     * 檢查 Email 是否已存在
     */
    boolean existsByEmail(String email);
}
