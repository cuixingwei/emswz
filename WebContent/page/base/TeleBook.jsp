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
	/* 导出数据 */
	var exportData = function() {
		var url = "exportTeleBookDatas";
		window.location.href = url;
	};
	/* 合并单元格 */
	/* 初始化页面标签 */
	function init() {
		grid = $('#grid').datagrid({
			url : 'getTeleBookDatas',
			pagePosition : 'bottom',
			pagination : true,
			striped : true,
			singleSelect : true,
			rownumbers : true,
			idField : 'ownerName',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				field : 'department',
				title : '单位名称',
				width : "15%",
				align : 'center'
			}, {
				field : 'ownerName',
				title : '机主姓名',
				width : "15%",
				align : 'center',
				sortable : true
			}, {
				field : 'contactPhone',
				title : '联系电话',
				width : "10%",
				align : 'center'
			}, {
				field : 'fixedPhone',
				title : '固定电话',
				width : "10%",
				align : 'center'
			}, {
				field : 'extension',
				title : '分机',
				width : "10%",
				align : 'center'
			}, {
				field : 'mobilePhone',
				title : '移动电话',
				width : "10%",
				align : 'center'
			}, {
				field : 'littleSmart',
				title : '小灵通',
				width : "10%",
				align : 'center'
			}, {
				field : 'remark',
				title : '备注',
				width : "19.9%",
				resizable : true,
				align : 'center'
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onSortColumn : function(sort, order) {
			},
			onLoadSuccess : function(data) {
				parent.$.messager.progress('close');
				$(this).datagrid("autoMergeCells", [ 'department' ]);
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
								<td>姓名:</td>
								<td><input style="width: 80px;" id="name" name="name" /></td>
								<td>联系电话:</td>
								<td><input style="width: 80px;" id="phone" name="phone" /></td>
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