package com.itheima.bos.web.interceptor;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInerceptor extends MethodFilterInterceptor{
	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		String url = namespace + actionName;
		System.out.println("拦截到url：" + url);
		//从session中获取当前用户
		User user = BOSContext.getLoginUser();
		if(user == null){
			//没有登录
			return "login";
		}else{
			//放行
			return invocation.invoke();
		}
	}
}
