<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.js"></script>
<title>大学生综合素质测评系统</title>
</head>
<script type="text/javascript">

//注销
function logout(){
	console.log("logout");
	$.ajax({
		url:"logout",
		success : function(data){
			location.href="loginPage";
		}
	});
}
</script>
<body>
<h1>主页</h1>
<div>
${sessionScope.account.accountName }
${sessionScope}
<label><a href="javascript:void(0)" onclick="logout()">注销</a></label>
</div>

</body>
</html>