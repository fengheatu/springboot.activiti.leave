package com.river.service;

import org.activiti.engine.repository.Model;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author: he.feng
 * @date: 10:22 2017/12/8
 * @desc:
 **/
public interface ModelerService {

    /**
     * 查询所有模型
     * @return
     */
    List<Model> queryModelerList();

    /**
     * 添加新模型
     * @param name
     * @param key
     * @param description
     */
    String addModeler(String name, String key, String description);

    /**
     * 删除模型
     * @param modelId
     */
    void deleteModeler(String modelId);

    /**
     * 导出BPMN
     * @param modelId
     * @return
     */
    ByteArrayInputStream exportBPMN(String modelId);


    /**
     * 流程部署
     * @param modelId
     */
    void deployProcess(String modelId);
}
