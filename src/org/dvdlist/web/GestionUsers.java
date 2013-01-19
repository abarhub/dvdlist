package org.dvdlist.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.dvdlist.jdo.DAO;
import org.dvdlist.jdo.Resultat;

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

		String msg_info;
		boolean error;
		if(user!=null&&!vide(user.getLogin()))
		{
			DAO dao = new DAO();
			Resultat res;
			log.info("Suppression de l'utilisateur "+user.getLogin());
			res=dao.suppr_user(user.getLogin());
			if(res==null)
			{
				msg_info="res null";
				error=true;
			}
			else if(res.isError())
			{
				msg_info="Erreur : "+res.getMsgErrors().get(0);
				error=true;
			}
			else
			{
				error=false;
				if(res.isTraitement())
				{
					msg_info="Traitement réussi !";
				}
				else
				{
					msg_info="Aucun traitement réalisé !";
				}
			}
		}
		else
		{
			msg_info="Utilisateur invalide";
			error=true;
		}
		log.info("Suppression de l'utilisateur "+msg_info);
		FacesMessage messageX = new FacesMessage(
				(error)?FacesMessage.SEVERITY_ERROR:FacesMessage.SEVERITY_INFO,
				msg_info, msg_info);
		FacesContext.getCurrentInstance().addMessage(null, messageX);
		return null;
	}

	public String ajoute(UserForm user)
	{
		String msg_info;
		boolean error;
		if(user!=null&&!vide(user.getLogin())&&!vide(user.getPassword()))
		{
			DAO dao = new DAO();
			Resultat res;
			log.info("Ajout de l'utilisateur "+user.getLogin());
			res=dao.add_user(user.getLogin(),user.getPassword(),user.isAdmin());
			user.setLogin(null);
			user.setPassword(null);
			user.setAdmin(false);
			if(res==null)
			{
				msg_info="res null";
				error=true;
			}
			else if(res.isError())
			{
				msg_info="Erreur : "+res.getMsgErrors().get(0);
				error=true;
			}
			else
			{
				error=false;
				if(res.isTraitement())
				{
					msg_info="Traitement réussi !";
				}
				else
				{
					msg_info="Aucun traitement réalisé !";
				}
			}
		}
		else
		{
			msg_info="Utilisateur invalide";
			error=true;
		}
		log.info("Ajout de l'utilisateur "+msg_info);
		FacesMessage messageX = new FacesMessage(
				(error)?FacesMessage.SEVERITY_ERROR:FacesMessage.SEVERITY_INFO,
				msg_info, msg_info);
		FacesContext.getCurrentInstance().addMessage(null, messageX);
		return "";
	}
	

	private boolean vide(String s) {
		return s==null||s.trim().length()==0;
	}

	
}
