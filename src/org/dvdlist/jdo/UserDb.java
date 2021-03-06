package org.dvdlist.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable//(identityType=IdentityType.APPLICATION)
public class UserDb {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String login;
	
	@Persistent
	private String password;

	@Persistent
	private Boolean admin;
	
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public UserDb() {
		// TODO Auto-generated constructor stub
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

	public boolean egal(String login2, String password2) {
		if(login!=null&&password!=null&&login2!=null&&password2!=null)
		{
			if(login.equals(login2)&&password.equals(password2))
				return true;
		}
		return false;
	}

	public boolean isAdmin() {
		return admin!=null&&admin.booleanValue();
	}
	
	
}
