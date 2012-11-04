package org.dvdlist.jdo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.dvdlist.web.ImportFichier;

public class DAO {

	private static final Logger log =Logger.getLogger(ImportFichier.class.getName());
	
	public DAO() {
		// TODO Auto-generated constructor stub
	}

	public boolean user_valide(String login, String password) {
		if(login!=null&&password!=null)
		{
			{
				UserDb user;
				AppStore app;
				PersistenceManager pm=null;
				try{
					pm= PMF.get().getPersistenceManager();
					pm.getObjectIdClass(AppStore.class);
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
									return true;
								}
							}
						}
					}
				}finally{
					pm.close();
				}
			}
		}
		return false;
	}
	
	public void init(){
		UserDb user;
		AppStore app;
		PersistenceManager pm=null;
		try{
			log.warning("Init");
			pm= PMF.get().getPersistenceManager();
			pm.getObjectIdClass(AppStore.class);
			app=getApp(pm);
			if(app==null)
			{
				app=new AppStore();
			}
			user=new UserDb();
			user.setLogin("XXXXXXXXXX");
			user.setPassword("XXXXXXXXXX");
			app.getUsers().clear();
			app.getUsers().add(user);
			pm.makePersistent(app);
		}finally{
			pm.close();
		}
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
			pm.getObjectIdClass(AppStore.class);
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

	public List<String> donne_liste_dvd() {
		AppStore app;
		PersistenceManager pm=null;
		List<String> liste_dvd=null;
		try{
			pm= PMF.get().getPersistenceManager();
			pm.getObjectIdClass(AppStore.class);
			app=getApp(pm);
			if(app==null)
			{
				app=new AppStore();
			}			
			if(app.getListe_dvd()!=null&&!app.getListe_dvd().isEmpty())
			{
				liste_dvd=new ArrayList<String>();
				for(DVDDb dvd:app.getListe_dvd())
				{
					liste_dvd.add(dvd.getNom());
				}
			}
			
			pm.makePersistent(app);
		}finally{
			pm.close();
		}
		return liste_dvd;
	}
}
