package com.river.service;

import com.river.model.dto.VariablesDTO;
import com.river.model.po.LeaveBill;
import com.river.model.po.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
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


    /**
     * 查询真正运行的任务
     * @return
     */
    List<Object[]> queryRunningList();


    /**
     * 请假任务列表
     * @return
     * @param user
     */
    List<Object[]> queryTaskList(User user);


    /**
     * 任务签收
     * @param taskId
     * @param user
     */
    void taskClaim(String taskId, User user);

    /**
     *
     * @param taskId
     * @param comment
     * @param variables
     */
    void taskComplete(String taskId, String comment, VariablesDTO variables);

    /**
     * 已完成任务
     * @return
     */
    List<Object[]> queryFinishedList();


    /**
     * 获取当前节点流程图
     * @param processInstanceId
     * @param response
     */
    void readProcessImage(String processInstanceId, HttpServletResponse response);
}
