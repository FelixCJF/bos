package com.itheima.bos.dao.base;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.page.PageBean;

/**
 * 通用Dao接口
 * 
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public void delete(T entity);
	public T findById(String id);
	public List<T> findAll();
	public void executeUpdate(String queryName,Object...args);
	public void pageQuery(PageBean pageBean);
	//根据任意条件查询
	public List<T> findByCondition(DetachedCriteria detachedCriteria);
}
