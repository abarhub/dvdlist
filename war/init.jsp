<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.dvdlist.jdo.*, org.dvdlist.web.*, java.util.*"%>
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
List<UserSession> liste_users;
action=request.getParameter("action");
login=request.getParameter("login");
password=request.getParameter("password");
admin=request.getParameter("admin");
dao=new DAO();
if(action!=null&&action.equals("enregistrer"))
{
	dao.init(login,password,admin!=null&&admin.equals("on"));
}
else if(action!=null&&action.equals("ajouter"))
{
	dao.add_user(login,password,admin!=null&&admin.equals("on"));
}
else if(action!=null&&action.equals("suppr"))
{
	dao.suppr_user(login);
}
liste_users=dao.donne_users();
%>
<center>
Liste des utilisateurs:
<table border="1">
<thead><td>Login</td><td>Admin</td><td></td></thead>
<%
if(liste_users!=null)
{
	for(UserSession u:liste_users)
	{
		out.println("<tr><td>"+u.getLogin()+"</td><td>"+((u.isAdmin())?"oui":"non")+"</td><td><a href=\"?action=suppr&login="+u.getLogin()+"\">Suppr</a></td></tr>");
	}
}
%>
</table>
</center>
<hr>
<center>
Nouvel utilisateur :<br/>
<form method="post" Action="?" autocomplete="off">
<input type="hidden" name="action" value="ajouter" />
<label for="login">Login :</label><br />
<input type="text" name="login" id="login" /><br />
<label for="password">Password :</label><br />
<input type="password" name="password" id="password" /><br />
<label for="admin">Admin :</label><br />
<input type="checkbox" name="admin" id="admin" /><br />
<input type="submit" value="Ajouter" />
</form>
</center>
<center>
<a href="/liste.jsp">Liste des DVD</a><br/>
<a href="/deconnect">Deconnection</a><br/>
</center>
</body>
</html>