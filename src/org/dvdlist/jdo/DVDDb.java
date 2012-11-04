/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dvdlist.jdo;

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

}
