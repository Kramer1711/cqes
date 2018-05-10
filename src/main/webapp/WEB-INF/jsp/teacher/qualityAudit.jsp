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
<!-- 引入datagrid detailview:表格详细信息视图 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/extends/datagrid-detailview.js"></script>
<title>审核查询系统</title>
</head>
<script type="text/javascript">
$(function() {
	//表格
	$('#tb').datagrid({
		view: detailview,//详细视图
		url : '${pageContext.request.contextPath}/teacher/searchAudit',
		title : "审核查询系统",
		queryParams : {
			key : $("#searchBox").val(),
			collegeId : '',
			majorId : '',
			status : '全部',
			academicYear : '2017-2018'
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
			{field : 'studentCollege',title : '学院',width : 100,align : 'center',sortable:true},
			{field : 'status',title : '审核总情况',width : 100,align : 'center',sortable:true,styler:cellStyler},
			{field : 'academicYear',title : '学年',width : 100,align : 'center',sortable:true},
			
		]],
    	toolbar: '#searchtool',
    	footer:'#ft',
        onHeaderContextMenu: function(e, field){
            e.preventDefault();
            $(this).datagrid('columnMenu').menu('show', {
                left:e.pageX,
                top:e.pageY
            });
        },
        detailFormatter:function(index,row){
        	if(row.allAudits=='尚未提交'){
        		//return false;
        	}else {
            	return '<div class="ddv"></div>';
        	}
        },
        onExpandRow: function(index,row){
            var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
            ddv.datagrid({
				rownumbers : true,
        		fitColumns : true,
        		height:150,
                border:false,
                cache:false,
                method:'GET',
                url:'${pageContext.request.contextPath}/teacher/searchAuditOfStudent?studentId='+row.studentId+'&academicYear='+row.academicYear,
                onLoad:function(){
                    $('#tb').datagrid('fixDetailRowHeight',index);
                },
        		columns : [[
        			{field : 'qualityItemId', title : '编号', width : 100, align : 'center',sortable:true}, 
        			{field : 'itemName',title : '项目',width : 100,align : 'center',sortable:true}, 
        			{field : 'itemStatus',title : '审核情况',width : 100,align : 'center',sortable:true,styler:cellStyler},
        		]],
            });
            $('#tb').datagrid('fixDetailRowHeight',index);
        }
    });
	//单元格风格 
	function cellStyler(value,row,index){
		if (value == "全部通过"||value == "通过"){
			return 'color:green;';
		}else if(value == "未通过"||value == "待审核"){
			return 'color:#FFCC00;';
		}else if(value == "尚未提交"||value == "不予通过"){
			return 'color:red;';
		}
	}
	//选择显示的列
	 function buildMenu(target){
         var state = $(target).data('datagrid');
         if (!state.columnMenu){
             state.columnMenu = $('<div></div>').appendTo('body');
             state.columnMenu.menu({
                 onClick: function(item){
                     if (item.iconCls == 'tree-checkbox1'){
                         $(target).datagrid('hideColumn', item.name);
                         $(this).menu('setIcon', {
                             target: item.target,
                             iconCls: 'tree-checkbox0'
                         });
                     } else {
                         $(target).datagrid('showColumn', item.name);
                         $(this).menu('setIcon', {
                             target: item.target,
                             iconCls: 'tree-checkbox1'
                         });
                     }
                 }
             })
             var fields = $(target).datagrid('getColumnFields',true).concat($(target).datagrid('getColumnFields',false));
             for(var i=0; i<fields.length; i++){
                 var field = fields[i];
                 var col = $(target).datagrid('getColumnOption', field);
                 state.columnMenu.menu('appendItem', {
                     text: col.title,
                     name: field,
                     iconCls: 'tree-checkbox1'
                 });
             }
         }
         return state.columnMenu;
     }
     $.extend($.fn.datagrid.methods, {
         columnMenu: function(jq){
             return buildMenu(jq[0]);
         }
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
				majorId : $('#majorComboBox').combobox('getValue'),
				status : $('#auditStatusComboBox').combobox('getText'),
				academicYear : $('#academicYearComboBox').combobox('getText')
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
		},
		onLoadSuccess : function(){
			var data = $(this).combobox("getData");
			var allSelection = {collegeId : -1,collegeName : '全部'};
			data.push(allSelection);
			console.log(data);
			//$(this).combobox("loadData",data);
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
	//审核状态
	$('#auditStatusComboBox').combobox({
		valueField : 'auditStatusId',
		textField : 'auditStatus',
		panelHeight : 'auto',
		editable : false,
		data : [{
			auditStatusId : 0,
			auditStatus : '全部'
		},{
			auditStatusId : 1,
			auditStatus	: '全部通过'
		},{
			auditStatusId : 2,
			auditStatus	: '尚未提交'
		},{
			auditStatusId : 3,
			auditStatus	: '未通过'
		}],
	});
	//学年
	$('#academicYearComboBox').combobox({
		valueField : 'auditStatusId',
		textField : 'auditStatus',
		panelHeight : 'auto',
		editable : false,
		data : [{
			auditStatusId : 0,
			auditStatus : '2017-2018'
		},{
			auditStatusId : 1,
			auditStatus	: '2016-2017'
		},{
			auditStatusId : 2,
			auditStatus	: '2015-2016'
		},{
			auditStatusId : 3,
			auditStatus	: '2014-2015'
		}],
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
                        审核状态:
        <select id="auditStatusComboBox" style="width:150px">
        </select>
                        学年:
        <select id="academicYearComboBox" style="width:150px">
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