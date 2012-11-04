package org.dvdlist.web;

public class Dvd {

	private String titre;
	private boolean dvd;
	private boolean blue_ray;
	private boolean version_digitale;
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public boolean isDvd() {
		return dvd;
	}

	public void setDvd(boolean dvd) {
		this.dvd = dvd;
	}

	public boolean isBlue_ray() {
		return blue_ray;
	}

	public void setBlue_ray(boolean blue_ray) {
		this.blue_ray = blue_ray;
	}

	public boolean isVersion_digitale() {
		return version_digitale;
	}

	public void setVersion_digitale(boolean version_digitale) {
		this.version_digitale = version_digitale;
	}

	public Dvd(){
		
	}
}
