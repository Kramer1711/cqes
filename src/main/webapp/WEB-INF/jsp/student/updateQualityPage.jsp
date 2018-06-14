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
<title>学生个人综合素质测评审核情况</title>
</head>
<script type="text/javascript">
$(function() {
	var rowData
	//表格
	$('#tb').datagrid({
		url : '${pageContext.request.contextPath}/student/searchAudit',
		fit : true,
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
        remoteSort : false,
        multiSort : true,
		sortName : "qualityItemId",
		sortOrder : "asc",
		columns : [[
			{field : 'qualityItemId', title : '编号', width : 50, align : 'center',sortable:true}, 
			{field : 'itemName',title : '项目',width : 150,align : 'center',sortable:true}, 
			{field : 'typeName',title : '类型' ,width : 90,align : 'center',sortable:true},
			{field : 'itemScore',title : '分数' ,width : 50,align : 'center',sortable:true,
				formatter : function(value,row,index){
					if(row.typeId == '1')
						return value*0.1;
					else
						return value;
				}},
			{field : 'evidence',title : '证明材料' ,width : 100,align : 'center', formatter:showImg},
			{field : 'evidenceUrl',title : '证明材料路径' ,width : 100 , hidden : true},
			{field : 'qualityTypeId',title : '类型id' ,width : 100 , hidden : true},
			{field : 'itemStatus',title : '审核情况',width : 100,align : 'center',sortable:true,styler:cellStyler},
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
			var values = $('#typeName').combobox('getData');
			console.log(values);
        },
        toolbar : "#tool",
        //双击修改
        onDblClickRow : function(index, row){
    		rowData = row;
			console.log(rowData)
			if(rowData != null && rowData.itemStatus!="通过"){
				$('#panel').window("center");
				$('#panel').window("open");
				$('#typeName').combobox('select',rowData.typeId);
				$('#itemName').textbox('setText',rowData.itemName);
				$('#itemScore').textbox('setText',rowData.itemScore);
				if(row.evidenceUrl != 'null')
					$('#evidence').attr('src','${pageContext.request.contextPath}/image/getImage?path='+rowData.evidenceUrl)
			}else{
				$.messager.alert('信息','该记录已通过审核，无法修改！');  
			}
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
	};
	//图片展示  
	function showImg(value, row, index){  
		console.log(row.evidenceUrl);
	    if(row.evidenceUrl != "null"){
	        return "<img name='aaa' class='easyui-tooltip' "+"style='height:50px;' border='1' src='${pageContext.request.contextPath}/image/getImage?path="+row.evidenceUrl+"'/>";  
	    }else {
	    	return "无";
	    }
	};
	//修改
	$('#updateBtn').linkbutton({
		iconCls: 'icon-search',
		plain:true,
		width : 80,
		onClick : function(){
			rowData = $('#tb').datagrid('getSelected');
			if(rowData != null && rowData.itemStatus!="通过"){
				$('#panel').window("center");
				$('#panel').window("open");
				$('#typeName').combobox('select',rowData.typeId);
				$('#itemName').textbox('setText',rowData.itemName);
				$('#itemScore').textbox('setText',rowData.itemScore);
				if(row.evidenceUrl != 'null')
					$('#evidence').attr('src','${pageContext.request.contextPath}/image/getImage?path='+rowData.evidenceUrl)
			}else{
				$.messager.alert('信息','该记录已通过审核，无法修改！');  
			}
		}
	});
	//删除
	$('#deleteBtn').linkbutton({
		iconCls: 'icon-remove',
		plain:true,
		onClick : function(){
			var deleteId = $('#tb').datagrid('getSelected').qualityItemId;
			if($('#tb').datagrid('getSelected').itemStatus == '通过'){
				$.messager.alert('信息','该记录已通过审核，无法删除！');  
			}else{
				$.messager.confirm('确认','您确认想要删除该记录吗？',function(r){    
				    if (r){    
						$.ajax({
							url : "${pageContext.request.contextPath }/student/deleteQualityItem",
							data : {
								deleteId : deleteId
							},
							method : 'post',
							success: function(data){
								console.log(data);
								if(data == "SUCCESS"){
									$('#tb').datagrid('reload');
								}else{
									alert('删除失败');		
								}
							},
							error : function(){
								alert('删除失败');
							}
						}); 
				    }    
				});  
			}
		}
	});
	//更新信息面板
	$('#panel').window({
		height : 500,
		width : 500,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closed : true ,
		closable : true,
		draggable : false,
		resizable : false 
	});
	//保存更新
	$('#saveBtn').linkbutton({
		text : '保         存',
		plain:true,
		iconCls : 'icon-save',
		onClick : function(){
			$('#ff').form({
			    queryParams : {
			    	qualityItemId : rowData.qualityItemId,
			    	typeId : $('#typeName').combobox('getValue'),
			    	itemName : $('#itemName').textbox('getText'),
			    	itemScore : $('#itemScore').textbox('getText')
			    }
			});
			$('#ff').submit();
		}
	});
	//关闭面板
	$('#closeBtn').linkbutton({
		text : '关闭',
		plain:true,
		iconCls : 'icon-cancel',
		onClick : function(){
			$('#panel').window('close');
		}
	});
	$('#typeName').combobox({
		url:'${pageContext.request.contextPath }/quality/getQualityType',
		valueField:'itemTypeId',
		textField:'typeName'
	});
	$('#ff').form({    
	    url:"${pageContext.request.contextPath }/student/updateQualityItem",    
	    onSubmit: function(param){
	    },    
	    success:function(data){    
	    	$('#tb').datagrid('reload');
	    	$('#panel').window('close');
	    }
	});
});

function fileSelect() {
    document.getElementById("fb").click(); 
};
function fileSelected() {
    // 文件选择后触发次函数
    console.log("change");
}
</script>
<body>
	<table id="tb"></table>
	<div id='tool'>
		<a id="updateBtn">修改</a>
		<a id="deleteBtn">删除</a>
	</div>
	<div id="panel" >
		<div style="    position: absolute;    left: 50%;    top: 50%;    transform: translate(-50%,-50%);">
		<div >
		<form id="ff" class='easyui-form'enctype="multipart/form-data" method="post">
		<p>
		<input id="typeName"  class="easyui-combobox" data-options="label:'类 型 ： ',width:190"/>
		</p>
		<p>
		<input id="itemName"  class="easyui-textbox" data-options="label:'项 目 ： ',width:250"/>
		</p>
		<p>
		<input id="itemScore"  class="easyui-textbox" data-options="label:'分 数 ： ',width:250"/>
		</p>
		<p>
		<label>证明材料： </label>
		</p>
		<p>
		<img id="evidence" onclick="fileSelect();"  style='width:200px;max-height:150px;' border='1'>
		</p>
		<p>
			<input type="file" name="fb" id="fb" onchange="fileSelected();" style="display:none;">
		</p>
		</form>
		</div>
		<div style="margin-top: 20px;">
			<a id='saveBtn' style="padding: 10px 0px;width: 100%;"></a>
			<!-- 
			 <a id='closeBtn' style="margin-left: 15px;padding: 5px 10px;"></a>
			 -->
		</div>
		</div>
	</div>
</body>
</html>