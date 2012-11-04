<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.dvdlist.jdo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administration</title>
</head>
<body>
<%
DAO dao;
String login,password,action,admin;
action=request.getParameter("action");
login=request.getParameter("login");
password=request.getParameter("password");
admin=request.getParameter("admin");
dao=new DAO();
if(action!=null&&action.equals("enregistrer"))
{
	dao.init(login,password,admin!=null&&admin.equals("on"));
}
%>
Utilisateur admin :<br/>
<form method="post" Action="?">
<input type="hidden" name="action" value="enregistrer" />
<label for="login">Login :</label><br />
<input type="text" name="login" id="login" /><br />
<label for="password">Password :</label><br />
<input type="password" name="password" id="password" /><br />
<label for="admin">Admin :</label><br />
<input type="checkbox" name="admin" id="admin" /><br />
<input type="submit" value="Envoyer" />
</form>
<a href="/liste.jsp">Liste des DVD</a><br/>
<a href="/deconnect">Deconnection</a><br/>
</body>
</html>