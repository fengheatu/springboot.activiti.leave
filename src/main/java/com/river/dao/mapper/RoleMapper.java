package com.river.dao.mapper;

import com.river.model.po.Role;
import org.apache.ibatis.annotations.Param;

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
}