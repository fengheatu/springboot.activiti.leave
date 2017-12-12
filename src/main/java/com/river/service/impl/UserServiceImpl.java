package com.river.service.impl;

import com.river.dao.mapper.UserMapper;
import com.river.model.po.User;
import com.river.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: he.feng
 * @date: 10:21 2017/12/8
 * @desc:
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;
    /**
     * query by userId
     *
     * @param id
     */
    @Override
    public User queryUserByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * @param mobile
     * @return
     */
    @Override
    public User queryUserByMobile(String mobile) {
        return userMapper.queryUserByMobile(mobile);
    }
}
