package org.dvdlist.web;

import java.io.Serializable;

public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 698630705466819721L;
	
	private String login;
	public String getLogin() {
		return login;
	}

	private boolean admin;
	
	public UserSession(String login,boolean admin)
	{
		this.login=login;
		this.admin=admin;
	}

	public boolean isAdmin() {
		return admin;
	}
}
