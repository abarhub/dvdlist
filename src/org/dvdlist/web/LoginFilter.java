package org.dvdlist.web;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.*;

public class LoginFilter implements Filter {

	    protected ServletContext servletContext;

	    @Override
	    public void init(FilterConfig filterConfig) {
	        servletContext = filterConfig.getServletContext();
	    }
	    

	    @Override
	    public void doFilter(
	        ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	        HttpServletRequest req = (HttpServletRequest)request;
	        HttpServletResponse resp = (HttpServletResponse)response;
	    
	        if (!isAuth(req)) {
	            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);  
	            return; //break filter chain, requested JSP/servlet will not be executed
	        }
	    
	        //propagate to next element in the filter chain, ultimately JSP/ servlet gets executed
	        chain.doFilter(request, response);        
	    }

	    /**
	     * logic to accept or reject access to the page, check log in status
	     * @param req 
	     * @return true when authentication is deemed valid
	     */
	    boolean isAuth(HttpServletRequest req){
	    	if(req!=null&&req.getSession(false)!=null)
	    	{
	    		return true;
	    	}
	    	return false;
	    }

		@Override
		public void destroy() {
			// ne rien faire
		}

}
