package com.river.service.impl;

import com.river.service.ProcessService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: he.feng
 * @date: 15:55 2017/12/11
 * @desc:
 **/
@Service("processService")
public class ProcessServiceImpl implements ProcessService {

    private static final Logger logger = Logger.getLogger(ProcessServiceImpl.class);

    @Resource
    private RepositoryService repositoryService;

    /**
     * 流程列表
     *
     * @return
     */
    @Override
    public List<Object[]> queryProcessList() {
        //保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
        List<Object[]> objects = new ArrayList<Object[]>();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionId().desc().list();
        for(ProcessDefinition pd : processDefinitions) {
            String deploymentId = pd.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            objects.add(new Object[]{pd,deployment});
        }
        return objects;
    }

    /**
     * 读取部署流程的xml  or png
     *
     * @param processDefinitionId
     * @param resourceType
     * @param response
     */
    @Override
    public void loadByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId).singleResult();
            String resourceName = "";
            if(StringUtils.equalsIgnoreCase("image",resourceType)) {
                resourceName = processDefinition.getDiagramResourceName();
            }else if(StringUtils.equalsIgnoreCase("xml",resourceType)) {
                resourceName = processDefinition.getResourceName();
            }

            InputStream resourceStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),resourceName);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = resourceStream.read(bytes)) != -1) {
                response.getOutputStream().write(bytes,0,len);
            }
        } catch (IOException e) {
            logger.error("读取部署流程的xml  or png异常",e);
        }
    }

    /**
     * 激活 挂起流程
     *
     * @param processDefinitionId
     * @param state
     */
    @Override
    public void updateProcessState(String processDefinitionId, String state) {
        if(StringUtils.equalsIgnoreCase(state,"active")) {
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, new Date());
        }else  if(StringUtils.equalsIgnoreCase(state,"suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId,true,new Date());
        }
    }

    /**
     * 级联删除流程
     *
     * @param deploymentId
     */
    @Override
    public void deleteProcessBydeploymentId(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId,true);
    }
}
