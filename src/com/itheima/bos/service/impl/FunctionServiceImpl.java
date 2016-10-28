package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IFunctionService;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{
	@Autowired
	private IFunctionDao functionDao;

	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null){
			String id = parentFunction.getId();
			if(StringUtils.isBlank(id)){
				model.setParentFunction(null);
			}
		}
		functionDao.save(model);
	}

	//查询所有权限菜单数据
	public List<Function> findAllMenu() {
		return functionDao.findAllMenu();
	}
	
	public List<Function> findMenuByUserId(String id) {
		return functionDao.findMenuByUserId(id);
	}
}
