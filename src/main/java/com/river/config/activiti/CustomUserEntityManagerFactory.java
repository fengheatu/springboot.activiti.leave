package com.river.config.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: he.feng
 * @date: 11:38 2017/12/8
 * @desc:
 **/
@Service
public class CustomUserEntityManagerFactory implements SessionFactory {

    private CustomUserEntityManager customUserEntityManager;

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return  customUserEntityManager;
    }


    @Autowired
    public void setCustomUserEntityManager(CustomUserEntityManager customUserEntityManager) {
        this.customUserEntityManager = customUserEntityManager;
    }
}
