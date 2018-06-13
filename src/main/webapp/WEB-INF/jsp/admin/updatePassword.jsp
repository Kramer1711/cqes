<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
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
</head>
<script>
	$(function() {
		//用户名和密码
		$("#sure").linkbutton({
			iconCls : 'icon-ok',
			onClick : function() {
				if($('#oldPassword').textbox('getText').length==0){
					$.messager.alert('提示','请输入当前密码','error');
				}else if($('#newPassword').textbox('getText').length==0){
					$.messager.alert('提示','请输入新密码','error');
				}else if($('#surePassword').textbox('getText').length==0){
					$.messager.alert('提示','请确认新密码','error');
				}else if($('#newPassword').textbox('getText')!=$('#surePassword').textbox('getText')){
					$.messager.alert('提示','新密码与确认新密码不匹配','error');
				}else{
					$.ajax({
						url : '${pageContext.request.contextPath}/admin/updatePassword',
						type : 'post',
						data : {
							"accountName":"${account.accountName}",
							"oldPassword":md5($('#oldPassword').textbox('getText')),
							"newPassword":md5($('#newPassword').textbox('getText'))
						},
						dataType : 'json',
						contentType: 'application/x-www-form-urlencoded',
						success : function(data){
							if(data.status=="true"){
								$.messager.alert('提示','修改密码成功','info');
							}else if(data.status=="false"){
								$.messager.alert('提示','当前密码不正确','error');
							}else{
								$.messager.alert('提示','修改密码失败','error');
							}
						}
					});
				}
			}
		});
		$('#oldPassword').textbox({
			iconAlign : 'left',
			prompt : '请输入当前密码',
			required: true,
			height : 40
		});
		$('#newPassword').textbox({
			iconAlign : 'left',
			prompt : '请输入新密码',
			height : 40
		});
		$('#surePassword').textbox({
			iconAlign : 'left',
			prompt : '请确认新密码',
			height : 40
		});
	});
</script>
<body>
	<div>
		<!--新增用户  -->
		<form id="ff" method="post">
			<div style="margin-left: 120px; margin-top: 100px">
				<h3>修改密码</h3>
				<br>
				<span>当前密码：</span> 
				<input id=oldPassword name="oldPassword" type="password" style="width: 300px;"> 
				<br>
				<br> 
				<span>新密码：&emsp;</span> 
				<input id="newPassword" name="newPassword" type="password" style="width: 300px"> 
				<br>
				<br>
				<span>请确认新密码：&emsp;</span>
				<input id="surePassword" name="surePassword" type="password" style="width: 300px"> 
					<br>
					<br> 
				<a id="sure" href="#">确认</a> 
			</div>
		</form>
	</div>
</body>
</html>