package com.river.model.po;

import java.util.Date;

public class Role {
    private Long id;

    private String roleEnName;

    private String roleCnName;

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

    public String getRoleEnName() {
        return roleEnName;
    }

    public void setRoleEnName(String roleEnName) {
        this.roleEnName = roleEnName == null ? null : roleEnName.trim();
    }

    public String getRoleCnName() {
        return roleCnName;
    }

    public void setRoleCnName(String roleCnName) {
        this.roleCnName = roleCnName == null ? null : roleCnName.trim();
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