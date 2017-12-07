package com.river.model.po;

import java.util.Date;

public class User {
    private Long id;

    private String userName;

    private String moble;

    private String password;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMoble() {
        return moble;
    }

    public void setMoble(String moble) {
        this.moble = moble == null ? null : moble.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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