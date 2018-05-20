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
	<div data-options="region:'north',title:'查询',collapsible:false,split:true" style="height: 160px;">
		<table style="width:100%;margin:0 auto; border-spacing:0px 10px;">
			<tr>
				<td align="right" width="18%">帐号：</td>
				<td width="27%"><input id="search_accountName" name="search_accountName" type="text"></td>
				
				<td align="right" width="18%">姓名：</td>
				<td width="27%"><input id="search_realName" name="search_realName" type="text"></td>
				<td width="5%">&nbsp;</td><td width="5%">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" width="18%">角色：</td>
				<td width="27%">
					<input id="search_roleId" name="search_roleId" class="easyui-combobox">
				</td>
				<td align="right" width="18%">状态：</td>
				<td width="27%">
					<input id="search_state" name="search_state" class="easyui-combobox">
				</td>
				<td width="5%">&nbsp;</td>
				<td width="5%">&nbsp;</td>
			</tr>
		</table>
		<div style="text-align: center; padding-top: 2px;">
			<a id="search" href="#">确认</a>
			&emsp;&emsp;
			<a id="cancel_search" href="#">重置</a>
		</div>
	</div>
	<div data-options="region:'center',title:'显示信息'"
		style="padding: 5px; background: #FFF;">
		<table id="dg"></table>

		<!-- 增加界面 -->
		<div id="addWindow" style="display:none;">
			<form id="addForm" method="post">
				<div style="text-align: center; padding-top: 20px;">
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
					<tr>
						<td align="right" width="15%">帐号：</td>
						<td width="85%">
							<input id="add_accountName" name="add_accountName" type="text"
								style="width:300px;">
						</td>
					</tr>
					<tr>
						<td align="right">姓名：</td>
						<td><input id="add_realName" name="add_realName" type="text"
							style="width:300px;">
						</td>
					</tr>
					<tr>
						<td align="right">角色：</td>
						<td><input id="add_roleId" name="add_roleId" class="easyui-combobox"
							style="width:300px;">
						</td>
					</tr>
					<tr>
						<td align="right">状态：</td>
						<td><input id="add_status" name="add_status" class="easyui-combobox"
							style="width:300px;">
						</td>
					</tr>
					</table>
					<a id="sureAdd" href="#">确认</a> <a id="cancelAdd" href="#">取消</a>
				</div>
			</form>
		</div>
		
		<!-- Excel导入界面 -->
		<div id="importWindow" style="display:none;">
			<form id="ff2" enctype="multipart/form-data" method="post">
				<div style="text-align: center; padding-top: 50px;">
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
						<tr>
							<td align="right" width="20%">上传文件：</td>
							<td width="80%">
								<input id="fb" type="text" name="file" style="width:100%">
							</td>
						</tr>
						<tr>
							<td align="right" width="20%">所属角色：</td>
							<td width="80%">
								<input id="import_roleId" name="import_roleId" class="easyui-combobox">
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<b style="color: rgb(176,7,14)">*表格列名请严格按照序号，帐号（学号/工号），用户名的顺序。</b>  
							</td>
						</tr>
					</table>
					<br> <a id="sureImport" href="#">确认</a>&emsp;<a id="cancelImport" href="#">取消</a>
				</div>
			</form>
		</div>
		
		<!-- 修改界面 -->
		<div id="updateWindow" style="display:none;">
			<form id="ff1" method="post">
				<div style="text-align: center; padding-top: 10px;">
					<table id="singleUpdateTable" style="width:80%;margin:0 auto; border-spacing:0px 10px;">
					<tr>
						<td align="right" width="15%">帐号：</td>
						<td width="85%">
							<input id="update_accountName" name="update_accountName" type="text"
								style="width:300px;">
						</td>
					</tr>
					<tr>
						<td align="right">用户名：</td>
						<td><input id="update_realName" name="update_realName" type="text"
							style="width:300px;">
						</td>
					</tr>
					</table>
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
					<tr>
						<td align="right" width="15%">角色：</td>
						<td width="85%"><input id="update_roleId" name="update_roleId" class="easyui-combobox"
							style="width:300px;">
						</td>
					</tr>
					<tr>
						<td align="right">状态：</td>
						<td><input id="update_status" name="update_status" class="easyui-combobox"
							style="width:300px;">
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
 * 查询区域
 */
