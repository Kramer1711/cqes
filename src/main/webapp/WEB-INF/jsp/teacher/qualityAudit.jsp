<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>  
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

/**
 * 
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 	总审核情况
 * 
 * 
 */
$(function() {
	var auditPermissionInfo;
	$.ajax({
		url : '${pageContext.request.contextPath}/auditPermission/getPermission',
		method : 'GET',
		async : false,
		success : function(data){
			auditPermissionInfo = data;
			console.log("data:",data);
			console.log("auditPermissionInfo:",auditPermissionInfo);
		}
	});
	console.log(auditPermissionInfo);
	var itemId ;//编辑的项目id
	var ddv;//详细信息表格
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
			academicYear : '2017-2018',
			existSelf : false,
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
    	onBeforeLoad : function(param){
    		//是否是代理审核人
    		console.log('onBeforeLoad');
    		if(auditPermissionInfo.roleId == 4){
    			param.collegeId = auditPermissionInfo.auditPermission.collegeId;
    			param.majorId = auditPermissionInfo.auditPermission.majorId;
    			param.existSelf = true;
    			$('#collegeComboBox').css('display','none');
    			$('#majorComboBox').css('display','none');
    	 		return true;
    	 	}else{
    	 		collegeAndMajorCombobox();
    	 		return true;
    	 	}
    	},
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
            ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
			//记录编辑的行序号
            var editIndex = undefined;
            ddv.datagrid({
        		singleSelect : true,
                remoteSort : false,
                multiSort : true,
        		sortName : "qualityItemId",
        		sortOrder : "asc",
				rownumbers : true,
        		fitColumns : true,
        		height:150,
                border:false,
                cache:false,
                method:'POST',
                queryParams : {
                	studentId : row.studentId,
                	academicYear : row.academicYear
                },
                url:'${pageContext.request.contextPath}/teacher/searchAuditOfStudent',
                onLoad:function(){
                    $('#tb').datagrid('fixDetailRowHeight',index);
                },
        		columns : [[
        			{field : 'qualityItemId', title : '编号', width : 50, align : 'center',sortable:true}, 
        			{field : 'itemName',title : '项目',width : 150,align : 'center',sortable:true}, 
        			{field : 'typeName',title : '类型' ,width : 90,align : 'center',sortable:true},
        			{field : 'itemScore',title : '分数' ,width : 50,align : 'center',sortable:true},
        			{field : 'evidence',title : '证明材料' ,width : 100,align : 'center', formatter:showImg},
        			{field : 'evidenceUrl',title : '证明材料路径' ,width : 100 , hidden : true},
        			{field : 'qualityTypeId',title : '类型id' ,width : 100 , hidden : true},
        			{field : 'itemStatus',title : '审核情况',width : 100,align : 'center',sortable:true,styler:cellStyler},
        			{field : 'auditBtn',width : 100,align : 'center',formatter : showAuditBtn}
        		]],
                onLoadSuccess : function(data){
                	//实现所有img显示tooltip
                	$('img[name="aaa"]').tooltip({    
                		position: 'left',   
                		onShow: function(){
                			$(this).tooltip('tip').css({            
                				backgroundColor: '#666',
                				borderColor: '#666'        
        	        		});
                			//当前img所在的行数index
                			var index = $(this).parent().parent().parent().prevAll().length;
                			$(this).tooltip({
        		        		content: "<img style='width:300px;' border='1' src='${pageContext.request.contextPath}/image/getImage?path="+data.rows[index].evidenceUrl+"'/>",    
                			});
                		}
                	});
                	//审核button
                	$('a[name="auditBtn"]').linkbutton({
               			text : '立即审核',
                		onClick : function(){
                			var index = $(this).parent().parent().parent().prevAll().length;
                    		var rowData = data.rows[index];
                    		itemId = rowData.qualityItemId;
                			if(rowData != null){
                				$('#auditPanel').window("center");
                				$('#auditPanel').window("open");
                				$('#typeName').html(rowData.typeName);
                				$('#itemName').html(rowData.itemName);
                				$('#itemScore').html(rowData.itemScore);
                				if(rowData.evidenceUrl != "null"){
	                				$('#evidence').attr('src','${pageContext.request.contextPath}/image/getImage?path='+rowData.evidenceUrl)
                					$('#evidence').parent().prev().css('display','inline');
                					$('#evidence').css('display','inline');
                				}else{
                					$('#evidence').parent().prev().css('display','none');
                					$('#evidence').css('display','none');
                				}
                				$('#mask').css('display','inline');
                			}
                		}
                	});
                },
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
 	//图片展示  
 	function showImg(value, row, index){  
 	    if(row.evidenceUrl != "null"){
 	        return "<img name='aaa' class='easyui-tooltip' "+"style='height:50px;' border='1' src='${pageContext.request.contextPath}/image/getImage?path="+row.evidenceUrl+"'/>";  
 	    }else {
 	    	return "无";
 	    }
 	};
 	//审核按钮
 	function showAuditBtn(){
 		return "<a name='auditBtn' style='height:100%;width:100%;'></a>"
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
				collegeId : $('#collegeComboBox').combobox('getValue'),
				majorId : $('#majorComboBox').combobox('getValue'),
				status : $('#auditStatusComboBox').combobox('getText'),
				academicYear : $('#academicYearComboBox').combobox('getText')
			});
		}
	});
	function collegeAndMajorCombobox(){
		//学院
		$('#collegeComboBox').combobox({
			label : '学 院:',
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
			}
		});
		//专业 
		$('#majorComboBox').combobox({
			label : '专 业:',
			methed : 'GET',
			valueField : 'majorId',
			textField : 'majorName',
			panelHeight : 'auto',
			editable : false
		});
	};
	//审核状态
	$('#auditStatusComboBox').combobox({
		label : '审核状态:',
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
		label : '学 年:',
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
	//关闭大图
	$('#bigImage').click(function(){
		$('#detailImage').css('display','none');
	});
	//审核面板
	$('#auditPanel').window({
		title: '审核',
		height : 500,
		width : 600,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closed : true ,
		closable : true,
		draggable : false,
		resizable : false,
		onClose : function(){
			$('#mask').css('display','none');
		}
	});
	//提交审核
	$('#submitAuditBtn').linkbutton({
		text : '提 交 审 核',
		width : 150,
		onClick : function(){
			console.log(itemId);
			var auditChecked = $('#sb').switchbutton('options').checked;
			var itemStatus ;
			if(auditChecked){
				itemStatus = '通过';
			}else{
				itemStatus = '不予通过';
			}
			var json = {
				itemId : itemId,
				itemStatus : itemStatus
			}
			$.ajax({
				url : "${pageContext.request.contextPath}/audit/auditItem",
				method : 'post',
				data : json,
				success : function(data){
					console.log(data);
					if(data == "SUCCESS"){
						$.messager.show({
			                title:'审核结果',
			                msg:'审核提交成功',
			                timeout:4000,
			                showType:'slide'
			        	});
						$('#auditPanel').window('close');
						ddv.datagrid('reload');
					}
				}
			});
			
		}
	});
	$('.switchbutton-on').css('background','green');
	$('.switchbutton-off').css('background','red');
	$('.switchbutton-off').css('color','white');
});
function showDetailImage(obj){
	var src = obj.src;
	$('#bigImage').attr('src',src);
	$('#detailImage').css('display','inline');
	$('#mask').css('display','inline');
}
</script>
<body>
	<!-- 数据表格 -->
    <table id="tb" ></table>
    <!-- 搜索框 -->
    <div id="searchtool" style="height: 30px;">
        <select id="collegeComboBox" style="width:150px">
        </select>
        <select id="majorComboBox" style="width:150px">
        </select>
        <select id="auditStatusComboBox" style="width:150px">
        </select>
        <select id="academicYearComboBox" style="width:150px">
        </select>
    	<div style="float: right;padding:2px 5px;">
    		<input id='searchBox' />
        	<a id="searchBtn" href="#" >Search</a>
    	</div>
    </div>
    <!-- 用于显示大图 -->
    <div id = 'detailImage' style="    
	    text-align: center;
	    background-color: #fff;
	    border-radius: 20px;
	    height: 500px;
	    position: absolute;
	    left: 50%;
	    top: 50%;
	    transform: translate(-50%,-50%);
	    z-index: 9999; 
	    display: none;">
    	<img id="bigImage" style="
	    	height: 100%;position: absolute;
		    left: 50%;
		    top: 50%;
		    transform: translate(-50%,-50%);
		    z-index: 9999; ">
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
	    z-index: 8000; 
	    display: none;
	    opacity:0.7">
    </div>
   	<div id="auditPanel" >
		<div style="position: absolute;left: 50%;top: 50%;transform: translate(-50%,-50%);font-family: sans-serif;font-size: 15px;">
			<div>
			<p><label>项  目 ： </label></p>
			<p><label id='itemName' style="font-size: 18px;"></label></p>
			<p><label>类  型 ： </label><label id='typeName'></label></p>
			<p><label>分  数 ： </label><label id='itemScore'></label></p>
			<p><label>证明材料： </label></p>
			<p><img id="evidence" onclick='showDetailImage(this)' style='max-height:150px;' border='1'></p>
			<div style="margin-top: 20px;">
			    <input id="sb" class="easyui-switchbutton" checked data-options="onText:'通过',offText:'不予通过'" style="width:100px;height:30px">
			    <a id='submitAuditBtn' style="margin-left: 20px;"></a>
			</div>
			</div>
		</div>
	</div>
	

</body>
</html>