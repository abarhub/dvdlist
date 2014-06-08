package org.dvdlist.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dvdlist.jdo.DAO;

@ManagedBean(name="user")
@SessionScoped
public class UserSession implements Serializable {

	private static final Logger log =Logger.getLogger(UserSession.class.getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 698630705466819723L;
	
	private String login;

	private boolean admin;
	private final boolean connecte;
	
	public UserSession()
	{
		connecte=false;
	}
	
	public UserSession(String login,boolean admin)
	{
		this.login=login;
		this.admin=admin;
		connecte=true;
	}

	public List<Dvd> getListeDvds()
	{
		List<Dvd> liste_dvd;
		DAO dao;
		
		dao=new DAO();
		liste_dvd=dao.donne_liste_dvd();
		return liste_dvd;
	}
	
	public String deconnection(){
		HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(session!=null)
		{
			session.invalidate();
		}
		return "index";
	}
	
	public void getUrlUpload0()
	{
		String url = "upload.jsp";
	    FacesContext context = FacesContext.getCurrentInstance();
	    try {  
	       context.getExternalContext().dispatch(url);
	    }catch (Exception e) {  
	       e.printStackTrace();
	    }  
	    finally{  
	       context.responseComplete();  
	    }  
	}
	
	public String getUrlUpload()
	{
		return "upload2";
	}
	
	public int sortByTitre(Object o1,Object o2)
	{
		if(o1==null&&o2==null)
			return 0;
		if(o1==null)
			return -1;
		if(o2==null)
			return 1;
		//Dvd dvd1=(Dvd) o1,dvd2=(Dvd) o2;
		String nom1,nom2;
		//nom1=dvd1.getTitre();
		//nom2=dvd2.getTitre();
		nom1=(String) o1;
		nom2=(String) o2;
		if(nom1==null&&nom2==null)
			return 0;
		if(nom1==nom2)
			return 0;
		if(nom1==null)
			return -1;
		if(nom2==null)
			return 1;
		return nom1.compareToIgnoreCase(nom2);
		
	}
	
	public int sortByNoCollection(Object o1,Object o2)
	{
		if(o1==null&&o2==null)
			return 0;
		if(o1==null)
			return -1;
		if(o2==null)
			return 1;
		Dvd dvd1=(Dvd) o1,dvd2=(Dvd) o2;
		String nom1,nom2;
		nom1=dvd1.getNo_collection();
		nom2=dvd2.getNo_collection();
		if(nom1==null&&nom2==null)
			return 0;
		if(nom1==nom2)
			return 0;
		if(nom1==null)
			return -1;
		if(nom2==null)
			return 1;
		if(nom1.length()>0&&nom2.length()>0)
		{
			if(Character.isDigit(nom1.charAt(0))
				&&Character.isDigit(nom2.charAt(0)))
			{
				int n1,n2;
				try{
					n1=Integer.parseInt(nom1);
					n2=Integer.parseInt(nom2);
					//return new Integer(n1).compareTo(n2);
					return Integer.compare(n1, n2);
				}catch(NumberFormatException e){
					log.log(Level.INFO, "Erreur ignore : "+e.getLocalizedMessage(),e);
				}
			}
		}
		return nom1.compareToIgnoreCase(nom2);
		
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isConnecte() {
		return connecte;
	}
}
