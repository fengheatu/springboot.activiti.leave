package com.river.controller.instant;

import com.river.controller.BaseController;
import com.river.model.dto.VariablesDTO;
import com.river.model.po.User;
import com.river.service.ProcessInstantService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: he.feng
 * @date: 19:52 2017/12/11
 * @desc:
 **/
@Controller
@RequestMapping("/process/instant")
public class ProcessInstantController extends BaseController{

    private static final Logger logger = LogManager.getLogger(ProcessInstantController.class);

    @Resource
    private ProcessInstantService processInstantService;


    /**
     * 待办理任务
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping("/task/todo/list")
    public ModelAndView taskToDoList(ModelAndView modelAndView,HttpServletRequest request) {
        logger.info("待办理任务");
        User user = getCurrentUser(request);
        List<Map<String,Object>> result = processInstantService.queryTaskToDoList(user);
        modelAndView.addObject("result",result);
        modelAndView.setViewName("oa/leave/taskList");
        return modelAndView;
    }

    @RequestMapping("/task/claim/{taskId}")
    public String taskClaim(HttpServletRequest request, @PathVariable("taskId") String taskId) {
        User user = getCurrentUser(request);
        logger.info("userId=" + user.getId() + "签收了任务 taskId=" + taskId);
        processInstantService.taskClaim(user,taskId);
        return "redirect:/process/instant/task/todo/list";
    }

    @RequestMapping("/task/complete/{taskId}/{leaveBillId}")
    public String taskComplete(@PathVariable("taskId") String taskId,@PathVariable("leaveBillId") Long leaveBillId ,VariablesDTO variables,String comment) {
        logger.info("完成任务：taskId = " + taskId);
        processInstantService.taskComplete(leaveBillId,taskId,comment,variables);
        return "redirect:/process/instant/task/todo/list";
    }

    /**
     * 正在运行的流程
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping("/running")
    public ModelAndView processRunning(ModelAndView modelAndView, HttpServletRequest request) {
        logger.info("查询运行中的流程");
        List<ProcessInstance> list = processInstantService.queryProcessRunning();
        modelAndView.addObject("result",list);
        modelAndView.setViewName("oa/leave/running");
        return modelAndView;
    }

}
