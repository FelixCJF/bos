package com.itheima.bos.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

/**
 * 测试ＰＯＩ解析Ｅｘｃｅｌ文件
 * 
 * 
 */
public class POITest {
	@Test
	public void test1() throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(
				"d:\\abc.xls")));
		HSSFSheet sheet = workbook.getSheetAt(0);//读取第一个sheet页
		for (Row row : sheet) {//循环读取每一行
			String v1 = row.getCell(0).getStringCellValue();//获得当前行的第一个单元格的文字内容
			String v2 = row.getCell(1).getStringCellValue();
			String v3 = row.getCell(2).getStringCellValue();
			String v4 = row.getCell(3).getStringCellValue();
			String v5 = row.getCell(4).getStringCellValue();
			System.out.println(v1 + " " + v2 + " "  +v3 + " " + v4 + " " + v5);
		}
	}
}
