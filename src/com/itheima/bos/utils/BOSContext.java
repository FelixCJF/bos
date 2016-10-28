package com.itheima.bos.utils;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.opensymphony.xwork2.ActionContext;

public class BOSContext {
	public static User getLoginUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		//return (User) ActionContext.getContext().getSession().get("loginUser");
	}
}
