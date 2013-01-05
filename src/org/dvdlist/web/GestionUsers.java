package org.dvdlist.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.dvdlist.jdo.DAO;

@ManagedBean(name="gestionUsers")
@ApplicationScoped
public class GestionUsers implements Serializable {

	private static final Logger log =Logger.getLogger(GestionUsers.class.getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1133865877726081037L;

	public GestionUsers(){
		
	}
	
	public List<UserComplet> getListUsers(){
		List<UserComplet> liste;
		DAO dao;
		log.info("Récupération des utilisateurs");
		dao=new DAO();
		liste=dao.donne_users2();
		log.info(((liste==null)?0:liste.size())+" utilisateurs récupérés");
				
		return liste;
	}
}
