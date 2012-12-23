package org.dvdlist.web.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemStream;
import org.dvdlist.web.ImportFichier;
import org.dvdlist.web.Upload;

@FacesRenderer(componentFamily="javax.faces.Input",
   rendererType="org.dvdlist.web.upload.Upload")
public class UploadRenderer extends Renderer {
	
	private static final Logger log =Logger.getLogger(UploadRenderer.class.getName());
	
   public void encodeBegin(FacesContext context, UIComponent component)  
      throws IOException {
	   log.log(Level.INFO,"generation upload rendu html");
      if (!component.isRendered()) return;
      ResponseWriter writer = context.getResponseWriter();

      String clientId = component.getClientId(context);
      
      writer.startElement("input", component);
      writer.writeAttribute("type", "file", "type");
      writer.writeAttribute("name", clientId, "clientId");
      writer.endElement("input");
      writer.flush();
   }

   public void decode(FacesContext context, UIComponent component) {
	   log.log(Level.INFO,"Traitement upload du fichier");
      ExternalContext external = context.getExternalContext(); 
      HttpServletRequest request = (HttpServletRequest) external.getRequest();
      String clientId = component.getClientId(context);
      FileItemStream item = (FileItemStream) request.getAttribute(clientId);

      Object newValue=null;
      /*ValueExpression valueExpr = component.getValueExpression("value");
      if (valueExpr != null) {
         Class<?> valueType = valueExpr.getType(context.getELContext());
         if (valueType == byte[].class) {
            //newValue = item.get();
         }
         else if (valueType == InputStream.class) {
            try {
               newValue = item.openStream();
            } catch (IOException ex) {
               throw new FacesException(ex);
            }
         }
         else {
            /*String encoding = request.getCharacterEncoding();
            if (encoding != null)
               try {
                  newValue = item.getString(encoding);
               } catch (UnsupportedEncodingException ex) {
                  newValue = item.getString(); 
               }
            else 
               newValue = item.getString(); * /
         }
         ((EditableValueHolder) component).setSubmittedValue(newValue);  
         ((EditableValueHolder) component).setValid(true);  
      }
      log.log(Level.INFO,"new value="+newValue);*/

      /*Object target = component.getAttributes().get("target");
         
      if (target != null) {
         File file;
         if (target instanceof File)
            file = (File) target;
         else {
            ServletContext servletContext 
               = (ServletContext) external.getContext();
            String realPath = servletContext.getRealPath(target.toString());
            file = new File(realPath); 
         }

         try { // ugh--write is declared with "throws Exception"
            item.write(file);
         } catch (Exception ex) { 
            throw new FacesException(ex);
         }
      }*/
      if(item!=null)
      {
    	  log.log(Level.INFO,"Enregistrement du fichier");
    	  try {
    		 //enregistre((InputStream) newValue);
    		  enregistre(item.openStream());
		} catch (IOException e) {
			log.log(Level.SEVERE, "Exception:"+e.getLocalizedMessage(), e);
		}
      }
      log.log(Level.INFO,"Fin traitement upload du fichier");
   }

	private void enregistre(InputStream stream) throws IOException {
		log.warning("Got an uploaded file: " //+ item.getFieldName() +
                /*", name = " + item.getName()*/);

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
    //res.getWriter().print("<html><body>");
    //res.getWriter().print("Salut<br/>"+s+"<br/>");
    //res.getWriter().print("<a href=\"/liste.jsp\">Liste DVD</a><br/>");
    //res.getWriter().print("<a href=\"/deconnect\">Deconnection</a><br/>");
    //res.getWriter().print("</body></html>");
	}   
}