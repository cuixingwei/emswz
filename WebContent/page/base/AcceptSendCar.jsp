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
		var url = "exportAcceptSendCarDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&overtimes="
				+ $('#overtimes').val() + "&dispatcher="
				+ $('#dispatcher').combobox('getValue');
		window.location.href = url;
	};
	var showFun = function(id) {
		var dialog = parent.cxw.modalDialog({
			title : '事件详细信息',
			url : 'base/AcceptSendCarDetailForm.jsp?id=' + id
		});
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
		$('#overtimes').numberbox({
			min : 0
		});
		grid = $('#grid')
				.datagrid(
						{
							url : 'getAcceptSendCarDatas',
							pagePosition : 'bottom',
							pagination : true,
							striped : true,
							singleSelect : true,
							rownumbers : true,
							idField : 'id',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							columns : [ [
									{
										field : 'dispatcher',
										title : '调度员',
										resizable : true,
										width : "15%",
										align : 'center'
									},
									{
										field : 'startAcceptTime',
										title : '开始受理时刻',
										width : "15%",
										align : 'center',
										sortable : true
									},
									{
										field : 'sendCarTime',
										title : '派车时刻',
										resizable : true,
										width : "15%",
										align : 'center'
									},
									{
										field : 'acceptType',
										title : '受理类型',
										width : "10%",
										align : 'center'
									},
									{
										field : 'ringPhone',
										title : '呼救电话',
										resizable : true,
										width : "10%",
										align : 'center'
									},
									{
										field : 'sendCarTimes',
										title : '派车时长',
										width : "10%",
										resizable : true,
										align : 'center'
									},
									{
										field : 'remark',
										title : '受理备注',
										resizable : true,
										width : "14%",
										align : 'center'
									},
									{
										field : 'id',
										title : '详情',
										width : "10%",
										resizable : true,
										align : 'center',
										formatter : function(value, row) {
											var str = '';
											str += cxw
													.formatString(
															'<button onclick="showFun(\'{0}\');">详情</button>',
															row.id);
											return str;
										}
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
									$.messager.alert('警告', '结束时间要大于开始时间',
											'warning');
								}
							},
							onLoadSuccess : function(data) {
								parent.$.messager.progress('close');
								$(this).datagrid("autoMergeCells",
										[ 'dispatcher' ]);
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
								<td><input style="width: 80em" id="dispatcher"
									name="dispatcher" /></td>
								<td>超时时长(秒):</td>
								<td><input type="text" style="width: 90px" value="120"
									id="overtimes" name="overtimes" /></td>
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