$(function() {
	//帐号
	$('#search_accountName').textbox({
		width : "100%",
		prompt : "请输入帐号",
		type : "text",
		iconCls : 'icon-man',
		iconAlign : 'left'
	});
	//用户名
	$('#search_realName').textbox({
		width : "100%",
		prompt : "请输入用户名",
		type : "text",
		iconCls : 'icon-man',
		iconAlign : 'left'
	});
	//所属角色
	$('#search_roleId').combobox({    
	    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',
	    width : "100%",
	    valueField:'id',
	    textField:'text',
	    editable:false,
	    loadFilter:function(data){
	        data.unshift({id:'-1',text:'全部',selected:'true'});
	        return data;
	    },
	    onChange:function(newValue,oldValue){
	    }
	});
	//用户状态
	$('#search_state').combobox({       
	    valueField:'id',
	    width : "100%",
	    textField:'text',
	    data:[{id:'-1',text:'全部',selected:'true'},{id:'0',text:'停用'},{id:'1',text:'正常'}],
	    editable:false,
	});
	//搜索
	$('#search').linkbutton({
		iconCls : 'icon-search',
		width   : 100,
		onClick : function() {
			$('#dg').datagrid('load',{
				"accountName": $("#search_accountName").val(),
				"realName": $("#search_realName").val(),
				"roleId":$("#search_roleId").val(),
				"status":$("#search_state").val()
			});
		}
	});
	//重置
	$('#cancel_search').linkbutton({
		iconCls : 'icon-reload',
		width   : 100,
		onClick : function() {
			$('#search_accountName').textbox('clear');
			$('#search_realName').textbox('clear');
			$('#search_roleId').combobox('setValue','-1');
			$('#search_state').combobox('setValue','-1');
		}
	});
});

/**
 * 添加用户窗口
 */
