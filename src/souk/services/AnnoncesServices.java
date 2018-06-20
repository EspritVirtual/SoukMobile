/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import souk.entite.Annonces;

/**
 *
 * @author HAYFA
 */
public class AnnoncesServices {

    public ArrayList<Annonces> getListAnnonces(String json) {

        ArrayList<Annonces> lstAnnonces = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> annonces = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) annonces.get("root");

            for (Map<String, Object> obj : list) {
                Annonces e = new Annonces();

                float id = Float.parseFloat(obj.get("id").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                String titre = obj.get("titre").toString();   
                String description = obj.get("description").toString();

                String dat = String.valueOf(obj.get("dateCreation"));

                System.out.println(dat);
                DateFormat formatter;
                Date date = new Date();
                formatter = new SimpleDateFormat("yy-MMM-dd");
                try {
                    date = formatter.parse(dat);
                } catch (ParseException ex) {
                }

                e.setDateCreation(date);
                e.setId((int) id);
                e.setDescription(description);
                e.setTitre(titre);
                e.setPrix(prix);

             
                lstAnnonces.add(e);
     
            }

        } catch (IOException ex) {
        }
       
        return lstAnnonces;

    }
    public Annonces getAnnoncesById(String json) {

        Annonces e = new Annonces();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> annonce = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) annonce.get("root");
           
            for (Map<String, Object> obj : list) {
             

                float id = Float.parseFloat(obj.get("id").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                String titre = obj.get("titre").toString();   
                String description = obj.get("description").toString();

                String dat = String.valueOf(obj.get("dateCreation"));

                System.out.println(dat);
                DateFormat formatter;
                Date date = new Date();
                formatter = new SimpleDateFormat("yy-MMM-dd");
                try {
                    date = formatter.parse(dat);
                } catch (ParseException ex) {
                }

                e.setDateCreation(date);
                e.setId((int) id);
                e.setDescription(description);
                e.setTitre(titre);
                e.setPrix(prix);

         
               
            }

        } catch (IOException ex) {
        }
       
        return e;

    }
}
