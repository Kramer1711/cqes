<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引入主题样式 -->
<link href="${pageContext.request.contextPath }/static/themes/bootstrap/easyui.css" rel="stylesheet">
<!-- 引入图标的样式 -->
<link href="${pageContext.request.contextPath }/static/themes/icon.css"	rel="stylesheet">
<!-- 先引入jquery -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-1.7.2.min.js"></script>
<!-- 引入easyui.js -->
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
</head>
<script type="text/javascript">
	$(function() {
		$('#userMessages').datagrid({
			url : 'getUsers',//创建时访问的url
			pagination : true,//分页
			singleSelect: true,
			pageSize : 5,//一页一共有多少条数据
			pageList : [ 3, 4, 5, 6, 7, 8, 9, 10 ],
			rownumbers : true,//展示是当前表格的第几行
			title : '用户信息',
            onClickCell: onClickCell,
            onEndEdit: onEndEdit,
			columns : [ [//行中的数据设置（ck为checkbox，以下的数据为json中）
			{
				field : 'uid',
				title : '用户编号',
				width : 100,
				hidden : true
			}, {
				field:'rid',
				title:'角色编号',
				width:100,
				hidden:true
			},{
				field : 'uname',
				title : '姓名',
				width : 100
			}, {
				field : 'rname',
				title : '角色',
				width : 100,
               // formatter:function(value,row){
               //      return row.rid;
               // },
                editor:{
                	type:'combobox',
                   	options:{
                   		url:'getRoles',    
                   		valueField:'id',
             	   		textField:'text',
             	    	editable:false,
                        required:true
                    }
                }
				}
			]],
			toolbar : [{//配置工具栏
				iconCls : 'icon-add',//图标
				text : '确认', //文字
				handler : function() { //点击事件
				}
			}]
		});
		
        var editIndex = undefined;
        function endEditing(){
            if (editIndex == undefined){return true}
            if ($('#userMessages').datagrid('validateRow', editIndex)){
            	//如果选中
                $('#userMessages').datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
        function onClickCell(index, field){
            if (editIndex != index){
                if (endEditing()){//如果当前未选中行或者选中的为其他行
                    $('#userMessages').datagrid('selectRow', index).datagrid('beginEdit', index);
                    editIndex = index;
                } else {//
                    setTimeout(function(){
                        $('#userMessages').datagrid('selectRow', editIndex);
                    },0);
                }
            }
        }
        function onEndEdit(index, row){//结束编辑时
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'rname'
            });//index为旧行
            var uid=row.uid;
            var rid=($(ed.target).combobox('getValue'));//如果未改变，返回的不是id
            var idReg=/[0-9]+$/;
            var oldRid=row.rid;
            console.log(idReg.test(rid));
            console.log(rid);
            console.log(oldRid);
            if(idReg.test(rid)&&rid!=oldRid){
				$.ajax({
					url : 'updateUserRole',
					type : 'post',
					data : {
						'uid' : uid,
						'rid':rid,
					},
					dataType : 'json',
					success : function(data) {
						console.log(data);
						if(data.state=="200"){
							$("#userMessages").datagrid('reload');
						}else{
							$.messager.alert('警告','更新失败');
						}
						editIndex = undefined;
					}
				});
            }//end if
            else{
            	console.log($(ed.target).combobox('getText'));
				row.rname=$(ed.target).combobox('getText');
            }
        }
	});
</script>
<body>
	<table id="userMessages">
	</table>
</body>
</html>