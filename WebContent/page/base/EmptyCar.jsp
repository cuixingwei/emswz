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
		var url = "exportEmptyCarData?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&station="
				+ $('#station').combobox('getValue') + "&emptyReason="
				+ $('#emptyReason').combobox('getValue') + "&dispatcher="
				+ $('#dispatcher').combobox('getValue');
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
		$('#station').combobox({
			url : 'getStations',
			valueField : 'stationCode',
			textField : 'stationName',
			method : 'get',
			onSelect : function(rec) {
				var url = 'getCars?id=' + rec.stationCode;
				$('#carCode').combobox('reload', url);
			}
		});
		$('#emptyReason').combobox({
			url : 'getEmptyReasons',
			valueField : 'code',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'getEmptyCarData',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'acceptTime',
						title : '受理时间',
						resizable : true,
						width : "20%",
						align : 'center'
					}, {
						field : 'sickAddress',
						title : '患者地址',
						resizable : true,
						width : "20%",
						align : 'center',
					}, {
						field : 'dispatcher',
						title : '调度员',
						resizable : true,
						width : "20%",
						align : 'center'
					}, {
						field : 'emptyRunTimes',
						title : '空跑时间',
						resizable : true,
						width : "20%",
						align : 'center',
					}, {
						field : 'emptyReason',
						title : '空炮原因',
						resizable : true,
						width : "19%",
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
								<td>&nbsp;分站:</td>
								<td><input style="width: 120em;" id="station"
									name="station" /></td>
								<td>&nbsp;空车原因:</td>
								<td><input style="width: 120em;" id="emptyReason"
									name="emptyReason" /></td>
							</tr>
							<tr>
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