package com.river.config.activiti;

import com.river.model.po.Role;
import com.river.service.RoleService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: he.feng
 * @date: 13:53 2017/12/5
 * @desc:
 **/
@Component
public class CustomGroupEntityManager extends GroupEntityManager {

    private static final Logger logger = Logger.getLogger(CustomUserEntityManager.class);

   @Resource
   private RoleService roleService;

    @Override
    public List<Group> findGroupsByUser(String userId) {
        logger.info("method:findGroupsByUser param: userId = " + userId);
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        Long id = Long.valueOf(userId);

        List<Role> roles  = roleService.queryRoleByUserId(id);
        List<Group> groups = new ArrayList<Group>();
        GroupEntity groupEntity = null;
        for (Role role : roles) {
            groupEntity = new GroupEntity();
            groupEntity.setRevision(1);
            groupEntity.setType("assignment");
            groupEntity.setName(role.getRoleEnName());
            groups.add(groupEntity);
        }
        return groups;
    }
}
