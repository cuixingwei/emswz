<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三峡中心医院出诊明细表</title>
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

		$('#taskResult').combobox({
			url : 'getTaskResult',
			valueField : 'id',
			textField : 'name',
			method : 'get'
		});

		$('#area').combobox({
			url : 'getArea',
			valueField : 'id',
			textField : 'name',
			method : 'get'
		});

		grid = $('#grid').datagrid(
				{
					url : 'getCenterHospitalOutDetail',
					pagePosition : 'bottom',
					pagination : true,
					nowrap : false,
					singleSelect : true,
					rownumbers : true,
					nowrap : false,
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'dateTime',
						title : '时间',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'patientName',
						title : '病人<br />姓名',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'sex',
						title : '性别',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'age',
						title : '年龄',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'diagnose',
						title : '诊断',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'diseaseDepartment',
						title : '疾病<br />科别',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'classState',
						title : '分类<br />统计',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'diseaseDegree',
						title : '病情<br />程度',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'treatmentEffet',
						title : '救治<br />效果',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'area',
						title : '区域',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'address',
						title : '现场<br />地址',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'outStation',
						title : '出诊<br />分站',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'outResult',
						title : '出诊<br />结果',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'sendAddress',
						title : '送达<br />地点',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'doctor',
						title : '出诊<br />医生',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'nurse',
						title : '出诊<br />护士',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'driver',
						title : '出诊<br />司机',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'distance',
						title : '出诊<br />里程',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'eventType',
						title : '出诊<br />类型',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'carCode',
						title : '车辆<br />编码',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'poorTime',
						title : '出车<br />差时',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'userTime',
						title : '出诊<br />耗时',
						resizable : true,
						width : "3%",
						align : 'center'
					}, {
						field : 'cureMeasure',
						title : '救治<br />措施',
						resizable : true,
						width : "4%",
						align : 'center'
					} , {
						field : 'dispatcher',
						title : '调度员',
						resizable : true,
						width : "4%",
						align : 'center'
					}] ],
					toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						var varify = cxw.checkStartTimeBeforeEndTime(
								'#startTime', '#endTime');
						if (!varify) {
							$.messager.alert('警告', '结束时间要大于开始时间', 'warning');
						}
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
								<td><input style="width: 100px;" name="station" /></td>
								<td>出诊结果:</td>
								<td><input style="width: 100px;" id="taskResult"
									name="taskResult" /></td>
								<td>出诊医生:</td>
								<td><input style="width: 100px;" name="doctor" /></td>
								<td>出诊护士:</td>
								<td><input style="width: 100px;" name="nurse" /></td>
							</tr>
							<tr>
								<td>出诊司机:</td>
								<td><input style="width: 100px;" name="driver" /></td>
								<td>区域:</td>
								<td><input style="width: 100px;" id="area" name="area" /></td>
								<td>送达地点:</td>
								<td><input style="width: 100px;" name="sendAddress" /></td>
							</tr>
							<tr>
								<td>查询时间:</td>
								<td colspan="3"><input id="startTime" name="startTime"
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