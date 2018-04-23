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
	        $("#uploadForm").form('submit', {
                type:"post",  //提交方式    
                url:"uploadScore", //请求url
                success:function(data){ 
                	//提交成功的回调函数   
                	console.log(data);
                }
	        	//失败时 
            });  
		}
	});
});
</script>
<body>
    <form id="uploadForm" class="easyui-form" enctype="multipart/form-data" method="post" style="width:100%;max-width:400px;padding:30px 60px;">
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
</body>
</html>