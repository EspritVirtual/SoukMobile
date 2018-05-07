/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.entite;

import java.util.Date;

/**
 *
 * @author HAYFA
 */
public class Annonces {

    private int id;

    private String titre;

    private String description;

    private float prix;

    private boolean disponible;

    private Date dateCreation;

    private Categories categorie;
    private User commercial;

    public Annonces(String titre, String description, float prix, boolean disponible, Date dateCreation, Categories categorie, User commercial) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.disponible = disponible;
        this.dateCreation = dateCreation;
        this.categorie = categorie;
        this.commercial = commercial;
    }

    public Annonces() {
    }

    public User getCommercial() {
        return commercial;
    }

    public void setCommercial(User commercial) {
        this.commercial = commercial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Categories getCategorie() {
        return categorie;
    }

    public void setCategorie(Categories categorie) {
        this.categorie = categorie;
    }

}
