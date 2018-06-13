<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/static/themes/bootstrap/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/static/themes/icon.css"	rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-1.7.2.min.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<!-- md5 -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/md5.js"></script>
<title>登录</title>
<style type="text/css">	
</style>
</head>
<script type="text/javascript">
if (window != top){
	top.location.href = location.href;
}
$(function() {
	//登录窗口配置
	$("#win").window({
		width : 320,
		height : 230,
		opacity: 0.9,
		title : '帐号登录',
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		draggable:false, 
		resizable:false 
	});
	//username输入框配置
	$("#username").textbox({
		iconCls : 'icon-man',
		iconAlign : 'left',
	    height:25,
	    width:'100%',
	    prompt:'请输入帐号'
	});
	//password密码输入框配置
	$("#password").textbox({
	    iconCls:'icon-lock', 
	    iconAlign:'left',
	    height:25,
	    width:'100%',
	    prompt:'请输入密码'
	});
	//登录button配置(异步提交表单) 
	$('#btn').linkbutton({    
	    iconCls: 'icon-ok',
	    width:75,
	    onClick:function(){
			console.log("login");
			var account = {
				"accountName" : $('#username').val(),
				"password" : md5($('#password').val())
			};
			var jsonData = JSON.stringify(account);
			console.log(account);
			$.ajax({
				url : "login",
				type : "post",
				data : jsonData,
				dataType : "json",
				contentType : "application/json",
				success : function(data) {
					console.log(data);
					console.log(data.result);
					if (data.result == "SUCCESS") {
						location.href = "main";
					}else{
						//账号或密码错误
			    		$.messager.alert('Message',data.result,'error',function(){
			    			//清除输入框内的值
			    			$('#username').textbox('clear');
			    		});
					}
				}
			});
	    }
	});
	//登录重置按钮 
	$('#cancel').linkbutton({    
	    iconCls: 'icon-no',
	    width:75,
	    onClick:function(){
   			$('#username').textbox('clear');
   			$('#password').textbox('clear');
	    }
	});
});
</script>
<body>
	<div id="win" style="text-align: center;">
		<div style="margin:auto;">
			<div>
				<h2>大学生综合素质评测系统</h2>
			</div>
			<form id="ff" method="post" style="margin-top: 30px;">
				<div style="width:80%;margin:auto;">
					<div style="margin-bottom:10px;">
						<input id="username" name="username" type="text"/>
					</div>
					<input id="password" name="password" type="password" />
				</div>
				<br>
				<a id="btn" href="#">登录</a>
					&emsp;
				<a id="cancel" href="#">重置</a>
			</form>
		</div>
	</div>
</body>
</html>