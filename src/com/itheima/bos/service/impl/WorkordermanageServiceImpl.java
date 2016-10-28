package com.itheima.bos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	@Autowired
	private IWorkordermanageDao workordermanageDao;

	public void save(Workordermanage model) {
		model.setUpdatetime(new Date());
		workordermanageDao.save(model);
	}

	public List<Workordermanage> findListNotStart() {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Workordermanage.class);
		// 添加条件：start == 0
		detachedCriteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByCondition(detachedCriteria);
	}

	@Resource
	private ProcessEngine processEngine;

	// 启动物流配送流程，将工作单对象设置到流程变量中
	public void start(String id) {
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");
		String processDefinitionKey = "transfer";// 物流配送流程key
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("业务数据", workordermanage);
		processEngine.getRuntimeService().startProcessInstanceByKey(
				processDefinitionKey, variables);
	}

	public void update(Workordermanage workOrderManage) {
		workordermanageDao.update(workOrderManage);
	}
}
