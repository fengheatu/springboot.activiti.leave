package com.river.model.po;

import java.util.Date;

public class LeaveBill {
    private Long id;

    private Long userId;

    private Integer days;

    private String reson;

    private Date beginTime;

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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson == null ? null : reson.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
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