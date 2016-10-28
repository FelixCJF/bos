package com.itheima.bos.service.impl;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private ProcessEngine processEngine;
	public User login(User model) {
		String password = model.getPassword();
		//使用md5加密
		password = MD5Utils.md5(password);
		User user = userDao.findUserByUsernameAndPassword(model.getUsername(),password);
		return user;
	}

	//修改密码
	public void editPassword(String password, String id) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("editPassword", password,id);
	}
	
	//添加用户,同步到activiti的act_id_user  act_id_mebership
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		//使用md5加密
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象
		org.activiti.engine.identity.User actUser = new UserEntity();
		actUser.setId(user.getId());
		processEngine.getIdentityService().saveUser(actUser);//操作act_id_user
		for (String id : roleIds) {
			Role role = roleDao.findById(id);
			//用户关联角色 
			user.getRoles().add(role);
			processEngine.getIdentityService().createMembership(actUser.getId(), role.getName());
		}
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}
}

