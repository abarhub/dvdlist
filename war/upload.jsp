<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload</title>
</head>
<body>
<center>
<form method="post" Action="upload" enctype="multipart/form-data">
<input type="hidden" name="MAX_FILE_SIZE" value="5242880" />
<label for="icone">Fichier à uploader (JPG, PNG ou GIF | max. 5Mo) :</label><br />
<input type="file" name="fichier" id="icone" /><br />
<input type="submit" name="submit" value="Envoyer" />
</form>
</center>
<center>
<a href="/liste.jsp">Liste DVD</a><br/>
<a href="/deconnect">Deconnection</a><br/>
</center>
</body>
</html>