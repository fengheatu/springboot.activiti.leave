package com.river.dao.mapper;

import com.river.model.po.LeaveBill;

public interface LeaveBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LeaveBill record);

    int insertSelective(LeaveBill record);

    LeaveBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LeaveBill record);

    int updateByPrimaryKey(LeaveBill record);
}