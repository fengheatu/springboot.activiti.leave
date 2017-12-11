package com.river.dao.mapper;

import com.river.model.po.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 根据用户id查询用户角色信息
     *
     * @param id
     * @return
     */
    List<Role> queryRoleByUserId(@Param("id") Long id);

    /**
     * 根据用户id获取角色名称
     *
     * @param id
     * @return
     */
    List<String> queryRoleEnNameByUserId(@Param("id") Long id);
}