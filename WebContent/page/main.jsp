<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String version = "20150311";
%>
<!DOCTYPE html>
<html>
<head>
<title>主界面</title>
<jsp:include page="../inc.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/style/header.css?version=<%=version%>">
<script type="text/javascript">
	var mainMenu;
	var mainTabs;
	$(function() {
		/* 登录成功提示 */
		$.messager.show({
			title : '提示',
			msg : '登录成功',
			timeout : 3000,
			showType : 'slide'
		});
		/* 修改密码 */
		$('#passwordDialog').show().dialog({
			modal : true,
			closable : true,
			iconCls : 'ext-icon-lock_edit',
			buttons : [ {
				text : '修改',
				handler : function() {
					if ($('#passwordDialog form').form('validate')) {
						$.post('changePwd', {
							'dataPwd' : $('#pwd').val()
						}, function(result) {
							if (result.success) {
								$.messager.alert('提示', '密码修改成功！', 'info');
								$('#passwordDialog').dialog('close');
							} else {
								$.messager.alert('提示', '密码修改失败！', 'info');
								$('#passwordDialog').dialog('close');
							}
						}, 'json');
					}
				}
			} ],
			onOpen : function() {
				$('#passwordDialog form :input').val('');
			}
		}).dialog('close');

		/* 初始化菜单 */
		mainMenu = $('#mainMenu')
				.tree(
						{
							url : 'getMenu',
							parentField : 'pid',
							onClick : function(node) {
								if (node.attributes.url) {
									var src = "base/" + node.attributes.url;
									if (node.attributes.target
											&& node.attributes.target.length > 0) {
										window
												.open(src,
														node.attributes.target);
									} else {
										var tabs = $('#mainTabs');
										var opts = {
											title : node.text,
											closable : true,
											iconCls : node.iconCls,
											content : cxw
													.formatString(
															'<iframe src="{0}" allowTransparency="true" style="border:0;width:99.9%;height:99%;" frameBorder="0"></iframe>',
															src),
											border : false,
											fit : true
										};
										if (tabs.tabs('exists', opts.title)) {
											tabs.tabs('select', opts.title);
										} else {
											tabs.tabs('add', opts);
										}
									}
								}
							}
						});

		$('#mainLayout').layout('panel', 'center').panel(
				{
					onResize : function(width, height) {
						cxw.setIframeHeight('centerIframe',
								$('#mainLayout').layout('panel', 'center')
										.panel('options').height - 5);
					}
				});

		mainTabs = $('#mainTabs')
				.tabs(
						{
							fit : true,
							border : false,
							tools : [
									{
										iconCls : 'ext-icon-arrow_up',
										handler : function() {
											mainTabs.tabs({
												tabPosition : 'top'
											});
										}
									},
									{
										iconCls : 'ext-icon-arrow_down',
										handler : function() {
											mainTabs.tabs({
												tabPosition : 'bottom'
											});
										}
									},
									{
										text : '刷新',
										iconCls : 'ext-icon-arrow_refresh',
										handler : function() {
											var panel = mainTabs.tabs(
													'getSelected').panel(
													'panel');
											var frame = panel.find('iframe');
											try {
												if (frame.length > 0) {
													for (var i = 0; i < frame.length; i++) {
														frame[i].contentWindow.document
																.write('');
														frame[i].contentWindow
																.close();
														frame[i].src = frame[i].src;
													}
													if (navigator.userAgent
															.indexOf("MSIE") > 0) {// IE特有回收内存方法
														try {
															CollectGarbage();
														} catch (e) {
														}
													}
												}
											} catch (e) {
											}
										}
									},
									{
										text : '关闭',
										iconCls : 'ext-icon-cross',
										handler : function() {
											var index = mainTabs
													.tabs(
															'getTabIndex',
															mainTabs
																	.tabs('getSelected'));
											var tab = mainTabs.tabs('getTab',
													index);
											if (tab.panel('options').closable) {
												mainTabs.tabs('close', index);
											} else {
												$.messager
														.alert(
																'提示',
																'['
																		+ tab
																				.panel('options').title
																		+ ']不可以被关闭！',
																'error');
											}
										}
									} ]
						});

	});
</script>
</head>
<body id="mainLayout" class="easyui-layout">
	<div
		data-options="region:'north',href:'<%=contextPath%>/page/north.jsp',border:false"
		style="height: 110px; overflow: hidden;"></div>
	<div data-options="region:'west',href:'',split:true" title="导航"
		style="width: 250px; padding: 10px;">
		<ul id="mainMenu"></ul>
	</div>
	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="mainTabs">
			<div title="任务性质统计"
				data-options="iconCls:'ext-icon-world',closable:true,fit:true">
				<iframe src="<%=contextPath%>/page/base/TaskNature.jsp"
					style="border: 0; width: 99.9%; height: 99%;"></iframe>
			</div>
		</div>
	</div>
	<div
		data-options="region:'south',href:'<%=contextPath%>/page/south.jsp',border:false"
		style="height: 30px; overflow: hidden;"></div>

	<div id="passwordDialog" title="修改密码" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th>新密码</th>
					<td><input id="pwd" name="dataPwd" type="password"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input type="password" class="easyui-validatebox"
						data-options="required:true,validType:'eqPwd[\'#pwd\']'" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>