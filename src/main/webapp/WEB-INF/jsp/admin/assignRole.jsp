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
	//用户名和密码
	var thisRid;
	$("#sure").linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
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
				'roleId' : $('#rid').combobox('getValue'),
				'roleName':$('#rid').combobox('getText'),
				'functionIds':fids
			};
			var jsonData = JSON.stringify(role);
			$.ajax({
				url : '${pageContext.request.contextPath}/admin/updateRole',
				type : 'post',
				data : jsonData,
				dataType : 'json',
				contentType : "application/json",
				success : function(data){
					if(data.result=="success"){
						$.messager.alert('提示','更新角色成功'); 
					}else{
						$.messager.alert('提示','更新角色失败');
					}
					editIndex = undefined;
				}
			});
		}
	});
	$('#rid').combobox({    
	    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',    
	    valueField:'id',
	    textField:'text',
	    editable:false,
	    onLoadSuccess: function (data) {
	    	if (data!=null) {
	    	   $('#rid').combobox('setValue',data[0].id);
	    	}
	    },
	    onChange:function(newValue,oldValue){
	    	thisRid=newValue;
			$('#tt').tree({
				url : '${pageContext.request.contextPath}/getRoleCheckTree?rid='+thisRid,
				checkbox : true,
				onLoadSuccess : function(node, data) {
					$("#tt").tree('expandAll');
				}
			});
	    },
		height : 30
	});
});

</script>
<body>
	<div style="margin-left:30px;">
		<h3 style="margin-bottom:2px;">角色功能管理</h3>
		<div style="height:1px;width:450px;margin-left:-15px;background:#7F7F7F;overflow:hidden;"></div>
			<br>
		<span>角色：&emsp;</span>
		<input id="rid" name="rid" class="easyui-combobox"> 
			<br>
			<br> 
			<ul id="tt"></ul>
		<div style="margin-top:10px;margin-left:50px; ">
			<a id="sure" href="#" >确认</a>
		</div>
	</div>
</body>
</html>