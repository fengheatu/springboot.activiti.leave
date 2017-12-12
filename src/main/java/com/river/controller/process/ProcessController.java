package com.river.controller.process;

import com.river.controller.BaseController;
import com.river.service.ProcessService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.cglib.core.ProcessArrayCallback;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: he.feng
 * @date: 15:54 2017/12/11
 * @desc:
 **/
@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {

    private static final Logger logger = LogManager.getLogger(ProcessController.class);

    @Resource
    private ProcessService processService;


    /**
     * 流程列表
      * @param modelAndView
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView) {
        logger.info("已部署流程列表");
        //保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
        List<Object[]> list = processService.queryProcessList();
        modelAndView.addObject("list",list);
        modelAndView.setViewName("workflow/process-list");
        return modelAndView;
    }

    /**
     * 读取部署流程的xml or png
     * @param processDefinitionId
     * @param resourceType
     * @param response
     */
    @RequestMapping("/resource/read")
    public void loadByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response) {
        logger.info("读取部署流程的xml or png type=" + resourceType + " processDefinitionId=" + processDefinitionId);
        processService.loadByDeployment(processDefinitionId,resourceType,response);
    }

    /**
     * 激活 挂起 流程
     * @param processDefinitionId
     * @param state
     * @return
     */
    @RequestMapping("/update/{state}/{processDefinitionId}")
    public String processUpdateState(@PathVariable("processDefinitionId") String processDefinitionId,
                                @PathVariable("state") String state){
        logger.info("激活 挂起 流程 processDefinitionId=" + processDefinitionId + " state=" + state);
        processService.updateProcessState(processDefinitionId,state);
        return "redirect:/process/list";
    }

    /**
     * 级联删除流程
     * @param deploymentId
     * @return
     */
    @RequestMapping("/delete/{deploymentId}")
    public String delete(@PathVariable("deploymentId") String deploymentId) {
        logger.info("级联删除流程 deploymentId=" + deploymentId);
        processService.deleteProcessBydeploymentId(deploymentId);
        return "redirect:/process/list";
    }




}
