package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workordermanage;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public List<Workordermanage> findListNotStart();

	public void start(String id);

	public void update(Workordermanage workOrderManage);

}
