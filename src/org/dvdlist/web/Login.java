package org.dvdlist.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.dvdlist.jdo.DAO;

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	
	public static final String USER = "user";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String login,password;
		UserSession user,tmp;
		login=req.getParameter("login");
		password=req.getParameter("password");
		tmp=est_valide(login,password);
		if(tmp!=null)
		{
			HttpSession session;
			session=req.getSession(true);
			user=new UserSession(tmp.getLogin(),tmp.isAdmin());
			session.setAttribute(USER, user);
			//resp.setContentType("text/plain");
			//resp.getWriter().println("Hello, world");
			try {
				req.getRequestDispatcher("/liste.jsp").forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			HttpSession session;
			session=req.getSession(false);
			if(session!=null)
			{
				session.invalidate();
			}
			try {
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}

	}
	
	private UserSession est_valide(String login, String password) {
		if(login!=null&&password!=null)
		{
			DAO dao;
			UserSession user;
			dao=new DAO();
			user=dao.user_valide(login,password);
			if(user!=null)
			{
				return user;
			}
		}
		return null;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req,resp);
	}
}
