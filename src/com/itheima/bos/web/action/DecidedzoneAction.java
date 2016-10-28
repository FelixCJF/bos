package com.itheima.bos.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 定区管理Action 
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//属性驱动，接收分区id数组
	private String[] subareaid;
	/**
	 * 添加定区
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	
	//注入代理对象
	@Resource
	private CustomerService customerService;
	
	/**
	 * 分页查询
	 */
	public String pageQuery(){
		decidedzoneService.pageQuery(pageBean);
		String[] excludes = new String[]{"subareas","decidedzones"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	/**
	 * 获取未关联到定区的客户数据,返回json
	 */
	public String findCustomerNotAssociation(){
		//使用代理对象远程调用crm服务，获取客户数据
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes = new String[]{};
		this.writeListBean2Json(list, excludes);
		return NONE;
	}
	
	/**
	 * 获取已经关联到指定定区的客户数据，返回json
	 */
	public String findCustomerAssociation(){
		String id = model.getId();
		List<Customer> list = customerService.findhasassociationCustomers(id);
		String[] excludes = new String[]{};
		this.writeListBean2Json(list, excludes);
		return NONE;
	}
	
	//接收客户id数组
	private Integer[] customerIds;
	
	/**
	 * 定区关联客户
	 */
	public String assigncustomerstodecidedzone(){
		//调用代理对象，远程调用crm服务，完成定区关联客户
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
	
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
	
	
}
