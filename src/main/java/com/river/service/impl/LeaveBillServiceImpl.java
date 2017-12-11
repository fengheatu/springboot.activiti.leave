package com.river.service.impl;

import com.river.dao.mapper.LeaveBillMapper;
import com.river.model.po.LeaveBill;
import com.river.model.po.User;
import com.river.service.LeaveBillService;
import groovy.util.ObservableMap;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author: he.feng
 * @date: 10:19 2017/12/8
 * @desc:
 **/
@Service("leaveService")
public class LeaveBillServiceImpl implements LeaveBillService {

    private static final Logger logger = Logger.getLogger(LeaveBillServiceImpl.class);

    @Resource
    private LeaveBillMapper leaveBillMapper;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;


    /**
     * 开始请假流程
     *
     * @param leaveBill
     * @param user
     */
    @Override
    public void startProcess(LeaveBill leaveBill, User user) {
        initLeaveBill(leaveBill,user);
        leaveBill.setStatus(0);
        leaveBillMapper.insert(leaveBill);
        //业务关联流程实例
        String businessKey = LeaveBill.class.getName() + ":" + leaveBill.getId();
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("inputUser",user.getId());
        variables.put("businessKey",businessKey);

        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveBill",businessKey,variables);

        //完成第一个申请任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        taskService.complete(task.getId());

    }

    /**
     * 根据任务id 获取业务详情
     *
     * @param taskId
     * @return
     */
    @Override
    public LeaveBill queryLeaveBillDetailsByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        Long leaveId = null;
        if(StringUtils.isNotBlank(businessKey)) {
            leaveId = Long.valueOf(businessKey.split(":")[1]);
        }
        return leaveBillMapper.selectByPrimaryKey(leaveId);
    }


    /**
     * 初始化请假表单
     * @param leaveBill
     * @param user
     */
    private void initLeaveBill(LeaveBill leaveBill, User user) {
        leaveBill.setUserId(user.getId());
        leaveBill.setCreateBy(user.getId());
        leaveBill.setUpdateBy(user.getId());
        leaveBill.setGmCreate(new Date());
        leaveBill.setGmModified(new Date());
        leaveBill.setIsDelete(false);
    }
}
