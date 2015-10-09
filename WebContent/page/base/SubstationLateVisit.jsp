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
		var url = "exportSubstationLateVisitDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&outCarTimesMax="
				+ $('#outCarTimesMax').val() + "&station="
				+ $('#station').combobox('getValue') + "&outCarTimesMin="
				+ $('#outCarTimesMin').val();
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
		$('#outCarTimesMin').numberbox({
			min : 0,
			value : 3
		});
		$('#station').combobox({
			url : 'getStations',
			valueField : 'stationCode',
			textField : 'stationName',
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'getSubstationLateVisitDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'siteAddress',
						title : '现场地址',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'eventType',
						title : '事件类型',
						resizable : true,
						width : "6%",
						align : 'center',
					}, {
						field : 'carCode',
						title : '车辆标识',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'acceptTime',
						title : '受理时刻',
						resizable : true,
						width : "12%",
						align : 'center'
					}, {
						field : 'createTaskTime',
						title : '生成任务时刻',
						resizable : true,
						width : "12%",
						align : 'center',
					}, {
						field : 'outCarTime',
						title : '出车时刻',
						resizable : true,
						width : "12%",
						align : 'center'
					}, {
						field : 'outCarTimes',
						title : '出车时长',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'taskResult',
						title : '出车结果',
						resizable : true,
						width : "8%",
						align : 'center',
					}, {
						field : 'remark',
						title : '任务备注',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'dispatcher',
						title : '调度员',
						resizable : true,
						width : "7%",
						align : 'center'
					} ] ],
					toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						var varify = cxw.checkStartTimeBeforeEndTime(
								'#startTime', '#endTime');
						var isMinBeforeMax = cxw.checkMinBeforeMax(
								'#outCarTimesMin', '#outCarTimesMax');
						if (!varify) {
							$.messager.alert('警告', '结束时间要大于开始时间', 'warning');
						}
						if (!isMinBeforeMax) {
							$.messager.alert('警告', '出车时长范围错误,较小值应该小于较大值',
									'warning');
						}
						if (varify && isMinBeforeMax) {
							parent.$.messager.progress({
								text : '数据加载中....'
							});
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
								<td>分站:</td>
								<td><input style="width: 120em;" id="station"
									name="station" /></td>
								<td>&nbsp;出车时长:</td>
								<td><input type="text" style="width: 80px;"
									id="outCarTimesMin" name="outCarTimesMin"
									class="easyui-numberbox" data-options="precision:0" />至<input
									type="text" style="width: 80px;" id="outCarTimesMax"
									name="outCarTimesMax" class="easyui-numberbox" /></td>
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