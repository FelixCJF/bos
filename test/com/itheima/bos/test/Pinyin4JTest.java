package com.itheima.bos.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.itheima.bos.utils.PinYin4jUtils;

public class Pinyin4JTest {
	@Test
	public void test1() {
		String city = "北京市";
		String province = "河北省";
		String city2 = "石家庄市";
		String district = "长安区";

		// 城市编码 北京市-----》beijing
		String str1 = PinYin4jUtils.hanziToPinyin(
				city.substring(0, city.length() - 1), "");
		System.out.println(str1);

		String info = province.substring(0, province.length() - 1)
				+ city2.substring(0, city2.length() - 1)
				+ district.substring(0, district.length() - 1);
		// 简码河北省石家庄市长安区-----》hbsjzca
		String[] strings = PinYin4jUtils.getHeadByString(info);
		String join = StringUtils.join(strings, "");
		System.out.println(join);
	}
}
