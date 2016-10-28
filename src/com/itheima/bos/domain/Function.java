package com.itheima.bos.domain;

import java.util.HashSet;
import java.util.Set;
/**
 * 权限实体
 * @author zhaoqx
 *
 */
public class Function implements java.io.Serializable {

	// Fields

	private String id;//编号
	private Function parentFunction;//当前权限对应的上一级权限
	private String name;//权限名称
	private String code;//关键字
	private String description;//描述
	private String mypage;//访问URL
	private String generatemenu;//当前权限是否生成到菜单  1:生成 0:不生成
	private Integer zindex;//排序
	private Set children = new HashSet(0);//当前权限对应的多个下级权限
	private Set roles = new HashSet(0);//当前权限对应的多个角色
	
	public String getpId() {
		if(parentFunction == null){
			return "0";
		}
		return parentFunction.getId();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Function getParentFunction() {
		return parentFunction;
	}
	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getGeneratemenu() {
		return generatemenu;
	}
	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}
	public Integer getZindex() {
		return zindex;
	}
	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}
	public Set getChildren() {
		return children;
	}
	public void setChildren(Set children) {
		this.children = children;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}


	public String getMypage() {
		return mypage;
	}


	public void setMypage(String mypage) {
		this.mypage = mypage;
	}
}