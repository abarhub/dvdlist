/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dvdlist.web;

import java.io.File;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dvdlist.jdo.DAO;
import org.dvdlist.jdo.DVDDb;


/**
 *
 * @author Alain
 */
public class ImportFichier {

	private static final Logger log =Logger.getLogger(ImportFichier.class.getName());
	
    private String nom_fichier;

    public ImportFichier()
    {
	//nom_fichier="C:\\Users\\Alain\\Documents\\liste_dvd\\Collection3.xml";
    }

    public String importation()
    {
	Document doc;
	File f;
	List<DVDDb> liste_dvd=null;
	boolean erreur=false;
	f=new File(nom_fichier);
	try {
	    doc = lecture_fichier(f);
	    liste_dvd=convertie(doc);
	    //System.out.println("Liste="+liste_dvd+"(nb="+liste_dvd.size()+")");
	    log.log(Level.INFO, "nb dvd="+liste_dvd.size());
	    if(liste_dvd!=null&&!liste_dvd.isEmpty())
	    {
		enregistre(liste_dvd);
	    }
	} catch (DocumentException ex) {
		log.log(Level.SEVERE, null, ex);
	    erreur=true;
	}
	if(erreur)
	{
	    return "rejected";
	}
	else
	{
	    return "accepted";
	}
    }

    public String importation(String s)
    {
	Document doc;
	//File f;
	List<DVDDb> liste_dvd=null;
	boolean erreur=false;
	int nb=0;
	//f=new File(nom_fichier);
	try {
        log.warning("Début lecture fichier ...");
	    doc = lecture_fichier(s);
	    log.warning("fin lecture fichier");
	    log.warning("debut convertie...");
	    liste_dvd=convertie(doc);
	    log.warning("fin convertie");
	    //System.out.println("Liste="+liste_dvd+"(nb="+liste_dvd.size()+")");
	    log.log(Level.INFO, "nb dvd="+liste_dvd.size());
	    if(liste_dvd!=null&&!liste_dvd.isEmpty())
	    {
	    	enregistre(liste_dvd);
	    	nb=liste_dvd.size();
	    }
	} catch (DocumentException ex) {
		log.log(Level.SEVERE, null, ex);
	    erreur=true;
	}
	if(erreur)
	{
	    return "rejected";
	}
	else
	{
	    return "accepted("+nb+")";
	}
    }

    private Document lecture_fichier(File f) throws DocumentException
    {
	SAXReader reader = new SAXReader();
        Document document = reader.read(f);
	return document;
    }
    
