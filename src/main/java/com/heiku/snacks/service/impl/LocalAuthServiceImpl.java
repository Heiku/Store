package com.heiku.snacks.service.impl;

import com.heiku.snacks.dao.LocalAuthDao;
import com.heiku.snacks.dto.LocalAuthExecution;
import com.heiku.snacks.entity.LocalAuth;
import com.heiku.snacks.enums.LocalAuthStateEnum;
import com.heiku.snacks.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    /**
     * 检查登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public LocalAuth checkLocalAuth(String userName, String password) {
        return localAuthDao.checkLocalAuth(userName, password);
    }


    /**
     * id查找账号信息
     *
     * @param userId
     * @return
     */
    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalAuthById(userId);
    }

    @Override
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) {
        if (localAuth == null || localAuth.getUserName() == null || localAuth.getPassword() == null){
            return new LocalAuthExecution(LocalAuthStateEnum.EMPTY.getState(), LocalAuthStateEnum.EMPTY.getStateInfo());
        }

        try {
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());

            // 用户密码 md5 加密处理

        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    @Override
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPass) {
        return null;
    }

    @Override
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) {
        return null;
    }
}
