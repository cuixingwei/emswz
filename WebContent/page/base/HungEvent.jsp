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
		var url = "exporthungEventData?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&dispatcher="
				+ $('#dispatcher').combobox('getValue') + "&hungReason="
				+ $('#hungReason').combobox('getValue');
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
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		$('#hungReason').combobox({
			url : 'getHungReasons',
			valueField : 'code',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'gethungEventData',
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
						width : "14%",
						align : 'center'
					}, {
						field : 'acceptType',
						title : '受理类型',
						resizable : true,
						width : "14%",
						align : 'center',
					}, {
						field : 'dispatcher',
						title : '操作人',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'hungTime',
						title : '挂起时刻',
						resizable : true,
						width : "14%",
						align : 'center',
					}, {
						field : 'hungReason',
						title : '挂起原因',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'endTime',
						title : '结束时刻',
						resizable : true,
						width : "14%",
						align : 'center',
					}, {
						field : 'hungtimes',
						title : '时长',
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
								<td>调度员:</td>
								<td><input style="width: 120em;" id="dispatcher"
									name="dispatcher" /></td>
								<td>&nbsp;挂起原因:</td>
								<td><input style="width: 120em;" id="hungReason"
									name="hungReason" /></td>
								<td>&nbsp;查询时间:</td>
								<td colspan="3"><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom_out',plain:true"
									onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置查询</a></td>
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