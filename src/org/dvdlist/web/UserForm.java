package org.dvdlist.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.dvdlist.jdo.DAO;

@ManagedBean(name="userForm")
@RequestScoped
public class UserForm {

	private String login;
	private String password;
	private boolean admin;
	
	public UserForm() {
		// constructeur vide
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
