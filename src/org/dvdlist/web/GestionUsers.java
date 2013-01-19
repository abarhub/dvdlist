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
	
	public String suppr(UserComplet user){
		
		if(user!=null&&!vide(user.getLogin()))
		{
			DAO dao = new DAO();
			log.info("Suppression de l'utilisateur "+user.getLogin());
			dao.suppr_user(user.getLogin());
		}
		return null;
	}

	public String ajoute(UserForm user)
	{
		if(user!=null&&!vide(user.getLogin())&&!vide(user.getPassword()))
		{
			DAO dao = new DAO();
			log.info("Ajout de l'utilisateur "+user.getLogin());
			dao.add_user(user.getLogin(),user.getPassword(),user.isAdmin());
			user.setLogin(null);
			user.setPassword(null);
			user.setAdmin(false);
		}
		return "";
	}
	

	private boolean vide(String s) {
		return s==null||s.trim().length()==0;
	}

	
}
