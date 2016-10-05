<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.xhs.ems.bean.easyui.SessionInfo"%>
<%
	String contextPath = request.getContextPath();
	SessionInfo sessionInfo = (SessionInfo) session
			.getAttribute("sessionInfo");
%>
<script type="text/javascript" charset="utf-8">
	function GetTimes(tCount) {
		var result = tCount;
		if (parseInt(tCount, 10) < 10) {
			result = "0" + tCount;
		}
		return result;
	}
	function show() {
		var date = new Date();
		$("#a").html(
				'现在是：' + date.getFullYear() + '年'
						+ GetTimes((date.getMonth() + 1)) + '月'
						+ GetTimes(date.getDate()) + '日&nbsp;&nbsp;'
						+ GetTimes(date.getHours()) + ':'
						+ GetTimes(date.getMinutes()) + ':'
						+ GetTimes(date.getSeconds()) + '&nbsp;&nbsp;星期'
						+ '日一二三四五六'.charAt(new Date().getDay()));
	}

	setInterval(show, 500);
	$(document).ready(function() {
		/* 注销系统 */
		$("#logOut").click(function() {
			$.post('logOut', function(result) {
				location.replace(cxw.contextPath + '/index.jsp');
			}, 'json');
		});

	});
</script>
<div class="hBgG"
	style="width: 100%; height: 69px; background-color: #44ACFB;">
	<div class="leftBgG" style="width: 500px; height: 69px; float: left;"></div>
	<div class="hTopRight"
		style="width: 450px; height: 69px; float: right;"></div>
</div>
<div style="width: 100%; height: 38px;" class="htopBgG">
	<div class="hleftBotom"
		style="width: 150px; height: 38px; float: left;"></div>
	<div
		style="width: 220px; height: 38px; float: left; background: url(../style/image/topmid_spilter.png) right center no-repeat;">
		<span
			style="background: url(../style/image/curretperon.png) left center no-repeat; padding-left: 20px; margin-top: 12px; margin-left: 10px; display: inline-block;"><%=sessionInfo.getUser().getStationName()%></span>
		<span
			style="background: url(../style/image/station.png) left center no-repeat; padding-left: 20px; margin-left: 10px;"><%=sessionInfo.getUser().getName()%></span>
	</div>

	<span id="a"
		style="color: #FF0000; float: left; width: 600px; height: 25px; margin-top: 10px; margin-left: 10px; display: inline-block;"></span>

	<div class="hleftRight"
		style="width: 125px; height: 38px; float: right;"></div>

	<div style="width: 220px; height: 38px; float: right; color: blue;">
		<a onclick="$('#passwordDialog').dialog('open');"
			style="width: 70px; height: 36px; line-height: 36px; text-align: center; vertical-align: middle; padding-left: 10px; background: url(../style/image/topmid_spilter.png) left center no-repeat;">修改密码</a>
		<a id="logOut"
			style="width: 30px; height: 36px; line-height: 36px; vertical-align: middle; padding-left: 10px; background: url(../style/image/topmid_spilter.png) left center no-repeat;">退出</a>
	</div>
</div>