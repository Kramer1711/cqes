<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>excel模版下载</title>
</head>
<body>
<h2>excel模版下载</h2>
<div>
<p><a href="${pageContext.request.contextPath }/template/download?filename=cjb.xls">成绩表.xls</a></p>
<p><a href="${pageContext.request.contextPath }/template/download?filename=szcxfdjb.xls">素质操行分登记表.xls</a></p>
<p><a href="${pageContext.request.contextPath }/template/download?filename=zhszcppmb.xls">综合素质测评排名表.xls</a></p>
</div>
</body>
</html>