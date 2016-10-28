package com.itheima.bos.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * 业务通知单
 * @author zhaoqx
 *
 */
public class Noticebill implements java.io.Serializable {

	private String id;
	private User user;
	private Staff staff;
	private String customerId;
	private String customerName;
	private String delegater;
	private String telephone;
	private String pickaddress;
	private String arrivecity;
	private String product;
	private Date pickdate;
	private Integer num;
	private Double weight;
	private String volume;
	private String remark;
	private String ordertype;//分单类型
	private Set workbills = new HashSet(0);
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDelegater() {
		return delegater;
	}
	public void setDelegater(String delegater) {
		this.delegater = delegater;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPickaddress() {
		return pickaddress;
	}
	public void setPickaddress(String pickaddress) {
		this.pickaddress = pickaddress;
	}
	public String getArrivecity() {
		return arrivecity;
	}
	public void setArrivecity(String arrivecity) {
		this.arrivecity = arrivecity;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Date getPickdate() {
		return pickdate;
	}
	public void setPickdate(Date pickdate) {
		this.pickdate = pickdate;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public Set getWorkbills() {
		return workbills;
	}
	public void setWorkbills(Set workbills) {
		this.workbills = workbills;
	}

}