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
	<div data-options="region:'center',title:'学院专业信息管理'"
		style="padding: 5px; background: #FFF;">
		<table id="dg"></table>
		<!-- 添加专业界面 -->
		<div id="addMajorWindow" style="display:none;">
			<div style="text-align: center; padding-top: 20px;">
				<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
				<tr>
					<td align="right" width="24%">所属学院：</td>
					<td width="76%">
						<input id="addParentCollege">
						</input> 
					</td>
				</tr>
				<tr>
					<td align="right">专业名：</td>
					<td>
						 <input id="addMajorName">
					</td>
				</tr>
				</table>
				<div style="margin-top:5px;margin-bottom: 15px;">
					<a id="sureAddMajor" href="#">确认</a>&emsp;<a id="cancelAddMajor" href="#">取消</a>
				</div>
			</div>
		</div>
		<!-- 添加学院界面 -->
		<div id="addCollegeWindow" style="display:none;">
			<div style="text-align: center; padding-top: 20px;">
				<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
				<tr>
					<td align="right" width="24%">学院名：</td>
					<td width="76%">
						 <input id="addCollegeName"/>
					</td>
				</tr>
				</table>
				<div style="margin-top:5px;margin-bottom: 15px;">
					<a id="sureAddCollege" href="#">确认</a>&emsp;<a id="cancelAddCollege" href="#">取消</a>
				</div>
			</div>
		</div>
		<!-- 修改界面 -->
		<div id="updateMajorWindow" style="display:none;">
			<form id="ff1" method="post">
				<div style="text-align: center; padding-top: 10px;">
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
						<tr>
							<td align="right" width="24%">所属学院：</td>
							<td width="76%">
								<input id="updateParentCollege"/>
							</td>
						</tr>
						<tr>
							<td align="right">专业名：</td>
							<td>
								 <input id="updateMajorName">
							</td>
						</tr>
					</table>
					<div style="margin-top:5px;margin-bottom: 15px;">
						<a id="sureUpdateMajor" href="#">确认</a>&emsp;<a id="cancelUpdateMajor" href="#">取消</a>
					</div>
				</div>
			</form>
		</div>

		<!-- 修改专业界面 -->
		<div id="updateCollegeWindow" style="display:none;">
			<form id="ff1" method="post">
				<div style="text-align: center; padding-top: 10px;">
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
						<tr>
							<td align="right" width="24%">学院名：</td>
							<td width="76%">
								<input id="updateCollegeName">
							</td>
						</tr>
					</table>
					<div style="margin-top:5px;margin-bottom: 15px;">
						<a id="sureUpdateCollege" href="#">确认</a>&emsp;<a id="cancelUpdateCollege" href="#">取消</a>
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
 * 添加专业信息窗口
 */
$(function() {
	$('#addMajorWindow').window({
		closed : true,
		width : 400,
		height : 230,
		modal : true,
		title : "添加专业信息",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	$('#addParentCollege').combobox({    
	    url:'${pageContext.request.contextPath}/collegeMajor/getCollegeCombobox',
	    width : "100%",
	    valueField:'collegeId',
	    textField:'collegeName',
	    editable:false,
	    onLoadSuccess: function (data) {
	    	if (data!=null) {
	    		console.log(data);
	    	   $('#addParentCollege').combobox('setValue',data[0].collegeId);
	    	}
	    }
	});
	$("#sureAddMajor").linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			if($('#addMajorName').val()==null||$('#addMajorName').val().length<1){
				$.messager.alert('提示','请输入专业名称','error');
			}else{
				var major={
					"majorName":$('#addMajorName').val(),
					"collegeId":$("#addParentCollege").val()
				};
				$('#addMajorWindow').window('close',true);
				ajaxLoading("请稍等，正在添加专业信息……");
				$.ajax({
					url : '${pageContext.request.contextPath}/collegeMajor/insertMajor',
					type : 'post',
					data : JSON.stringify(major),
					dataType : 'json',
					contentType : "application/json",
					success : function(data){
						ajaxLoadEnd();
						$('#addMajorName').textbox('clear');
						if(data.status=="true"){
							$.messager.alert('提示','添加专业成功','info');
							$('#addParentCollege').combobox('reload');
							$('#updateParentCollege').combobox('reload');
							$('#dg').treegrid('load');
						}else if(data.status=="false"){
							$.messager.alert('提示','添加专业失败','error');
						}
					},
					error: function(){
						ajaxLoadEnd();
						$.messager.alert('提示','添加专业失败');
					}
				});//ajax END 
			}
		}
	});
	$("#cancelAddMajor").linkbutton({
		iconCls : 'icon-no',
		onClick : function() {
			$('#addMajorWindow').window('close',true);
		}
	});
	$('#addMajorName').textbox({
		iconAlign : 'left',
		prompt : '请输入专业名',
		required:true,
		width  : '100%'
	});
});
/**
 * 添加学院信息窗口
 */
