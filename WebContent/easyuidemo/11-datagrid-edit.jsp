<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid编辑功能</title>
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
<script
	src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		var index = -1;
		$("#grid").datagrid({
			columns : [ [ {
				field : 'id',
				checkbox : true,
			}, {
				field : 'name',
				title : '姓名',
				width : 120,
				align : 'center',
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			}, {
				field : 'telephone',
				title : '手机号',
				width : 120,
				align : 'center',
				editor : {
					type : 'validatebox',
					options : {
						required : true
					}
				}
			} ] ],
			//事件：结束编辑状态时触发
			onAfterEdit:function(rowIndex, rowData, changes){
				//发送ajax请求，将数据提交到服务端修改数据库
			},
			url : '${pageContext.request.contextPath}/json/staff.json',
				toolbar : [ {
								id : 'button-add',
								text : '增加一行',
								iconCls : 'icon-add',
								handler : function(){
									$("#grid").datagrid("insertRow",{//插入一行
										index:0,//在第一行插入
										row:{}//空行
									});
									index = 0;
									//开启第一行的编辑状态
									$("#grid").datagrid("beginEdit",index);//开启第一行编辑状态
								}
							}, //按钮
							{
								id : 'button-save',
								text : '保存',
								iconCls : 'icon-save',
								handler : function(){
									//结束编辑状态
									$("#grid").datagrid("endEdit",index);//开启第一行编辑状态
								}
							},
							{
								id : 'button-eidt',
								text : '编辑',
								iconCls : 'icon-save',
								handler : function(){
									var rows = $("#grid").datagrid("getSelections");
									if(rows.length == 1){
										//获得当前选中行的索引
										index = $("#grid").datagrid("getRowIndex",rows[0]);
										$("#grid").datagrid("beginEdit",index);
									}
								}
							}
						]
		});
	});
</script>
</head>
<body>
	<table id="grid"></table>
</body>
</html>