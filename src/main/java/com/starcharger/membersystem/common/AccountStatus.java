package com.starcharger.membersystem.common;

public class AccountStatus {
    /**
     * 尚未完成 Email 開通
     */
    public static final String PENDING = "PENDING";

    /**
     * 已完成 Email 開通，可正常登入
     */
    public static final String ACTIVE = "ACTIVE";

    /**
     * 帳號鎖定
     */
    public static final String LOCKED = "LOCKED";
}
