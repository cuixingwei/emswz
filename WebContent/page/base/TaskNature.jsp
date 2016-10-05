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
		var url = "exportTaskNature?startTime="
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
					url : 'getTaskNatureDatas',
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
						field : 'outCallType',
						title : '出诊类型',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'times',
						title : '次数',
						resizable : true,
						width : "5%",
						align : 'center',
					}, {
						field : 'takeBacks',
						title : '接回数',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'takeBackRate',
						title : '接回率',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'distance',
						title : '里程',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'averageResponseTime',
						title : '平均反应时间',
						resizable : true,
						width : "10%",
						align : 'center',
					}, {
						field : 'averageTime',
						title : '平均用时',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'emptyCars',
						title : '空车',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'refuseToHospitals',
						title : '拒绝入院',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'deaths',
						title : '死亡',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'shiqu',
						title : '市区',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'wanzhou',
						title : '万州区',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'others',
						title : '其他',
						resizable : true,
						width : "7%",
						align : 'center'
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
								<td>查询时间:</td>
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