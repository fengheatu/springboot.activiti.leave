package com.river.controller.modeler;

import com.river.constant.UserConst;
import com.river.controller.BaseController;
import com.river.model.po.User;
import com.river.service.ModelerService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author: he.feng
 * @date: 18:00 2017/12/7
 * @desc:
 **/
@Controller
@RequestMapping("/modeler")
public class ModelerController extends BaseController{

    private static final Logger logger = Logger.getLogger(ModelerController.class);

    @Resource
    private ModelerService modelerService;


    /**
     * 模型列表
     * @param modelAndView
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request) {
        logger.info("模型列表");
        User user = getCurrentUser(request);
        List<Model> list = modelerService.queryModelerList();
        modelAndView.addObject("list",list);
        modelAndView.setViewName("workflow/model-list");
        return modelAndView;
    }

    /**
     * 新增模型
     * @param name
     * @param key
     * @param description
     * @param request
     * @return
     */
    @RequestMapping("create")
    public String create(String name,String key,String description,HttpServletRequest request) {
        User user = getCurrentUser(request);
        logger.info("创建新模型 name=" + name + " key=" + key);
        String modelId = modelerService.addModeler(name,key,description);
        return "redirect:../modeler.html?modelId=" + modelId;
    }

    /**
     * 编辑模型
     * @param modelId
     * @return
     */
    @RequestMapping(value = "/modify/{modelId}",method = RequestMethod.GET)
    public String modify(@PathVariable("modelId") String modelId) {
        logger.info("编辑模型 modelId = " + modelId);
        return "redirect:../modeler.html?modelId=" + modelId;
    }


    /**
     *删除模型
     * @param modelId
     * @return
     */
    @RequestMapping("/delete/{modelId}")
    public String delete(@PathVariable("modelId") String modelId)  {
        logger.info("删除模型：modelId = " + modelId);
        modelerService.deleteModeler(modelId);
        return "redirect:/modeler/list";
    }

    /**
     * 导出BPMN
     * @param modelId
     * @param response
     */
    @RequestMapping("/export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
        logger.info("导出BPMN modelId = " + modelId);

        ByteArrayInputStream in = null;
        try {
            in = modelerService.exportBPMN(modelId);
            IOUtils.copy(in,response.getOutputStream());
        } catch (Exception e) {
            logger.error("导出BPMN异常",e);
        }

    }


    /**
     * 流程部署
     * @return
     */
    @RequestMapping("/deploy/{modelId}")
    public String deploy(@PathVariable("modelId") String modelId) {
        logger.info("流程部署：modelId=" + modelId );
        modelerService.deployProcess(modelId);
        return "redirect:/modeler/list";
    }

}
