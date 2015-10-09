<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String version = "20150311";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var grid;
	var exportData = function() {
		var url = "exportReturnVisit?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue');
		window.location.href = url;
	};
	/* 初始化页面标签 */
	function init() {
		$('#startTime').datetimebox({
			required : true,
			value : firstOfMouth()
		});
		$('#endTime').datetimebox({
			value : getCurrentTime()
		});

		grid = $('#grid').datagrid(
				{
					url : 'getReturnVisitDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'date',
						title : '日期',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'name',
						title : '姓名',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'phone',
						title : '电话',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'address',
						title : '地址',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'PreDisgnose',
						title : '初步诊断',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'cost',
						title : '收费',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'doctor',
						title : '出诊医生',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'nurse',
						title : '出诊护士',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'driver',
						title : '出诊司机',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'dispatcher',
						title : '调度员',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'satisfyCount',
						title : '满意计数',
						resizable : true,
						width : "24%",
						align : 'center'
					}, {
						field : 'commonCount',
						title : '一般计数',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'unsatisfyCount',
						title : '不满意计数',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'totalScore',
						title : '评分合计',
						resizable : true,
						width : "7%",
						align : 'center'
					} ] ],
					toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						var varify = cxw.checkStartTimeBeforeEndTime(
								'#startTime', '#endTime');
						if (varify) {
							parent.$.messager.progress({
								text : '数据加载中....'
							});
						} else {
							$.messager.alert('警告', '结束时间要大于开始时间', 'warning');
						}
					},
					onLoadSuccess : function(data) {
						parent.$.messager.progress('close');
					}
				});
	}

	$(document).ready(function() {
		init();
		grid.datagrid('load', cxw.serializeObject($('#searchForm')))
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>查询时间:</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td><a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="iconCls:'ext-icon-table_go',plain:true"
					onclick="exportData();">导出</a></td>
			</tr>
		</table>
	</div>

	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>

</body>
</html>