    private Document lecture_fichier(String s) throws DocumentException
    {
	SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(s));
	return document;
    }

    private List<DVDDb> convertie(Document doc) {
	List<DVDDb> liste;
	boolean dvd0,blue_ray,digital_copie;
	String id,upc,no_collection,genre;
	int annee_production,duree_filme;
	Date date_sortie,date_achat;
	liste=new ArrayList<DVDDb>();
	if(doc!=null)
	{
	    Element root = doc.getRootElement();
	    if(log.isLoggable(Level.INFO))
	    {
	    	log.log(Level.INFO, "racine="+root.getName());
	    }
	    if(root.getName().equals("Collection"))
	    {
		/*for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
		    Element element = (Element) i.next();
		    Element elt;
		    if(element.getName().equals("DVD"))
		    {
			elt=element.
		    }
		}*/
		//List list = doc.selectNodes( "//Collection/DVD/Title" );
		/*for ( Iterator i = list.iterator(); i.hasNext(); ) {
		    Element element = (Element) i.next();
		    Logger.getLogger(ImportFichier.class.getName()).log(Level.INFO, "elt="+element.getName());
		    Logger.getLogger(ImportFichier.class.getName()).log(Level.INFO, "elt(q)="+element.getQName());
		    if(element.getName().equals("Title"))
		    {
			DVD dvd;
			dvd=new DVD();
			dvd.setNom(element.getTextTrim());
			liste.add(dvd);
		    }
		}*/
		for ( Iterator<Element> i = root.elementIterator(); i.hasNext(); ) {
		    Element element = i.next();
		    Element elt;
		    if(element.getName().equals("DVD"))
		    {
		    	DVDDb dvd2=null;
		    	dvd0=false;
		    	blue_ray=false;
		    	digital_copie=false;
		    	id=null;
		    	upc=null;
		    	no_collection=null;
		    	genre=null;
		    	annee_production=0;
		    	duree_filme=0;
		    	date_sortie=null;
		    	date_achat=null;
				for ( Iterator<Element> i2 = element.elementIterator(); i2.hasNext(); ) {
				    elt = i2.next();
				    if(elt.getName().equals("Title"))
				    {
						DVDDb dvd;
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    dvd=new DVDDb();
						    dvd.setNom(s);
						    dvd2=dvd;
						    liste.add(dvd);
						    if(log.isLoggable(Level.INFO))
						    {
							    log.log(Level.INFO,
								    "ajout de '"+dvd.getNom()+"','"+elt.getName()+"','"+
								    elt.getText()+"','"+elt.asXML()+"','"+elt.getStringValue()+"'");
						    }
						}
				    }
				    else if(elt.getName().equals("MediaTypes"))
				    {
				    	for ( Iterator<Element> j = elt.elementIterator(); j.hasNext(); ) {
						    Element elt2 = j.next();
						    String s;
						    if(elt2.getName().equals("DVD"))
						    {
						    	s=elt2.getTextTrim();
						    	if(s!=null&&s.equalsIgnoreCase("true"))
						    	{
						    		dvd0=true;
						    	}
						    }
						    else if(elt2.getName().equals("BluRay"))
						    {
						    	s=elt2.getTextTrim();
						    	if(s!=null&&s.equalsIgnoreCase("true"))
						    	{
						    		blue_ray=true;
						    	}
						    }
				    	}
				    }
				    else if(elt.getName().equals("Features"))
				    {
				    	for ( Iterator<Element> j = elt.elementIterator(); j.hasNext(); ) {
						    Element elt2 = j.next();
						    String s;
						    if(elt2.getName().equals("FeatureDigitalCopy"))
						    {
						    	s=elt2.getTextTrim();
						    	if(s!=null&&s.equalsIgnoreCase("true"))
						    	{
						    		digital_copie=true;
						    	}
						    }
				    	}
				    }
				    else if(elt.getName().equals("ID"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    id=s;
						}
				    }
				    else if(elt.getName().equals("UPC"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    upc=s;
						}
				    }
				    else if(elt.getName().equals("CollectionNumber"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    no_collection=s;
						}
				    }
				    else if(elt.getName().equals("ProductionYear"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    annee_production=Integer.parseInt(s);
						}
				    }
				    else if(elt.getName().equals("RunningTime"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    duree_filme=Integer.parseInt(s);
						}
				    }
				    else if(elt.getName().equals("Released"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
							SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-MM-dd");
						    s=s.trim();
						    try {
								date_sortie=parserSDF.parse(s);
							} catch (ParseException e) {
								log.log(Level.SEVERE, "erreur="+e.getLocalizedMessage()+"("+elt+")",e);
							}
						}
				    }
				    /*else if(elt.getName().equals("UPC"))
				    {
						String s=elt.getTextTrim();
						if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
						{
						    s=s.trim();
						    //date_achat=s;
						}
				    }*/
				    else if(elt.getName().equals("PurchaseInfo"))
				    {
				    	for ( Iterator<Element> j = elt.elementIterator(); j.hasNext(); ) {
						    Element elt2 = j.next();
						    String s;
						    if(elt2.getName().equals("PurchaseDate"))
						    {
						    	s=elt2.getTextTrim();
						    	if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
								{
						    		SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-MM-dd");
								    s=s.trim();
								    try {
										date_achat=parserSDF.parse(s);
									} catch (ParseException e) {
										log.log(Level.SEVERE, "erreur="+e.getLocalizedMessage()+"("+elt+")",e);
									}
								}
						    }
				    	}
				    }
				    else if(elt.getName().equals("Genres"))
				    {
				    	String txt="";
				    	for ( Iterator<Element> j = elt.elementIterator(); j.hasNext(); ) {
						    Element elt2 = j.next();
						    String s;
						    if(elt2.getName().equals("Genre"))
						    {
						    	s=elt2.getTextTrim();
						    	if(s!=null&&!s.isEmpty()&&!s.trim().isEmpty())
								{
						    		s=s.trim();
						    		if(txt.length()>0)
						    			txt+=", ";
						    		txt+=s;
								}
						    }
				    	}
				    	if(txt.length()>0)
				    		genre=txt;
				    }
				}
				if(dvd2!=null)
				{
					dvd2.setDvd(Boolean.valueOf(dvd0));
					dvd2.setBlue_ray(Boolean.valueOf(blue_ray));
					dvd2.setDigital_copy(Boolean.valueOf(digital_copie));
					dvd2.setId_collection(id);
					dvd2.setUpc(upc);
					dvd2.setNo_collection(no_collection);
					dvd2.setAnnee_production(annee_production);
					dvd2.setDuree_minutes(duree_filme);
					dvd2.setDate_sortie(date_sortie);
					dvd2.setDate_achat(date_achat);
					dvd2.setGenre(genre);
				}
		    }
		}
	    }
	}
	return liste;
    }

    private void enregistre(List<DVDDb> liste_dvd) {
    	DAO dao;
    	dao=new DAO();
    	log.warning("Début enregistrement...");
    	dao.enregistre_dvd(liste_dvd);
    	log.warning("fin enregistrement");
	/*SessionFactory sessionFactory;
	Session session=null;
	boolean fin;

	if(liste_dvd!=null&&!liste_dvd.isEmpty())
	{
	    sessionFactory=GestionHibernate.getSession();

	    try{
	    session = sessionFactory.openSession();
		fin=false;
		try{
		session.beginTransaction();

		//if(true)
		{
			//Class c =DVD.class;
			//String hql = "delete from "+c.getSimpleName();
			String hql = "delete from DVD ";
			Query q = session.createQuery( hql );
			q.executeUpdate();
		}
		/*else if(false)
		{
			Class c =DVD.class;
			List<DVD> liste_dvd2;
			//session.delete("select dvd FROM "+c.getSimpleName()+" as dvd ");
			//liste_dvd2=session.
			Criteria crit = session.createCriteria(DVD.class);
			List result = crit.list();
			liste_dvd2=result;
			if(liste_dvd2!=null&&!liste_dvd2.isEmpty())
			{
				for(DVD tmp:liste_dvd2)
				{
					session.delete( tmp );
				}
				//session.getTransaction().commit();
			}
		}
		else
		{
			Class c =DVD.class;
			//session.createQuery("delete from "+c.getSimpleName()).executeUpdate();
		}* /

		for(DVD tmp:liste_dvd)
		{
		    session.save( tmp );
		}
		fin=true;
		}finally{
		    if(fin)
				session.getTransaction().commit();
		    else
				session.getTransaction().rollback();
		}
	    }finally{
		if(session!=null)
		    session.close();
	    }
	}*/
    }
}
