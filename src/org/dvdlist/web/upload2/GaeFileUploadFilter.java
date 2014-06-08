package org.dvdlist.web.upload2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.primefaces.webapp.MultipartRequest;

/*
 * http://coder-in-training.blogspot.fr/2012/04/primefaces-30-fileupload-component-on.html
 */

public class GaeFileUploadFilter implements Filter  {
	  private final static Logger logger = Logger.getLogger(GaeFileUploadFilter.class.getName());
	  
	  public void init(FilterConfig filterConfig) throws ServletException {
	    if (logger.isLoggable(Level.FINE)) {
	      logger.fine("FileUploadFilter initiated successfully");
	    }
	  }
	 
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
	 
	    if (isMultipart) {
	      if (logger.isLoggable(Level.FINE)) {
	        logger.fine("Parsing file upload request");
	      }
	 
	      final FileItemFactory fileItemFactory = new InMemoryFileItemFactory();
	 
	      ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
	      MultipartRequest multipartRequest = new MultipartRequest(httpServletRequest, servletFileUpload);
	 
	      if (logger.isLoggable(Level.FINE)) {
	        logger.fine("File upload request parsed succesfully, continuing with filter chain with a wrapped multipart request");
	      }
	      try {
	        filterChain.doFilter(multipartRequest, response);
	      } catch (ServletException ex) {
	        logger.log(Level.SEVERE, "servlet exception occured", ex);
	        throw ex;
	      } catch (IOException ex) {
	        logger.log(Level.SEVERE, "io exception occured", ex);
	        throw ex;
	      }
	    } else {
	      filterChain.doFilter(request, response);
	    }
	  }
	 
	  public void destroy() {
	    if (logger.isLoggable(Level.FINE)) {
	      logger.fine("Destroying FileUploadFilter");
	    }
	  }
}
