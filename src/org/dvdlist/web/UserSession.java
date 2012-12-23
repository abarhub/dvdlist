package org.dvdlist.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dvdlist.jdo.DAO;

@ManagedBean(name="user")
@SessionScoped
public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 698630705466819721L;
	
	private String login;

	private boolean admin;
	//private boolean connecte;
	
	/*public UserSession()
	{
		
	}*/
	
	public UserSession(String login,boolean admin)
	{
		this.login=login;
		this.admin=admin;
	}

	public List<Dvd> getListeDvds()
	{
		List<Dvd> liste_dvd;
		DAO dao;
		
		dao=new DAO();
		liste_dvd=dao.donne_liste_dvd();
		return liste_dvd;
	}
	
	public String deconnection(){
		HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(session!=null)
		{
			session.invalidate();
		}
		return "index";
	}
	
	/*public String checkUser()
	{
		if(login!=null&&login.equals("abc2"))
		{
			connecte=true;
			return "welcome";
		}
		else
		{
			connecte=false;
			return "";
		}
	}*/
	
	public void getUrlUpload()
	{
		String url = "upload.jsp";
	    FacesContext context = FacesContext.getCurrentInstance();
	    try {  
	       context.getExternalContext().dispatch(url);
	    }catch (Exception e) {  
	       e.printStackTrace();
	    }  
	    finally{  
	       context.responseComplete();  
	    }  
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
