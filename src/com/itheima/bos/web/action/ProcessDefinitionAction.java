package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程定义管理Action
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	@Resource
	private ProcessEngine processEngine;

	/**
	 * 查询最新版本的流程定义列表数据
	 */
	public String list() {
		ProcessDefinitionQuery query = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		query.latestVersion();
		List<ProcessDefinition> list = query.list();
		// 压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	// 接收上传的文件
	private File deploy;// zip文件

	/**
	 * 部署流程定义
	 * 
	 * @throws Exception
	 */
	public String deploy() throws Exception {
		DeploymentBuilder deploymentBuilder = processEngine
				.getRepositoryService().createDeployment();
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(
				deploy));
		deploymentBuilder.addZipInputStream(zipInputStream);
		deploymentBuilder.deploy();
		return "toList";
	}

	public void setDeploy(File deploy) {
		this.deploy = deploy;
	}

	//接收流程定义ID
	private String pdId;
	/**
	 * 查看png图片
	 */
	public String showpng(){
		//processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		InputStream pngStream = processEngine.getRepositoryService().getProcessDiagram(pdId);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		//使用struts2的文件下载功能展示png图片
		return "showpng";
	}

	public void setPdId(String pdId) {
		this.pdId = pdId;
	}
}
