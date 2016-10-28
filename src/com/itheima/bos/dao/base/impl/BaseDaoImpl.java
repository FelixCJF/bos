package com.itheima.bos.dao.base.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.page.PageBean;

/**
 * 通用dao实现
 * 
 *
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T>{
	Class<T> entityClass;//实体类型 
	
	//注入SessionFactory对象
	@Resource
	public void setSF(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 在构造方法中获取实体类型
	 */
	public BaseDaoImpl() {
		//获得父类（BaseDaoImpl）类型
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得泛型数组
		Type[] typeArguments = superclass.getActualTypeArguments();
		//获得实体类型
		entityClass = (Class<T>) typeArguments[0];
	}
	
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public T findById(String id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	//执行任意HQL
	public void executeUpdate(String queryName, Object... args) {
		Session session = this.getSession();
		//命名查询
		Query query = session.getNamedQuery(queryName);
		int length = args.length;
		int i = 0;
		for (Object arg : args) {
			//为？赋值
			query.setParameter(i++, arg);
		}
		query.executeUpdate();
	}

	/**
	 * 通用分页查询方法
	 */
	public void pageQuery(PageBean pageBean) {
		//查询条件
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//指定由hibernate框架发出select count(id) from xxx....
		detachedCriteria.setProjection(Projections.rowCount());
		//查询总记录数
		List<Long> countList = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		int total = countList.get(0).intValue();
		pageBean.setTotal(total);
		//指定由hibernate框架发出select * from xxx... 
		detachedCriteria.setProjection(null);
		//改变hibernate的映射行为，从表中查询的数据对应包装成pojo对象
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		
		int firstResult = (currentPage - 1 ) * pageSize;
		int maxResults = pageSize;
		
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public List<T> findByCondition(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
