package com.river.model.po;

import java.util.Date;

public class UserRole {
    private Long id;

    private Long userId;

    private Long roleId;

    private Long createBy;

    private Long updateBy;

    private Date gmCreate;

    private Date gmModified;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getGmCreate() {
        return gmCreate;
    }

    public void setGmCreate(Date gmCreate) {
        this.gmCreate = gmCreate;
    }

    public Date getGmModified() {
        return gmModified;
    }

    public void setGmModified(Date gmModified) {
        this.gmModified = gmModified;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}