package org.dvdlist.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	
	private static final Logger log =Logger.getLogger(Upload.class.getName());

		  public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws ServletException, IOException {
		    try {
		      ServletFileUpload upload = new ServletFileUpload();
		      res.setContentType("text/html; charset=ISO-8859-1");

		      FileItemIterator iterator = upload.getItemIterator(req);
		      while (iterator.hasNext()) {
		        FileItemStream item = iterator.next();
		        InputStream stream = item.openStream();

		        if (item.isFormField()) {
		          log.warning("Got a form field: " + item.getFieldName());
		        } else {
		          log.warning("Got an uploaded file: " + item.getFieldName() +
		                      ", name = " + item.getName());

		          // You now have the filename (item.getName() and the
		          // contents (which you can read from stream). Here we just
		          // print them back out to the servlet output stream, but you
		          // will probably want to do something more interesting (for
		          // example, wrap them in a Blob and commit them to the
		          // datastore).
		          int len;
		          byte[] buffer = new byte[8192];
		          ByteArrayOutputStream buf;
		          String s="",s2;
		          log.warning("Début lecture fichier ...");
		          buf=new ByteArrayOutputStream();
		          while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
		            buf.write(buffer, 0, len);
		          }
		          buffer=null;
		          s2=buf.toString("ISO-8859-1");//ISO-8859-1  UTF-8
		          buf=null;
		          log.warning("Fin lecture fichier");
		          if(s2.length()>0&&s2.startsWith("<?xml "))
		          {
			          ImportFichier imp;
			          imp=new ImportFichier();
			          log.warning("Début importation ...");
			          s=imp.importation(s2);
			          log.warning("fin importation");
		          }
		          s2=null;
		          res.getWriter().print("<html><body>");
		          res.getWriter().print("Salut<br/>"+s+"<br/>");
		          res.getWriter().print("<a href=\"/liste.jsf\">Liste DVD</a><br/>");
		          res.getWriter().print("<a href=\"/deconnect\">Deconnection</a><br/>");
		          res.getWriter().print("</body></html>");
		        }
		      }
		    } catch (Exception ex) {
		      throw new ServletException(ex);
		    }
		  }

}
