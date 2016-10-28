package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	public List<Function> findFunctionsByUserId(String userid) {
		String hql = "select f from Function f left outer join " +
				"f.roles r left outer join r.users u where u.id = ?";
		return this.getHibernateTemplate().find(hql,userid);
	}

	public List<Function> findAllMenu() {
		String hql = "from Function f where f.generatemenu = '1' order by f.zindex";
		return this.getHibernateTemplate().find(hql);
	}

	public List<Function> findMenuByUserId(String id) {
		String hql = "select distinct f from Function f left outer join " +
				"f.roles r left outer join r.users u where u.id = ? and " +
				"f.generatemenu = '1' order by f.zindex";
		return this.getHibernateTemplate().find(hql,id);
	}

}
