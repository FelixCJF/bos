package com.itheima.bos.domain;

import java.sql.Timestamp;
/**
 * 工单
 * @author zhaoqx
 *
 */
public class Workbill implements java.io.Serializable {

	// Fields

	private String id;
	private Noticebill noticebill;//工单关联的业务通知单
	private Staff staff;//工单关联的取派员
	private String type;//工单的类型:新、追、改、销
	private String pickstate;//取件状态：未取件、取件中、已取件
	private Timestamp buildtime;//工单产生的系统时间
	private Integer attachbilltimes;//追单次数
	private String remark;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Noticebill getNoticebill() {
		return noticebill;
	}
	public void setNoticebill(Noticebill noticebill) {
		this.noticebill = noticebill;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPickstate() {
		return pickstate;
	}
	public void setPickstate(String pickstate) {
		this.pickstate = pickstate;
	}
	public Timestamp getBuildtime() {
		return buildtime;
	}
	public void setBuildtime(Timestamp buildtime) {
		this.buildtime = buildtime;
	}
	public Integer getAttachbilltimes() {
		return attachbilltimes;
	}
	public void setAttachbilltimes(Integer attachbilltimes) {
		this.attachbilltimes = attachbilltimes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}