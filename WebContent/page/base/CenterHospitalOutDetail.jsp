<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医生护士出诊明细表</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var grid;
	var exportData = function() {
		var url = "exportCenterHospitalOutDetail?startTime="
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
					url : 'getCenterHospitalOutDetail',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					nowrap  : false,
					singleSelect : true,
					rownumbers : true,
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'dateTime',
						title : '时间',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'patientName',
						title : '病人姓名',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'sex',
						title : '性别',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'age',
						title : '年龄',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'diagnose',
						title : '诊断',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'diseaseDepartment',
						title : '疾病科别',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'classState',
						title : '分类统计',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'diseaseDegree',
						title : '病情程度',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'treatmentEffet',
						title : '救治效果',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'area',
						title : '区域',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'address',
						title : '现场地址',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'outStation',
						title : '出诊分站',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'outResult',
						title : '出诊结果',
						resizable : true,
						width : "5%",
						align : 'center',
					}, {
						field : 'sendAddress',
						title : '送达地点',
						resizable : true,
						width : "5%",
						align : 'center',
					}, {
						field : 'doctor',
						title : '出诊医生',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'nurse',
						title : '出诊护士',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'driver',
						title : '出诊司机',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'distance',
						title : '出诊里程',
						resizable : true,
						width : "5%",
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