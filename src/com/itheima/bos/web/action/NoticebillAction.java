package com.itheima.bos.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 业务通知单管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//注入代理对象，调用crm服务
	@Resource
	private CustomerService customerService;
	
	/**
	 * 根据手机号查询客户信息
	 */
	public String findCustomerByPhone(){
		//调用crm服务
		Customer customer = customerService.findCustomerByPhone(model.getTelephone());
		this.writeObjectBean2Json(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * 保存业务通知单
	 */
	public String add(){
		noticebillService.save(model);
		return "toAddUI";
	}
}
