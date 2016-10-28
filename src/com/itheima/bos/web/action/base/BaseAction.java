package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.service.IWorkordermanageService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 通用Action
 * 
 * 
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	protected T model;// 模型对象

	public T getModel() {
		return model;
	}

	protected PageBean pageBean = new PageBean();
	// 离线条件查询对象，用于包装查询条件
	DetachedCriteria detachedCriteria = null;// DetachedCriteria.forClass(Staff.class);

	public void setPage(int page) {
		pageBean.setCurrentPage(page);// 当前页码
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);// 每页显示记录数
	}

	@Resource
	protected IStaffService staffService;
	@Resource
	protected IUserService userService;
	@Resource
	protected IRegionService regionService;
	@Resource
	protected ISubareaService subareaService;
	@Resource
	protected IDecidedzoneService decidedzoneService;
	@Resource
	protected INoticebillService noticebillService;
	@Resource
	protected IWorkordermanageService workordermanageService;
	@Resource
	protected IFunctionService functionService;
	@Resource
	protected IRoleService roleService;

	/**
	 * 获得实体类型，通过反射创建模型对象
	 */
	public BaseAction() {
		// 获得父类（BaseAction） 类型
		ParameterizedType superclass = null;//
		
		Type type = this.getClass().getGenericSuperclass();
		
		if(type instanceof ParameterizedType){
			superclass = (ParameterizedType)type;
		}else{
			superclass = (ParameterizedType)this.getClass().getSuperclass().getGenericSuperclass();
		}
		
		// 获得父类上的泛型数组
		Type[] typeArguments = superclass.getActualTypeArguments();
		// 获得实体类型
		Class<T> domainClass = (Class<T>) typeArguments[0];
		// 获得实体类型后创建离线条件查询对象
		detachedCriteria = DetachedCriteria.forClass(domainClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model = domainClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将PageBean序列化为json返回
	 */
	public void writePageBean2Json(PageBean pageBean, String[] excludes) {
		// 使用jsonlib将PageBean对象序列化为json数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(pageBean, jsonConfig).toString();

		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将List序列化为json返回
	 */
	public void writeListBean2Json(List list, String[] excludes) {
		// 使用jsonlib将PageBean对象序列化为json数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String json = JSONArray.fromObject(list, jsonConfig).toString();

		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将Object序列化为json返回
	 */
	public void writeObjectBean2Json(Object object, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(object, jsonConfig).toString();

		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
