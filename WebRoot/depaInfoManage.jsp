<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%	
    response.setHeader("Cache-Control","no-cache"); //强制缓存从服务器上获取新的页面
    response.setHeader("Cache-Control","no-store"); //在任何环境下缓存不保存任何页面
    response.setDateHeader("Expires", 0); 
    response.setHeader("Pragma","no-cache");
     // 权限验证
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("login.jsp");
		return;
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系别信息管理</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<!-- 汉化插件 -->
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function searchDepa(){
	 //dg为table的id，调用一个内置函数load，获取id为s_depaName的文本框的里面的值，并赋值给depaName，向后台发送
	 //$('#s_depaName').val()这是jQuery文本框的取值方式
	 //$('#s_sex').combobox("getValue")选择框的取值方式
     //$('#s_bbirthday').datebox("getValue")日期框的取值方式
     
     
     //$("selectId").combobox('setValue',value);给下拉框设置值
		$('#dg').datagrid('load',{
			depaName:$('#s_depaName').val()
		});
	}
	
	function deleteDepa(){
	     //内置函数datagrid('getSelections')，获取选中项的一个集合，集合里的内容为一整行上的内容，
	     //在Firefox浏览器的脚本里可以打断点调式
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
		    //easyUI提供的方法messager
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		//获取系别编号，放在一个数组里
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].deId);
		}
		var ids=strIds.join(",");
		//alert(ids);
		//alert(strIds);
		//confirm为确认框，返回true跟false
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
			// 这里面的代码为ajax代码
			//post请求，（地址，参数，返回的数据，json）  第四个参数json表明以json方式请求 
				$.post("depaDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						//返回成功后，调用datagrid("reload")刷新
						$("#dg").datagrid("reload");
					}else{
					    //代码没写错的情况下，一般不会删除失败
						$.messager.alert("系统提示","<font color=red>"+selectedRows[result.errorIndex].depaName+"</font>"+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	//弹出添加对话框
	function openDepaAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加系别信息");
		url="depaSave";//设置请求地址
	}
	
	function openDepaModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){//只能编辑一条数据
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];//获取第一条数据，（也只有一条）
		$("#dlg").dialog("open").dialog("setTitle","编辑系别信息");
		$("#fm").form("load",row);//获取一整行数据,设置在打开的编辑框里,根据name跟field里的名字对应放进数据
		url="depaSave?dId="+row.dId;//返回的是表格field的，不是对话框里的deId
	}
	//对话框关闭
	function closeDepaDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	function resetValue(){
		$("#depaName").val("");
		$("#deId").val("");
	}
	
	
	function saveDepa(){
		$("#fm").form("submit",{
		//url:url,onSubmit:（提交之前验证必输项是否有数据，没有则提示输入）,success:返回的是success
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			//返回的是success
			success:function(result){
			//将返回的json转换成对象  ，不然没法判断result.errorMsg跟result.successMsg
				var result = eval("(" + result + ")");
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
				if(result.successMsg){
					$.messager.alert("系统提示","保存成功！");
					resetValue();
					$("#dlg").dialog("close");//关闭对话框
					$("#dg").datagrid("reload");//刷新数据
				    }
					
				}
			}
		});
	}
	
	
	function checkNum(obj){
	//isNaN()判断是否是非数字值
	   if(!isNaN(obj.value)){
	     
	       
	   }else{
	     $.messager.alert("系统提示","系别编号只能为数字！");
	         return false;
	   }
	}
</script>
</head>
<body style="margin: 5px;">
<!-- fitColumns：设置每一行按百分比来布局，自动把每行的宽度填满表格的宽度 
     pagination：数据分页
     rownumbers：显示每行行号
     fit：把表格填充满
     url：请求地址   访问系别管理页面时请求的地址   初始化表格
     post请求后，会向后台发送当前页page ，每页行数rows
     
-->
     <!-- easyui-datagrid field的值与数据库中的属性名要一致   toolbar="#tb"工具条、菜单条 -->
	<table id="dg" title="系别信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="depaList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="dId" width="50" hidden="true">编号</th>
				<th field="deId" width="50" align="center">系别编号</th>
				<th field="depaName" width="100" align="center">系别名称</th>				
			</tr>
		</thead>
	</table>
	<!-- 将tb加载在table里面  通过toolbar -->
	<div id="tb">
		<div>
			<a href="javascript:openDepaAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openDepaModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteDepa()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>&nbsp;系别名称：&nbsp;<input type="text" name="s_depaName" id="s_depaName"/><a href="javascript:searchDepa()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
	</div>
	<!-- easyui-dialog为对话框，div里的buttons属性引用id为dlg-buttons的div里的两个按钮
	 -->
	                                                                                            
	<div id="dlg" class="easyui-dialog" style="width: 400px;height: 280px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
			    <tr>
					<td>系别编号：</td>
					<!-- easyui-validatebox表单验证，required="true"表示该输入项为必须输入 -->
					<td><input type="text" name="deId" id="deId" onblur="checkNum(this);" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>系别名称：</td>
					<td><input type="text" name="depaName" id="depaName" class="easyui-validatebox" required="true"/></td>
				</tr>
				
			</table>
		</form>
	</div>
	<!-- 修改窗口 -->
	
	
	<div id="dlg-buttons">
		<a href="javascript:saveDepa()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDepaDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<!-- 修改窗口控件 -->
	
</body>
</html>