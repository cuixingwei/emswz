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
		var url = "exportCarStateChangeDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&eventName="
				+ $('#eventName').val() + "&carCode="
				+ $('#carCode').combobox('getValue');
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
		})
		$('#carCode').combobox({
			url : 'getCars',
			valueField : 'carCode',
			textField : 'carIdentification',
			method : 'get'
		});
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'getCarStateChangeDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'eventName',
						title : '事件名称',
						resizable : true,
						width : "17%",
						align : 'center'
					}, {
						field : 'carCode',
						title : '车辆',
						resizable : true,
						width : "13%",
						align : 'center',
					}, {
						field : 'carState',
						title : '车辆状态',
						resizable : true,
						width : "13%",
						align : 'center'
					}, {
						field : 'recordTime',
						title : '记录时刻',
						resizable : true,
						width : "17%",
						align : 'center'
					}, {
						field : 'recordClass',
						title : '记录类型',
						resizable : true,
						width : "13%",
						align : 'center'
					}, {
						field : 'seatCode',
						title : '坐席号',
						resizable : true,
						width : "13%",
						align : 'center'
					}, {
						field : 'dispatcher',
						title : '操作人',
						resizable : true,
						width : "13%",
						align : 'center',
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
						$(this).datagrid("autoMergeCells", [ 'eventName' ]);
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
								<td>事件名称:</td>
								<td><input style="width: 150px;" id="eventName"
									name="eventName" /></td>
								<td>&nbsp;车辆:</td>
								<td><input style="width: 100em;" id="carCode"
									name="carCode" /></td>
								<td>&nbsp;查询时间:</td>
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