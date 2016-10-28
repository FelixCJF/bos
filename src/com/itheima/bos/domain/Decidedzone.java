package com.itheima.bos.domain;

import java.util.HashSet;
import java.util.Set;
/**
 * 定区实体
 */
public class Decidedzone implements java.io.Serializable {

	private String id;//编号
	private Staff staff;//定区关联的取派员 
	private String name;//定区名称
	private Set subareas = new HashSet(0);//当前定区对应的多个分区

	// Constructors

	/** default constructor */
	public Decidedzone() {
	}

	/** minimal constructor */
	public Decidedzone(String id) {
		this.id = id;
	}

	/** full constructor */
	public Decidedzone(String id, Staff staff, String name, Set subareas) {
		this.id = id;
		this.staff = staff;
		this.name = name;
		this.subareas = subareas;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getSubareas() {
		return this.subareas;
	}

	public void setSubareas(Set subareas) {
		this.subareas = subareas;
	}

}