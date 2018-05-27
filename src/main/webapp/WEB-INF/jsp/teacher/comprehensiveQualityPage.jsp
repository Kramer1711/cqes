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
	var queryParams = {
		key : '',
		collegeId : '',
		majorId : '',
		grade : ''
	};
	//表格
	$('#tb').datagrid({
		url : '${pageContext.request.contextPath}/teacher/searchComprehensiveQualityScore',
		title : "查询系统" ,
		queryParams : {
			key : $("#searchBox").val(),
			collegeId : '',
			majorId : '',
			grade : ''
		},
		fit : true,
        border: true,  
        singleSelect : true,
		rownumbers : true,
		fitColumns : true,
        pagination : true,		
        sortName : "studentId",
		sortOrder : "asc",
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 1, 2 , 3, 5, 10, 15, 20 ,30 ,40 ,50 ],
		columns : [[
			{field : 'studentId', title : '学号',  width : 100, align : 'center'}, 
			{field : 'studentName',title : '姓名',  width : 80,align : 'center'}, 
			{field : 'grade',title : '年级',  width : 60,align : 'center'}, 
			{field : 'majorId',title : '专业id',  width : 100,align : 'center',hidden:true}, 
			{field : 'majorName',title : '专业',  width : 120,align : 'center'}, 
			{field : 'collegeId',title : '学院id',  width : 100,align : 'center',hidden:true}, 
			{field : 'collegeName',title : '学院',  width : 100,align : 'center'},
			{field : 'gpa',title : '学年平均学分绩点', width : 100,align : 'center',formatter:scoreFormatter},
			{field : 'aveScore',title : '学年成绩平均分',  width : 95,align : 'center',formatter:scoreFormatter},
			{field : 'qualitySumScore',title : '素质操行总分', width : 90,align : 'center',formatter:scoreFormatter},
			{field : 'comprehensiveQualityScore', title : '综合素质测评总分',width : 100,align : 'center',formatter:scoreFormatter},
		]],
    	toolbar: '#searchtool'
    });
	//计算总分
	function getSumScore(value,row,index){
		var sumScore = row.jbcxf+row.cxADDf+row.cxSUBf+row.cjcxf+row.wtf;
		if(isNaN(sumScore )) return 0;
		else return sumScore;
	}
	//如果为空显示0
	function scoreFormatter(value,row,index){
		if(value == null)
			return 0;
		else
			return value.toFixed(2);
	}
	//搜索输入框
	$("#searchBox").textbox({
		prompt : "学号或姓名"
	});
	//搜索按钮
	$("#searchBtn").linkbutton({
		iconCls : "icon-search",
		onClick : function(){
			queryParams.key=$("#searchBox").val();
			queryParams.collegeId = $('#collegeComboBox').combobox('getValue');
			queryParams.majorId = $('#majorComboBox').combobox('getValue');
			queryParams.grade = $('#gradeComboBox').combobox('getText');
			//重载表格
			$('#tb').datagrid("load",queryParams);
		}
	});
	//导出
	$("#downloadBtn").linkbutton({
		iconCls : "icon-undo",
		plain : "true",
		text : "导出表格",
		onClick : function(){
			var data =  $('#tb').datagrid("getData");
			if(data.total == 0){
				$.messager.alert('警告','该搜索没有结果!'); 
			}else{
				window.location.href  = "exportExcel?key="+queryParams.key
					+"&collegeId="+queryParams.collegeId
					+"&majorId="+queryParams.majorId
					+"&grade="+queryParams.grade
					+"&type=comprehensiveQualityScore";
			}
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
			$('#majorComboBox').combobox('reload',
					'${pageContext.request.contextPath}/major/getMajorListByCollegeId?collegeId='
							+record.collegeId);
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
	//年级
	$('#gradeComboBox').combobox({
		url :'${pageContext.request.contextPath}/student/getGrades',
		methed : 'GET',
		valueField : 'id',
		textField : 'grade',
		panelHeight : 'auto',
		editable : false
	});
});

</script>
<body>
    <table id="tb" ></table>
    <div id="searchtool" style="height: 50px;">
    	学 院:
        <select id="collegeComboBox" style="width:150px">
        </select>
    	专 业:
        <select id="majorComboBox" style="width:150px">
        </select> 
                        年 级:
        <select id="gradeComboBox" style="width:110px">
        </select>
    	<div style="float: right;padding:2px 5px;">
    		<input id='searchBox' />
        	<a id="searchBtn" href="#" >Search</a>
    	</div>
    	<div>
    		<a id="downloadBtn"></a>
    	</div>
    </div>
</body>
</html>