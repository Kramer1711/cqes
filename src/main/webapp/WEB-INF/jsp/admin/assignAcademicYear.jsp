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
	<div data-options="region:'north',title:'查询',collapsible:false,split:true" style="height: 120px;">
		<table style="width:100%;margin:0 auto; border-spacing:0px 10px;">
			<tr>
				<td align="right" width="25%">年份：</td>
				<td width="12%"><input id="search_startYear" name="search_startYear" type="text"></td>
				<td width="1%">-</td>
				<td width="12%"><input id="search_endYear" name="search_endYear" type="text"></td>
				<td align="right" width="25%">状态：</td>
				<td width="20%">
					<input id="search_state" name="search_state" class="easyui-combobox"></td>
				<td width="5%">&nbsp;</td><td width="5%">&nbsp;</td>
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
		<!-- 展示界面 -->
		<div id="showWindow" style="display:none;">
			<div style="text-align: center;">
				<h3 id="show_academicYear"></h3>
				<table style="width:80%;margin:0 auto;border-collapse: collapse;"
				 	border="1px;" cellspacing="0">
					<tr>
						<td align="right" width="60%">学年成绩平均分所取百分比：</td>
						<td><input id="show_cjpjf" type="text" readonly="true"
							style="border:none;width:100%">
						</td>
					</tr>
					<tr>
						<td align="right">操行分所取百分比：</td>
						<td><input id="show_cxf" type="text" readonly="true"
							style="border:none;width:100%;">
						</td>
					</tr>
					<tr>
						<td align="right">科技创新分所取百分比：</td>
						<td><input id="show_kjcxf" type="text" readonly="true"
							style="border:none;width:100%;">
						</td>
					</tr>
					<tr>
						<td align="right">文体分所取百分比：</td>
						<td><input id="show_wtf" type="text" readonly="true"
							style="border:none;width:100%;">
						</td>
					</tr>
				</table>
				<a id="sureShow" href="#" style="margin-top:10px;">确认</a>
			</div>
		</div>
		<!-- 增加界面 -->
		<div id="addWindow" style="display:none;">
			<form id="addForm" method="post">
				<div style="text-align: center; padding-top: 20px;">
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
					<tr>
						<td align="right" width="15%">起始年份：</td>
						<td width="85%">
							<input id="add_startYear" name="add_startYear" type="text"
								style="width:300px;">
						</td>
					</tr>
					<tr>
						<td align="right">终止年份：</td>
						<td><input id="add_endYear" name="add_endYear" type="text"
							style="width:300px;">
						</td>
					</tr>
					</table>
					<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
						<tr>
							<td align="right" width="40%">学年成绩平均分所取百分比：</td>
							<td><input id="add_cjpjf" name="add_cjpjf" type="text"
								style="width:90%;"><b>%</b>
							</td>
						</tr>
						<tr>
							<td align="right">操行分所取百分比：</td>
							<td><input id="add_cxf" name="add_cxf" type="text"
								style="width:90%;"><b>%</b>
							</td>
						</tr>
						<tr>
							<td align="right">科技创新分所取百分比：</td>
							<td><input id="add_kjcxf" name="add_kjcxf" type="text"
								style="width:90%;"><b>%</b>
							</td>
						</tr>
						<tr>
							<td align="right">文体分所取百分比：</td>
							<td><input id="add_wtf" name="add_wtf" type="text"
								style="width:90%;"><b>%</b>
							</td>
						</tr>
					</table>
					<a id="sureAdd" href="#">确认</a>
					&emsp;<a id="cancelAdd" href="#">取消</a>
				</div>
			</form>
		</div>
		<!-- 修改界面 -->
		<div id="updateWindow" style="display:none;">
			<div style="text-align: center; padding-top: 20px;">
				<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
				<tr>
					<td align="right" width="15%">起始年份：</td>
					<td width="85%">
						<input id="update_startYear" name="update_startYear" type="text"
							style="width:300px;">
					</td>
				</tr>
				<tr>
					<td align="right">终止年份：</td>
					<td><input id="update_endYear" name="update_endYear" type="text"
						style="width:300px;">
					</td>
				</tr>
				</table>
				<table style="width:80%;margin:0 auto; border-spacing:0px 10px;">
					<tr>
						<td align="right" width="40%">学年成绩平均分所取百分比：</td>
						<td><input id="update_cjpjf" name="update_cjpjf" type="text"
							style="width:90%;"><b>%</b>
						</td>
					</tr>
					<tr>
						<td align="right">操行分所取百分比：</td>
						<td><input id="update_cxf" name="update_cxf" type="text"
							style="width:90%;"><b>%</b>
						</td>
					</tr>
					<tr>
						<td align="right">科技创新分所取百分比：</td>
						<td><input id="update_kjcxf" name="update_kjcxf" type="text"
							style="width:90%;"><b>%</b>
						</td>
					</tr>
					<tr>
						<td align="right">文体分所取百分比：</td>
						<td><input id="update_wtf" name="update_wtf" type="text"
							style="width:90%;"><b>%</b>
						</td>
					</tr>
				</table>
				<a id="sureUpdate" href="#">确认</a>
				&emsp;<a id="cancelUpdate" href="#">取消</a>
			</div>
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
function checkSearch(){
	if($("#search_startYear").val()!=""&&$("#search_endYear").val()!=""){
		if(parseInt($("#search_startYear").val())>=parseInt($("#search_endYear").val())){
			$.messager.alert('提示', '结束年份必须大于起始年份', 'error');
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}
/**
 * 查询区域
 */
$(function() {
	//帐号
	$('#search_startYear').numberbox({
		width : "100%",
		prompt : "请输入起始年份",
		type : "text",
		iconAlign : 'left'
	});
	//用户名
	$('#search_endYear').numberbox({
		width : "100%",
		prompt : "请输入结束年份",
		type : "text",
		iconAlign : 'left'
	});
	//用户状态
	$('#search_state').combobox({       
	    valueField:'id',
	    width : "100%",
	    textField:'text',
	    panelHeight:"auto",
	    data:[{id:'all',text:'全部',selected:'true'},
	          {id:'done',text:'已完成'},
	          {id:'doing',text:'进行中'},
	          {id:'undo',text:'未开始'}
	     ],
	    editable:false,
	});
	//搜索
	$('#search').linkbutton({
		iconCls : 'icon-search',
		width   : 100,
		onClick : function() {
			if(checkSearch()==false){
				return false;
			}
			$('#dg').datagrid('load',{
				"startYear": $("#search_startYear").val(),
				"endYear": $("#search_endYear").val(),
				"status":$("#search_state").val()
			});
		}
	});
	//重置
	$('#cancel_search').linkbutton({
		iconCls : 'icon-reload',
		width   : 100,
		onClick : function() {
			$('#search_startYear').numberbox('clear');
			$('#search_endYear').numberbox('clear');
			$('#search_state').combobox('setValue','all');
		}
	});
});
function showWindow(academicYear){
	$('#show_academicYear').html(""+academicYear+"学年");
	ajaxLoading("查询中……");
	$.ajax({
		url : '${pageContext.request.contextPath}/evaluationMethod/getByAcademicYear',
		type : 'post',
		data :{
			'academicYear':academicYear
		},
		dataType : 'json',
		success : function(data){
			$('#show_cxf').val(data.cxf);
			$('#show_wtf').val(data.wtf);
			$('#show_cjpjf').val(data.cjpjf);
			$('#show_kjcxf').val(data.kjcxf);
			ajaxLoadEnd();
			$('#showWindow').window('open');
		},
		error: function(){
			ajaxLoadEnd();
			$.messager.alert('提示','查询信息失败');
		}
	});

}
/**
 * 显示评测学年信息窗口
 */
 $(function() {
 	$('#showWindow').window({
 		closed : true,
 		width : 350,
 		height : 215,
 		modal : true,
 		title : "查看评测学年信息",
 		collapsible : false,
 		minimizable : false,
 		maximizable : false,
 		closable : true,
 		draggable : true,
 		resizable : false,
 	});
	//重置
	$('#sureShow').linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			$('#showWindow').window('close',true);
		}
	});
 });
