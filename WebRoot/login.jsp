
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息管理系统登录</title>
<script type="text/javascript">
    function reset(){
    document.getElementById("username").value="";
    document.getElementById("password").value="";
    }
</script>
</head>
<body>
  <form method="post" action="login">
   <table width=1223px; height=606px; background="images/beijing.jpg" border="0">
       <tr height="55%">
	   </tr>
	   <tr height="5">
		    <td width="70%"></td>
		    <td width="10%" align="right">用户名：</td>
		    <td width="20%"><input type="text" value="${userName}" name="userName" id="userName"/></td>   
	   </tr><!--request.setAttribute("username", userName); ${username}作用是获取服务器跳转设置的信息 -->
	   <tr height="5">
	       <td width="70%"></td>
	       <td width="10%" align="right">密&nbsp;&nbsp;&nbsp;码：</td>
	       <td width="20%"><input type="password" value="${password}" name="password" id="password"/></td>   	   
	   </tr>
	    <tr height="5">
	       <td width="70%"></td>
	       <td width="15%" align="right"><input type="radio" checked="checked" name="buttonbox" value="manager"/>管理员 
             </td>
	       <td width="15%" align="left"><input type="radio" name="buttonbox" value="student"/>学生</td>  
	   </tr>
	   <tr height="5">
	       <td width="70%"></td>
	       <td width="10%" align="right"><input type="submit" value="登录"></td>
	       <td width="20%" align="center"><input type="reset" value="重置" /></td>  
	   </tr>
	     <tr height="5">
	       <td width="70%"></td>
	       <td width="10%" ></td>
	       <td width="20%"><font color="red">${error }</font></td>   	   
	   </tr>
	   
	   <tr>
	   </tr>
	   
        
   </table>
  </form>

</body>
</html>