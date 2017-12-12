package com.river.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.river.service.ModelerService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author: he.feng
 * @date: 10:22 2017/12/8
 * @desc:
 **/
@Service("modelerService")
public class ModelerServiceImpl implements ModelerService {

    private static final  Logger logger = LogManager.getLogger(ModelerServiceImpl.class);

    @Resource
    private RepositoryService repositoryService;

    /**
     * 查询所有模型
     *
     * @return
     */
    @Override
    public List<Model> queryModelerList() {
        return repositoryService.createModelQuery().list();
    }

    /**
     * 添加新模型
     *
     * @param name
     * @param key
     * @param description
     */
    @Override
    public String addModeler(String name, String key, String description) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            return modelData.getId();
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
            throw new RuntimeException("创建模型失败：", e);
        }

    }

    /**
     * 删除模型
     *
     * @param modelId
     */
    @Override
    public void deleteModeler(String modelId) {
        repositoryService.deleteModel(modelId);
    }

    /**
     * 导出BPMN
     *
     * @param modelId
     * @return
     */
    @Override
    public ByteArrayInputStream exportBPMN(String modelId) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            //获取一个Process对象
            if(bpmnModel.getMainProcess() == null) {
                throw new RuntimeException("没有 main process,不能导出BPMN");
            }

            BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
            byte[] exportBytes = bpmnXMLConverter.convertToXML(bpmnModel);
            String fileName = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
            logger.info("导出BPMN名称：" + fileName);
            return new ByteArrayInputStream(exportBytes);
        } catch (Exception e) {
           logger.error("导出BPMN异常",e);
           throw new RuntimeException("导出BPMN异常",e);
        }
    }

    /**
     * 流程部署
     *
     * @param modelId
     */
    @Override
    public void deployProcess(String modelId) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processName = modelData.getName() + ".bpmn20.xml";

            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName,new String(bpmnBytes))
                    .deploy();
            logger.info("部署成功，部署ID=" + deployment.getId());
        } catch (IOException e) {
            logger.error("流程部署异常",e);
        }
    }


}
