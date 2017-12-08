package com.river.service;

import com.river.model.po.Role;

import java.util.List;

/**
 * @author: he.feng
 * @date: 10:14 2017/12/8
 * @desc:
 **/
public interface RoleService {

    /**
     * 根据用户id查询用户角色信息
     * @param id
     * @return
     */
    List<Role> queryRoleByUserId(Long id);
}
