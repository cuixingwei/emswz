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
		var url = "exportAnswerAlarmDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&alarmPhone="
				+ $('#alarmPhone').val() + "&dispatcher="
				+ $('#dispatcher').combobox('getValue') + "&siteAddress="
				+ $('#siteAddress').val();
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
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'getAnswerAlarmDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'answerAlarmTime',
						title : '接诊时间',
						width : "15%",
						align : 'center'
					}, {
						field : 'alarmPhone',
						title : '报警电话',
						width : "10%",
						align : 'center',
						sortable : true
					}, {
						field : 'relatedPhone',
						title : '相关电话',
						width : "10%",
						align : 'center'
					}, {
						field : 'siteAddress',
						title : '报警地址',
						width : "15%",
						align : 'center'
					}, {
						field : 'judgementOnPhone',
						title : '电话判断',
						width : "15%",
						align : 'center'
					}, {
						field : 'station',
						title : '出车急救站',
						width : "10%",
						resizable : true,
						align : 'center'
					}, {
						field : 'sendCarTime',
						title : '派车时间',
						width : "14%",
						align : 'center'
					}, {
						field : 'dispatcher',
						title : '调度员',
						width : "10%",
						resizable : true,
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
								<td>报警电话:</td>
								<td><input type="text" style="width: 80px" id="alarmPhone"
									name="alarmPhone" /></td>
								<td>调度员:</td>
								<td><input style="width: 100em" id="dispatcher"
									name="dispatcher" /></td>
								<td>报警地点:</td>
								<td><input type="text" style="width: 80px" id="siteAddress"
									name="siteAddress" /></td>
								<td>查询时间</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a><a
									href="javascript:void(0);" class="easyui-linkbutton"
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