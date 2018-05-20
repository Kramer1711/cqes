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
<title>登录</title>
<style type="text/css">	
</style>
</head>
<script type="text/javascript">
	$(function() {
		//登录窗口配置
		$("#win").window({
			width : 350,
			height : 300,
			title : '登录',
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : false,
			draggable:false, 
			resizable:false 
		});
		//提交表单配置 
		$('#ff').form({    
		    url:'/login',
		    success:function(data){
		    	var obj = eval("("+data+")");
		    	if(obj.result==true){
		    		//账号密码匹配,进入主页
		    		location.href = '/main';
		    	}else{
		    		
		    	}
		    }    
		});    
		//username输入框配置
		$("#username").textbox({
		    iconCls:'icon-man', 
		    iconAlign:'right',
		    height:25,
		    width:180,
		    prompt:'username'
		});
		//password密码输入框配置
		$("#password").textbox({
		    iconCls:'icon-lock', 
		    iconAlign:'right',
		    height:25,
		    width:180,
		    prompt:'password'
		});
		//登录button配置(异步提交表单) 
		$('#btn').linkbutton({    
		    iconCls: 'icon-ok',
		    width:180,
		    onClick:function(){
				console.log("login");
				var account = {
					"accountName" : $('#username').val(),
					"password" : $('#password').val(),
					"token": $('#token').val()
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
				    			$("#password").textbox('clear');
				    		});
						}
					}
				});
		    }
		});
	});
</script>
<body>
	<div id="win">
		<form id="ff" method="post" style="margin-left: 75px; margin-top: 65px;">
			<div>
				<input id="username" name="username" type="text" />
			</div>
			<div style="margin-top: 25px; margin-bottom: 25px;">
				<input id="password" name="password" type="password" />
			</div>
			<a id="btn" href="#">登录</a>
		</form>
	</div>
</body>
</html>