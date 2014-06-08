package org.dvdlist.jdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.dvdlist.web.Dvd;
import org.dvdlist.web.UserComplet;
import org.dvdlist.web.UserSession;

public class DAO {

	private static final Logger log =Logger.getLogger(DAO.class.getName());
	
	public DAO() {
		// TODO Auto-generated constructor stub
	}

	public UserSession user_valide(String login, String password) {
		if(login!=null&&password!=null)
		{
			{
				AppStore app;
				PersistenceManager pm=null;
				try{
					pm= PMF.get().getPersistenceManager();
					//pm.getObjectIdClass(AppStore.class);
					app=getApp(pm);
					if(app!=null)
					{
						//app=new AppStore();
						if(app.getUsers()!=null&&!app.getUsers().isEmpty())
						{
							for(UserDb u:app.getUsers())
							{
								if(u.egal(login,password))
								{
									UserSession user2;
									user2=new UserSession(u.getLogin(),u.isAdmin());
									return user2;
								}
							}
						}
					}
				}finally{
					pm.close();
				}
			}
		}
		return null;
	}
	
	public void init(String login,String password,boolean admin){
		UserDb user;
		AppStore app;
		PersistenceManager pm=null;
		try{
			log.warning("Init");
			if(login!=null&&login.length()>0&&password!=null&&password.length()>0)
			{
				pm= PMF.get().getPersistenceManager();
				//pm.getObjectIdClass(AppStore.class);
				app=getApp(pm);
				if(app==null)
				{
					app=new AppStore();
				}
				user=new UserDb();
				user.setLogin(login);
				user.setPassword(password);
				if(admin)
					user.setAdmin(Boolean.TRUE);
				else
					user.setAdmin(Boolean.FALSE);
				app.getUsers().clear();
				app.getUsers().add(user);
				pm.makePersistent(app);
			}
		}finally{
			pm.close();
		}
	}

	public Resultat add_user(String login,String password,boolean admin){
		UserDb user;
		AppStore app;
		PersistenceManager pm=null;
		Resultat res=new Resultat();
		int len;
		try{
			log.warning("add user "+login);
			if(login!=null&&login.length()>0&&password!=null&&password.length()>0)
			{
				len=login.length();
				if(len<5||len>100)
				{
					res.add_error("Longueur du login incorrecte !");
				}
				len=password.length();
				if(len<8||len>100)
				{
					res.add_error("Longueur du mot de passe incorrecte !");
				}
				if(invalide(login))
				{
					log.warning("Caractères invalide pour ce login : '"+login+"'");
					res.add_error("Login incorrecte !");
				}
				if(!res.isError())
				{
					pm= PMF.get().getPersistenceManager();
					app=getApp(pm);
					if(app==null)
					{
						app=new AppStore();
					}
					if(app!=null)
					{
						if(app.getUsers()!=null&&!app.getUsers().isEmpty())
						{
							for(UserDb u:app.getUsers())
							{
								if(u!=null&&u.getLogin()!=null&&u.getLogin().equalsIgnoreCase(login))
								{
									log.warning("Login deja présent : "+login);
									res.add_error("Login incorrecte !");
								}
							}
						}
					}
					if(!res.isError())
					{
						user=new UserDb();
						user.setLogin(login);
						user.setPassword(password);
						if(admin)
							user.setAdmin(Boolean.TRUE);
						else
							user.setAdmin(Boolean.FALSE);
						app.getUsers().add(user);
						pm.makePersistent(app);
						res.setTraitement(true);
					}
				}
			}
			else
			{
				res.add_error("Le login et le mot de passe ne peuvent pas être null");
			}
		}finally{
			if(pm!=null)
				pm.close();
		}
		return res;
	}

	private boolean invalide(String login) {
		if(login!=null)
		{
			for(int i=0;i<login.length();i++)
			{
				char c=login.charAt(i);
				if(Character.isLetterOrDigit(c))
				{// caractères valides
					
				}
				else
				{
					switch(c)
					{
					case '_':case '-':case '=':case '@':
					case '²':case '&':case 'é':case '"':
					case '(':case 'è':case 'ç':
					case 'à':case ')':case '~':
					case '{':case '[':case '|':
					case ']':case '}':
						// caractères valides
						break;
					default:
						log.warning("caractère invalide:\'"+c+"\'("+((int)c)+")");
						return true;
					}
				}
			}
		}
		return false;
	}

