package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 取派员操作Action
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	/**
	 * 添加取派员
	 */
	public String add(){
		staffService.save(model);
		return "list";
	}
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean, new String[] { "currentPage", "pageSize","detachedCriteria", "decidedzones" });
		return NONE;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	//属性驱动
	private String ids;
	
	/**
	 * 批量作废功能
	 */
	public String delete(){
		staffService.deleteBatch(ids);
		return "list";
	}
	
	/**
	 * 修改取派员信息
	 */
	public String edit(){
		//查询原始数据
		Staff staff = staffService.findById(model.getId());
		//使用表单提交的数据覆盖原始数据
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setStandard(model.getStandard());
		staff.setHaspda(model.getHaspda());
		
		staffService.update(staff);
		return "list";
	}
	
	/**
	 * 查询为作废的取派员，返回json
	 */
	public String listajax(){
		List<Staff> list = staffService.findListNotDelete();
		String[] excludes = new String[]{"decidedzones"};
		this.writeListBean2Json(list, excludes );
		return NONE;
	}
	
}
