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
<!-- 引入easyui cellEdit -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/extends/datagrid-cellediting.js"></script>    
<title>代理审核管理</title>
</head>
<script type="text/javascript">
$(function() {
	
	var oldStatus ;
	var currentRow = {collegeId : -1};
	//表格
	$('#tb').datagrid({
		url : '${pageContext.request.contextPath}/auditPermission/searchAgentAudit',
		title : "审核系统",
	    saveUrl: '',
	    updateUrl: '',
	    destroyUrl: '',
		queryParams : {
			key : $("#searchBox").val(),
			collegeId : '',
			majorId : '',
			status : ''
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
		sortName : "auditPermissionId",
		sortOrder : "asc",
		checkbox:true,
		columns : [[
			{field : 'auditPermissionId', title : '编号', width : 80, align : 'center'},
			{field : 'studentId', title : '学号', width : 100, align : 'center'}, 
			{field : 'name',title : '姓名',width : 100,align : 'center'}, 
			{field : 'collegeName',title : '学院',width : 100,align : 'center'},
			{field : 'majorName',title : '代理审核专业',width : 100,align : 'center'},
			{field : 'status',title : '状态',width : 80,align : 'center',styler:cellStyler,
				formatter : function(value,row,index){
					if(value == 2){
						return "停用";
					}else if(value == 1){
						return "正常";
					}else{
						return "异常";
					}
				},
				editor:{
					type:'combobox',
					options:{
						valueField:"value",
						textField:"text",
						editable:false,
						data : [{value:1,text:"正常"},{value:2,text:"停用"}],
						panelHeight:'auto'
					}
				}
			},
			{field : 'del',width : 80,align : 'center',formatter:function(value,row,index){
				return "<a href='#' onclick='deleteAuditor("+row.auditPermissionId+")'>删除</a>";
			}},
		]],
    	toolbar: '#searchtool',
    	footer:'#ft',
    	onBeginEdit :function(index, row){
    		oldStatus = row.status;
    		currentRow = row;
    	},
    	onEndEdit :function(index, row, changes){
    		if(oldStatus != row.status){
	    		$.ajax({
	    			url:'${pageContext.request.contextPath}/auditPermission/changeStatus',
	    			method:'POST',
	    			data : {
	    				auditPermissionId : row.auditPermissionId,
	    				status : row.status
	    			},
	    			success : function(data){
	    				if(data){
							$.messager.show({
								title : '修改结果',
								msg : '修改成功',
								timeout : 4000,
								showType : 'slide'
							});
	    				}else{
							$.messager.show({
								title : '修改结果',
								msg : '修改失败,请刷新重试',
								timeout : 4000,
								showType : 'slide'
							});
	    				}
	    			}
	    		});
    		}
    	}
    }).datagrid('enableCellEditing').datagrid('gotoCell', {
		index: 0,
		field: 'auditPermissionId'
	});

	//单元格风格 
	function cellStyler(value,row,index){
		if (value == 1){
			return 'color:green;';
		}else if(value == 2){
			return 'color:red;';
		}else {
			return 'color:#FFCC00;';
		}
	}
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
				collegeId : $('#aamCollegeComboBox').combobox('getValue'),
				majorId : $('#aamMajorComboBox').combobox('getValue'),
				status : $('#statusComboBox').combobox('getValue'),
			});
		}
	});
	//设置 代理审核管理的combobox配置
	setCMComboboxOptions("aam");
	//状态
	$('#statusComboBox').combobox({
		label : '状 态:',
		methed : 'GET',
		valueField : 'id',
		textField : 'status',
		panelHeight : 'auto',
		editable : false,
		value : -1,
		data : [{
			id : -1,
			status : '全部'
		},{
			id : 1,
			status : '正常'
		},{
			id : 2,
			status : '停用'
		}]
	});
	
	//新增
	$("#addBtn").linkbutton({
		iconCls : "icon-add",
		plain : "true",
		text : "添加",
		onClick : function(){
			$('#stuWin').window("open");
			$('#mask').css('display','inline');
		}
	});
	//学生搜索面板
	$("#stuWin").window({
		title:"添加代理审核人",
		height : 500,
		width : 600,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closed : true ,
		closable : true,
		resizable : false,
		zIndex:8000,
		draggable :false,
		onBeforeClose : function(){
			var options = $("#addWin").window("options");
			if(options.closed){
				$('#mask').css('display','none');
			}
		}
	});
	$("#stuSearchBox").textbox({
		prompt:'姓名/学号',
		width:120
	});
	$("#stuSearchBtn").linkbutton({
		text:"Search",    
		iconCls: 'icon-search',
		onClick:function(){
			$('#stuTb').datagrid({
				url : '${pageContext.request.contextPath}/teacher/searchStudent',
				queryParams : {
					key : $("#stuSearchBox").val(),
					collegeId : $('#stuCollegeComboBox').combobox('getValue'),
					majorId : $('#stuMajorComboBox').combobox('getValue')
				},
			});
		}
	})
	$("#stuTb").datagrid({
		fit : true,
		ctrlSelect:true,
        border: true,  
		rownumbers : true,
		fitColumns : true,
        pagination : true,
		pageNumber : 1,
		pageSize : 15,
		pageList : [10, 15, 20 ,30 ,40 ,50 ],
		sortName : "studentId",
		sortOrder : "asc",
		columns : [[
			{field : 'studentId', title : '学号', width : 100, align : 'center'}, 
			{field : 'studentName',title : '姓名',width : 100,align : 'center'}, 
			{field : 'studentCollege',title : '学院',width : 100,align : 'center'},
			{field : 'studentMajor',title : '专业',width : 100,align : 'center'}
		]],
		toolbar :"#stuSearchtool",
		onDblClickRow:function(index, row){
			$("#addWin").window("open");
			console.log(row);
			var stuInfoDiv = $('#stuInfo label');
			stuInfoDiv.eq(0).html(row.studentName);
			stuInfoDiv.eq(1).html(row.studentId);
			stuInfoDiv.eq(2).html(row.studentCollege);
			stuInfoDiv.eq(3).html(row.studentMajor);
		}
	});
	//设置代理的combobox配置
	setCMComboboxOptions("stu");
	//添加代理面板
	$("#addWin").window({
		title:"添加代理审核人",
		height : 500,
		width : 400,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closed : true ,
		closable : true,
		draggable :false,		
		onOpen :function(){
			$('#stuWin').window("close");
		},
		onClose : function(){
			$('#stuWin').window("open");
			console.log(1111111);
			$('#permissionMajorComboBox').combobox('reset');
			$('#permissionCollegeCombobox').combobox('setValue',-1);
		}
	});
	//设置代理的combobox配置
	setCMComboboxOptions("permission");
	//添加代理审核人
	$("#addAuditPermissionBtn").linkbutton({
		
		text : '添 加 代 理 审 核 人',
		width : 150,
		onClick : function(){
		var cId = $('#permissionCollegeComboBox').combobox('getValue');
		var mId = $('#permissionMajorComboBox').combobox("getValue");
		if(cId == -1 || mId == -1 || mId == '' || cId ==''){
			$.messager.show({
				title : '警告',
				msg : '学院和专业不能为空或全部',
				timeout : 4000,
				showType : 'slide'
			});
			return;
		}
		$.messager.confirm('提示', '确定添加该代理审核人吗?', function(r){
			if (r){
				var stuInfoDiv = $('#stuInfo label');
				var status = 0;
				if($('#sb').switchbutton('options').checked){
					status = 1;
				}else{
					status = 2;
				}
				var agentParam = {
					studentId : stuInfoDiv.eq(1).html(),
					collegeId : $('#permissionCollegeComboBox').combobox('getValue'),
					majorId : $('#permissionMajorComboBox').combobox("getValue"),
					status : status
				};
				console.log(agentParam);
	    		$.ajax({
	    			url:'${pageContext.request.contextPath}/auditPermission/addAuditPermission',
	    			method:'POST',
	    			data : agentParam,
	    			success : function(data){
	    				if(data){
							$.messager.show({
								title : '添加结果',
								msg : '添加成功',
								timeout : 4000,
								showType : 'slide'
							});
							$("#addWin").window("close");
							$("#stuWin").window("close");
							$('#tb').datagrid("reload");
	    				}else{
							$.messager.show({
								title : '添加结果',
								msg : '添加失败,他已经是代理审核人了',
								timeout : 4000,
								showType : 'slide'
							});
	    				}
	    			}
	    		});
			}
		});
		}
	});
	
});
//设置学院专业的combobox初始值
function setCMComboboxOptions(id){
	//学院
	$('#'+id+'CollegeComboBox').combobox({
		label : '学 院:',
		methed : 'GET',
		url : '${pageContext.request.contextPath}/college/getCollegeList',
		valueField : 'collegeId',
		textField : 'collegeName',
		panelHeight : 'auto',
		editable : false,
		onSelect : function(record){
			$('#'+id+'MajorComboBox').combobox('clear');
			$('#'+id+'MajorComboBox').combobox('reload','${pageContext.request.contextPath}/major/getMajorListByCollegeId?collegeId='+record.collegeId);
		},
		onLoadSuccess : function(){
			var data = $(this).combobox("getData");
			var allSelection = {collegeId : -1,collegeName : '全部'};
			data.push(allSelection);
		}
	});
	//专业 
	$('#'+id+'MajorComboBox').combobox({
		label : '专 业:',
		methed : 'GET',
		valueField : 'majorId',
		textField : 'majorName',
		panelHeight : 'auto',
		editable : false
	});
}
//删除代理审核人
function deleteAuditor(auditPermissionId,studentId){
	$.ajax({
		url:"${pageContext.request.contextPath}/auditPermission/delete",
		data:{auditPermissionId:auditPermissionId},
		type:'post',
		success :function(data){
			if(data){
				$('#tb').datagrid('reload');
				$.messager.alert('信息','删除成功!');
			}else{
				$.messager.alert('信息','服务器繁忙，请稍后再试！');
			}
		}
	});
	
}
</script>
<body>
    <table id="tb" ></table>
    <div id="searchtool" style="height: 50px;">
        <select id="aamCollegeComboBox" style="width:150px">
        </select>
        <select id="aamMajorComboBox" style="width:150px">
        </select>
        <select id="statusComboBox" style="width:150px">
        </select>
    	<div style="float: right;padding:2px 5px;">
    		<input id='searchBox' />
        	<a id="searchBtn" href="#" >Search</a>
    	</div>
   	    <div>
	        <a id="addBtn"></a>
	    </div>	
    </div>

    <!-- 遮罩层 -->
    <div id="mask" style="
	    background-color: #646464;
	    width: 100%;
	    height: 100%;
	    position: absolute;
	    left: 50%;
	    top: 50%;
	    transform: translate(-50%,-50%);
	    z-index: 7500; 
	    display: none;
	    opacity:0.7">
    </div>
    <div id="stuWin" >
		<table id="stuTb" style="height: 100%;"></table>
	    <div id="stuSearchtool" style="height: 30px;">
	        <select id="stuCollegeComboBox" style="width:150px">
	        </select>
	        <select id="stuMajorComboBox" style="width:150px">
	        </select>
	    	<div style="float: right;padding:2px 5px;">
	    		<input id='stuSearchBox' />
	        	<a id="stuSearchBtn" href="#" >Search</a>
	    	</div>
	    </div>
	</div>
	<div id="addWin">
		<div style="position: absolute;left: 50%;top: 50%;transform: translate(-50%,-50%);font-family: sans-serif;">
			<div>
				<label style="font-size: 18px;">学生信息：</label>
				<div id="stuInfo" style="font-size: 15px;">
					<p>姓  名 ： <label ></label></p>
					<p>学  号 ： <label ></label></p>
					<p>学  院 ： <label ></label></p>
					<p>专  业 ：<label ></label></p>
				</div>
			</div>
			<div>
				<label  style="font-size: 18px;">代理信息：</label>
				<div style="font-size: 15px;">
					<div style="margin-top: 15px;">
						<select id="permissionCollegeComboBox" style="width:150px;">
				        </select>
					</div>
					<div style="margin-top: 15px;">
				        <select id="permissionMajorComboBox" style="width:150px;margin-top: 50px;">
				        </select>
					</div>
					<div style="margin-top: 15px;">
			        	<p>状  态 ：
						<input id="sb" class="easyui-switchbutton" 
							checked data-options="onText:'正常',offText:'停用'" style="width:100px;height:30px"><p>
					</div>
					<div style="font-size: 18px;position: absolute;left: 50%;transform: translate(-50%,0%);">
						<p><a id='addAuditPermissionBtn' style="padding: 5px 15px;"></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>