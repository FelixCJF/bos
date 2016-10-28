package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.service.IRegionService;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Resource
	private IRegionDao regionDao;
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}
	public List<Region> findAll() {
		return regionDao.findAll();
	}
	public List<Region> findByQ(String q) {
		/*DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
		Criterion lhs = Restrictions.like("province", "%"+q+"%");
		Criterion rhs = Restrictions.like("city", "%"+q+"%");
		detachedCriteria.add(Restrictions.or(lhs, rhs));
		//SimpleExpression expression = Restrictions.like("province", "%"+q+"%");
		detachedCriteria.add(Restrictions.like("province", "%"+q+"%"));
		detachedCriteria.add(Restrictions.like("city", "%"+q+"%"));
		detachedCriteria.add(Restrictions.like("district", "%"+q+"%"));
		return regionDao.findByCondition(detachedCriteria);*/
		return regionDao.findByCondition(q);
	}
}
