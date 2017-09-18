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
<title>班级信息管理</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<!-- 汉化插件 -->
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function searchGrade(){
		$('#dg').datagrid('load',{
			gradeName:$('#s_gradeName').val(),
			specId:$('#s_specId').combobox("getValue"),
			depaId:$('#s_depaId').combobox("getValue")
		});
	}
	
	function deleteGrade(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].grId);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("gradeDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].gradeName+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	//打开添加窗口
	function openGradeAddDialog(){
	    resetValue();
		$("#dlg").dialog("open").dialog("setTitle","添加班级信息");
		url="gradeSave";
	}
	
	function openGradeModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑班级信息");
		$("#fm").form("load",row);
		url="gradeSave?gId="+row.gId;
	}
	
	function closeGradeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	
	function resetValue(){
		$("#gradeName").val("");
		$("#grId").val("");
		$("#spId").val("");
		$("#specId").combobox("setValue","");
		$("#depaId").combobox("setValue","");
	}
	
	
	function saveGrade(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
			if($('#depaId').combobox("getValue")==""){
					$.messager.alert("系统提示","请选择所属系别！");
					return false;
				}
			if($('#specId').combobox("getValue")==""){
					$.messager.alert("系统提示","请选择所属专业！");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
			     //将返回的json转换成对象  ，不然没法判断result.errorMsg跟result.successMsg，重要重要
			var result = eval("(" + result + ")");
		     
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else if(result.successMsg){
			    
					$.messager.alert("系统提示","保存成功"); 
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				 }
				
			
			}
		});
	}
	
	
	function checkNum(obj){
	//isNaN()判断是否是非数字值
	   if(!isNaN(obj.value)){
	     
	       
	   }else{
	     $.messager.alert("系统提示","班级编号只能为数字！");
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
     url：请求地址
     post请求后，会向后台发送当前页page ，每页行数rows
-->
	<table id="dg" title="班级信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="gradeList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="grId" width="50" align="center">编号</th>
				<th field="gradeName" width="100" align="center">班级名称</th>
				<th field="depaId" width="100" align="center" hidden="true">系别ID</th>
				<th field="specId" width="100" align="center">专业编号</th>
				<th field="depaName" align="center" width="250">所属系别</th>
				<th field="specName" align="center" width="250">所属专业</th>
			</tr>
		</thead>
	</table>
	<!-- 将tb加载在table里面  通过toolbar -->
	<div id="tb">
		<div>
			<a href="javascript:openGradeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openGradeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteGrade()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		 <div>&nbsp;班级名称：&nbsp;<input type="text" name="s_gradeName" id="s_gradeName"/>
              &nbsp;所属系别：&nbsp;<input class="easyui-combobox" id="s_depaId" name="s_depaId" size="20" data-options="panelHeight:'auto',editable:false,valueField:'deId',textField:'depaName',url:'depaComboList',
              onSelect:function(rec){ $('#s_specId').combobox('clear');  var url='specComboList?depaId='+rec.deId;$('#s_specId').combobox('reload',url);}"/>	 
 		      &nbsp;所属专业：&nbsp;<input class="easyui-combobox" id="s_specId" name="s_sepcId" size="20" data-options="panelHeight:'auto',editable:false,valueField:'spId',textField:'specName',url:'specComboList'"/> 
		   
		<a href="javascript:searchGrade()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px;height: 280px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
				    <td>所属系别：</td>
					<td><input class="easyui-combobox" id="depaId" name="depaId"  data-options="panelHeight:'auto',editable:false,valueField:'deId',textField:'depaName',url:'depaComboList',
					onSelect:function(rec){ $('#specId').combobox('clear');  var url='specComboList?depaId='+rec.deId;$('#specId').combobox('reload',url);}"/>
					</td>
				</tr>
				<tr>
				    <td>所属专业：</td>
					<td><input class="easyui-combobox" id="specId" name="specId"  data-options="panelHeight:'auto',editable:false,valueField:'spId',textField:'specName',url:'specComboList',
					onSelect:function(rec){$('#spId').val(rec.spId);}"/>
					</td>
				</tr>
				<tr>
				    <td>专业编号：</td>
				    <td><input type="text"id="spId" class="easyui-validatebox" disabled/></td>
				</tr>
				<tr>
				    <td colspan="2"> <font color="red">建议班级编号=专业编号+(班级号)&nbsp;如：0101</font></td>
				</tr>
			    <tr>
				    <td>班级编号：</td>
					<td><input type="text" class="easyui-validatebox" id="grId" name="grId" />
					</td>
				</tr>
				<tr>
					<td>班级名称：</td>
					<td><input type="text" name="gradeName" id="gradeName" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveGrade()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeGradeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	<!-- 修改窗口控件 -->
	
</body>
</html>