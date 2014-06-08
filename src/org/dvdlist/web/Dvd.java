package org.dvdlist.web;

import java.util.Date;

import javax.jdo.annotations.Persistent;

public class Dvd {

	private String titre;
	private boolean dvd;
	private boolean blue_ray;
	private boolean version_digitale;
	
    private String no_collection;
    private String id_collection;
    private String upc;
    private String genre;
    private int annee_production;
    private Date date_sortie;
    private Date date_achat;
    private int duree_minutes;
	
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

	public String getNo_collection() {
		return no_collection;
	}

	public void setNo_collection(String no_collection) {
		this.no_collection = no_collection;
	}

	public String getId_collection() {
		return id_collection;
	}

	public void setId_collection(String id_collection) {
		this.id_collection = id_collection;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getAnnee_production() {
		return annee_production;
	}

	public void setAnnee_production(int annee_production) {
		this.annee_production = annee_production;
	}

	public Date getDate_sortie() {
		return date_sortie;
	}

	public void setDate_sortie(Date date_sortie) {
		this.date_sortie = date_sortie;
	}

	public Date getDate_achat() {
		return date_achat;
	}

	public void setDate_achat(Date date_achat) {
		this.date_achat = date_achat;
	}

	public int getDuree_minutes() {
		return duree_minutes;
	}

	public void setDuree_minutes(int duree_minutes) {
		this.duree_minutes = duree_minutes;
	}
}
