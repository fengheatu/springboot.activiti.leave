package com.river.service.impl;

import com.river.constant.LeaveBillConst;
import com.river.dao.mapper.LeaveBillMapper;
import com.river.model.dto.VariablesDTO;
import com.river.model.po.LeaveBill;
import com.river.model.po.User;
import com.river.service.ProcessInstantService;
import com.river.service.RoleService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: he.feng
 * @date: 19:53 2017/12/11
 * @desc:
 **/
@Service("processInstantService")
public class ProcessInstantServiceImpl implements ProcessInstantService {

    private static final Logger logger = LogManager.getLogger(ProcessInstantServiceImpl.class);

    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RoleService roleService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private LeaveBillMapper leaveBillMapper;

    /**
     * 待办任务
     *
     * @param user
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTaskToDoList(User user) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        //已经签收任务
        List<Task> toDoList = taskService.createTaskQuery().taskAssignee(String.valueOf(user.getId())).active().list();
        for(Task task : toDoList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId).singleResult();
            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status","todo");
            result.add(singleTask);
        }

        // 等待签收的任务
        List<String> roles = roleService.queryRoleEnNameByUserId(user.getId());
        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateGroupIn(roles).active().list();
        for (Task task : toClaimList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId).singleResult();
            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "claim");
            result.add(singleTask);
        }

        return result;
    }

    /**
     * 任务签收
     *
     * @param user
     * @param taskId
     */
    @Override
    public void taskClaim(User user, String taskId) {
        taskService.claim(taskId,String.valueOf(user.getId()));
    }

    /**
     * 获取任务参数
     *
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> getVarbles(String taskId) {
        return taskService.getVariables(taskId);
    }

    /**
     * @param leaveBillId
     * @param taskId
     * @param comment
     */
    @Override
    @Transactional
    public void taskComplete(Long leaveBillId, String taskId, String comment, VariablesDTO variable) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        //审批备注
        taskService.addComment(taskId,processInstanceId,comment);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(variable.getKeys(), variable.getValues());

        taskService.complete(taskId, variables);

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        if (null == processInstance) {
            LeaveBill leaveBill = leaveBillMapper.selectByPrimaryKey(leaveBillId);
            if ("pass".equals(variable.getKeys()) && "true".equals(variable.getValues())) {
                leaveBill.setStatus(LeaveBillConst.LEAVE_BILL_PASS);
            } else {
                leaveBill.setStatus(LeaveBillConst.LEAVE_BILL_NOT_PASS);
            }
            leaveBillMapper.updateByPrimaryKeySelective(leaveBill);
        }

    }

    /**
     * 查询运行中的流程
     *
     * @return
     */
    @Override
    public List<ProcessInstance> queryProcessRunning() {
        return runtimeService.createProcessInstanceQuery()
                .orderByProcessInstanceId()
                .desc()
                .list();
    }


    /**
     * 初始化已签收任务
     * @param sdf
     * @param task
     * @param processDefinition
     * @return
     */
    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("createTime", sdf.format(task.getCreateTime()));
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdversion", processDefinition.getVersion());
        singleTask.put("pid", task.getProcessInstanceId());
        return singleTask;
    }
}
