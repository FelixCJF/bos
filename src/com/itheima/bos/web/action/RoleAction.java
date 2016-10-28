package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 角色管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private String funcitonIds;//权限ID
	
	/**
	 * 添加角色方法
	 */
	public String add(){
		roleService.save(model,funcitonIds);
		return "list";
	}

	/**
	 * 分页查询方法
	 */
	public String pageQuery(){
		roleService.pageQuery(pageBean);
		String[] excludes = new String[]{ "currentPage", "pageSize","detachedCriteria","functions","users"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	/**
	 * 查询所有角色，返回json数据
	 */
	public String listajax(){
		List<Role> list = roleService.findAll();
		String[] excludes = new String[]{"functions","users"};
		this.writeListBean2Json(list, excludes );
		return NONE;
	}
	
	public void setFuncitonIds(String funcitonIds) {
		this.funcitonIds = funcitonIds;
	}
}
