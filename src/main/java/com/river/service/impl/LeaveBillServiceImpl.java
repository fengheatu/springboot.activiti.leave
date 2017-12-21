package com.river.service.impl;

import com.river.constant.LeaveBillConst;
import com.river.dao.mapper.LeaveBillMapper;
import com.river.model.dto.VariablesDTO;
import com.river.model.po.LeaveBill;
import com.river.model.po.User;
import com.river.service.LeaveBillService;
import com.river.service.RoleService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @author: he.feng
 * @date: 10:19 2017/12/8
 * @desc:
 **/
@Service("leaveService")
public class LeaveBillServiceImpl implements LeaveBillService {

    private static final Logger logger = LogManager.getLogger(LeaveBillServiceImpl.class);

    @Resource
    private LeaveBillMapper leaveBillMapper;

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private RoleService roleService;

    @Resource
    private HistoryService historyService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private RepositoryService repositoryService;


    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;



    /**
     * 开始请假流程
     *
     * @param leaveBill
     * @param user
     */
    @Override
    @Transactional
    public void startProcess(LeaveBill leaveBill, User user) {
        initLeaveBill(leaveBill,user);
        leaveBill.setStatus(LeaveBillConst.LEAVE_BILL_APPROVE_ING);
        leaveBillMapper.insert(leaveBill);
        //业务关联流程实例
        String businessKey = leaveBill.getClass().getSimpleName() + ":" + leaveBill.getId();
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
     * 查询真正运行的任务
     *
     * @return
     */
    @Override
    public List<Object[]> queryRunningList() {

        List<Object[]> result = new ArrayList<>();
        //当前运行流程
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
        for(ProcessInstance processInstance : processInstanceList) {
            String businessKey = processInstance.getBusinessKey();
            Long leaveBillId = null;
            if (StringUtils.isNotBlank(businessKey)) {
                leaveBillId = Long.valueOf(businessKey.split(":")[1]);
                LeaveBill leaveBill = leaveBillMapper.selectByPrimaryKey(leaveBillId);
                Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
                result.add(new Object[]{leaveBill,task,processInstance});
            }

        }

        return result;
    }

    /**
     * 请假任务列表
     *
     * @return
     * @param user
     */
    @Override
    public List<Object[]> queryTaskList(User user) {
        List<Object[]> result = new ArrayList<>();

        //已签收任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(String.valueOf(user.getId())).list();
        initTaskList(result, tasks);

        //未签收的
        List<String> roles = roleService.queryRoleEnNameByUserId(user.getId());
        if(!CollectionUtils.isEmpty(roles)) {
            List<Task> tasks1 = taskService.createTaskQuery().taskCandidateGroupIn(roles).active().list();
            initTaskList(result,tasks1);
        }
        return result;
    }

    /**
     * 任务签收
     *
     * @param taskId
     * @param user
     */
    @Override
    public void taskClaim(String taskId, User user) {
        taskService.claim(taskId,String.valueOf(user.getId()));
    }


    /**
     * @param taskId
     * @param comment
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void taskComplete(String taskId, String comment, VariablesDTO variable) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        //审批备注
        if(StringUtils.isNotBlank(comment)) {
            taskService.addComment(taskId,processInstanceId,comment);

        }
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(variable.getKeys(), Boolean.valueOf(variable.getValues()));
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        //完成任务
        taskService.complete(taskId, variables);

        Long leaveBillId = null;
        if(StringUtils.isNotBlank(businessKey)) {
            leaveBillId = Long.valueOf(businessKey.split(":")[1]);
        }

        //判断任务是否完成
        ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (null == processInstance1) {
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
     * 已完成任务
     *
     * @return
     */
    @Override
    public List<Object[]> queryFinishedList() {
        List<Object[]> result = new ArrayList<>();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("LeaveBill").list();
        for (HistoricProcessInstance historicProcessInstance : list) {
            String businessKey = historicProcessInstance.getBusinessKey();
            if(StringUtils.isNotBlank(businessKey)) {
                Long leaveBillId = Long.valueOf(businessKey.split(":")[1]);
                LeaveBill leaveBill = leaveBillMapper.selectByPrimaryKey(leaveBillId);
                result.add(new Object[]{leaveBill,historicProcessInstance});
            }
        }
        return result;
    }

    /**
     * 获取当前节点流程图
     *
     * @param processInstanceId
     * @param response
     */
    @Override
    public void readProcessImage(String processInstanceId, HttpServletResponse response) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

        //BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,"png",activeActivityIds,
                Collections.<String>emptyList(),
                processEngineConfiguration.getActivityFontName(),
                processEngineConfiguration.getLabelFontName(),
                processEngineConfiguration.getAnnotationFontName(),
                processEngineConfiguration.getClassLoader(),
                1.0);

        byte[] bytes = new byte[1024];
        int len;
        try {
            while ((len = in.read(bytes)) != -1) {
                response.getOutputStream().write(bytes,0,len);
            }
        } catch (IOException e) {
            logger.error("获取当前节点流程图异常",e);
            throw new RuntimeException("获取当前节点流程图异常",e);
        }

    }

    /**
     * 初始化任务列表
     * @param result
     * @param tasks
     */
    private void initTaskList(List<Object[]> result, List<Task> tasks) {
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
                    .singleResult();
            Long leaveBillId = null;
            String businessKey = processInstance.getBusinessKey();
            if(StringUtils.isNotBlank(businessKey)) {
                leaveBillId = Long.valueOf(businessKey.split(":")[1]);
                LeaveBill leaveBill = leaveBillMapper.selectByPrimaryKey(leaveBillId);
                result.add(new Object[]{leaveBill,task,processInstance});
            }
        }
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
