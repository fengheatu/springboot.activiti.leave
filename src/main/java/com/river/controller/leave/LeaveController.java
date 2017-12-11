package com.river.controller.leave;

import com.river.controller.BaseController;
import com.river.model.po.LeaveBill;
import com.river.model.po.User;
import com.river.service.LeaveBillService;
import com.river.service.ProcessInstantService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: he.feng
 * @date: 19:04 2017/12/11
 * @desc:
 **/
@Controller
@RequestMapping("leave")
public class LeaveController extends BaseController{

    private static final Logger logger = Logger.getLogger(LeaveController.class);

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
     * @param modelAndView
     * @param
     * @return
     */
    @RequestMapping("/start")
    public ModelAndView start(ModelAndView modelAndView, LeaveBill leaveBill, HttpServletRequest request){
        logger.info("启动流程");
        User user = getCurrentUser(request);
        leaveBillService.startProcess(leaveBill,user);
        modelAndView.setViewName("oa/leave/running");
        return modelAndView;
    }

    @RequestMapping("/leave/details/{taskId}")
    public ModelAndView leaveDetails(HttpServletRequest request, ModelAndView modelAndView, @PathVariable("taskId") String taskId){
        logger.info("任务详情 taskId=" + taskId);
        LeaveBill leaveBill = leaveBillService.queryLeaveBillDetailsByTaskId(taskId);
        //任务参数
        Map<String,Object> variables = processInstantService.getVarbles(taskId);
        modelAndView.addObject("variables",variables);
        modelAndView.addObject("leaveBill",leaveBill);
        modelAndView.addObject("taskId",taskId);
        modelAndView.setViewName("workflow/taskForm");

        return modelAndView;
    }
}
