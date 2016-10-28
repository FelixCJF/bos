package com.itheima.bos.test;

import net.sf.json.JSONObject;
import cn.itcast.crm.domain.Customer;

public class JsonLibTest {
	public static void main(String[] args) {
		Customer c = null;
		String json = JSONObject.fromObject(c).toString();
		System.out.println(json);
	}
}
