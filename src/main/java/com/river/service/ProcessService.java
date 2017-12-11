package com.river.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: he.feng
 * @date: 15:55 2017/12/11
 * @desc:
 **/
public interface ProcessService {

    /**
     * 流程列表
     * @return
     */
    List<Object[]> queryProcessList();

    /**
     * 读取部署流程的xml  or png
     * @param processDefinitionId
     * @param resourceType
     * @param response
     */
    void loadByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response);

    /**
     * 激活 挂起流程
     * @param processDefinitionId
     * @param state
     */
    void updateProcessState(String processDefinitionId, String state);


    /**
     * 级联删除流程
     * @param deploymentId
     */
    void deleteProcessBydeploymentId(String deploymentId);
}
