package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.page.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(String password, String id);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
