<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid数据表格</title>
<!-- 引入easyui相关资源文件 -->
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<h2>方式一：将静态html代码渲染为datagrid</h2>
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>xiaoming</td>
				<td>88</td>
			</tr>
			<tr>
				<td>2</td>
				<td>laowang</td>
				<td>3</td>
			</tr>
		</tbody>
	</table>
	
	<h2>方式二：加载远程数据</h2>
	<table class="easyui-datagrid"
	 data-options="url:'${pageContext.request.contextPath }/json/data.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
	</table>
	
	<h2>方式三：使用js代码创建datagrid</h2>
	<script type="text/javascript">
		$(function(){
			//将页面上的table元素展示为datagrid
			$("#grid").datagrid({
				//设置数据表格的属性
				columns:[[
				          {field:'id',title:'编号',checkbox:true},//每个json代表一列
				          {field:'name',title:'姓名'},
				          {field:'age',title:'年龄'}
				          ]],
				 url:'${pageContext.request.contextPath }/json/data.json',
				 rownumbers:true,
				 singleSelect:true,
				 pagination:true,//显示分页条
				 toolbar:[
				          {text:'添加',iconCls:'icon-add',handler:function(){
				        	  alert("add");
				          }}
				          ]//工具栏
			});
		});
	</script>
	<table id="grid">
	</table>
</body>
</html>