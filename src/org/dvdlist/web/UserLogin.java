package org.dvdlist.web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.dvdlist.jdo.DAO;

@ManagedBean(name="userLogin")
@RequestScoped
public class UserLogin {

	private String login;
	private String password;
	private String msgError;
	
	public UserLogin()
	{
		
	}
	
	public String checkUser()
	{
		DAO dao;
		UserSession user;
		dao=new DAO();
		user=dao.user_valide(login, password);
		if(user!=null)
		{
			//UserSession user;
			//user=new UserSession(login, true);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( "user",user );
			//connecte=true;
			//return "welcome";
			return "liste";
		}
		else
		{
			//connecte=false;
			//msgError="Impossible de se connecter avec cet identifiant et ce mot de passe !";
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage message=new FacesMessage("Impossible de se connecter avec cet identifiant et ce mot de passe !");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(null, message);
			return "";
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
