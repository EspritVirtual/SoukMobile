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
public class Reservation {
    private int id;
    private String client;
    private int id_evenement;
    private Date date_res;

    public Reservation(int id, String client, int id_evenement, Date date_res) {
        this.id = id;
        this.client = client;
        this.id_evenement = id_evenement;
        this.date_res = date_res;
    }

    public Reservation() {
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

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public Date getDate_res() {
        return date_res;
    }

    public void setDate_res(Date date_res) {
        this.date_res = date_res;
    }
    
    
}
