package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IDecidedzoneService;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private ISubareaDao subareaDao;
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);//持久状态对象
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);//持久状态
			//分区关联定区
			subarea.setDecidedzone(model);
		}
	}
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}
}
