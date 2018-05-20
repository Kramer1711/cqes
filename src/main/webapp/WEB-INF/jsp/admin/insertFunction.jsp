<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
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
</head>

<body>
    <h3 style="magin-top:60px;margin-left:40px;">新增功能</h3>
	<div style="text-align: center; width:500px;">
		<table id="singleUpdateTable" style="width:80%;margin:0 auto; border-spacing:0px 10px;">
		<tr>
			<td align="right" width="30%">父功能：</td>
			<td width="70%">
				<select id="addParentFun" class="easyui-combotree"style="width: 100%;" 
					data-options="url:'${pageContext.request.contextPath}/getRoleTree?rid=-1',
					required:true,prompt:'请选择所属父功能'">
				</select> 
			</td>
		</tr>
		<tr>
			<td align="right">功能名：</td>
			<td>
				 <input id="addFuncName">
			</td>
		</tr>
		<tr>
			<td align="right">对应路径：</td>
			<td>
				<input id="addFuncUrl">
			</td>
		</tr>
		</table>
		<div style="margin-top:5px;margin-bottom: 15px;">
			<a id="sureAdd" href="#">确认</a>&emsp;<a id="cancelAdd" href="#">取消</a>
		</div>
	</div>
</body>


<script>
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
function insertFunction(){
	var parentFunc=$('#addParentFun').combotree('tree').tree('getSelected');
	console.log(parentFunc);
	var func={
		"functionName":$('#addFuncName').val(),
		"functionPath":$("#addFuncUrl").val(),
		"functionPid":parentFunc.id,
		"parentState":parentFunc.state
	};
	ajaxLoading("请稍等，正在添加相应功能……");
	$.ajax({
		url : '${pageContext.request.contextPath}/function/insertFunction',
		type : 'post',
		data : JSON.stringify(func),
		dataType : 'json',
		contentType : "application/json",
		success : function(data){
			ajaxLoadEnd();
			if(data.status=="true"){
				$.messager.alert('提示','添加功能成功','info');
				$('#addParentFun').combotree('clear');
				$('#addParentFun').combotree('reload');
			}else if(data.status=="false"){
				$.messager.alert('提示','添加功能失败','error');
			}else if(data.status=="using"){
				$.messager.alert('提示',data.message,'error');
			}
		},
		error: function(){
			ajaxLoadEnd();
			$.messager.alert('提示','添加功能失败');
		}
	});//ajax END 
}
	$(function() {
		$("#sureAdd").linkbutton({
			iconCls : 'icon-ok',
			onClick : function() {
				var parentFunc=$('#addParentFun').combotree('tree').tree('getSelected');
				if(parentFunc==null){
					$.messager.alert('提示','请选择父功能','error');
					return;
				}	
				if($('#addFuncName').val()==null||$('#addFuncName').val()==''){
					$.messager.alert('提示','请输入功能名','error');
					return;
				}
				if(parentFunc.attributes.fpath==null||parentFunc.attributes.fpath==""){
					insertFunction();
				}else{
					$.messager.confirm('提示', '所选功能为具体功能，确定将其变为功能文件夹吗？',function(sure){
						if(sure){
							insertFunction();
						}
					});
				}
				
			}
		});
		$("#cancelAdd").linkbutton({
			iconCls : 'icon-reload',
			onClick : function() {
			}
		});
		$('#addFuncName').textbox({
			iconAlign : 'left',
			prompt : '请输入功能名',
			required:true,
			width  : '100%'
		})
		$('#addFuncUrl').textbox({
			iconAlign : 'left',
			prompt : '请输入访问路径',
			width  : '100%'
		})
	});
</script>
</html>