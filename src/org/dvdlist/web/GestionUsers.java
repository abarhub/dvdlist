package org.dvdlist.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="gestionUsers")
@ApplicationScoped
public class GestionUsers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1133865877726081037L;

	public GestionUsers(){
		
	}
	
	public List<UserComplet> getListUsers(){
		List<UserComplet> liste;
		UserComplet tmp;
		
		liste=new ArrayList<UserComplet>();
		tmp=new UserComplet();
		tmp.setLogin("aaa");
		liste.add(tmp);
		tmp=new UserComplet();
		tmp.setLogin("bbb");
		liste.add(tmp);
		
		return liste;
	}
}
