package com.itheima.bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSContext;

/**
 * 自定义realm，实现认证和授权
 * 
 * @author zhaoqx
 * 
 */
public class BOSRealm extends AuthorizingRealm {
	// 注入dao
	@Resource
	private IUserDao userDao;
	@Resource
	private IFunctionDao functionDao;

	// 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证方法。。。。");
		UsernamePasswordToken pwdToken = (UsernamePasswordToken) token;
		String username = pwdToken.getUsername();
		// 根据用户名查询密码，由安全管理器负责比对查询出的数据库中的密码和页面输入的密码是否一致
		User user = userDao.findUserByUsername(username);
		if(user == null){
			return null;
		}
		String dbPassword = user.getPassword();
		//参数一：
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,
				dbPassword, this.getClass().getSimpleName());
		return info;
	}

	// 授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		User user = (User) principals.getPrimaryPrincipal();
		System.out.println(user);
		
		Subject subject = SecurityUtils.getSubject();
		User user2 = (User) subject.getPrincipal();
		
		//User user3 = BOSContext.getLoginUser();
		
		//为所用的用户授予staff权限
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<Function> list = null;//
		//根据当前登录用户，查询用户的角色，根据角色对应获得权限，将权限添加到信息对象中
		if(user.getUsername().equals("admin")){
			//如果是超级管理员，授予所有权限
			list = functionDao.findAll();
		}else{
			//普通用户，根据用户查询对应的权限
			list = functionDao.findFunctionsByUserId(user.getId());
		}
		
		for (Function function : list) {
			//权限的关键字
			String code = function.getCode();
			info.addStringPermission(code);
		}
		
		return info;
	}

}
