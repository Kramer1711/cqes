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
		url : '${pageContext.request.contextPath}/log/searchLog',
		title : "日志管理" ,
		queryParams : {
			key : $("#searchBox").val(),
			type : '',
			title : '',
			beginDate : '',
			timeout : ''
		},
		fit : true,
        border: true,  
		rownumbers : true,
		fitColumns : true,
        pagination : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [ 1, 2 , 3, 5, 10, 15, 20 ,30 ,40 ,50 ],
		sortName : "beginDate",
		sortOrder : "desc",
		columns : [[
			{field : 'logId', title : 'id', width : 100, align : 'center'}, 
			{field : 'operatorId',title : '操作者账号id', width : 100,align : 'center'}, 
			{field : 'realName',title : '操作者账号', width : 100,align : 'center'}, 
			{field : 'type',title : '日志类型', width : 100,align : 'center'}, 
			{field : 'title',title : '日志名称', width : 100,align : 'center'}, 
			{field : 'remoteAddr',title : '远程地址',width : 100,align : 'center'}, 
			{field : 'requestUri',title : '请求路径', width : 100,align : 'center'}, 
			{field : 'method',title : '请求方式', width : 100,align : 'center'}, 
			{field : 'param',title : '参数', width : 100,align : 'center'},
			{field : 'exception',title : '异常', width : 100,align : 'center'},
			{field : 'beginDate',title : '开始时间', width : 100,align : 'center',	formatter:datetimeFormat_1},
			{field : 'timeout',title : '结束时间', width : 100,align : 'center',formatter:datetimeFormat_1},
		]],
    	toolbar: '#searchtool',
        onHeaderContextMenu: function(e, field){
            e.preventDefault();
            $(this).datagrid('columnMenu').menu('show', {
                left:e.pageX,
                top:e.pageY
            });
        }
    });
    function datetimeFormat_1(longTypeDate){  
        var datetimeType = "";  
        var date = new Date();  
        date.setTime(longTypeDate);  
        datetimeType+= date.getFullYear();   //年  
        datetimeType+= "-" + a0(date.getMonth()+1); //月   
        datetimeType += "-" + a0(date.getDate());   //日  
        datetimeType+= "&nbsp;&nbsp;" + a0(date.getHours());   //时  
        datetimeType+= ":" + a0(date.getMinutes());      //分
        datetimeType+= ":" + a0(date.getSeconds());      //分
        return datetimeType;
    } 
    //返回 01-12 的月份值   
    function a0(n){  
        return n<10?"0"+n:n;  
    }  
	//搜索输入框
	$("#searchBox").textbox({
		prompt : "姓名/账号/路径/远程地址/参数/异常"
	});
	//搜索按钮
	$("#searchBtn").linkbutton({
		iconCls : "icon-search",
		onClick : function(){
			//重载表格
			$('#tb').datagrid("load",{
				key : $("#searchBox").val(),
				type : $('#typeComboBox').combobox('getValue'),
				title : $('#titleComboBox').combobox('getValue'),			
				beginDate : $('#sTimeComboBox').datetimebox('getValue'),
				timeout : $('#eTimeComboBox').datetimebox('getValue')
			});
		}
	});
	//日志名称
	$('#typeComboBox').combobox({
		methed : 'GET',
		url : '${pageContext.request.contextPath}/log/getType',
		valueField : 'type',
		textField : 'type',
		panelHeight : 'auto',
		editable : false,
	});
	//日志类型
	$('#titleComboBox').combobox({
		methed : 'GET',
		url : '${pageContext.request.contextPath}/log/getTitle',
		textField : 'title',
		valueField : 'title',
		panelHeight : 'auto',
		editable : false
	});
	$('#sTimeComboBox').datetimebox({    
	    currentText : "Today",
	    /*
	    formatter :	function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+m+'-'+d;
		}
	*/
	});  
	$('#eTimeComboBox').datetimebox({    
	    currentText : "Today",
	    /*
	    formatter :	function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+m+'-'+d;
		}
		*/
	});  
	var i=0;
	var realDate;
	$.fn.datebox.defaults.formatter = function(date){
		if(i==0){
			realDate=date;
		}else{
			date=realDate;
		}
		i=i+1;
		if(i==3){
			i=0;
		}
		console.log(date);
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+m+'-'+d;
	}
	//表格选择可见项
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
});

</script>
<body>
    <table id="tb" ></table>
    <div id="searchtool" style="height: 50px;">
    	日志类型:
        <select id="typeComboBox" style="width:150px">
        </select>
    	日志名称:
        <select id="titleComboBox" style="width:150px">
        </select>
    	操作开始时间:
        <select id="sTimeComboBox" style="width:150px">
        </select>
    	结束时间:
        <select id="eTimeComboBox" style="width:150px">
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