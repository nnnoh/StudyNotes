<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Information</title>
</head>
<body>
	<%
	request.setCharacterEncoding("UTF-8"); //防中文乱码
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String age = request.getParameter("age");
	String gender = request.getParameter("gender");
	String phone = request.getParameter("phone");
	String mail = request.getParameter("mail")+"@"+request.getParameter("domain");
	String[] hobbyArray = request.getParameterValues("hobby");
	// 可能返回null，特别是radio、checkbox
	String hobby="";
	if(hobbyArray!=null){
		int i;
		for(i=0;i<hobbyArray.length-1;i++){
			hobby+=(hobbyArray[i]+"、");
		}
		if(hobbyArray[i].equals("others")){
			String others = request.getParameter("others");
			if(others!=null && !others.equals(""))
				hobby+=others;
			else if(hobby.length() > 0)
				hobby.substring(0,hobby.length()-1);
		} else 
			hobby+=hobbyArray[i];
	}
%>
	<h1>注册信息</h1>
	<table border="1">
		<tr>
			<td>用户名</td>
			<td><%= username%></td>
		</tr>
		<tr>
			<td>密码</td>
			<td><%= password%></td>
		</tr>
		<tr>
			<td>年龄</td>
			<td><%= age%></td>
		</tr>
		<tr>
			<td>性别</td>
			<td><%= gender%></td>
		</tr>
		<tr>
			<td>电话</td>
			<td><%= phone%></td>
		</tr>
		<tr>
			<td>邮箱</td>
			<td><%= mail%></td>
		</tr>
		<tr>
			<td>兴趣爱好</td>
			<td><%= hobby%></td>
		</tr>
	</table>
</body>
</html>