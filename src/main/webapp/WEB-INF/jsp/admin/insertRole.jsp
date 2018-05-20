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
<script type="text/javascript"	src="${pageContext.request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
</head>
<script>
$(function() {
	$('#tt').tree({
		url : '${pageContext.request.contextPath}/getRoleCheckTree?rid=-1',
		checkbox : true,
		onLoadSuccess : function(node, data) {
			$("#tt").tree('expandAll');
		}
	});
	//用户名和密码
	$("#sure").linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			if($('#rname').textbox('isValid')) {
				var nodes1=$('#tt').tree('getChecked');
				var nodes2=$('#tt').tree('getChecked', 'indeterminate');
				var fids="";
				var start=true;
				if(nodes1.length==0 && nodes2.length==0){
					$.messager.alert('提示','请选择该角色所拥有权限的功能','error'); 
					return;
				}
				for(var i=0;i<nodes1.length;i++){
					if(start){
						start=false;
						fids+=nodes1[i].id;
					}else{
						fids=fids+","+nodes1[i].id;
					}
				}
				for(var i=0;i<nodes2.length;i++){
					if(start){
						start=false;
						fids+=nodes2[i].id;
					}else{
						fids=fids+","+nodes2[i].id;
					}
				}
				var role={
					"roleName":$('#rname').textbox('getText'),
					"functionIds":fids
				};
				var jsonData = JSON.stringify(role);
				$.ajax({
					url : '${pageContext.request.contextPath}/admin/insertRole',
					type : 'post',
					data : jsonData,
					dataType : 'json',
					contentType : "application/json",
					success : function(data){
						console.log(data.result=="success");
						if(data.result=="success"){
							$.messager.alert('提示','新增角色成功');
						}else{
							$.messager.alert('提示','新增角色失败');
						}
						editIndex = undefined;
					}
				});
			}else{
				$.messager.alert('提示','请输入角色名','error');
			}
		}
	});
	$('#cancel').linkbutton({
		iconCls : 'icon-cancel',
		onClick : function() {
			$('#rname').textbox('clear');
 			var nodes=$('#tt').tree('getChecked');
 			for(var i=0;i<nodes.length;i++){
				$('#tt').tree('uncheck',nodes[i].target);
			} 
		}
	});
	$('#rname').textbox({
		iconCls : 'icon-man',
		iconAlign : 'left',
		prompt : '角色名',
		required : true,
		missingMessage : "请输入角色名",
		validType : 'minLength[1]',
		height : 35
	});
});

</script>
<body>
	<div style="margin-left:30px;margin-top:30px;">
		<h3>新增角色</h3>
			<br>
		<span>角色名：</span> 
		<input id="rname" name="rname" type="text" style="width: 300px;"> 
			<br>
			<br> 
			<ul id="tt"></ul>
			<a id="sure" href="#" style="margin:auto">确认</a>
			<a id="cancel" href="#" style="margin:auto">重置</a>
	</div>
</body>
</html>