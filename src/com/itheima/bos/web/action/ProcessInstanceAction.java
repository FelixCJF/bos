package com.itheima.bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.utils.ActivitiUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程实例管理Action
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport {
	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 查询正在运行的流程实例数据
	 */
	public String list() {
		// 流程实例查询对象，查询表act_ru_execution
		ProcessInstanceQuery query = processEngine.getRuntimeService()
				.createProcessInstanceQuery();
		query.orderByProcessDefinitionKey().desc();
		List<ProcessInstance> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	// 接收流程实例ID
	private String id;

	/**
	 * 根据流程实例ID查询流程变量
	 * 
	 * @throws IOException
	 */
	public String findData() throws IOException {
		Map<String, Object> variables = processEngine.getRuntimeService()
				.getVariables(id);
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables);
		return NONE;
	}

	/**
	 * 查询坐标
	 */
	public String showPng() {
		Map<String, Object> map = ActivitiUtils.findDataByInstanceId(processEngine, id);
		ActionContext.getContext().getValueStack().push(map);
		return "showPng";
	}

	String deploymentId;
	String imageName;

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * 查询png图片的输入流，展示图片
	 */
	public String viewImage() {
		InputStream pngStream = processEngine.getRepositoryService()
				.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "viewImage";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
