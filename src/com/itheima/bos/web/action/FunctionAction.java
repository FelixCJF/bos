package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.itheima.bos.domain.Function;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 权限管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	/**
	 * 分页查询方法
	 */
	public String pageQuery()throws IOException{
		functionService.pageQuery(pageBean);
		String[] excludes = new String[]{"currentPage",
					"pageSize","detachedCriteria","parentFunction","children","roles"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	/**
	 * 查询所有权限数据，返回json
	 */
	public String listajax(){
		List<Function> list = functionService.findAll();
		String[] excludes = new String[]{"parentFunction","children","roles"};
		this.writeListBean2Json(list, excludes );
		return NONE;
	}
	
	/**
	 * 添加权限
	 */
	public String add(){
		functionService.save(model);
		return "list";
	}
}
