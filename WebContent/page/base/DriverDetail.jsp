<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驾驶员出诊明细表</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var grid;
	var exportData = function() {
		var url = "exportDriverDetail?startTime="
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

		$('#taskResult').combobox({
			url : 'getTaskResult',
			valueField : 'id',
			textField : 'name',
			method : 'get'
		});

		grid = $('#grid').datagrid(
				{
					url : 'getDriverDetail',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'dateTime',
						title : '时间',
						resizable : true,
						width : "14%",
						align : 'center',
					}, {
						field : 'patientName',
						title : '病人姓名',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'address',
						title : '现场地址',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'outStation',
						title : '出诊分站',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'outResult',
						title : '出诊结果',
						resizable : true,
						width : "14%",
						align : 'center',
					}, {
						field : 'driver',
						title : '出诊司机',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'distance',
						title : '出诊里程',
						resizable : true,
						width : "14%",
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
		grid.datagrid('load', cxw.serializeObject($('#searchForm')));
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
								<td>出诊分站:</td>
								<td><input style="width: 120px;" name="station" /></td>
								<td>出诊结果:</td>
								<td><input style="width: 100px;" id="taskResult"
									name="taskResult" /></td>
								<td>出诊司机:</td>
								<td><input style="width: 100px;" name="driver" /></td>
								<td>查询时间:</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /><input type="hidden" value="3"
									name="doctorOrNurseOrDriver" /></td>
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