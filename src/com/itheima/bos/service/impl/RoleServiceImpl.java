package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IRoleService;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Resource
	private IRoleDao roleDao;
	@Resource
	private ProcessEngine processEngine;
	//保存角色，同步到activiti的act_id_group
	public void save(Role model, String funcitonIds) {
		roleDao.save(model);//持久对象
		Group group = new GroupEntity();
		group.setId(model.getName());
		group.setName(model.getName());
		processEngine.getIdentityService().saveGroup(group );
		String[] ids = funcitonIds.split(",");
		for (String id : ids) {
			Function function = new Function();
			function.setId(id);
			model.getFunctions().add(function);//角色关联权限
		}
	}

	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}
}	
