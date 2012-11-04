package org.dvdlist.web;

import java.io.Serializable;

public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 698630705466819721L;
	
	private boolean admin;
	
	public UserSession(boolean admin)
	{
		this.admin=admin;
	}

	public boolean isAdmin() {
		return admin;
	}
}
