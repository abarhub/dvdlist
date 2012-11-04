package org.dvdlist.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OutilsWeb {

	public static final UserSession getUser(HttpServletRequest req)
	{
		if(req!=null&&req.getSession(false)!=null)
    	{
    		HttpSession session;
    		UserSession user;
    		session=req.getSession(false);
    		if(session!=null)
    		{
    			Object o;
    			o=session.getAttribute(Login.USER);
    			if(o!=null&&o instanceof UserSession)
    			{
    				user=(UserSession) o;
    				return user;
    			}
    		}
    	}
		return null;
	}
}
