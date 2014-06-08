/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dvdlist.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 *
 * @author Alain
 */
@PersistenceCapable
public class DVDDb {

	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
    private String nom;
	
	@Persistent
    private Boolean dvd;

	@Persistent
    private Boolean blue_ray;

	@Persistent
    private Boolean digital_copy;

	@Persistent
    private String no_collection;

	@Persistent
    private String id_collection;

	@Persistent
    private String upc;

	@Persistent
    private String genre;

	@Persistent
    private Integer annee_production;

	@Persistent
    private Date date_sortie;

	@Persistent
    private Date date_achat;

	@Persistent
    private Integer duree_minutes;

	public DVDDb()
    {

    }

    public DVDDb(DVDDb tmp)
    {
	this.id=tmp.id;
	this.nom=tmp.nom;
    }

    public DVDDb(String nom)
    {
	this.nom=nom;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public Key getId() {
	return id;
    }

    public void setId(Key id) {
	this.id = id;
    }

	public Boolean getDvd() {
		return dvd;
	}

	public void setDvd(Boolean dvd) {
		this.dvd = dvd;
	}

	public Boolean getBlue_ray() {
		return blue_ray;
	}

	public void setBlue_ray(Boolean blue_ray) {
		this.blue_ray = blue_ray;
	}

	public Boolean getDigital_copy() {
		return digital_copy;
	}

	public void setDigital_copy(Boolean digital_copy) {
		this.digital_copy = digital_copy;
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

	public Integer getAnnee_production() {
		return annee_production;
	}

	public void setAnnee_production(Integer annee_production) {
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

	public Integer getDuree_minutes() {
		return duree_minutes;
	}

	public void setDuree_minutes(Integer duree_minutes) {
		this.duree_minutes = duree_minutes;
	}

}
