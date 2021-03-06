/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.entite;

import java.util.Date;

/**
 *
 * @author salsa
 */
public class Evenements {
    private int id;
    private String client;
    private int id_comm;
    private String titre;
    private String description;
    private Date date_deb;
    private Date date_fin;
    private String lieu;
    private float prix;
    private float etat;

    public Evenements() {
        
    }

 
    public Evenements(int id, String client, int id_comm, String titre, String description, Date date_deb, Date date_fin, String lieu, float prix, float etat) {
        this.id = id;
        this.client = client;
        this.id_comm = id_comm;
        this.titre = titre;
        this.description = description;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.lieu = lieu;
        this.prix = prix;
        this.etat= etat;
    }

    public Evenements(int id,String titre, String description, Date date_deb, String lieu, float prix) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date_deb = date_deb;
        this.lieu = lieu;
        this.prix = prix;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getId_comm() {
        return id_comm;
    }

    public void setId_comm(int id_comm) {
        this.id_comm = id_comm;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(Date date_deb) {
        this.date_deb = date_deb;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getEtat() {
        return etat;
    }

    public void setEtat(float etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Evenements{" + "id=" + id + ", client=" + client + ", id_comm=" + id_comm + ", titre=" + titre + ", description=" + description + ", date_deb=" + date_deb + ", date_fin=" + date_fin + ", lieu=" + lieu + ", prix=" + prix + ", etat=" + etat + '}';
    }
    
    
    
}
