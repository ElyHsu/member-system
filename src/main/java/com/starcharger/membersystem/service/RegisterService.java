package com.starcharger.membersystem.service;

import com.starcharger.membersystem.dto.RegisterReq;
import com.starcharger.membersystem.dto.RegisterResp;

/**
 * 註冊
 */
public interface RegisterService {
    /**
     * 會員註冊
     *
     * @param registerReq 註冊上行
     * @return RegisterResp
     */
     RegisterResp register(RegisterReq registerReq);
}
