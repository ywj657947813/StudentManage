<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>学生信息管理系统登录</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<!-- 汉化插件 -->
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<style>
body{TEXT-ALIGN: center;}
#center{ 
background-color:#C0D9D9;
MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
height:200px;
width:400px;
vertical-align:middle;
}
</style>
<script type="text/javascript">
    function reset(){
    $("#password").val("");
	$("#newPassword").val("");
	$("#newPassword1").val("");
    }
    function savePassword(){
    var url="pwmodify";
      $("#fm").form("submit",{
		//url:url,onSubmit:（提交之前验证必输项是否有数据，没有则提示输入）,success:返回的是success
			url:url,
			onSubmit:function(){
			  var newPassword=document.getElementById("newPassword").value;//新密码
			  var newPassword1=document.getElementById("newPassword1").value;//确认密码
			  
			  if(newPassword.length<6||newPassword.length>16)
			  {
			      $.messager.alert("系统提示","请输入长度为6到16的新密码");
			      return false;
			  }
			   if(newPassword!=newPassword1)
			  {
			      $.messager.alert("系统提示","您两次密码输入的密码不一致，请重新输入！");
			      return false;
			  }
				return $(this).form("validate");
			},
			//返回的是success
			success:function(result){
			
			//将返回的json转换成对象  ，不然没法判断result.errorMsg跟result.successMsg
				var result = eval("(" + result + ")");
				if(result.errorMsg){//errorMsg保存的是失败的信息 ，如果存在，说明修改失败，弹出失败信息
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
				if(result.successMsg){//errorMsg保存的是失败的信息 ，如果存在，说明修改失败，弹出失败信息
					$.messager.alert("系统提示",result.successMsg);
					reset();
				 }
					
				}
			}
		});
	}
	
/*function checkLength(obj){
   if(obj.value.length<6||obj.value.length>16){
      $.messager.alert("系统提示","请输入长度为6到16的新密码");
   }
}*/
</script>
</head>
<body >
  <div id="center">
  <form id="fm" method="post">
 
     <table border="0">
        <tr>
           <td align="right">旧密码：</td><td><input type="password" id="password" name="password1" required="true" /></td>
        </tr>
        <tr>
       <!-- onblur="checkLength(this);" -->
           <td align="right">新密码：</td><td><input type="password"  id="newPassword" name="newPassword" required="true" /></td>
        </tr>
        <tr>
           <td align="right">确认密码：</td>
           <td><input type="password" id="newPassword1" name="newPassword1" required="true" /></td>
        </tr>
        <tr>
           <td align="center"><input type="button" value="保存" onclick="savePassword()"/></td>
           <td align="center"><input type="button" value="重置" onclick="reset()"/></td>
        </tr>
     </table> 
      
  </form>
</div>
</body>
</html>