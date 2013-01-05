package org.dvdlist.web;

import java.io.Serializable;

public class UserComplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2609481197551064160L;
	
	private String login;
	private String password;
	private boolean admin;
	
	public UserComplet(){
		
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