$(function() {
	$('#addWindow').window({
		closed : true,
		width : 550,
		height : 300,
		modal : true,
		title : "添加用户",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	//帐号
	$('#add_accountName').textbox({
		width : "100%",
		prompt : "请输入帐号",
		type : "text",
		iconCls : 'icon-man',
		iconAlign : 'left'
	});
	//用户名
	$('#add_realName').textbox({
		width : "100%",
		prompt : "请输入用户名",
		type : "text",
		iconCls : 'icon-man',
		iconAlign : 'left'
	});
	//所属角色
	$('#add_roleId').combobox({    
	    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',
	    width : "100%",
	    valueField:'id',
	    textField:'text',
	    editable:false,
	    onLoadSuccess: function (data) {
	    	if (data!=null) {
		    	$('#add_roleId').combobox('setValue',data[0].id);
		    }
		}
	});
	//用户状态
	$('#add_status').combobox({       
	    valueField:'id',
	    width : "100%",
	    textField:'text',
	    data:[{id:'1',text:'正常',selected:'true'},{id:'0',text:'停用'}],
	    editable:false,
	});
	//1，3新增按钮
	$('#sureAdd').linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			$('#addForm').form('submit');
		}
	});
	$('#cancelAdd').linkbutton({
		iconCls : 'icon-no',
		onClick : function() {
			$('#addWindow').window("close", true);
		}
	});
	//添加表单
	$('#addForm').form({
		url : '${pageContext.request.contextPath}/admin/insertAccountOfParam',
		onSubmit : function() {
			var accountName = $('#add_accountName').val();
			var realName = $('#add_realName').val();
			if (accountName.length == 0 && realName.length == 0) {
				$.messager.alert('错误', '请输入帐号和姓名', 'error');
				return false;
			}else if (accountName.length == 0) {
				$.messager.alert('错误', '请输入帐号和姓名', 'error');
				return false;
			}else if (realName.length == 0) {
				$.messager.alert('错误', '请输入姓名', 'error');
				return false;
			} 
		},
		success : function(data) {
			var s = eval("(" + data + ")");
			if (s.status == 'true') {
				$.messager.alert('成功', '添加用户成功', 'info');
				$('#addWindow').window("close", true);
				$('#add_accountName').textbox('clear');
				$('#add_realName').textbox('clear');
				$('#dg').datagrid('load',{
						"accountName": $("#search_accountName").val(),
						"realName": $("#search_realName").val(),
						"roleId":$("#search_roleId").val(),
						"status":$("#search_state").val()});
			} else {
				$.messager.alert('错误', '添加用户失败（可能已存在该用户）', 'error');
			}
		},
		error : function(){
			$.messager.alert('错误', '网络无连接或服务器出现故障', 'error');
		}
		
	});
});
/**
 * 导入excel文件窗口
 */
 $(function() {
		$('#importWindow').window({
			closed : true,
			width : 500,
			height : 300,
			modal : true,
			title : "导入用户数据",
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : true,
			draggable : true,
			resizable : false,
		});	 

		$('#fb').filebox({
			buttonText : '选择文件',
			buttonAlign : 'left'
		});
		//所属角色
		$('#import_roleId').combobox({    
		    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',
		    width : '100%',
		    valueField:'id',
		    textField:'text',
		    editable:false,
		    onLoadSuccess: function (data) {
		    	if (data!=null) {
			    	$('#import_roleId').combobox('setValue',data[0].id);
			    }
			}
		});
		//导入Excel表单
		$('#ff2').form({
			url : '${pageContext.request.contextPath}/admin/insertAccountsOfXML',
			onSubmit : function() {
				var name = $("#fb").filebox('getValue');
				console.log(name);
				if (name.endsWith(".xlsx") || name.endsWith(".xls")) {
					$('#importWindow').window("close", true);
					ajaxLoading("请稍等，正在上传文件……");
					return true;
				} else {
					$.messager.alert('错误', '所选中文件不是Excel文件', 'error');
					$("#fb").filebox('clear');
					return false;
				}
			},
			success : function(data) {
				ajaxLoadEnd();
				var s = eval("(" + data + ")");
				if (s.status=="true"){
					var message="上传成功，共有"+s.real+"条数据。<br>其中成功导入"+s.insert+"条数据，已存在"+s.have+"条数据。";
					$.messager.alert('成功', message, 'info');
					$('#dg').datagrid('load',{
						"accountName": $("#search_accountName").val(),
						"realName": $("#search_realName").val(),
						"roleId":$("#search_roleId").val(),
						"status":$("#search_state").val()});
				} else {
					$.messager.alert('错误', '导入用户数据失败，请检查表格格式是否正确', 'error');
				}
			}
		});
		$('#sureImport').linkbutton({
			iconCls : 'icon-ok',
			onClick : function() {
				$('#ff2').form('submit');
			}
		});
		$('#cancelImport').linkbutton({
			iconCls : 'icon-no',
			onClick : function() {
				$('#importWindow').window("close", true);
			}
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

	$('#update_accountName').textbox({
		width : "100%",
		disable:"true",
		type : "text",
		iconAlign : 'left',
		disabled:'true'
	});
	$('#update_realName').textbox({
		width : "100%",
		prompt : "请输入用户姓名",
		type : "text",
		iconAlign : 'left'
	});
	//所属角色
	$('#update_roleId').combobox({    
	    url:'${pageContext.request.contextPath}/admin/getAllRolesOfCombobox',
	    width : "100%",
	    valueField:'id',
	    textField:'text',
	    editable:false
	});
	//用户状态
	$('#update_status').combobox({       
	    valueField:'id',
	    width : "100%",
	    textField:'text',
	    data:[{id:'1',text:'正常'},{id:'0',text:'停用'}],
	    editable:false,
	});
	$('#sureUpdate').linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			var accountList= new Array();
			var checks = $('#dg').datagrid('getSelections');
			if(checks==null||checks.length<1){
				$.messager.alert('错误', '请选择待修改的帐号', 'error');
				return;
			}
			else if(checks.length==1){
				var accountName=$('#update_roleId').val();
				if(accountName==null||accountName.length==0){
					$.messager.alert('错误', '用户名不能为空', 'error');
					return;
				}else{
					accountList.push({'accountId':checks[0].accountId,
						'realName':$('#update_realName').val(),
						'roleId':$('#update_roleId').val(),
						'accountStatus':$('#update_status').val()
						});
				}
			}else{
				for(var i=0;i<checks.length;i++){
					accountList.push({'accountId':checks[i].accountId,
						'roleId':$('#update_roleId').val(),
						'accountStatus':$('#update_status').val()
						});
				}
			}
			$('#updateWindow').window("close", true);
			ajaxLoading("请稍等，正在修改帐号信息……");
			$.ajax({
				url : '${pageContext.request.contextPath}/admin/updateAccounts',
				type : 'post',
				data : JSON.stringify(accountList),
				dataType : 'json',
				contentType : "application/json",
				success : function(data){
					ajaxLoadEnd();
					if(data.status=="true"){
						$.messager.alert('提示','修改账号信息成功');
						$('#dg').datagrid('load',{
							"accountName": $("#search_accountName").val(),
							"realName": $("#search_realName").val(),
							"roleId":$("#search_roleId").val(),
							"status":$("#search_state").val()
						});
					}else{
						$.messager.alert('提示','修改账号信息失败');
					}
				},
				error: function(){
					ajaxLoadEnd();
					$.messager.alert('提示','修改帐号信息失败');
				}
			});
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
	$('#dg').datagrid({
		url : '${pageContext.request.contextPath}/admin/getAccounts',
		striped : true,
		rownumbers : true,
		fitColumns : true,
		maxHeight:"589px",
		nowrap : false,
		pagination : true,//分页
		pageSize : 10,//初始化数据行数
		pageList : [ 10, 20, 50 ,100],//每页数据行数,
		checkOnSelect : true,
		queryParams:{
			"accountName": $("#search_accountName").val(),
			"realName": $("#search_realName").val(),
			"roleId":$("#search_roleId").val(),
			"status":$("#search_state").val()},
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'accountName',
			title : '帐号',
			width : 100
		}, {
			field : 'realName',
			title : '用户名',
			width : 100
		}, {
			field : 'roleName',
			title : '角色名',
			width : 100
		}, {
			field : 'statusName',
			title : '状态',
			width : 100
		}] ],
		toolbar : 
	    [{
			text : '添加用户',
			iconCls : 'icon-add',
			handler : function() {
				$('#addWindow').window("open", true);
			}
		},'-',
		{
			text : '导入用户数据',
			iconCls : 'icon-redo',
			handler : function() {
				$('#importWindow').window("open", true);
			}
		},'-',
		{
			text : '修改帐号信息',
			iconCls : 'icon-edit',
			handler : function() {
				var checks = $('#dg').datagrid('getSelections');
				if (checks.length == 1) {
					$('#singleUpdateTable').css('display','table');
					$('#update_accountName').textbox('setValue',checks[0].accountName);
					$('#update_realName').textbox('setValue',checks[0].realName);
					$('#update_roleId').combobox('select',checks[0].roleId);
					$('#update_status').combobox('select',checks[0].accountStatus);
					$('#updateWindow').window("open", true);
				}else if(checks.length<1){
					$.messager.alert('提示','请选择待修改的帐号信息','info');
				}else{
					$('#singleUpdateTable').css('display','none');
					$('#update_roleId').combobox('select',checks[0].roleId);
					$('#update_status').combobox('select',checks[0].accountStatus);
					$('#updateWindow').window("open", true);
				}
			}
		},'-',
		{
			text : '重置密码',
			iconCls : 'icon-reload',
			handler : function() {
				var checks = $('#dg').datagrid('getSelections');
				if(checks.length<1){
					$.messager.alert('提示','请选择待重置密码的帐号','info');
				}else{
					$.messager.confirm('提示', '确定重置所选帐号的密码吗？',function(sure){
						console.log(sure);
						if (sure){
							var accountList=new Array();
							for(var i=0;i<checks.length;i++){
								accountList.push({'accountId':checks[i].accountId});
							}
							ajaxLoading("请稍等，正在重置帐号密码……");
							$.ajax({
								url : '${pageContext.request.contextPath}/admin/resetPassword',
								type : 'post',
								data : JSON.stringify(accountList),
								dataType : 'json',
								contentType : "application/json",
								success : function(data){
									ajaxLoadEnd();
									if(data.status=="true"){
										$.messager.alert('提示','重置帐号密码成功');
									}else{
										$.messager.alert('提示','重置帐号密码失败');
									}
								},
								error: function(){
									ajaxLoadEnd();
									$.messager.alert('提示','重置帐号密码失败');
								}
							});//ajax END
					    }//if(sure) END
					});//conofirm END
				}//else END
			}
		},'-',
		{
			text : '下载Excel表格',
			iconCls : 'icon-tip',
			handler : function() {
		        var url = "${pageContext.request.contextPath}/admin/downloadXML";
		        var fileName = "帐号信息表.xls";
		        var form = $("<form></form>").attr("action", url).attr("method", "post");
		        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
		        form.appendTo('body').submit().remove();
			}
		},'-']
	});
	var p = $('#dg').datagrid('getPager');
       $(p).pagination({
           pageSize: 10,//每页显示的记录条数，默认为10  
           pageList: [10, 20,50,100],//可以设置每页记录条数的列表  
           beforePageText: '第',//页数文本框前显示的汉字  
           afterPageText: '页    共 {pages} 页',
           displayMsg: '当前显示第 {from} - {to} 条记录&emsp;&nbsp;共 {total} 条记录',
       });
});
</script>
</html>