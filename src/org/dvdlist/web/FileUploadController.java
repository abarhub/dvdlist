package org.dvdlist.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;  
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;  
  
import org.primefaces.model.UploadedFile; 


@ManagedBean(name="fileUploadController")
@RequestScoped
public class FileUploadController implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5134819655532559663L;

	private static final Logger log =Logger.getLogger(FileUploadController.class.getName());
		  
	    private UploadedFile file;  
	  
	    public UploadedFile getFile() {  
	        return file;
	    }  
	  
	    public void setFile(UploadedFile file) {  
	        this.file = file;  
	    }  
	  
	    public void upload() {
	    	log.info("debut upload...");
	        if(file != null) {
	        	log.info("fichier traité");
	            //FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");  
	            //FacesContext.getCurrentInstance().addMessage(null, msg);
	            String contenu=null;
	            try {
	            	log.info("Lecture du fichier ...");
					contenu=OutilsWeb.lecture(file.getInputstream(),"ISO-8859-1");
					log.info("Fin de lecture du fichier");
				} catch (IOException e) {
					log.info(e.getLocalizedMessage());
					e.printStackTrace();
					message_erreur("Erreur pendant l'importation: exception pendant la lecture du fichier");
				}
	            if(contenu!=null)
	            {
	            	log.info("taille fichier:"+contenu.length());
	            	if(contenu.length()>0&&contenu.startsWith("<?xml "))
			          {
				          ImportFichier imp;
				          String s;
				          imp=new ImportFichier();
				          log.warning("Début importation ...");
				          s=imp.importation(contenu);
				          log.warning("fin importation:"+s);
				          if(s!=null&&s.equals("rejected"))
				          {
				        	  log.warning("Erreur pendant l'importation");
				        	  message_erreur("Erreur pendant l'importation: fichier rejeté");
				          }
				          else
				          {
				        	  String s2=null;
				        	  int pos;
				        	  
				        	  pos=s.indexOf("(");
				        	  if(pos>=0&&pos+1<s.length())
				        	  {
				        		  s2=s.substring(pos+1);
				        		  if(s2.endsWith(")"))
				        		  {
				        			  s2=s2.substring(0, s2.length()-1);
				        		  }
				        	  }
				        	  message_ok(file.getFileName() + " importé avec succès"+
				        			  ((s2!=null&&s2.length()>0)?"("+s2+" dvd importés)":""));
				          }
			          }
	            	else
	            	{
	            		message_erreur("Erreur pendant l'importation : Le contenu du fichier est invalide");
	            	}
	            }
	            else
	            {
	            	log.info("taille fichier: vide");
	            	message_erreur("Erreur pendant l'importation : Le contenu du fichier est vide");
	            }
	        }
	        else
	        {
	        	message_erreur("Erreur pendant l'importation: Le fichier n'a pas été envoyé !");
	        }
	        log.info("fin upload");
	    }

	    private void message_ok(String msg0) {
	    	message(FacesMessage.SEVERITY_INFO,msg0);
	    }

	    private void message_erreur(String msg0) {
	    	message(FacesMessage.SEVERITY_ERROR,msg0);
	    }
	    
		private void message(Severity severityInfo, String msg0) {

            FacesMessage msg = new FacesMessage(severityInfo,"", msg0);  
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	
}
