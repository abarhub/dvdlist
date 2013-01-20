package org.dvdlist.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	private static final Logger log =Logger.getLogger(LoginFilter.class.getName());
	
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
	    
	        log.entering("LoginFilter", "doFilter");
	        if (!isAuth(req)) {
	        	log.info("Page non autorisé:"+req.getPathTranslated());
	            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED); 
	            log.exiting("LoginFilter", "doFilter");
	            return; //break filter chain, requested JSP/servlet will not be executed
	        }
	    
	        //propagate to next element in the filter chain, ultimately JSP/ servlet gets executed
	        chain.doFilter(request, response);
	        log.exiting("LoginFilter", "doFilter");
	    }

	    /**
	     * logic to accept or reject access to the page, check log in status
	     * @param req 
	     * @return true when authentication is deemed valid
	     */
	    boolean isAuth(HttpServletRequest req){
	    	UserSession user;
	    	user=OutilsWeb.getUser(req);
	    	log.info("user:"+user+";connected="+((user!=null&&user.isConnecte())?"true":"false"));
	    	return user!=null&&user.isConnecte();
	    }

		@Override
		public void destroy() {
			// ne rien faire
		}

}
