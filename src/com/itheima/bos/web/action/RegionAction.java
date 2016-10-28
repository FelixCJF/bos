package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.page.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 区域管理Action
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	//接收上传的文件
	private File myFile;
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	
	/**
	 *  批量导入方法
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws FileNotFoundException, IOException{
		List<Region> list = new ArrayList<Region>();
		//使用POI解析上传的Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myFile));
		HSSFSheet sheet = workbook.getSheet("Sheet1");//读取名称为Sheet1的页
		for (Row row : sheet) {
			int rowNum = row.getRowNum();//行号
			if(rowNum == 0){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();//获得当前行第一个单元格内容
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			
			//城市编码
			String citycode = PinYin4jUtils.hanziToPinyin(
					city.substring(0, city.length() - 1), "");

			String info = province.substring(0, province.length() - 1)
					+ city.substring(0, city.length() - 1)
					+ district.substring(0, district.length() - 1);
			// 简码河北省石家庄市长安区-----》hbsjzca
			String[] strings = PinYin4jUtils.getHeadByString(info);
			//简码
			String shortcode = StringUtils.join(strings, "");
			Region region = new Region(id, province, city, district, postcode, shortcode, citycode, null);
			list.add(region);
		}
		regionService.saveBatch(list);
		return NONE;
	}
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	@RequiresPermissions(value="region")//执行这个方法需要region.query权限
	//@RequiresRoles(value="admin")
	public String pageQuery() throws IOException{
		regionService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	
	private String q;//模糊查询参数
	
	/**
	 *  查询所有区域返回json
	 */
	public String list(){
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			//根据q进行模糊查询
			list = regionService.findByQ(q.trim());
		}else{
			//查询所有
			list = regionService.findAll();
		}
		String[] excludes = new String[]{"subareas"};
		this.writeListBean2Json(list, excludes);
		return NONE;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
