package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BOSContext;

import freemarker.template.SimpleDate;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Resource
	private INoticebillDao noticebillDao;
	//注入代理对象
	@Resource
	private CustomerService customerService;
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private IWorkbillDao workbillDao;
	
	public void save(Noticebill model) {
		User user = BOSContext.getLoginUser();
		model.setUser(user);//当前登录用户
		noticebillDao.save(model);//持久对象
		
		//自动分单----为当前客户找到一个取派员，取件
		//取件地址
		String pickaddress = model.getPickaddress();
		//根据取件地址获取定区ID
		String decidedzoneId = customerService.findDecidedzoneidByPickAddress(pickaddress);
		if(decidedzoneId != null){
			model.setOrdertype("自动");
			//匹配成功，可以自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();//获得定区的负责人
			
			model.setStaff(staff);//建立业务通知单和取派员关系
			
			//为当前匹配到取派员产生一个工单
			Workbill workbill = new Workbill();
			workbill.setNoticebill(model);//关联业务通知单
			workbill.setStaff(staff);//关联取派员
			workbill.setType("新");//类型
			workbill.setPickstate("未取件");//取件状态
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//系统时间
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setRemark(model.getRemark());//备注
			//保存工单
			workbillDao.save(workbill);
			
			//调用短信接口向取派员发送短信
			
		}else{
			//匹配失败，转入人工分单
			model.setOrdertype("人工");
		}
		
	}
}
