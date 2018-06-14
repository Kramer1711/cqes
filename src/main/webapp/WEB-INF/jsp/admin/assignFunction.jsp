<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
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
<body class="easyui-layout">
	<div data-options="region:'center',title:'功能管理'"
		style="padding: 5px; background: #FFF;">
		<table id="dg"></table>
		<!-- 增加界面 -->
		<div id="addWindow" style="display:none;">
			<div style="text-align: center; padding-top: 20px;">
				<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
				<tr>
					<td align="right" width="24%">父功能：</td>
					<td width="76%">
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
		</div>
		
		<!-- 修改界面 -->
		<div id="updateWindow" style="display:none;">
			<form id="ff1" method="post">
				<div style="text-align: center; padding-top: 10px;">
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
					<tr>
						<td align="right" width="25%">功能名：</td>
						<td width="75%">
							<input id="updateFuncName" name="updateFuncName" type="text"
								style="100%;">
						</td>
					</tr>
					<tr>
						<td align="right">访问路径：</td>
						<td><input id="updateFuncUrl" name="updateFuncUrl" type="text"
							style="width:100%;">
						</td>
					</tr>
					</table>
					<div style="margin-top:5px;margin-bottom: 15px;">
						<a id="sureUpdate" href="#">确认</a>&emsp;<a id="cancelUpdate" href="#">取消</a>
					</div>
				</div>
			</form>
		</div>


	</div>
</body>
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

/**
 * 添加功能窗口
 */
