/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.entite;

import java.util.Date;

/**
 *
 * @author Boufares
 */
public class CommentairesAnc {

    private int id;
    private String contenu;
    private Date dateCmt;

    public CommentairesAnc() {
    }

    public CommentairesAnc(int id, String contenu, Date dateCmt) {
        this.id = id;
        this.contenu = contenu;
        this.dateCmt = dateCmt;
    }

    public CommentairesAnc(String contenu, Date dateCmt) {
        this.contenu = contenu;
        this.dateCmt = dateCmt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCmt() {
        return dateCmt;
    }

    public void setDateCmt(Date dateCmt) {
        this.dateCmt = dateCmt;
    }

    @Override
    public String toString() {
        return "CommentairesAnc{" + "id=" + id + ", contenu=" + contenu + ", dateCmt=" + dateCmt + '}';
    }

}
