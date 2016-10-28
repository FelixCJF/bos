package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.domain.Region;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{

	/**
	 * 根据条件查询区域信息
	 */
	public List<Region> findByCondition(String info) {
		String hql = "from Region r where r.province like ? or r.city like ? or r.district like ?";
		return this.getHibernateTemplate().find(hql,"%"+info+"%","%"+info+"%","%"+info+"%");
	}

}
