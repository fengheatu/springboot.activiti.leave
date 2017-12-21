package com.river.controller.leave;

import com.river.controller.BaseController;
import com.river.model.dto.VariablesDTO;
import com.river.model.po.LeaveBill;
import com.river.model.po.User;
import com.river.service.LeaveBillService;
import com.river.service.ProcessInstantService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author: he.feng
 * @date: 19:04 2017/12/11
 * @desc:
 **/
@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController{

    private static final Logger logger = LogManager.getLogger(LeaveController.class);

    @Resource
    private LeaveBillService leaveBillService;

    @Resource
    private ProcessInstantService processInstantService;


    /**
     * 请假表单页面
     * @param modelAndView
     * @return
     */
    @RequestMapping("/apply")
    public ModelAndView apply(ModelAndView modelAndView) {
        modelAndView.setViewName("oa/leave/leaveApply");
        return modelAndView;
    }


    /**
     * 启动流程
     * @param
     * @param
     * @return
     */
    @RequestMapping("/start")
    public String start(LeaveBill leaveBill, HttpServletRequest request){
        logger.info("启动流程");
        User user = getCurrentUser(request);
        leaveBillService.startProcess(leaveBill,user);
        return "redirect:/leave/running/list";
    }

    /**
     * 业务详情
     * @param taskId
     * @return
     */
    @RequestMapping("/details/{taskId}")
    @ResponseBody
    public String leaveDetails(@PathVariable("taskId") String taskId){
        logger.info("任务详情 taskId=" + taskId);
        LeaveBill leaveBill = leaveBillService.queryLeaveBillDetailsByTaskId(taskId);
        return objectToJson(leaveBill);
    }


    /**
     * 任务参数详情
     * @param taskId
     * @return
     */
    @RequestMapping("/details/vars/{taskId}")
    @ResponseBody
    public String detailsVars(@PathVariable("taskId") String taskId){
        logger.info("任务参数详情 taskId=" + taskId);
        Map<String,Object> variables = processInstantService.getVarbles(taskId);
        return objectToJson(variables);
    }

    /**
     * 运行中的任务
     * @param modelAndView
     * @return
     */
    @RequestMapping("/running/list")
    public ModelAndView runningList(ModelAndView modelAndView) {
        logger.info("运行中的任务流程");
        List<Object[]> result = leaveBillService.queryRunningList();
        modelAndView.addObject("result",result);
        modelAndView.setViewName("oa/leave/running");
        return modelAndView;
    }

    /**
     * 请假任务列表
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping("/list/task")
    public ModelAndView taskList(ModelAndView modelAndView,HttpServletRequest request){
        logger.info("请假办理任务列表");
        User user = getCurrentUser(request);
        List<Object[]> result = leaveBillService.queryTaskList(user);
        modelAndView.addObject("result",result);
        modelAndView.setViewName("oa/leave/taskList");
        return modelAndView;
    }


    /**
     * 任务签收
     * @param taskId
     * @param request
     * @return
     */
    @RequestMapping("/task/claim/{taskId}")
    public String taskClaim(@PathVariable("taskId") String taskId,HttpServletRequest request) {
        User user = getCurrentUser(request);
        logger.info("任务签收 taskId=" + taskId + " userId=" + user.getId());
        leaveBillService.taskClaim(taskId,user);
        return "redirect:/leave/list/task";
    }


    /**
     * 任务办理
     * @param taskId
     * @param variables
     * @param comment
     * @return
     */
    @RequestMapping("/task/complete/{taskId}")
    public void taskComplete(@PathVariable("taskId") String taskId, VariablesDTO variables, String comment) {
        logger.info("完成任务：taskId = " + taskId);
        leaveBillService.taskComplete(taskId,comment,variables);

    }

    /**
     * 已完成流程
     * @param modelAndView
     * @return
     */
    @RequestMapping("/list/finished")
    public ModelAndView finishedList(ModelAndView modelAndView) {
        logger.info("已完成流程");
        List<Object[]> result = leaveBillService.queryFinishedList();
        modelAndView.addObject("result",result);
        modelAndView.setViewName("oa/leave/finished");
        return modelAndView;
    }


    @RequestMapping("/process/trace/auto/{processInstanceId}")
    public void readImage(@PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response){
        logger.info("获取当前节点的流程图pid=" + processInstanceId);
        leaveBillService.readProcessImage(processInstanceId,response);
    }

    @RequestMapping("/process/trace/auto/{processInstanceId}/{processDefinitionId}")
    public String readImage2(@PathVariable("processInstanceId") String processInstanceId,
                           @PathVariable("processDefinitionId") String processDefinitionId){
        logger.info("获取当前节点的流程图pid=" + processInstanceId);
        return "redirect:../../../../../diagram-viewer/index.html?processDefinitionId=" + processDefinitionId + "&processInstanceId=" + processInstanceId;
    }

}
