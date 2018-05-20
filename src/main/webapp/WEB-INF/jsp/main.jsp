<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>大学生综合素质测评系统</title>
</head>

<script type="text/javascript">
$(function(){
	$('#tt').tree({
	    url:'${pageContext.request.contextPath }/getRoleTree?rid=${sessionScope.account.roleId}',
	    onClick:function(node){
	    	if(node.attributes.fpath){
	    		if($('#tabs').tabs('exists',node.text)){
	    			$('#tabs').tabs('select',node.text)
	    		}
	    		else{
	    		$('#tabs').tabs('add',{
	    			title: node.text,
	    			closable: true,
	    			content:"<iframe  width='100%' height='100%' frameborder='0' src='"+node.attributes.fpath+"'></iframe>"
	    		});
	    		}
	    	}
	    },
	    onLoadSuccess:function(node,data){
	    	$("#tt").tree('expandAll');
	    }
	});     
})
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
<body class="easyui-layout" >
	<div data-options="region:'north',title:'大学生综合素质测评系统',split:true" style="height:100px;">
	<div>
		${sessionScope.account.accountName }
		${sessionScope.account.password }
		${sessionScope}
		<label><a href="javascript:void(0)" onclick="logout()">注销</a></label>
	</div>
	</div>   
    <div data-options="region:'west',title:'欢迎',split:true" style="width:200px;">
    	<ul id="tt"></ul>
    </div>   
    <div data-options="region:'center',border:false" style="padding:5px;background:#eee;">
    	<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false"> 
    		<div title="欢迎界面" style="padding:20px;display:none;">  
    			<iframe  width='100%' height='100%' frameborder='0' src='index'></iframe>
    		</div>
    	</div>
    </div>    
</body>
</html>