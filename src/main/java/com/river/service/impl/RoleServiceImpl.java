package com.river.service.impl;

import com.river.dao.mapper.RoleMapper;
import com.river.model.po.Role;
import com.river.service.RoleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: he.feng
 * @date: 10:20 2017/12/8
 * @desc:
 **/
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleMapper roleMapper;

    /**
     * 根据用户id查询用户角色信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Role> queryRoleByUserId(Long id) {
        return roleMapper.queryRoleByUserId(id);
    }

    /**
     * 根据用户id获取角色名称
     *
     * @param id
     * @return
     */
    @Override
    public List<String> queryRoleEnNameByUserId(Long id) {
        return roleMapper.queryRoleEnNameByUserId(id);
    }
}
