package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 任务操作Action
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
	@Resource
	private ProcessEngine processEngine;

	/**
	 * 查询组任务
	 */
	public String findGroupTask() {
		// 任务查询
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();
		// 根据组任务过滤
		query.taskCandidateUser(candidateUser);
		query.orderByTaskCreateTime().desc();
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "groupTaskList";
	}

	// 接收任务ID
	private String taskId;

	/**
	 * 查询流程变量
	 * 
	 * @throws IOException
	 */
	public String showData() throws IOException {
		Map<String, Object> map = processEngine.getTaskService().getVariables(
				taskId);
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(map);
		return NONE;
	}

	/**
	 * 拾取组任务
	 */
	public String takeTask() {
		processEngine.getTaskService().claim(taskId,
				BOSContext.getLoginUser().getId());
		return "toGroupTaskList";
	}

	/**
	 * 查询当前登录人的个人任务
	 */
	public String findPersonalTask() {
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee(BOSContext.getLoginUser().getId());
		query.orderByTaskCreateTime().desc();
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "personalTaskList";
	}

	private Integer check;// 审核结果

	/**
	 * 办理审核工作单任务
	 */
	public String checkWorkOrderManage() {
		if (check == null) {
			// 跳转到审核工作单表单页面
			// 根据任务的ID查询流程变量
			Map<String, Object> map = processEngine.getTaskService()
					.getVariables(taskId);
			ActionContext.getContext().getValueStack().set("map", map);
			return "checkUI";
		} else {
			// 2、修改工作单中的managerCheck为“1”
			Workordermanage workOrderManage = (Workordermanage) processEngine
					.getTaskService().getVariable(taskId, "业务数据");
			workOrderManage.setManagerCheck("1");
			// 3、如果审核不通过，修改工作单的start为“0”
			if (check == 0) {
				workOrderManage.setStart("0");
			}
			workordermanageService.update(workOrderManage);
			// 1、根据任务ID办理任务,设置流程变量check
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("check", check);
			processEngine.getTaskService().complete(taskId, variables);
			return "toPersonalTaskList";
		}
	}

	@Resource
	private IWorkordermanageService workordermanageService;

	// 办理出库任务
	public String outStore() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("仓库管理员", BOSContext.getLoginUser().getUsername());
		processEngine.getTaskService().complete(taskId, variables);
		return "toPersonalTaskList";
	}

	// 办理配送任务
	public String transferGoods() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("取派员", BOSContext.getLoginUser().getUsername());
		processEngine.getTaskService().complete(taskId, variables);
		return "toPersonalTaskList";
	}

	// 办理签收任务
	public String receive() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("取派员", BOSContext.getLoginUser().getUsername());
		processEngine.getTaskService().complete(taskId, variables);
		return "toPersonalTaskList";
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}
}