$(function() {
	$('#addWindow').window({
		closed : true,
		width : 400,
		height : 230,
		modal : true,
		title : "添加功能",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	function insertFunction(){
		var parentFunc=$('#addParentFun').combotree('tree').tree('getSelected');
		console.log(parentFunc);
		var func={
			"functionName":$('#addFuncName').val(),
			"functionPath":$("#addFuncUrl").val(),
			"functionPid":parentFunc.id,
			"parentState":parentFunc.status
		};
		$('#addWindow').window('close',true);
		ajaxLoading("请稍等，正在添加相应功能……");
		$.ajax({
			url : '${pageContext.request.contextPath}/function/insertFunction',
			type : 'post',
			data : JSON.stringify(func),
			dataType : 'json',
			contentType : "application/json",
			success : function(data){
				ajaxLoadEnd();
				$('#addFuncName').textbox('clear');
				$("#addFuncUrl").textbox('clear');
				if(data.status=="true"){
					$.messager.alert('提示','添加功能成功','info');
					$('#addParentFun').combotree('clear');
					$('#addParentFun').combotree('reload');
					$('#dg').treegrid('load');
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
			if(parentFunc.attributes.fpath==null||parentFunc.attributes.fpath==''){
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
		iconCls : 'icon-no',
		onClick : function() {
			$('#addWindow').window('close',true);
		}
	});
	$('#addFuncName').textbox({
		iconAlign : 'left',
		prompt : '请输入功能名',
		required:true,
		width  : '100%'
	});
	$('#addFuncUrl').textbox({
		iconAlign : 'left',
		prompt : '请输入访问路径',
		width  : '100%'
	});
});
/**
 * 修改用户信息窗口
 */
 $(function() {
	$('#updateWindow').window({
		closed : true,
		width : 435,
		height : 'auto',
		modal : true,
		title : "修改帐号信息",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});

	$('#updateFuncName').textbox({
		width : "100%",
		type : "text",
		prompt : "请输入功能名称",
		iconAlign : 'left',
		required : 'true'
	});
	$('#updateFuncUrl').textbox({
		width : "100%",
		prompt : "请输入访问路径",
		type : "text",
		iconAlign : 'left'
	});
	$('#sureUpdate').linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			var checks = $('#dg').treegrid('getSelections');
			if(checks==null||checks.length<1){
				$.messager.alert('错误', '请选择待修改的功能', 'error');
				return;
			}
			else if(checks.length==1){
				var funcName=$('#updateFuncName').val();
				var func={
					"functionName":funcName,
					"functionPath":$("#updateFuncUrl").val(),
					"functionId":checks[0].id
				};
				if(funcName==null||funcName.length==0){
					$.messager.alert('错误', '功能名不能为空', 'error');
					return;
				}else{
					$('#updateWindow').window("close", true);
					ajaxLoading("请稍等，正在修改功能信息……");
					$.ajax({
						url : '${pageContext.request.contextPath}/function/updateFunction',
						type : 'post',
						data : JSON.stringify(func),
						dataType : 'json',
						contentType : "application/json",
						success : function(data){
							ajaxLoadEnd();
							if(data.status=="true"){
								$.messager.alert('提示','修改修改功能信息成功');
								$('#dg').treegrid('load');
							}else{
								$.messager.alert('提示','修改功能信息失败');
							}
						},
						error: function(){
							ajaxLoadEnd();
							$.messager.alert('提示','修改功能信息失败');
						}
					});
				}
			}else{
				$.messager.alert('提示','一次只能修改一个功能','error');
				return;
			}
		}
	});
	$('#cancelUpdate').linkbutton({
		iconCls : 'icon-no',
		onClick : function() {
			$('#updateWindow').window("close", true);
		}
	});	
 });

/**
 * 表格
 */
$(function() {
	$('#dg').treegrid({
		url : '${pageContext.request.contextPath}/getRoleTree?rid=-1',
		idField:"id",
		treeField:"text",
		striped : true,
		rownumbers : true,
		fitColumns : true,
		maxHeight:"868px",
		singleSelect:true,
		checkOnSelect : true,
		onClickRow: function(rowIndex){
            $("input[type='radio'][value='"+rowIndex.id+"']").attr('checked',true);
        },
        onLoadSuccess:function(){
        	$('#dg').treegrid('expandAll');
        },
		columns : [ [ {
			field : 'ck',
			formatter: function(value, rowData, rowIndex){
	            return '<input type="radio" name="selectRadio" id="selectRadio" value="' + rowData.id + '" />';
	        },
		}, {
			field : 'text',
			title : '名称',
			width : 100
		}, {
			field : 'fpath',
			title : '路径',
			width : 100
		}] ],
		toolbar : 
	    [{
			text : '添加功能',
			iconCls : 'icon-add',
			handler : function() {
				var checks = $('#dg').treegrid('getSelections');
				if(checks==null||checks.length<1){
					$.messager.alert('提示','请选择相应的父功能','info');
				}else{
					$('#addParentFun').combotree('setValue',checks[0].id);
					$('#addWindow').window('open');
				}
			}
		},'-',
		{
			text : '修改功能信息',
			iconCls : 'icon-edit',
			handler : function() {
				var checks = $('#dg').treegrid('getSelections');
				if (checks.length == 1) {
					$('#updateFuncName').textbox('setValue',checks[0].text);
					$('#updateFuncUrl').textbox('setValue',checks[0].fpath);
					$('#updateWindow').window("open", true);
				}else if(checks.length<1){
					$.messager.alert('提示','请选择待修改的帐号信息','info');
					return;
				}else{
					$.messager.alert('提示','一次只能修改一个功能','info');
					return;
				}
			}
		},'-',
		{
			text : '删除功能',
			iconCls : 'icon-no',
			handler : function() {
				var checks = $('#dg').treegrid('getSelections');
				if(checks.length<1){
					$.messager.alert('提示','请选择待删除的功能','info');
				}else{
					var parent = $("#dg").treegrid("getParent", checks[0].id);
					if(parent==null){
						$.messager.alert('提示','该功能为最顶层功能，无法删除','error');
					}else{
						$.messager.confirm('提示', '确定删除所选的功能吗？',function(sure){
							if (sure){
								var func=null;
								if(parent.children.length==1){
									func={
										"functionId":checks[0].id,
										"functionPid":checks[0]._parentId
									};
								}else{
									func={
										"functionId":checks[0].id	
									};
								}
								ajaxLoading("请稍等，正在删除所选功能……");
								$.ajax({
									url : '${pageContext.request.contextPath}/function/deleteFunction',
									type : 'post',
									data : JSON.stringify(func),
									dataType : 'json',
									contentType : "application/json",
									success : function(data){
										ajaxLoadEnd();
										if(data.status=="true"){
											if(parent.children.length==1){
												$.messager.alert('提示','删除功能成功,3秒后将刷新页面');
												setTimeout("window.location.reload()", 3000 );
											}else{
												$.messager.alert('提示','删除功能成功');
												$('#dg').treegrid('load');
											}
										}else{
											$.messager.alert('提示','删除功能失败');
											$('#dg').treegrid('load');
										}
									},
									error: function(){
										ajaxLoadEnd();
										$.messager.alert('提示','删除功能失败');
									}
								});//ajax END
						    }//if(sure) END
						});//conofirm END						
					}//else(parent!=null) END
				}//else(checks.length>0) END
			}
		},'-']
	});
});
</script>
</html>