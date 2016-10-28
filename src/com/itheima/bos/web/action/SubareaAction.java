package com.itheima.bos.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 分区管理Action
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	/**
	 * 添加分区
	 */
	public String add() {
		subareaService.save(model);
		return "list";
	}

	/**
	 * 分页查询
	 */
	public String pageQuery() {
		DetachedCriteria subareaDC = pageBean.getDetachedCriteria();
		// 从模型对象中获取提交的查询参数
		String addresskey = model.getAddresskey();// 关键字
		// 单表查询
		if (StringUtils.isNotBlank(addresskey)) {
			// 添加查询条件，根据关键字进行模糊查询
			subareaDC.add(Restrictions.like("addresskey",
					"%" + addresskey.trim() + "%"));
		}
		Region region = model.getRegion();
		// 多表查询
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			DetachedCriteria regionDC = subareaDC.createCriteria("region");
			if (StringUtils.isNotBlank(province)) {
				// 添加查询条件，根据省进行模糊查询
				regionDC.add(Restrictions
						.like("province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				// 添加查询条件，根据省进行模糊查询
				regionDC.add(Restrictions.like("city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				// 添加查询条件，根据省进行模糊查询
				regionDC.add(Restrictions
						.like("district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		String[] excludes = new String[] { "decidedzone", "subareas" };
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}

	/**
	 * 导出分区数据到Excel文件并提供下载
	 * 
	 * @throws IOException
	 */
	public String exportXls() throws IOException {
		List<Subarea> list = subareaService.findAll();
		// 使用POI将查询的数据写入Excel文件中
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在工作表中创建一个sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		// 创建单元格
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("关键字");
		headRow.createCell(2).setCellValue("地址");
		headRow.createCell(3).setCellValue("省市区");
		for (Subarea subarea : list) {
			// 创建数据行
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			String id = subarea.getId();
			String addresskey = subarea.getAddresskey();
			String position = subarea.getPosition();
			Region region = subarea.getRegion();
			dataRow.createCell(0).setCellValue(id);
			dataRow.createCell(1).setCellValue(addresskey);
			dataRow.createCell(2).setCellValue(position);
			if (region != null) {
				String province = region.getProvince();
				String city = region.getCity();
				String district = region.getDistrict();
				String info = province + city + district;
				dataRow.createCell(3).setCellValue(info);
			}
		}

		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("user-agent");//浏览器类型
		filename = FileUtils.encodeDownloadFilename(filename, agent );
		
		//根据文件名称动态获得类型
		String contentType = ServletActionContext.getServletContext()
				.getMimeType(filename);
		// 通知客户端下载文件的类型
		ServletActionContext.getResponse().setContentType(contentType);
		
		//指定以附件形式下载,指定文件名称
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);
		// 通过输出流向客户端浏览器写Excel文件
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		workbook.write(out);
		return NONE;
	}
	
	/**
	 * 查询未分配到定区的分区，返回json
	 */
	public String listajax(){
		List<Subarea> list = subareaService.findListNotAssociation();
		String[] excludes = new String[]{"decidedzone","region"};
		this.writeListBean2Json(list, excludes );
		return NONE;
	}
}
