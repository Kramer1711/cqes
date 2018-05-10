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
<title>查询系统</title>
</head>
<script type="text/javascript">
$(function() {
	//表格
	$('#tb').datagrid({
		url : '${pageContext.request.contextPath}/teacher/searchStudent',
		title : "查询系统" ,
		queryParams : {
			key : $("#searchBox").val(),
			collegeId : '',
			majorId : ''
		},
		fit : true,
		ctrlSelect:true,
        border: true,  
		rownumbers : true,
		fitColumns : true,
        pagination : true,
        remoteSort : false,
        multiSort : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 1, 2 , 3, 5, 10, 15, 20 ,30 ,40 ,50 ],
		sortName : "studentId",
		sortOrder : "asc",
		columns : [[
			{field : 'studentId', title : '学号', width : 100, align : 'center',sortable:true}, 
			{field : 'studentName',title : '姓名',width : 100,align : 'center',sortable:true}, 
			{field : 'studentMajor',title : '专业',width : 100,align : 'center',sortable:true}, 
			{field : 'studentGrade',title : '年级',width : 100,align : 'center',sortable:true}, 
			{field : 'studentCollege',title : '学院',width : 100,align : 'center',sortable:true}
		]],
    	toolbar: '#searchtool',
    	footer:'#ft'
    });
	//搜索输入框
	$("#searchBox").textbox({
		prompt : "学号或姓名"
	});
	//搜索按钮
	$("#searchBtn").linkbutton({
		iconCls : "icon-search",
		onClick : function(){
			//重载表格
			$('#tb').datagrid("load",{
				key : $("#searchBox").val(),
				collegeId : $('#collegeComboBox').combobox('getValue'),
				majorId : $('#majorComboBox').combobox('getValue')
			});
		}
	});
	//新增
	$("#addBtn").linkbutton({
		iconCls : "icon-add",
		plain : "true",
		text : "添加",
		onClick : function(){
		}
	});
	//编辑
	$("#editBtn").linkbutton({
		iconCls : "icon-edit",
		plain : "true",
		text : "编辑",
		onClick : function(){
		}
	});
	//删除
	$("#delBtn").linkbutton({
		iconCls : "icon-remove",
		plain : "true",
		text : "删除",
		onClick : function(){
		}
	});
	//学院
	$('#collegeComboBox').combobox({
		methed : 'GET',
		url : '${pageContext.request.contextPath}/college/getCollegeList',
		valueField : 'collegeId',
		textField : 'collegeName',
		panelHeight : 'auto',
		editable : false,
		onSelect : function(record){
			console.log(record.collegeId+" "+record.collegeName);
			$('#majorComboBox').combobox('clear');
			$('#majorComboBox').combobox('reload','${pageContext.request.contextPath}/major/getMajorListByCollegeId?collegeId='+record.collegeId);
		}
	});
	//专业 
	$('#majorComboBox').combobox({
		methed : 'GET',
		valueField : 'majorId',
		textField : 'majorName',
		panelHeight : 'auto',
		editable : false
	});
});
</script>
<body>
    <table id="tb" ></table>
    <div id="searchtool" style="height: 30px;">
    	学 院:
        <select id="collegeComboBox" style="width:150px">
        </select>
    	专 业:
        <select id="majorComboBox" style="width:150px">
        </select>
    	<div style="float: right;padding:2px 5px;">
    		<input id='searchBox' />
        	<a id="searchBtn" href="#" >Search</a>
    	</div>
    </div>
    <div id="ft" style="padding:2px 5px;">
        <a id="addBtn"></a>
        <a id="editBtn"></a>
        <a id="delBtn"></a>
        <p style="float: right;font-size: 5px;margin-top: 5px;margin-bottom: 0px;">按住Ctrl可多选</p>
    </div>
</body>
</html>