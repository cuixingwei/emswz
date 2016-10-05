<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String version = "20150311";
%>
<!DOCTYPE html>
<html>
<head>
<title>系统登录</title>
<jsp:include page="../inc.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/style/login.css?version=<%=version%>">
<script type="text/javascript">
	function initButtons() {
		$("#btnLogin").click(function() {
			/* 设置验证结果文本为空 */
			$("#validationSummary").text("");
			/* 取用户名密码的值 */
			var userId = $("#txtUserName").val();
			if (userId == "" || userId == null) {
				alert("请输入用户名!");
				return;
			}
			var password = $("#txtPassword").val();

			/* 根据是否记住用户名复选框选中，进行相关cookie保存删除操作 */
			if ($("#cbRemember").is(":checked")) {
				$.cookie('emsUserId', $("#txtUserName").val(), {
					path : "/",
					expires : 7
				});
			} else {
				$.cookie('emsUserId', null, {
					path : '/',
					expires : -1
				});
			}
			/* 登录验证 */
			$.ajax({
				type : "post",
				url : "page/login",
				dataType : "json",
				data : {
					userId : userId,
					password : password
				},
				error : function() {
					alert('登录失败，请稍后重试');
				},
				success : function(result) {
					switch (result.msg) {
					case "fail":
						$("#validationSummary").text("用户不存在");
						break;
					case "error":
						$("#validationSummary").text("密码错误");
						break;
					case "noPermission":
						$.messager.alert('警告', '你没有权限登录', 'info');
						break;
					case "success":
						window.location.replace("page/main.jsp");
						break;
					default:
						break;
					}
				}
			});
		});

		$("#btnCancel").click(function() {
			$("#txtUserName").val("");
			$("#txtPassword").val("");
			$("#cbRemember").attr("checked", false);
		});

		$("html").keypress(function(e) {
			var e = e || event;
			keycode = e.which || e.keyCode;
			if (keycode == 13) {
				$("#btnLogin").trigger("click");
			}
		});
	}

	function fetchUserId() {
		if ($.cookie('emsUserId')) {
			str = $.cookie('emsUserId');
			$("#txtUserName").val(str);
		} else {
			$("#txtUserName").val("");
		}
	}
	$(document).ready(function() {
		if ($.cookie('emsUserId')) {
			$("#cbRemember").attr("checked", true);
		}
		initButtons();
		fetchUserId();
		$("#txtUserName").focus();
	});
</script>
<title>系统登录</title>
</head>
<body>
	<div class="divMain">
		<div id="divLogin">
			<ul>
				<li class="login_li login_li_bg">请输入用户名:</li>
				<li class="login_li"><input class="input_user" id="txtUserName"
					maxlength="20" /></li>
			</ul>
			<ul style="margin-top: -5px;">
				<li class="login_li login_li_bg">密&nbsp;&nbsp;&nbsp;&nbsp;码：</li>
				<li class="login_li"><input class="input_user" id="txtPassword"
					type="password" maxLength="20" /></li>
			</ul>
			<ul style="margin-top: -5px;">
				<li class="login_li" style="font-size: 12px; margin-left: -3px;">
					<input type="checkbox" id="cbRemember"
					style="vertical-align: middle;" /> <label for="cbRemember"
					style="margin-left: -3px; vertical-align: middle;"> 记住用户名 </label>
				</li>
			</ul>
			<ul style="margin-top: 0px;">
				<li class="login_li" style="font-size: 12px;"><input
					id="btnLogin" type="image"
					src="<%=contextPath%>/style/image/btnLogin.png"
					style="WIDTH: 98px; HEIGHT: 26px" /> &nbsp;&nbsp; <input
					id="btnCancel" type="image"
					src="<%=contextPath%>/style/image/btnCancel.png"
					style="WIDTH: 98px; HEIGHT: 26px" /></li>
			</ul>
			<ul>
				<li class="login_li" style="margin-top: -5px;"><span
					id="validationSummary"
					style="DISPLAY: inline; color: red; font-size: 12px;"></span></li>
			</ul>
		</div>
	</div>


</body>
</html>