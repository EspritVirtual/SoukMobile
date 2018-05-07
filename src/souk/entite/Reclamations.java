
package souk.entite;

import java.util.Date;

/**
 *
 * @author Soumaya
 */
public class Reclamations {
    private int id ;
    private String contenu;
    private int etat ;
    private Date DateRec;
    private User client;
    private User commercial;

    public Reclamations() {
    }

    
    
    public Reclamations(int id, String contenu, int etat, User client, User commercial) {
        this.id = id;
        this.contenu = contenu;
        this.etat = etat;
        this.client = client;
        this.commercial = commercial;
    }

    public Reclamations(String contenu, int etat) {
        this.contenu = contenu;
        this.etat = etat;
    }

    public Reclamations(int etat) {
        this.etat = etat;
    }

  
    
    
    public int getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public int getEtat() {
        return etat;
    }

    public User getClient() {
        return client;
    }

    public User getCommercial() {
        return commercial;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setCommercial(User commercial) {
        this.commercial = commercial;
    }

    public Date getDateRec() {
        return DateRec;
    }

    public void setDateRec(Date DateRec) {
        this.DateRec = DateRec;
    }
    
    
    
    
    
}
