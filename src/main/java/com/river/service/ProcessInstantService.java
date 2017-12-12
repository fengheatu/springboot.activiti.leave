package com.river.service;

import com.river.model.dto.VariablesDTO;
import com.river.model.po.User;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;

/**
 * @author: he.feng
 * @date: 19:53 2017/12/11
 * @desc:
 **/
public interface ProcessInstantService {

    /**
     * 待办任务
     * @param user
     * @return
     */
    List<Map<String,Object>> queryTaskToDoList(User user);


    /**
     * 任务签收
     * @param user
     * @param taskId
     */
    void taskClaim(User user, String taskId);

    /**
     * 获取任务参数
     * @param taskId
     * @return
     */
    Map<String,Object> getVarbles(String taskId);


    /**
     *
     * @param leaveBillId
     * @param taskId
     * @param comment
     * @param variables
     */
    void taskComplete(Long leaveBillId, String taskId, String comment, VariablesDTO variables);


    /**
     * 查询运行中的流程
     * @return
     */
    List<ProcessInstance> queryProcessRunning();
}
