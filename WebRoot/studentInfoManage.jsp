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
<title>学生信息管理</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function deleteStudent(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].stuId);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("studentDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}

	function searchStudent(){
		$('#dg').datagrid('load',{
			stuNo:$('#s_stuNo').val(),
			stuName:$('#s_stuName').val(),
			sex:$('#s_sex').combobox("getValue"),
			depaId:$('#s_depaId').combobox("getValue"),
			specId:$('#s_specId').combobox("getValue"),
			//bbirthday:$('#s_bbirthday').datebox("getValue"),
			//ebirthday:$('#s_ebirthday').datebox("getValue"),
			gradeId:$('#s_gradeId').combobox("getValue")
		});
	}
	
	//打开添加窗口
	function openStudentAddDialog(){
	    resetValue();
		$("#dlg").dialog("open").dialog("setTitle","添加学生信息");
		url="studentSave";
	}
	
	function saveStudent(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
			   
				if($('#sex').combobox("getValue")==""){
					$.messager.alert("系统提示","请选择性别");
					return false;
				}
				if($('#gradeId').combobox("getValue")==""){
					$.messager.alert("系统提示","请选择所属班级");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
				//将返回的json转换成对象  ，不然没法判断result.errorMsg跟result.successMsg
				var result = eval("(" + result + ")");
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
				if(result.successMsg){
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");//关闭对话框
					$("#dg").datagrid("reload");//刷新数据
				    }
					
				}
			}
		
		});
	}
	
	function resetValue(){
		$("#stuNo").val("");
		$("#stuName").val("");
		$("#sex").combobox("setValue","");
		$("#birthday").datebox("setValue","");
		$("#gradeId").combobox("setValue","");
		$("#email").val("");
		$("#stuDesc").val("");
		$("#inTime").val("");
		$("#depaId").combobox("setValue","");
		$("#specId").combobox("setValue","");
	}
	
	function closeStudentDialog(){
		$("#dlg").dialog("close");
		
		resetValue();
	}
	
	function openStudentModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑学生信息");
		$("#fm").form("load",row);
		url="studentSave?stuId="+row.stuId;
	}
</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="学生信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="studentList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="stuId" width="50" align="center" hidden="true">编号</th>
				<th field="stuNo" width="100" align="center">学号</th>
				<th field="stuName" width="100" align="center">姓名</th>
				<th field="sex" width="100" align="center">性别</th>
				<th field="birthday" width="100" align="center">出生日期</th>
				<th field="gradeId" width="100" align="center" hidden="true">班级ID</th>
				<th field="depaId" width="100" align="center" hidden="true">系别ID</th>
				<th field="specId" width="100" align="center" hidden="true">专业ID</th>
				<th field="depaName" width="150" align="center">系别名称</th>
				<th field="specName" width="100" align="center">专业名称</th>
				<th field="gradeName" width="100" align="center">班级名称</th>
				<th field="email" width="150" align="center">Email</th>
				<th field="inTime" width="60" align="center">入学时间</th>
				<th field="stuDesc" width="100" align="center" hidden="true">学生简述</th>
				<!-- 隐藏起来不需在客户端显示，但在学生修改的时候需要用到，所以还是要写上代码  例如:系别id，专业id，班级id 要根据这些id去找到对应的 系别，专业，班级-->
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openStudentAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openStudentModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteStudent()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>&nbsp;学号：&nbsp;<input type="text" name="s_stuNo" id="s_stuNo" size="10"/>
		&nbsp;姓名：&nbsp;<input type="text" name="s_stuName" id="s_stuName" size="10"/>
		&nbsp;性别：&nbsp;<select class="easyui-combobox" id="s_sex" name="s_sex" editable="false" panelHeight="auto">
		    <option value="">请选择...</option>
			<option value="男">男</option>
			<option value="女">女</option>
		</select>
		<!-- data-options="panelHeight:'auto',editable:false,valueField:'id',textField:'gradeName',url:'gradeComboList'"
		     里面的值为键值对，所有属性可以写在里面；valueField:'id',textField:'gradeName' 通过ajax根据班级id异步查找班级名称，valuefield是选择的值，textField是显示的值
		 -->
		&nbsp;所属系别：&nbsp;<input class="easyui-combobox" name="s_depaId" id="s_depaId" size="15" data-options="panelHeight:'auto',editable:false,valueField:'deId',textField:'depaName',url:'depaComboList',
					onSelect:function(rec){ $('#s_specId').combobox('clear');  var url='specComboList?depaId='+rec.deId;$('#s_specId').combobox('reload',url);}"/>
		&nbsp;所属专业：&nbsp;<input class="easyui-combobox" name="s_specId" id="s_specId" size="15"  data-options="panelHeight:'auto',editable:false,valueField:'spId',textField:'specName',url:'specComboList',
					onSelect:function(rec){$('#s_gradeId').combobox('clear');  var url='gradeComboList?specId='+rec.spId;$('#s_gradeId').combobox('reload',url);}"/>
		&nbsp;所属班级：&nbsp;<input class="easyui-combobox" id="s_gradeId" name="s_gradeId" size="15" data-options="panelHeight:'auto',editable:false,valueField:'grId',textField:'gradeName',url:'gradeComboList'"/> 
		<a href="javascript:searchStudent()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 630px;height: 400px;padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;"> 
				<tr>
					<td>学号：</td>
					<td><input type="text" name="stuNo" id="stuNo" class="easyui-validatebox" required="true"/></td>
					<td>姓名：</td>
					<td><input type="text" name="stuName" id="stuName" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><select class="easyui-combobox" id="sex" name="sex" editable="false" panelHeight="auto" style="width: 150px">
					    <option value="">请选择...</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select>
					</td>

					<td>出生日期：</td>
					<td><input class="easyui-datebox" name="birthday" id="birthday" required="true" editable="false" /></td>
				</tr>
				<tr>
					<td>所属系别：</td>
					<td><input class="easyui-combobox" id="depaId" name="depaId"  data-options="panelHeight:'auto',editable:false,valueField:'deId',textField:'depaName',url:'depaComboList',
					onSelect:function(rec){ $('#specId').combobox('clear');  var url='specComboList?depaId='+rec.deId;$('#specId').combobox('reload',url);}"/></td>					
					<td>所属专业：</td>
					<td><input class="easyui-combobox" id="specId" name="specId"  data-options="panelHeight:'auto',editable:false,valueField:'spId',textField:'specName',url:'specComboList',
					onSelect:function(rec){$('#gradeId').combobox('clear');  var url='gradeComboList?specId='+rec.spId;$('#gradeId').combobox('reload',url);}"/></td>
				</tr>
				<tr>
					<td>所属班级：</td>
					<td><input class="easyui-combobox" id="gradeId" name="gradeId"  data-options="panelHeight:'auto',editable:false,valueField:'grId',textField:'gradeName',url:'gradeComboList'"/></td>
					<td>Email：</td>
					<td><input type="text" name="email" id="email" class="easyui-validatebox" required="true" validType="email"/></td>
				</tr>
				<tr>
				    <td>入学时间（年）：</td>
				    <td colspan="3"><input type="text" name="inTime" id="inTime" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td valign="top">学生备注：</td>
					<td colspan="4"><textarea rows="7" cols="50" name="stuDesc" id="stuDesc"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveStudent()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeStudentDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
</body>
</html>