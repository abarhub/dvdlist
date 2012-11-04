<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.dvdlist.jdo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
DAO dao;
String login,password,action;
action=request.getParameter("action");
login=request.getParameter("login");
password=request.getParameter("password");
if(action!=null&&action.equals("enregistrer"))
{
	dao=new DAO();
	dao.init(login,password);
}
%>
<form method="post" Action="?">
<input type="hidden" name="action" value="enregistrer" />
<label for="login">Login :</label><br />
<input type="text" name="login" id="login" /><br />
<label for="password">Password :</label><br />
<input type="password" name="password" id="password" /><br />
<input type="submit" value="Envoyer" />
</form>
<a href="/liste.jsp">Liste des DVD</a><br/>
<a href="/deconnect">Deconnection</a><br/>
</body>
</html>