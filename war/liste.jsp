<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.dvdlist.jdo.*, java.util.*,org.dvdlist.web.* "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liste de dvd</title>
</head>
<body>
<center>
Liste :<br/>
</center>
<center>
<table border="1">
<thead><td></td><td>Titre</td></thead>
<%
DAO dao;
List<Dvd> liste_dvd;
int i;
boolean est_admin=false;
UserSession user;
user=OutilsWeb.getUser(request);
if(user!=null)
{
	est_admin=user.isAdmin();
}
dao=new DAO();

liste_dvd=dao.donne_liste_dvd();
if(liste_dvd!=null&&!liste_dvd.isEmpty())
{
	i=1;
	for(Dvd s:liste_dvd)
	{
		out.println("<tr><td>"+i+"</td><td>"+s.getTitre()+"</td></tr>");
		i++;
	}
}
%>
</table>
</center>
<center>
<%
if(est_admin)
{
%>
<a href="/upload.jsp">Upload</a><br/>
<a href="/init.jsp">Admin</a><br/>
<%
}
%>
<a href="/deconnect">Deconnection</a><br/>
</center>
</body>
</html>