$(function() {
	$('#addCollegeWindow').window({
		closed : true,
		width : 400,
		height : 180,
		modal : true,
		title : "添加学院信息",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	$('#addCollegeName').textbox({
		iconAlign : 'left',
		prompt : '请输入学院名',
		required:true,
		width  : '100%'
	});
	$("#sureAddCollege").linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			if($('#addCollegeName').val()==null||$('#addCollegeName').val().length<1){
				$.messager.alert('提示','请输入学院名称','error');
			}else{
				var college={
					"collegeName":$('#addCollegeName').val()
				};
				$('#addCollegeWindow').window('close',true);
				ajaxLoading("请稍等，正在添加学院信息……");
				$.ajax({
					url : '${pageContext.request.contextPath}/collegeMajor/insertCollege',
					type : 'post',
					data : JSON.stringify(college),
					dataType : 'json',
					contentType : "application/json",
					success : function(data){
						ajaxLoadEnd();
						$('#addCollegeName').textbox('clear');
						if(data.status=="true"){
							$.messager.alert('提示','添加学院信息成功','info');
							$('#addParentCollege').combobox('reload');
							$('#updateParentCollege').combobox('reload');
							$('#dg').treegrid('load');
						}else if(data.status=="false"){
							$.messager.alert('提示','添加学院信息失败','error');
						}
					},
					error: function(){
						ajaxLoadEnd();
						$.messager.alert('提示','添加学院信息失败');
					}
				});//ajax END 
			}
		}
	});
	$("#cancelAddCollege").linkbutton({
		iconCls : 'icon-no',
		onClick : function() {
			$('#addCollegeWindow').window('close',true);
		}
	});
});
/**
 * 修改专业信息窗口
 */
 $(function() {
	$('#updateMajorWindow').window({
		closed : true,
		width : 435,
		height : 'auto',
		modal : true,
		title : "修改专业信息",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	$('#updateParentCollege').combobox({    
	    url:'${pageContext.request.contextPath}/collegeMajor/getCollegeCombobox',
	    width : "100%",
	    valueField:'collegeId',
	    textField:'collegeName',
	    editable:false,
	    onLoadSuccess: function (data) {
	    	if (data!=null) {
	    	   $('#updateParentCollege').combobox('setValue',data[0].collegeId);
	    	}
	    }
	});
	$('#updateMajorName').textbox({
		width : "100%",
		prompt : "请输入专业名",
		required : 'true',
		type : "text",
		iconAlign : 'left'
	});
	$('#sureUpdateMajor').linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			var checks = $('#dg').treegrid('getSelections');
			var major={
				"majorName":$('#updateMajorName').val(),
				"collegeId":$("#updateParentCollege").val(),
				"majorId":checks[0].majorId
			};
			if($('#updateMajorName').val()==null||$('#updateMajorName').val().length==0){
				$.messager.alert('错误', '专业名不能为空', 'error');
				return;
			}else{
				$('#updateMajorWindow').window("close", true);
				ajaxLoading("请稍等，正在修改专业信息……");
				$.ajax({
					url : '${pageContext.request.contextPath}/collegeMajor/updateMajor',
					type : 'post',
					data : JSON.stringify(major),
					dataType : 'json',
					contentType : "application/json",
					success : function(data){
						ajaxLoadEnd();
						if(data.status=="true"){
							$.messager.alert('提示','修改专业信息成功');
							$('#dg').treegrid('load');
						}else{
							$.messager.alert('提示','修改专业信息失败');
						}
					},
					error: function(){
						ajaxLoadEnd();
						$.messager.alert('提示','修改功能信息失败');
					}
				});
			}
		}
	});
	$('#cancelUpdateMajor').linkbutton({
		iconCls : 'icon-no',
		onClick : function() {
			$('#updateMajorWindow').window("close", true);
		}
	});	
 });

 /**
  * 修改学院信息窗口
  */
  $(function() {
 	$('#updateCollegeWindow').window({
 		closed : true,
 		width : 435,
 		height : 'auto',
 		modal : true,
 		title : "修改学院信息",
 		collapsible : false,
 		minimizable : false,
 		maximizable : false,
 		closable : true,
 		draggable : true,
 		resizable : false,
 	});
 	$('#updateCollegeName').textbox({
 		width : "100%",
 		prompt : "请输入学院名",
 		required : 'true',
 		type : "text",
 		iconAlign : 'left'
 	});
 	$('#sureUpdateCollege').linkbutton({
 		iconCls : 'icon-ok',
 		onClick : function() {
 			var checks = $('#dg').treegrid('getSelections');
 			var college={
 				"collegeName":$('#updateCollegeName').val(),
 				"collegeId":checks[0].collegeId
 			};
 			if($('#updateCollegeName').val()==null||$('#updateCollegeName').val().length==0){
 				$.messager.alert('错误', '学院名不能为空', 'error');
 				return;
 			}else{
 				$('#updateCollegeWindow').window("close", true);
 				ajaxLoading("请稍等，正在修改学院信息……");
 				$.ajax({
 					url : '${pageContext.request.contextPath}/collegeMajor/updateCollege',
 					type : 'post',
 					data : JSON.stringify(college),
 					dataType : 'json',
 					contentType : "application/json",
 					success : function(data){
 						ajaxLoadEnd();
 						if(data.status=="true"){
 							$.messager.alert('提示','修改学院信息成功');
 							$('#dg').treegrid('load');
 						}else{
 							$.messager.alert('提示','修改学院信息失败');
 						}
 					},
 					error: function(){
 						ajaxLoadEnd();
 						$.messager.alert('提示','修改学院信息失败');
 					}
 				});
 			}
 		}
 	});
 	$('#cancelUpdateCollege').linkbutton({
 		iconCls : 'icon-no',
 		onClick : function() {
 			$('#updateCollegeWindow').window("close", true);
 		}
 	});	
  });
 
