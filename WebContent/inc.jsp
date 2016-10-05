<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String version = "20150311";
	String contextPath = request.getContextPath();
%>

<%
	Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	Cookie[] cookies = request.getCookies();
	if (null != cookies) {
		for (Cookie cookie : cookies) {
	cookieMap.put(cookie.getName(), cookie);
		}
	}
	String easyuiTheme = "default";//指定如果用户未选择样式，那么初始化一个默认样式
	if (cookieMap.containsKey("easyuiTheme")) {
		Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
		easyuiTheme = cookie.getValue();
	}
%>

<script type="text/javascript">
var cxw = cxw || {};
cxw.contextPath = '<%=contextPath%>';
cxw.basePath = '<%=basePath%>';
cxw.version = '<%=version%>';
</script>



<%-- 引入jQuery --%>
<%
	String User_Agent = request.getHeader("User-Agent");
	if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1
			&& (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1
					|| StringUtils.indexOfIgnoreCase(User_Agent,
							"MSIE 7") > -1 || StringUtils
					.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)) {
		out.println("<script src='"
				+ contextPath
				+ "/jslib/jquery-1.9.1.js' type='text/javascript' charset='utf-8'></script>");
	} else {
		out.println("<script src='"
				+ contextPath
				+ "/jslib/jquery-2.0.3.js' type='text/javascript' charset='utf-8'></script>");
	}
%>
<%-- 引入jquery扩展 --%>
<script
	src="<%=contextPath%>/jslib/cxwExtJquery.js?version=<%=version%>"
	type="text/javascript" charset="utf-8"></script>
<%--引入jquery.cookie.js --%>
<script type="text/javascript"
	src="<%=contextPath%>/jslib/jquery.cookie.js"></script>
<%-- 引入EasyUI --%>
<link id="easyuiTheme" rel="stylesheet"
	href="<%=contextPath%>/jslib/jquery-easyui-1.4.1/themes/<%=easyuiTheme%>/easyui.css"
	type="text/css">
<script type="text/javascript"
	src="<%=contextPath%>/jslib/jquery-easyui-1.4.1/jquery.easyui.min.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jslib/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"
	charset="utf-8"></script>
<%-- 引入EasyUI Portal插件 --%>
<link rel="stylesheet"
	href="<%=contextPath%>/jslib/jquery-easyui-portal/portal.css"
	type="text/css">
<script type="text/javascript"
	src="<%=contextPath%>/jslib/jquery-easyui-portal/jquery.portal.js"
	charset="utf-8"></script>
<%-- 引入easyui扩展 --%>
<script
	src="<%=contextPath%>/jslib/cxwExtEasyUI.js?version=<%=version%>"
	type="text/javascript" charset="utf-8"></script>

<%-- 引入扩展图标 --%>
<link rel="stylesheet"
	href="<%=contextPath%>/style/cxwExtIcon.css?version=<%=version%>"
	type="text/css">

<%-- 引入自定义样式 --%>
<link rel="stylesheet"
	href="<%=contextPath%>/style/cxwExtCss.css?version=<%=version%>"
	type="text/css">

<%-- 引入javascript扩展 --%>
<script
	src="<%=contextPath%>/jslib/cxwExtJavascript.js?version=<%=version%>"
	type="text/javascript" charset="utf-8"></script>

<%-- 引入自定义js --%>
<script src="<%=contextPath%>/jslib/cxwUtil.js?version=<%=version%>"
	type="text/javascript" charset="utf-8"></script>
