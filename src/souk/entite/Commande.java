/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.entite;

import java.util.Date;

/**
 *
 * @author nour
 */
public class Commande {
    private int id;
    private Date dateCom;
    private int quantite;
    private int etat;


    public Commande() {
    }
    
    public Commande(int id, Date dateCom, int quantite, int etat) {
        this.id = id;
        this.dateCom = dateCom;
        this.quantite = quantite;
        this.etat = etat;
    }

    public Commande(Date dateCom, int quantite, int etat) {
        this.dateCom = dateCom;
        this.quantite = quantite;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCom() {
        return dateCom;
    }

    public void setDateCom(Date dateCom) {
        this.dateCom = dateCom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
}
