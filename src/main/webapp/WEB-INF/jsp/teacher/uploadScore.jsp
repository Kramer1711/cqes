<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<title>上传成绩</title>
</head>
<script type="text/javascript">
function ajaxLoading(message){   
    $("<div class=\"datagrid-mask\"></div>")
    	.css({display:"block","z-index":9000,
    		  width:"100%",height:$(window).height()})
        .appendTo("body");   
    $("<div class=\"datagrid-mask-msg\"></div>").html(message).appendTo("body")
    	.css({display:"block", "z-index":9011,
    		  left:($(document.body).outerWidth(true) - 190)/2,
    		  top:($(window).height()-45)/2});   
}   
function ajaxLoadEnd(){   
     $(".datagrid-mask").remove();   
     $(".datagrid-mask-msg").remove();               
} 
$(function() {
	$('#nameTxbox').textbox({
		label:'Name:',
		labelPosition:'top'
	});
	$('#fileBox').filebox({    
	    buttonText: '选择文件', 
	    buttonAlign: 'left',
	    label:'File:',
	    labelPosition:'top',
	    prompt:'选择上传的文件'
	});
	
	/**
		？？？？ 
		文件上传的格式：
		1.后缀名是否为xls
		2.若文件格式是被强制修改为xls
	
	*/
	/*
		文件上传 
	*/
	$("#uploadBtn").linkbutton({
		text:'上传',
		onClick:function(){
			var fileName = $('#fileBox').filebox("getText");
			var suffix = fileName.substring(fileName.lastIndexOf('.')+1,fileName.length);
			console.log(suffix);
			if(fileName.endsWith(".xlsx") != 'xls' && !fileName.endsWith(".xls"))
				$.messager.alert('警告','请上传excel文件!');    
			else{
				$.messager.confirm('确认','您确认要上传该成绩单吗（已存在的成绩将被覆盖）？',function(r){if (r){   
			        $("#uploadForm").form('submit', {
		                type:"post",  //提交方式    
		                url:"uploadScore", //请求url
		                onSubmit:function(){
		                	ajaxLoading("请稍等，正在上传文件……");
		                },
		                success:function(data){ 
		                	ajaxLoadEnd();
		                	data = eval("("+data+")");
		                	//提交成功的回调函数   
		                	console.log(data);
		                	if(data.result != "SUCCESS")
		                		$.messager.show({
			                		title:'消息',
			                		msg:"您的excel文件出错了，请上传标准格式的excel文件！",
			                		showType:'slide'
			                	});
		                	else{
			             	 	var showmsg = "上传成绩成功"+data.successNumber+"/"+data.totalNumber+"。\n";
			              		if(data.errorArray.length>0){
			              			showmsg +="以下学生可能尚未创建账号,请联系管理员创建系统账号：\n"
			              			for(var i = 0;i < data.errorArray.length;i++){
			              				showmsg+=data.errorArray[i].学号+" "+data.errorArray[i].姓名+"\n";
			              			}
			              		}
			                	$.messager.show({
			                		title:'消息',
			                		msg:showmsg,
			                		//timeout:4000,
			                		showType:'slide'
			                	});
		                	}
		                }
		            });    
				}});  
			}
		}
	});
});
</script>
<body>
	<div style="width:100%;">
    <form id="uploadForm" class="easyui-form" enctype="multipart/form-data" method="post" 
        style="max-width:400px;margin:auto;">
        <div style="margin-bottom:20px">
            <input id="nameTxbox" name="name" style="width:100%">
        </div>
        <div style="margin-bottom:20px">
            <input id="fileBox" name="file" style="width:100%">
        </div>
        <div>
            <a id="uploadBtn" style="width:100%"></a>
		</div>
    </form>
    </div>
</body>
</html>