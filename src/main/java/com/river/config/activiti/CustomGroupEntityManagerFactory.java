package com.river.config.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: he.feng
 * @date: 13:49 2017/12/5
 * @desc:
 **/
@Service
public class CustomGroupEntityManagerFactory implements SessionFactory {

    private CustomGroupEntityManager customGroupEntityManager;

    @Override
    public Class<?> getSessionType() {
        //注意此处必须为Activiti原生的类，否则自定义类不会生效
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return customGroupEntityManager;
    }

    @Autowired
    public void setCustomGroupEntityManager(CustomGroupEntityManager customGroupEntityManager) {
        this.customGroupEntityManager = customGroupEntityManager;
    }
}
