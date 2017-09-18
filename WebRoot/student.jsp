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
<title>学生页面</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/demo/demo.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js">       </script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<!-- 汉化插件 -->
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		// 数据
		var treeData=[{
			text:"基本操作",
			children:[{
				text:"修改密码",
				attributes:{
					url:"modifyPas.jsp"
				}
			}]
		}];
		
		// 实例化树菜单
		$("#tree").tree({
			data:treeData,
			lines:true,//虚线是否可见
			onClick:function(node){
				if(node.attributes){
					openTab(node.text,node.attributes.url);
				}
			}
		});
		
		// 新增Tab
		function openTab(text,url){
			if($("#tabs").tabs('exists',text)){
				$("#tabs").tabs('select',text);
			}else{
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#tabs").tabs('add',{
					title:text,
					closable:true,
					content:content
				});
			}
		}
	});
	
	function saveStudent(){
	var url="stuSave";
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
			var email = document.getElementById("email").value;
            if (email != "") {
            
               var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
               if (!reg.test(email)) {
                       $.messager.alert("系统提示","邮箱格式不正确，请重新输入！");
                        return false;
                    }
             }
			
			    var sex=document.getElementById("sex").value;//获取性别的值，判断是否为男或女
	
			    if(sex!="男"&&sex!="女"){
			    $.messager.alert("系统提示","性别只能填<font color=red>男</font>或<font color=red>女</font>！");
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
				    }
					
				}
			}
		
		});
	}
</script>
</head>
        <!-- readonly表示文本框是允许查看不允许修改，并且可以提交数据   disabled不可以提交，提交的数据为null -->

<body class="easyui-layout">

	<div region="north" style="height: 80px;background-color: #E0EDFF">
	    <div align="left" style="width: 80%;float: left"><img src="images/main.jpg"></div>
			<div style="padding-top: 50px;padding-right: 20px;">当前用户：&nbsp;${name.stuName }&nbsp;|&nbsp;<a href="exit" >退出</a></div>	
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="个人信息" >
			<form id="fm" method="post">
			 <div style="border=2px;border-color=red;">
				<table border="1" width="100%">
				<tr height="30">
				<td width="10%">学号：</td>
				<td width="32%"><input type="text" value="${name.stuNo}" name="stuNo" readonly/></td>
				<td width="10%">出生日期：</td>
				<td width="32%"><input class="easyui-datebox" name="birthday" id="birthday" value="${name.birthday}" required="true" editable="false" /></td>
				<td width="16%" rowspan="5" align="center"><a href="#">选择照片...</a></td>
				</tr>
				<tr height="30">
				<td width="10%">姓名：</td>
				<td width="15%"><input type="text" value="${name.stuName}" name="stuName" required="true"/></td>
				<td width="10%">性别：</td>
				<td width="15%"><input type="text" value="${name.sex}" id="sex" name="sex" required="true"/></td>
				
				</tr>
				<tr height="30">
				<td width="10%">专业：</td>
				<td width="15%"><input type="text" value="${name.specName}" disabled/></td>
				<td width="10%">邮箱：</td>
				<td width="15%"><input type="text" value="${name.email}" id="email" name="email"/></td>
				
				</tr>
				<tr height="30">
				<td width="10%">系别：</td>
				<td width="15%"><input type="text" value="${name.depaName}" disabled/></td>
				<td width="10%">班级：</td>
				<td width="15%"><input type="text" value="${name.gradeName}" disabled/></td>
				</tr>
				<tr>
				   <td>入学时间：</td>
				   <td width="15%" colspan="3"><input type="text" value="${name.inTime}" disabled/></td>
				</tr>
				<tr>
				   <td>个人简述：</td>
				   <td colspan="5" ><textarea cols="100%" rows="10" name="stuDesc" style="resize:none">${name.stuDesc}</textarea></td>
				</tr>
				<tr>
				   <td align="center" colspan="5"><a href="javascript:saveStudent()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a></td>
				</tr>
				</table>	
				</div>	
				</form>		
			</div>
		</div>
	</div>
	<div region="west" style="width: 150px;" title="导航菜单" split="true">
		<ul id="tree"></ul>
	</div>
	<div region="south" style="height: 25px;" align="center">版权所有<a href="#">www.StudentManage@WJ.com</a></div>
</body>
</html>