	public Resultat suppr_user(String login){
		UserDb user;
		AppStore app;
		PersistenceManager pm=null;
		Resultat res=new Resultat();
		try{
			log.warning("Suppr user "+login);
			if(login!=null&&login.length()>0)
			{
				pm= PMF.get().getPersistenceManager();
				app=getApp(pm);
				if(app==null)
				{
					app=new AppStore();
				}
				/*user=new UserDb();
				user.setLogin(login);
				user.setPassword(password);
				if(admin)
					user.setAdmin(Boolean.TRUE);
				else
					user.setAdmin(Boolean.FALSE);
				//app.getUsers().clear();
				app.getUsers().add(user);*/
				for(int i=0;i<app.getUsers().size();i++)
				{
					user=app.getUsers().get(i);
					if(user!=null&&user.getLogin()!=null&&user.getLogin().equals(login))
					{
						log.warning("user '"+user.getLogin()+"' supprimé");
						app.getUsers().remove(i);
						i--;
						res.setTraitement(true);
					}
				}
				pm.makePersistent(app);
			}
			else
			{
				res.add_error("Le login ne peut pas être null");
			}
		}finally{
			if(pm!=null)
				pm.close();
		}
		return res;
	}

	private AppStore getApp(PersistenceManager pm) {
		Query q;
		q=pm.newQuery(AppStore.class);
		List<AppStore> list;
		list=(List<AppStore>) q.execute();
		if(list!=null&&!list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

	public void enregistre_dvd(List<DVDDb> liste_dvd) {
		AppStore app;
		PersistenceManager pm=null;
		try{
			log.warning("Début enregistre DVD");
			pm= PMF.get().getPersistenceManager();
			//pm.getObjectIdClass(AppStore.class);
			app=getApp(pm);
			if(app==null)
			{
				app=new AppStore();
			}
			if(app.getListe_dvd()==null)
			{
				app.setListe_dvd(new ArrayList<DVDDb>());
			}
			//app.getListe_dvd().clear();
			
			if(liste_dvd!=null&&!liste_dvd.isEmpty())
			{
				int i=0,len;
				DVDDb tmp;
				List<DVDDb> liste2;
				liste2=app.getListe_dvd();
				len=liste2.size();
				log.warning("Début parcourt de la liste des DVD...");
				for(DVDDb dvd:liste_dvd)
				{
					if(i<len)
					{
						tmp=liste2.get(i);
						if(tmp.getNom()!=null&&dvd.getNom()!=null&&tmp.getNom().equals(dvd.getNom()))
						{// on saute
							
						}
						else
						{
							log.warning("update '"+dvd.getNom()+"'");
							tmp.setNom(dvd.getNom());
						}
						if(!egal(tmp.getDvd(),dvd.getDvd()))
							tmp.setDvd(dvd.getDvd());
						if(!egal(tmp.getBlue_ray(),dvd.getBlue_ray()))
							tmp.setBlue_ray(dvd.getBlue_ray());
						if(!egal(tmp.getDigital_copy(),dvd.getDigital_copy()))
							tmp.setDigital_copy(dvd.getDigital_copy());
						if(!egal(tmp.getNo_collection(),dvd.getNo_collection()))
							tmp.setNo_collection(dvd.getNo_collection());

						if(!egal(tmp.getId_collection(),dvd.getId_collection()))
							tmp.setId_collection(dvd.getId_collection());
						if(!egal(tmp.getUpc(),dvd.getUpc()))
							tmp.setUpc(dvd.getUpc());
						if(!egal(tmp.getGenre(),dvd.getGenre()))
							tmp.setGenre(dvd.getGenre());
						if(!egal(tmp.getAnnee_production(),dvd.getAnnee_production()))
							tmp.setAnnee_production(dvd.getAnnee_production());
						if(!egal(tmp.getDate_sortie(),dvd.getDate_sortie()))
							tmp.setDate_sortie(dvd.getDate_sortie());
						if(!egal(tmp.getDate_achat(),dvd.getDate_achat()))
							tmp.setDate_achat(dvd.getDate_achat());
						if(!egal(tmp.getDuree_minutes(),dvd.getDuree_minutes()))
							tmp.setDuree_minutes(dvd.getDuree_minutes());
					}
					else
					{
						log.warning("ajout '"+dvd.getNom()+"'");
						liste2.add(dvd);
					}
					i++;
				}
				log.warning("Fin parcourt de la liste des DVD...");
			}
			else
			{
				log.warning("Début suppression de la liste des DVD...");
				app.getListe_dvd().clear();
				log.warning("Fin suppression de la liste des DVD");
			}
			
			pm.makePersistent(app);
		}finally{
			pm.close();
		}
		log.warning("Fin enregitre DVD");
	}

	private boolean egal(Boolean dvd, Boolean dvd2) {
		if(dvd==dvd2)
			return true;
		if(dvd==null&&dvd2!=null)
			return false;
		if(dvd!=null&&dvd2==null)
			return false;
		return dvd.booleanValue()==dvd2.booleanValue();
	}

	private boolean egal(String dvd, String dvd2) {
		if(dvd==dvd2)
			return true;
		if(dvd==null&&dvd2!=null)
			return false;
		if(dvd!=null&&dvd2==null)
			return false;
		return dvd.equals(dvd2);
	}

	private boolean egal(Integer dvd, Integer dvd2) {
		if(dvd==dvd2)
			return true;
		if(dvd==null&&dvd2!=null)
			return false;
		if(dvd!=null&&dvd2==null)
			return false;
		return dvd.equals(dvd2);
	}

	private boolean egal(Date dvd, Date dvd2) {
		if(dvd==dvd2)
			return true;
		if(dvd==null&&dvd2!=null)
			return false;
		if(dvd!=null&&dvd2==null)
			return false;
		return dvd.equals(dvd2);
	}

	public List<Dvd> donne_liste_dvd() {
		AppStore app;
		PersistenceManager pm=null;
		List<Dvd> liste_dvd=null;
		Dvd dvd0;
		try{
			pm= PMF.get().getPersistenceManager();
			//pm.getObjectIdClass(AppStore.class);
			app=getApp(pm);
			if(app==null)
			{
				app=new AppStore();
			}			
			if(app.getListe_dvd()!=null&&!app.getListe_dvd().isEmpty())
			{
				liste_dvd=new ArrayList<Dvd>();
				for(DVDDb dvd:app.getListe_dvd())
				{
					dvd0=new Dvd();
					dvd0.setTitre(dvd.getNom());
					dvd0.setDvd(dvd.getDvd()!=null&&dvd.getDvd().booleanValue());
					dvd0.setBlue_ray(dvd.getBlue_ray()!=null&&dvd.getBlue_ray().booleanValue());
					dvd0.setVersion_digitale(dvd.getDigital_copy()!=null&&dvd.getDigital_copy().booleanValue());
					dvd0.setId_collection(dvd.getId_collection());
					dvd0.setUpc(dvd.getUpc());
					dvd0.setNo_collection(dvd.getNo_collection());
					dvd0.setGenre(dvd.getGenre());
					dvd0.setDate_sortie(dvd.getDate_sortie());
					dvd0.setDate_achat(dvd.getDate_achat());
					dvd0.setAnnee_production(dvd.getAnnee_production());
					dvd0.setDuree_minutes(dvd.getDuree_minutes());
					liste_dvd.add(dvd0);
				}
			}
			
			//pm.makePersistent(app);
		}finally{
			pm.close();
		}
		return liste_dvd;
	}
	
	/*public List<UserSession> donne_users()
	{
		AppStore app;
		PersistenceManager pm=null;
		List<UserSession> liste_users=null;
		try{
			pm= PMF.get().getPersistenceManager();
			//pm.getObjectIdClass(AppStore.class);
			app=getApp(pm);
			if(app==null)
			{
				app=new AppStore();
			}			
			if(app.getUsers()!=null&&!app.getUsers().isEmpty())
			{
				liste_users=new ArrayList<UserSession>();
				for(UserDb u:app.getUsers())
				{
					UserSession tmp;
					tmp=new UserSession(u.getLogin(),u.isAdmin());
					liste_users.add(tmp);
				}
			}
			
			//pm.makePersistent(app);
		}finally{
			pm.close();
		}
		return liste_users;
	}*/

	
	public List<UserComplet> donne_users2()
	{
		AppStore app;
		PersistenceManager pm=null;
		List<UserComplet> liste_users=null;
		try{
			pm= PMF.get().getPersistenceManager();
			//pm.getObjectIdClass(AppStore.class);
			app=getApp(pm);
			if(app==null)
			{
				app=new AppStore();
			}			
			if(app.getUsers()!=null&&!app.getUsers().isEmpty())
			{
				liste_users=new ArrayList<UserComplet>();
				for(UserDb u:app.getUsers())
				{
					UserComplet tmp;
					tmp=new UserComplet();
					tmp.setLogin(u.getLogin());
					tmp.setAdmin(u.isAdmin());
					liste_users.add(tmp);
				}
			}
			
			//pm.makePersistent(app);
		}finally{
			pm.close();
		}
		return liste_users;
	}
}
