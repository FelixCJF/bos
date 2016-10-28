package com.itheima.bos.utils;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;

public class ActivitiUtils {
	public static Map<String, Object> findDataByInstanceId(
			ProcessEngine processEngine, String id) {
		// 查询部署ID、png图片名称、当前流程任务的坐标
		ProcessInstanceQuery query = processEngine.getRuntimeService()
				.createProcessInstanceQuery();
		// 根据流程实例ID查询流程实例对象
		query.processInstanceId(id);
		ProcessInstance processInstance = query.singleResult();

		// 根据流程实例对象查询流程定义ID
		String processDefinitionId = processInstance.getProcessDefinitionId();
		// 根据流程定义ID查询流程定义对象
		ProcessDefinitionQuery query2 = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		query2.processDefinitionId(processDefinitionId);
		ProcessDefinition processDefinition = query2.singleResult();

		// 获得当前流程实例执行到那个任务了
		String activityId = processInstance.getActivityId();// usertask2

		// 根据流程定义ID查询流程定义对象（包含有坐标信息）
		ProcessDefinitionEntity processDefinition2 = (ProcessDefinitionEntity) processEngine
				.getRepositoryService().getProcessDefinition(
						processDefinitionId);
		ActivityImpl activityImpl = processDefinition2.findActivity(activityId);
		int x = activityImpl.getX();
		int y = activityImpl.getY();
		int width = activityImpl.getWidth();
		int height = activityImpl.getHeight();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deploymentId", processDefinition.getDeploymentId());
		map.put("imageName", processDefinition.getDiagramResourceName());
		map.put("x", x);
		map.put("y", y);
		map.put("width", width);
		map.put("height", height);
		return map;
	}
}
