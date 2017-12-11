package com.river.service;


import com.river.model.po.User;

/**
 * @author: he.feng
 * @date: 10:14 2017/12/8
 * @desc:
 **/
public interface UserService {

    /**
     * query by userId
     * @param aLong
     * @return
     */
    User queryUserByPrimaryKey(Long aLong);

    /**
     *
     * @param mobile
     * @return
     */
    User queryUserByMobile(String mobile);
}
