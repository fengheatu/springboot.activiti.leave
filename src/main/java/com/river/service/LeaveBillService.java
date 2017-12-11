package com.river.service;

import com.river.model.po.LeaveBill;
import com.river.model.po.User; /**
 * @author: he.feng
 * @date: 10:14 2017/12/8
 * @desc:
 **/
public interface LeaveBillService {

    /**
     * 开始请假流程
     * @param leaveBill
     * @param user
     */
    void startProcess(LeaveBill leaveBill, User user);


    /**
     * 根据任务id 获取业务详情
     * @param taskId
     * @return
     */
    LeaveBill queryLeaveBillDetailsByTaskId(String taskId);
}
