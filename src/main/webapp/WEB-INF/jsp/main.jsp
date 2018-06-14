<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

 <!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/static/themes/bootstrap/easyui.css" rel="stylesheet">
<!--
    themes/insdep/easyui_animation.css
    Insdep对easyui的额外增加的动画效果样式，根据需求引入或不引入，此样式不会对easyui产生影响
-->
<link href="${pageContext.request.contextPath }/static/css/easyui_animation.css" rel="stylesheet" type="text/css">
<!--
    themes/insdep/easyui_plus.css
    Insdep对easyui的额外增强样式,内涵所有 insdep_xxx.css 的所有组件样式
    根据需求可单独引入insdep_xxx.css或不引入，此样式不会对easyui产生影响
-->
<link href="${pageContext.request.contextPath }/static/css/easyui_plus.css" rel="stylesheet" type="text/css">
<!--
    themes/insdep/insdep_theme_default.css
    Insdep官方默认主题样式,更新需要自行引入，此样式不会对easyui产生影响
-->
<link href="${pageContext.request.contextPath }/static/css/insdep_theme_default.css" rel="stylesheet" type="text/css">
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
      <div data-options="region:'north',border:false" style="height:110px;">
      	<div class="theme-navigate" style="padding:0px;height:110px;float: left;" >
              <div class="left">
                  	<img src="${pageContext.request.contextPath }/static/title.jpg"/>
              </div>
          </div>
          	<div style="float:right;margin-right: 15px;margin-top: 5px;">
          		<label>${sessionScope.account.realName }</label>
          		<label style="margin-left: 10px;"><a href="#" onclick="logout()">注销</a></label>
          	</div>
      </div>
    <div data-options="region:'west',title:'功能导航',split:true" style="width:200px;">
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
<script type="text/javascript">
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		var url="${pageContext.request.requestURL}";
		url=url.substring(url.indexOf("http:")+4,url.indexOf("WEB-INF/"));
		websocket = new WebSocket('ws'+url+'websocket/${account.accountName}/${account.roleId}');
		console.log('websocket connect success');
	} else {
		console.log('当前浏览器不支持 websocket');
	}
	//连接发生错误的回调方法
	websocket.onerror = function() {
		//alert("WebSocket连接发生错误!");
	};
	//连接成功建立的回调方法
	websocket.onopen = function() {
		//alert("WebSocket连接成功!");
	}
	//接收到消息的回调方法
	websocket.onmessage = function(event) {
		
		var data=eval('('+event.data+')');
		$.messager.alert('来自管理员'+data.realName+'发送的提示', data.text, 'info');
		//alert(event.data);
	}
	//连接关闭的回调方法
	websocket.onclose = function() {
		//alert("WebSocket连接关闭!");
	}
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		closeWebSocket();
	}
	//关闭WebSocket连接
	function closeWebSocket() {
		websocket.close();
	}
</script>
</html>