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
		var doctorOrNurseOrDriver = $("#doctorOrNurseOrDriver").val();
		var url = "exportDocterNurseDriverOutCarTimeDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue')
				+ "&doctorOrNurseOrDriver=" + doctorOrNurseOrDriver;
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
		grid = $('#grid').datagrid(
				{
					url : 'getDocterNurseDriverOutCarTimeDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'name',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'name',
						title : '姓名',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'helpNumbers',
						title : '急救次数',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'helpNormalNumbers',
						title : '急救标准出车计数',
						resizable : true,
						width : "12%",
						align : 'center'
					}, {
						field : 'rate1',
						title : '占比',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'helpLateNumbers',
						title : '急救蜗牛出车计数',
						resizable : true,
						width : "12%",
						align : 'center'
					}, {
						field : 'rate2',
						title : '占比',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'tranforNumbers',
						title : '转诊次数',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'tranforNormalNumbers',
						title : '转诊标准次数',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'rate3',
						title : '占比',
						resizable : true,
						width : "7%",
						align : 'center',
					}, {
						field : 'tranforLateNumbers',
						title : '转诊蜗牛出车次数',
						resizable : true,
						width : "13%",
						align : 'center'
					}, {
						field : 'rate4',
						title : '占比',
						resizable : true,
						width : "7%",
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
								<td>选择</td>
								<td><select name="doctorOrNurseOrDriver"
									id="doctorOrNurseOrDriver"><option value="1">医生</option>
										<option value="2">护士</option>
										<option value="3">驾驶员</option></select></td>
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