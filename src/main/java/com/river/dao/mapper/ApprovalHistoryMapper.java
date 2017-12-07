package com.river.dao.mapper;

import com.river.model.po.ApprovalHistory;

public interface ApprovalHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApprovalHistory record);

    int insertSelective(ApprovalHistory record);

    ApprovalHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApprovalHistory record);

    int updateByPrimaryKey(ApprovalHistory record);
}