<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/static/themes/bootstrap/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/static/themes/icon.css"	rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-1.7.2.min.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<title>个人信息</title>
</head>
<script type="text/javascript">
$.ajax({
	url:"getPersonalInfo",
	type:"GET",
	success:function(data){
		console.log(data);
		$('#name').html(data.name);
		$('#sex').html(data.sex);
		$('#college').html(data.collegeName);
		$('#major').html(data.majorName);
		$('#birthday').html(data.birthday);
		$('#phoneNumber').html(data.phoneNumber);
	}
});
</script>
<body>
<h2>个人信息</h2>
<table>
	<tr>
		<td>姓名：</td><td id="name"></td>
		<td style="margin-left: 40px;">性别:</td><td id="sex"></td>
	</tr>
	<tr>
		<td>学院：</td><td id="college"></td>
		<td style="margin-left: 40px;">专业：</td><td id="major"></td>
	</tr>
	<tr>
		<td>生日：</td><td id="birthday"></td>
		<td style="margin-left: 40px;">联系方式：</td><td id="phoneNumber"></td>
	</tr>
</table>
</body>
</html>