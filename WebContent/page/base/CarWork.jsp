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
		var url = "exportCarWorkDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&carCode="
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
		grid = $('#grid').datagrid(
				{
					url : 'getCarWorkDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					nowrap : false,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'carCode',
						title : '车辆',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'outCarNumbers',
						title : '出车次数',
						resizable : true,
						width : "13%",
						align : 'center'
					}, {
						field : 'averageOutCarTimes',
						title : '平均出车时长',
						resizable : true,
						width : "13%",
						align : 'center'
					}, {
						field : 'arriveSpotNumbers',
						title : '到达现场次数',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'averageArriveSpotTimes',
						title : '平均到达现场时长',
						resizable : true,
						width : "15%",
						align : 'center'
					}, {
						field : 'pauseNumbers',
						title : '暂停次数',
						resizable : true,
						width : "10%",
						align : 'center',
					}, {
						field : 'outDistance',
						title : '出诊里程统计',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'distance',
						title : '里程统计',
						resizable : true,
						width : "12%",
						align : 'center',
					} ] ],
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
								<td>&nbsp;车辆:</td>
								<td><input style="width: 120em;" id="carCode"
									name="carCode" /></td>
								<td>查询时间:</td>
								<td colspan="3"><input id="startTime" name="startTime"
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