package org.dvdlist.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.dvdlist.jdo.DAO;

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String login,password;
		login=req.getParameter("login");
		password=req.getParameter("password");
		if(est_valide(login,password))
		{
			HttpSession session;
			session=req.getSession(true);
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
	
	private boolean est_valide(String login, String password) {
		if(login!=null&&password!=null)
		{
			DAO dao;
			dao=new DAO();
			if(dao.user_valide(login,password))
			{
				return true;
			}
		}
		return false;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req,resp);
	}
}
