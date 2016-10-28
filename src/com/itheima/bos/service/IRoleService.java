package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Role;
import com.itheima.bos.page.PageBean;

public interface IRoleService {

	public void save(Role model, String funcitonIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
