package org.dvdlist.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OutilsWeb {

	private static final Logger log =Logger.getLogger(OutilsWeb.class.getName());
	
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
	
	public static String lecture(InputStream in,String charset) throws IOException{
		int len;
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream buf;
        String s="",s2;
        log.warning("Début lecture fichier ...");
        buf=new ByteArrayOutputStream();
        while ((len = in.read(buffer, 0, buffer.length)) != -1) {
          buf.write(buffer, 0, len);
        }
        buffer=null;
        s2=buf.toString(charset);
        return s2;
	}
}