/**
 * 表格
 */
$(function() {
	$('#dg').treegrid({
		url : '${pageContext.request.contextPath }/collegeMajor/getCollegeMajorTree',
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
		}] ],
		toolbar : 
	    [{
			text : '添加学院',
			iconCls : 'icon-add',
			handler : function() {
				$('#addCollegeWindow').window('open');
			}
		},'-',
		{
			text : '添加专业',
			iconCls : 'icon-add',
			handler : function() {
				var checks = $('#dg').treegrid('getSelections');
				if(checks!=null && checks.length>0){
					$('#addParentCollege').combobox('setValue',checks[0].collegeId);
				}
				$('#addMajorWindow').window('open');
			}
		},'-',
		{
			text : '修改学院或专业信息',
			iconCls : 'icon-edit',
			handler : function() {
				var checks = $('#dg').treegrid('getSelections');
				if(checks!=null && checks.length>0){
					var parent = $("#dg").treegrid("getParent", checks[0].id);
					if (parent!=null) {
						$('#updateMajorName').textbox('setValue',checks[0].text);
						$('#updateParentCollege').combobox('setValue',checks[0].collegeId);
						$('#updateMajorWindow').window("open", true);
					}else{
						$('#updateCollegeName').textbox('setValue',checks[0].text);
						$('#updateCollegeWindow').window("open", true);
					}
				}else{
					$.messager.alert('提示','请选择待修改的学院或专业信息','info');
					return;
				}
			}
		},'-',
		{
			text : '删除学院或专业信息',
			iconCls : 'icon-no',
			handler : function() {
				var checks = $('#dg').treegrid('getSelections');
				if(checks.length<1){
					$.messager.alert('提示','请选择待删除的学院或专业','info');
				}else{
					var parent = $("#dg").treegrid("getParent", checks[0].id);
					if(parent==null){
						if(checks[0].children.length>0){
							$.messager.alert('提示','请先删除学院相应的专业信息','error');
						}else{
							$.messager.confirm('提示', '确定删除所选的学院信息吗？',function(sure){
								if (sure){
									ajaxLoading("请稍等，正在删除所选学院信息……");
									$.ajax({
										url : '${pageContext.request.contextPath}/collegeMajor/deleteCollege',
										type : 'post',
										data : {
											'collegeId':checks[0].collegeId
										},
										dataType : 'json',
										contentType : "application/x-www-form-urlencoded",
										success : function(data){
											ajaxLoadEnd();
											if(data.status=="true"){
												$.messager.alert('提示','删除学院信息成功');
												$('#dg').treegrid('load');
												$('#addParentCollege').combobox('reload');
											}else{
												$.messager.alert('提示','删除学院信息失败');
											}
										},
										error: function(){
											ajaxLoadEnd();
											$.messager.alert('提示','删除学院信息失败');
										}
									});//ajax END
							    }//if(sure) END
							});//conofirm END	
						}
					}else{
						$.messager.confirm('提示', '确定删除所选的专业吗？',function(sure){
							if (sure){
								ajaxLoading("请稍等，正在删除所选专业……");
								$.ajax({
									url : '${pageContext.request.contextPath}/collegeMajor/deleteMajor',
									type : 'post',
									data : {
										"majorId":checks[0].majorId
									},
									dataType : 'json',
									contentType : "application/x-www-form-urlencoded",
									success : function(data){
										ajaxLoadEnd();
										if(data.status=="true"){
											$.messager.alert('提示','删除专业信息成功');
											setTimeout("window.location.reload()", 1000 );
										}else{
											$.messager.alert('提示','删除专业信息失败');
										}
									},
									error: function(){
										ajaxLoadEnd();
										$.messager.alert('提示','删除专业信息失败');
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