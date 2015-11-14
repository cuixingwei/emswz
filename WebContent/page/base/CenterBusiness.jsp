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
		var url = "exportCenterBusinessDatas?startTime="
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
		})
		grid = $('#grid').datagrid(
				{
					url : 'getCenterBusinessDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					nowrap : false,
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						title : '电话数量',
						colspan : 3
					}, {
						title : '电话来源分类',
						colspan : 4
					}, {
						title : '派车分类',
						colspan : 5
					}, {
						title : '受理时限',
						colspan : 2
					}, {
						title : '派车单位',
						colspan : 2
					}, {
						title : '其他',
						colspan : 2
					} ], [ {
						field : 'inPhone',
						title : '呼入电话数',
						width : "6%",
						align : 'center'
					}, {
						field : 'outPhone',
						title : '呼出电话数',
						width : "6%",
						align : 'center'
					}, {
						field : 'totalPhone',
						title : '合计',
						width : "4%",
						align : 'center'
					}, {
						field : 'helpPhone',
						title : '呼救电话数',
						width : "6%",
						align : 'center'
					}, {
						field : 'phoneOf110',
						title : '110来电',
						width : "5%",
						align : 'center'
					}, {
						field : 'phoneOf119',
						title : '119来电',
						width : "5%",
						align : 'center'
					}, {
						field : 'phoneOfOther',
						title : '其他',
						width : "4%",
						align : 'center'
					}, {
						field : 'spotFirstAid',
						title : '现场急救<br />派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'stationTransfer',
						title : '医院转诊<br />派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'inHospitalTransfer',
						title : '转运派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'safeguard',
						title : '医疗保障<br />派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'noFirstAid',
						title : '非急救派车',
						width : "6%",
						align : 'center'
					}, {
						field : 'firstaidAcceptTime',
						title : '急救受理时长',
						width : "7%",
						align : 'center'
					}, {
						field : 'referralAcceptTime',
						title : '转诊受理时长',
						width : "7%",
						align : 'center'
					}, {
						field : 'centerSendCar',
						title : '中心派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'referralSendCar',
						title : '分诊派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'hungNumbers',
						title : '挂起事件计数',
						width : "7.5%",
						align : 'center'
					}, {
						field : 'stopStaskNumbers',
						title : '中止任务计数',
						width : "7.5%",
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
								<td>查询时间</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
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