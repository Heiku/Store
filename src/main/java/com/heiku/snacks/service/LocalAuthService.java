package com.heiku.snacks.service;

import com.heiku.snacks.dto.LocalAuthExecution;
import com.heiku.snacks.entity.LocalAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService {

    /**
     * 登录检查
     *
     * @param userName
     * @param password
     * @return
     */
    LocalAuth checkLocalAuth(String userName, String password);


    /**
     * 注册
     *
     * @param profileImg
     * @return
     */
    LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg);


    /**
     * 由id获取用户信息
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);


    /**
     * 修改账号信息
     *
     * @param userId
     * @param username
     * @param password
     * @param newPass
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPass);


    /**
     * 绑定本地账号
     *
     * @param localAuth
     * @return
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth);
}
