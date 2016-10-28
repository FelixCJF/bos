package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSContext;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Controller//("abc")
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	//提供属性接收验证码
	private String checkcode;
	/**
	 * 使用shiro方式进行认证
	 */
	public String login(){
		//从session中获取自动生成的验证码
		String key = (String) ActionContext.getContext().getSession().get("key");
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//使用shiro方式进行认证
			String username = model.getUsername();
			String password = model.getPassword();
			password = MD5Utils.md5(password);
			Subject subject = SecurityUtils.getSubject();//主体,当前状态为“未认证”状态
			AuthenticationToken token = new UsernamePasswordToken(username, password);//用户名密码令牌
			try{
				subject.login(token);//使用subject调用SecurityManager,安全管理器调用Realm
				User user = (User) subject.getPrincipal();
				//登录成功,将User对象放入session 
				//ActionContext.getContext().getSession().put("loginUser", user);
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			}catch (UnknownAccountException e) {//用户名不存在异常
				e.printStackTrace();
				return "login";
			}catch (IncorrectCredentialsException e) {//密码错误异常
				e.printStackTrace();
				return "login";
			}
			return "home";
		}else{
			//验证码有误,添加错误信息，跳转到登录页面
			this.addActionError(this.getText("checkcodeError"));
			return "login";
		}
	}
	
	public String login_bak(){
		//从session中获取自动生成的验证码
		String key = (String) ActionContext.getContext().getSession().get("key");
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//验证码正确
			User user = userService.login(model);
			if(user != null){
				//登录成功,将User对象放入session 
				ActionContext.getContext().getSession().put("loginUser", user);
				return "home";
			}else{
				//登录失败,添加错误信息，跳转到登录页面
				this.addActionError(this.getText("loginError"));
				return "login";
			}
		}else{
			//验证码有误,添加错误信息，跳转到登录页面
			this.addActionError(this.getText("checkcodeError"));
			return "login";
		}
	}
	
	
	/**
	 * 注销
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * 修改密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		//Subject subject = SecurityUtils.getSubject();
		//subject.checkPermission("abc");
		
		String password = model.getPassword();
		User user = BOSContext.getLoginUser();
		String id = user.getId();
		String flag = "1";
		try{
			userService.editPassword(password,id);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	//接受角色ID数组
	private String[] roleIds;
	/**
	 * 添加用户
	 */
	public String add(){
		userService.save(model,roleIds);
		return "list";
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery(){
		userService.pageQuery(pageBean);
		String[] excludes = new String[]{ "currentPage", 
				"pageSize","detachedCriteria","noticebills","roles"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	/**
	 * 根据登录人查询对应的权限菜单数据
	 */
	public String findMenu(){
		User user = BOSContext.getLoginUser();
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//超级管理员，加载所有的菜单数据
			list = functionService.findAllMenu();
		}else{
			//根据用户ID查询菜单数据
			list = functionService.findMenuByUserId(user.getId());
		}
		
		String[] excludes = new String[]{"parentFunction","children","roles"};
		this.writeListBean2Json(list, excludes );
		return NONE;
	}
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
