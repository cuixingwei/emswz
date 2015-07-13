<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/menu.css">
<script type="text/javascript" src="../lib/jquery-2.1.3.js"></script>
<title></title>
</head>
<body>
	<div id="masterdiv"></div>
	<script type="text/javascript">
		function SwitchMenu(obj) {
			var el = obj;
			$("span").each(function() {
				$(this).removeClass("show");
				$(this).addClass("hidden");
			});
			el.toggleClass("hidden");
			el.toggleClass("show");
		}
		/* 初始化每组菜单 */
		var lastSelectedLinkId = $("a:first");
		function createContentPane(aContainer, menu, index) {
			var div = $('<div>' + menu.menuName + '</div>');
			div.addClass("subMenu");
			var br = $('<br>');
			div.attr("id", "subMenu" + index);
			aContainer.append(div);
			div.click(function() {
				SwitchMenu(span);
			});
			div.append(br);
			var span = $('<span></span>');
			span.attr("id", "sub" + index);
			div.append(span);

			$.each(menu.resources, function(i, resource) {
				var link = $('<a>' + resource.name + '</a>');
				link.addClass("items")
				var br = $('<br>');
				link.attr("id", "a_" + index + "_" + i);
				link.click(function() {
					$("a:first").removeClass("selected");
					console.log(resource.url);
					$('#right', window.parent.document).attr('src',
							resource.url);
					if (lastSelectedLinkId
							&& lastSelectedLinkId != link) {
						lastSelectedLinkId.removeClass("selected");
						link.addClass('selected');
						lastSelectedLinkId = link;
					} else {
						link.addClass("selected");
						lastSelectedLinkId = link;
					}
					console.log(lastSelectedLinkId);
				});
				link.append(br);
				span.append(link);
			});
		}
		/* 初始化菜单 */
		function initMenu(data) {
			var aContainer = $("#masterdiv");
			$.each(data, function(i, menu) {
				createContentPane(aContainer, menu, i);
			});
			SwitchMenu($("#sub0"))
			$("a:first").addClass("selected");
		}
		$(document).ready(
				function() {
					$.ajax({
						type : "post",
						url : "getMenu",
						dataType : "json",
						/* 该函数有3个参数，即XMLHttpRequest对象、错误信息、捕获的错误对象(可选) */
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							console.log("textStatus" + textStatus
									+ "\nerrorThrown:" + errorThrown);
						},
						success : function(data, textStatus) {
							initMenu(data);
						}
					});
				});
	</script>
</body>
</html>