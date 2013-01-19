package org.dvdlist.jdo;

import java.util.ArrayList;
import java.util.List;

public class Resultat {

	private final List<String> msgErrors;
	private boolean traitement;
	
	public Resultat() {
		msgErrors=new ArrayList<String>();
	}
	
	public boolean isError(){
		return !msgErrors.isEmpty();
	}

	public List<String> getMsgErrors() {
		return msgErrors;
	}

	public boolean isTraitement() {
		return traitement;
	}

	public void setTraitement(boolean traitement) {
		this.traitement = traitement;
	}

	public void add_error(String msg) {
		if(msg!=null&&msg.length()>0)
			msgErrors.add(msg);
	}

	public void ajoute(Resultat res) {
		if(res!=null)
		{
			msgErrors.addAll(res.msgErrors);
			if(res.isTraitement())
			{
				setTraitement(true);
			}
		}
	}
}
