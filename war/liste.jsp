<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.dvdlist.jdo.*, java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ma liste</title>
</head>
<body>
Liste :<br/>
<%
DAO dao;
List<String> liste_dvd;
int i;
dao=new DAO();

liste_dvd=dao.donne_liste_dvd();
if(liste_dvd!=null&&!liste_dvd.isEmpty())
{
	i=1;
	for(String s:liste_dvd)
	{
		out.println(i+" : "+s+"<br/>");
		i++;
	}
}
%>

<a href="/upload.jsp">Upload</a><br/>
<a href="/init.jsp">Admin</a><br/>
<a href="/deconnect">Deconnection</a><br/>
</body>
</html>