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
		var url = "exportRingToAcceptDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&overtimes="
				+ $('#overtimes').val() + "&dispatcher="
				+ $('#dispatcher').combobox('getValue');
		window.location.href = url;
	};
	/* 初始化页面标签 */
	function init() {
		$('#overtimes').numberbox({
			min : 0,
			value : 15
		});
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
					url : 'getRingToAcceptDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'ringTime',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'dispatcher',
						title : '调度员',
						width : "15%",
						align : 'center'
					}, {
						field : 'ringTime',
						title : '电话振铃时刻',
						width : "15%",
						align : 'center',
						sortable : true
					}, {
						field : 'callTime',
						title : '通话时刻',
						width : "15%",
						align : 'center'
					}, {
						field : 'ringDuration',
						title : '响铃时长(秒)',
						width : "20%",
						align : 'center'
					}, {
						field : 'acceptCode',
						title : '受理台号',
						width : "15%",
						align : 'center'
					}, {
						field : 'acceptRemark',
						title : '受理备注',
						width : "19.9%",
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
						$(this).datagrid("autoMergeCells", [ 'dispatcher' ]);
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
								<td>超时时长:</td>
								<td><input type="text" style="width: 80em;" id="overtimes"
									name="overtimes" class="easyui-numberbox"
									data-options="min:0,precision:0" /></td>
								<td>调度员:</td>
								<td><input style="width: 80em;" id="dispatcher"
									name="dispatcher" /></td>
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