<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/static/themes/bootstrap/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/static/themes/icon.css" rel="stylesheet">
<!-- 引入jquery -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>    
<title>综合素质测评提交</title>
</head>
<script type="text/javascript">
$(function() {
	$('#addItemBtn').linkbutton({
		onClick : function(){
			$("#addItemBtn").before('<div> <p> <input name="itemType" /> <input name="itemName"/> <input name="itemScore"/> <input name="fb" class="easyui-filebox" type="text" style="width:300px"> </p> </div>');
			$("input[name='itemType']:last").combobox({
				method:'get',
				url:'${pageContext.request.contextPath }/quality/getQualityType',
				label:'类型',
				valueField:'itemTypeId',    
				textField:'typeName',
			});
			$('input[name="itemName"]:last').textbox({
				label:'项目'
			});
			$('input[name="itemScore"]:last').textbox({
				label:'分数'
			});
			
			$("input[name='fb']:last").filebox({
				label:'证明材料',
			    buttonText: '选择文件', 
			    buttonAlign: 'left' 
			});
		}	
	});	
	$("input[name='itemType']").combobox({
		method:'get',
		url:'${pageContext.request.contextPath }/quality/getQualityType',
		label:'类型',
		valueField:'itemTypeId',    
		textField:'typeName',
		onSelect : function(record){
			console.log(record);
			console.log($(this).combobox('getValue'));
			//console.log($('input[name="itemType"]').first().combobox('getText'));
		}
	});
	$('input[name="itemName"]').textbox({
		label:'项目'
	});
	$('input[name="itemScore"]').textbox({
		label:'分数'
	});
	$("input[name='fb']").filebox({
		label:'证明材料',
	    buttonText: '选择文件', 
	    buttonAlign: 'left' 
	});
	
	$('#form').form({    
	    url:"${pageContext.request.contextPath }/student/uploadQuality",    
	    onSubmit: function(){
	    	
	    },
	    success:function(data){    
	    	console.log(data);
	        alert("上传成功");
	    }    
	});    
	$('#uploadBtn').linkbutton({
		onClick : function(){
			$("#form").submit();  
		}
	});
});
</script>
<body>
	<form id="form" method="post" enctype="multipart/form-data">
		<div style="border-bottom: black">
		<p>
			<input name="itemType" />
			<input name="itemName"/>
			<input name="itemScore"/>
			<input name="fb" type="text" style="width:300px">
		</p>
		</div>
		<a href ='#' id="addItemBtn">添加一项</a>
	</form>
	<a href ='#' id="uploadBtn">submit</a>
</body>
</html>