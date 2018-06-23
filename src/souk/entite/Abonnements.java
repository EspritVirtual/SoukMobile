/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.entite;

/**
 *
 * @author ASUS PC
 */
public class Abonnements {
       
    private int id;
    private String designation;
    private String description;
    private  float prix;
    private int nbMois;

    public Abonnements() {
    }

    public Abonnements(int id, String designation, String description, float prix, int nbMois) {
        this.id = id;
        this.designation = designation;
        this.description = description;
        this.prix = prix;
        this.nbMois = nbMois;
    }

    public Abonnements(int id) {
        this.id = id;
    }

    public Abonnements(String designation) {
        this.designation = designation;
    }

    public Abonnements(float prix) {
        this.prix = prix;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getNbMois() {
        return nbMois;
    }

    public void setNbMois(int nbMois) {
        this.nbMois = nbMois;
    }


    
    

 
}