/**
 * 添加评测学年窗口
 */
$(function() {
	$('#addWindow').window({
		closed : true,
		width : 550,
		height : 370,
		modal : true,
		title : "添加评测学年",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	$('#add_startYear').numberbox({
		width : "100%",
		prompt : "请输入起始年份",
		required :'true',
		type : "text",
		iconAlign : 'left'
	});
	//用户名
	$('#add_endYear').numberbox({
		width : "100%",
		prompt : "请输入终止年份",
		required :'true',
		type : "text",
		iconAlign : 'left'
	});
	$('#add_cxf').numberbox({
		width : "88%",
		prompt : "请输入操行分所取百分比",
		required :'true',
		type : "text"
	});
	$('#add_kjcxf').numberbox({
		width : "88%",
		prompt : "请输入科技创新分所取百分比",
		required :'true',
		type : "text",
		iconAlign : 'left'
	});
	$('#add_cjpjf').numberbox({
		width : "88%",
		prompt : "请输入学年成绩平均分所取百分比",
		required :'true',
		type : "text"
	});
	$('#add_wtf').numberbox({
		width : "88%",
		prompt : "请输入文体分所取百分比",
		required :'true',
		type : "text"
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
		url : '${pageContext.request.contextPath}/academicYear/insertAcademicYearAndEvaluationMethod',
		onSubmit : function() {
			var startYear = $('#add_startYear').val();
			var endYear = $('#add_endYear').val();
			var cjpjf =  $('#add_cjpjf').val();
			var cxf =  $('#add_cxf').val();
			var wtf =  $('#add_wtf').val();
			var kjcxf =  $('#add_kjcxf').val();
			if(startYear.length==0||endYear.length==0||cjpjf.length==0
				||cxf.length==0||wtf.length==0||kjcxf.length==0){
				$.messager.alert('错误', '存在必填项未填写', 'error');
				return false;
			}
			if(cjpjf.startsWith("-")||cxf.startsWith("-")||wtf.startsWith("-")||kjcxf.startsWith("-")){
				$.messager.alert('错误', '各项成绩所取百分比均不能为负数', 'error');
				return false;
			}
			if(startYear.startsWith("-")||endYear.startsWith("-")){
				$.messager.alert('错误', '起始年份和终止年份均不能为负数', 'error');
				return false;
			}
			if(parseInt(startYear)>=parseInt(endYear)){
				$.messager.alert('错误', '终止年份必须大于起始年份', 'error');
				return false;
			}
		},
		success : function(data) {
			var s = eval("(" + data + ")");
			if (s.status == 'true') {
				$.messager.alert('成功', s.message, 'info');
				$('#addWindow').window("close", true);
				$('#add_startYear').numberbox('clear');
				$('#add_endYear').numberbox('clear');
				$('#dg').datagrid('load',{
					"startYear": $("#search_startYear").val(),
					"endYear": $("#search_endYear").val(),
					"status":$("#search_state").val()
				});
			} else {
				$.messager.alert('错误', '添加评测学年失败', 'error');
			}
		},
		error : function(){
			$.messager.alert('错误', '网络无连接或服务器出现故障', 'error');
		}
		
	});
});
/**
 * 修改用户信息窗口
 */
 $(function() {
	$('#updateWindow').window({
		closed : true,
		width : 550,
		height : 370,
		modal : true,
		title : "修改评测学年信息",
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : true,
		draggable : true,
		resizable : false,
	});
	$('#update_startYear').numberbox({
		width : "100%",
		prompt : "请输入起始年份",
		required :'true',
		type : "text",
		iconAlign : 'left'
	});
	//用户名
	$('#update_endYear').numberbox({
		width : "100%",
		prompt : "请输入终止年份",
		required :'true',
		type : "text",
		iconAlign : 'left'
	});
	$('#update_cxf').numberbox({
		width : "88%",
		prompt : "请输入操行分所取百分比",
		required :'true',
		type : "text"
	});
	$('#update_kjcxf').numberbox({
		width : "88%",
		prompt : "请输入科技创新分所取百分比",
		required :'true',
		type : "text",
		iconAlign : 'left'
	});
	$('#update_cjpjf').numberbox({
		width : "88%",
		prompt : "请输入学年成绩平均分所取百分比",
		required :'true',
		type : "text"
	});
	$('#update_wtf').numberbox({
		width : "88%",
		prompt : "请输入文体分所取百分比",
		required :'true',
		type : "text"
	});

	$('#sureUpdate').linkbutton({
		iconCls : 'icon-ok',
		onClick : function() {
			var startYear = $('#updatestartYear').val();
			var endYear = $('#updateendYear').val();
			var cjpjf =  $('#updatecjpjf').val();
			var cxf =  $('#updatecxf').val();
			var wtf =  $('#updatewtf').val();
			var kjcxf =  $('#updatekjcxf').val();
			if(startYear.length==0||endYear.length==0||cjpjf.length==0
				||cxf.length==0||wtf.length==0||kjcxf.length==0){
				$.messager.alert('错误', '存在必填项未填写', 'error');
				return false;
			}
			if(cjpjf.startsWith("-")||cxf.startsWith("-")||wtf.startsWith("-")||kjcxf.startsWith("-")){
				$.messager.alert('错误', '各项成绩所取百分比均不能为负数', 'error');
				return false;
			}
			if(startYear.startsWith("-")||endYear.startsWith("-")){
				$.messager.alert('错误', '起始年份和终止年份均不能为负数', 'error');
				return false;
			}
			if(parseInt(startYear)>=parseInt(endYear)){
				$.messager.alert('错误', '终止年份必须大于起始年份', 'error');
				return false;
			}
			$('#updateWindow').window("close", true);
			ajaxLoading("请稍等，正在修改评测学年信息……");
			$.ajax({
				url : '${pageContext.request.contextPath}/evaluationMethod/update',
				type : 'post',
				data :{
					'startYear':$('#update_startYear').val(),
					'endYear':$('#update_endYear').val(),
					'cxf':$('#update_cxf').val(),
					'kjcxf':$('#update_kjcxf').val(),
					'cjpjf':$('#update_cjpjf').val(),
					'wtf':$('#update_wtf').val()
				},
				dataType : 'json',
				success : function(data){
					ajaxLoadEnd();
					if(data.status=="true"){
						$.messager.alert('提示','修改评测学年信息成功');
						$('#dg').datagrid('load',{
							"startYear": $("#search_startYear").val(),
							"endYear": $("#search_endYear").val(),
							"status":$("#search_state").val()
						});
					}else{
						$.messager.alert('提示','修改评测学年信息失败');
					}
				},
				error: function(){
					ajaxLoadEnd();
					$.messager.alert('提示','修改评测学年信息失败');
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
function updateAcademicYear(academicYearId,status){
	var academicYear = {
		'academicYearId':academicYearId,
		'status':status
	};
	ajaxLoading("请稍等，正在更改评测学年状态……");
	$.ajax({
		url : '${pageContext.request.contextPath}/academicYear/updateAcademicYear',
		type : 'post',
		data : JSON.stringify(academicYear),
		dataType : 'json',
		contentType : "application/json",
		success : function(data){
			ajaxLoadEnd();
			if(data.status=="true"){
				$.messager.alert('提示','修改评测学年状态成功','info');
				$('#dg').datagrid('load',{
					"startYear": $("#search_startYear").val(),
					"endYear": $("#search_endYear").val(),
					"status":$("#search_state").val()
				});
			}else if(data.status=="having"){
				$.messager.alert('提示',data.year+'学年正在评测中，无法开展其他学年的评测','error');
			}else{
				$.messager.alert('提示','修改评测学年状态失败','error');
			}
		},
		error: function(){
			ajaxLoadEnd();
			$.messager.alert('提示','修改评测学年状态失败');
		}
	});
	}
 $(function() {

 });
/**
 * 表格
 */
$(function() {
	$('#dg').datagrid({
		url : '${pageContext.request.contextPath}/academicYear/getAcademicYears',
		striped : true,
		rownumbers : true,
		fitColumns : true,
		maxHeight:"589px",
		nowrap : false,
		pagination : true,//分页
		pageSize : 10,//初始化数据行数
		pageList : [ 10, 20, 50 ,100],//每页数据行数,
		onClickRow: function (rowIndex, rowData) {
	      $('#dg').datagrid('unselectRow', rowIndex);
	    },
		checkOnSelect : true,
		queryParams:{
			"startYear": $("#search_startYear").val(),
			"endYear": $("#search_endYear").val(),
			"status":$("#search_state").val()
		},
		columns : [ [{
			field : 'academicYear',
			title : '评测学年',
			width : 100,
			formatter: function(value, rowData, rowIndex){
				return '<a href="javascript:showWindow(\''+rowData.academicYear+'\');">'+rowData.academicYear+'</a>';
	        },
		}, {
			field : 'statusName',
			title : '评测状态',
			width : 100,
			formatter: function(value, rowData, rowIndex){
				if(rowData.status=="done"){
					return '已完成';
				}else if(rowData.status=="doing"){
					return '进行中';
				}else if(rowData.status=="undo"){
					return '未开始'
				}
	        },
		}, {
			field : 'controller',
			title : '操作',
			width : 100,
			formatter: function(value, rowData, rowIndex){
				if(rowData.status=="done"){
					return '<a href="javascript:updateAcademicYear('+rowData.academicYearId+',\'doing\');">重新开始评测</a>';
				}else if(rowData.status=="doing"){
					return '<a href="javascript:updateAcademicYear('+rowData.academicYearId+',\'done\');">完成评测</a>';
				}else if(rowData.status=="undo"){
					return '<a href="javascript:updateAcademicYear('+rowData.academicYearId+',\'doing\');">开始评测</a>';
				}
	        },
		}] ],
		toolbar : 
	    [{
			text : '添加评测学年',
			iconCls : 'icon-add',
			handler : function() {
				$('#addWindow').window("open", true);
			}
		},'-',
		{
			text : '修改评测学年信息',
			iconCls : 'icon-edit',
			handler : function() {
				$('#updateWindow').window("open", true);
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