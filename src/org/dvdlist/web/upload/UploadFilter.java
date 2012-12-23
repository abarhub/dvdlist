package org.dvdlist.web.upload;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFilter implements Filter {
	
	private static final Logger log =Logger.getLogger(UploadFilter.class.getName());
	
   private int sizeThreshold = -1;
   private String repositoryPath;

   public void init(FilterConfig config) throws ServletException {
      //repositoryPath = config.getInitParameter(
         //"org.dvdlist.web.upload.UploadFilter.repositoryPath");
      try {
         String paramValue = config.getInitParameter(
            "org.dvdlist.web.upload.UploadFilter.sizeThreshold");
         if (paramValue != null) 
            sizeThreshold = Integer.parseInt(paramValue);
      }
      catch (NumberFormatException ex) {
         ServletException servletEx = new ServletException();
         servletEx.initCause(ex);
         throw servletEx;
      }
   }

   public void destroy() {
   }

   public void doFilter(ServletRequest request, 
      ServletResponse response, FilterChain chain) 
      throws IOException, ServletException {
	   log.log(Level.INFO,"debut filtrage upload");

      if (!(request instanceof HttpServletRequest)) {
    	  log.log(Level.INFO,"filtrage upload: pas http");
         chain.doFilter(request, response);
         return;
      }

      HttpServletRequest httpRequest = (HttpServletRequest) request;

      boolean isMultipartContent 
         = ServletFileUpload.isMultipartContent(httpRequest);
      if (!isMultipartContent) {
    	  log.log(Level.INFO,"filtrage upload: pas multipart");
         chain.doFilter(request, response);
         return;
      }

      log.log(Level.INFO,"filtrage upload: preparation parametres");
      /*DiskFileItemFactory factory = new DiskFileItemFactory();
      if (sizeThreshold >= 0)
         factory.setSizeThreshold(sizeThreshold);
      if (repositoryPath != null) 
         factory.setRepository(new File(repositoryPath));*/
      ServletFileUpload upload = new ServletFileUpload();//new ServletFileUpload(factory);
      
      log.log(Level.INFO,"filtrage upload: conv parametres");
      try {
          final Map<String, String[]> map = new HashMap<String, String[]>();
          FileItemIterator iterator = upload.getItemIterator(httpRequest);
	      while (iterator.hasNext()) {
	        FileItemStream item = iterator.next();
	        //InputStream stream = item.openStream();

	        if (item.isFormField()) {
	        	//String str = item.getString();
	          log.warning("Got a form field: " + item.getFieldName());
		        //String str = ((FileItem) item).getString();
	          InputStream in = item.openStream();
	          ByteArrayOutputStream out=new ByteArrayOutputStream();
	          byte buf[]=new byte[512];
	          int len;
	          while((len=in.read(buf))>=0)
	          {
	        	  out.write(buf,0,len);
	          }
	          String str=out.toString();//out.toString(request.getCharacterEncoding());
	          map.put(item.getFieldName(), new String[] { str });
	        } else {
	          log.warning("Got an uploaded file: " + item.getFieldName() +
	                      ", name = " + item.getName());
	          httpRequest.setAttribute(item.getFieldName(), item);
	        }
	      }
         /*@SuppressWarnings("unchecked") List<FileItem> items 
            = (List<FileItem>) upload.parseRequest(httpRequest);
         for (FileItem item : items) {
            String str = item.getString();
            if (item.isFormField())
               map.put(item.getFieldName(), new String[] { str });
            else
               httpRequest.setAttribute(item.getFieldName(), item);
         }*/
          
         log.log(Level.INFO,"filtrage upload: envoie des parametres");
         chain.doFilter(new 
            HttpServletRequestWrapper(httpRequest) {
               public Map<String, String[]> getParameterMap() {
                  return map;
               }                   
               // busywork follows ... should have been part of the wrapper
               public String[] getParameterValues(String name) {
                  Map<String, String[]> map = getParameterMap();
                  return (String[]) map.get(name);
               }
               public String getParameter(String name) {
                  String[] params = getParameterValues(name);
                  if (params == null) return null;
                  return params[0];
               }
               public Enumeration<String> getParameterNames() {
                  Map<String, String[]> map = getParameterMap();
                  return Collections.enumeration(map.keySet());
               }
            }, response);
         log.log(Level.INFO,"filtrage upload: fin");
      } catch (FileUploadException ex) {
         ServletException servletEx = new ServletException();
         servletEx.initCause(ex);
         throw servletEx;
      }  
   }   
}
