package org.dvdlist.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class AppStore {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private List<UserDb> users=new ArrayList<UserDb>();

	@Persistent
	private List<DVDDb> liste_dvd=new ArrayList<DVDDb>();
	
	public List<DVDDb> getListe_dvd() {
		return liste_dvd;
	}

	public void setListe_dvd(List<DVDDb> liste_dvd) {
		this.liste_dvd = liste_dvd;
	}

	public List<UserDb> getUsers() {
		return users;
	}

	public void setUsers(List<UserDb> users) {
		this.users = users;
	}

	public AppStore() {
		// TODO Auto-generated constructor stub
	}
}
