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
</head>
<script>
	$(function() {
		$.extend($.fn.validatebox.defaults.rules, {    
		    minLength: {    
		        validator: function(value, param){    
		        	if(value==null){
		        		return false;
		        	}
		            return value.length >= param[0];    
		        },    
		        message: '请输入最少{0}个字符'
		    }    
		}); 
		//用户名和密码
		$("#sure").linkbutton({
			iconCls : 'icon-ok',
			onClick : function() {
				if($('#username').textbox('isValid')){
					var role={
							"accountName":$('#username').textbox('getText'),
							"password":$('#password').textbox('getText'),
							"roleId":$('#rid').combobox('getValue')
						};
						var jsonData = JSON.stringify(role);
					$.ajax({
						url : '${pageContext.request.contextPath}/admin/insertAccount',
						type : 'post',
						data : jsonData,
						dataType : 'json',
						contentType : "application/json",
						success : function(data){
							console.log(data.result=="success");
							if(data.result=="success"){
								$.messager.alert('提示','新增用户成功');
							}else{
								$.messager.alert('提示','新增用户失败');
							}
							editIndex = undefined;
						}
					});
				}
				
			}
		});
		$('#username').textbox({
			iconCls : 'icon-man',
			iconAlign : 'left',
			prompt : '用户名',
			required: true,
			missingMessage: "请输入用户名",
			validType:'minLength[4]',
			height : 40
		})
		$('#password').textbox({
			iconCls : 'icon-lock',
			iconAlign : 'left',
			prompt : '密码',
			height : 40
		})
		$('#rid').combobox({    
		    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',    
		    valueField:'id',
		    textField:'text',
		    editable:false,
			height : 30
		});
	});
</script>
<body>
	<div>
		<!--新增用户  -->
		<form id="ff" method="post">
			<div style="margin-left: 120px; margin-top: 100px">
				<h3>新增用户</h3>
				<br>
				<span>用户名：</span> 
				<input id="username" name="username" type="text" style="width: 300px;"> 
				<br>
				<br> 
				<span>密码：&emsp;</span> 
				<input id="password" name="password" type="password" style="width: 300px"> 
				<br>
				<br>
				<span>角色：&emsp;</span>
				<input id="rid" name="rid" class="easyui-combobox"> 
					<br>
					<br> 
				<a id="sure" href="#">确认</a> 
			</div>
		</form>
	</div>
</body>
</html>