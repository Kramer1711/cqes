<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入主题样式 -->
<link
	href="${pageContext.request.contextPath }/static/themes/bootstrap/easyui.css"
	rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/static/themes/icon.css"
	rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/jquery-1.7.2.min.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<!-- 引入easyUI中文环境 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/easyui-lang-zh_CN.js"></script>	
<title>ChattingRoom</title>
<script>
$(function(){
	$('#rid').combobox({    
	    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',    
	    valueField:'id',
	    textField:'text',
	    width:125,
	    editable:false,
	    loadFilter:function(data){
	        data.unshift({id:'-1',text:'全部',selected:'true'});
	        return data;
	    },
		height : 30
	});
	//配置提交按钮
	$('#text').textbox({    
	    //buttonText:'用户名',    
	    iconAlign:'right',
	    prompt:'输入聊天信息',
	    width:'100%',
	    height:'100%',
	});
	//配置发送按钮
	$("#send").linkbutton({
		iconCls: 'icon-ok',
	    onClick:function(){
	    	send();
	    	$("#text").textbox("clear");
	    }
	});
});
</script>
</head>
<body>
	<div>
		<form id="ff" method="post">
			<div style="margin-left: 120px; margin-top: 100px">
				<h3>发送通知</h3>
				<br>
				<span>接收通知角色：</span> 
				<input id="rid" name="rid" class="easyui-combobox" style="width: 300px;"> 
				<br>
				<br> 
				<span>通知内容：&emsp;</span>
				<input id="text" class="easyui-textbox" data-options="multiline:true">
					<br>
					<br> 
				<a id="send">发送通知</a>
			</div>
		</form>
	</div>
</body>
<body class="easyui-layout" > 
</body>
<script type="text/javascript">
	//发送消息
	function send() {
		var message = document.getElementById('text').value;
		var msg={
			accountName:"${account.accountName}",
			realName:"${account.realName}",
			targetRoleId:$('#rid').val(),
			text:message
		};
		if(message!="")
		{
			//window.parent.websocket.send("${account.accountName}"+":  "+message);
			window.parent.websocket.send( JSON.stringify(msg));
		}
	}
	//------------------------------------------------------------------显示在线人数

</script>

</html>