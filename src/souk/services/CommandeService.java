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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import souk.entite.Annonces;
import souk.entite.Commande;

/**
 *
 * @author nour
 */
public class CommandeService {
    

     public ArrayList<Commande> getListCommandes(String json) {

        ArrayList<Commande> lstCommandes = new ArrayList<>();
        
        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> coms = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) coms.get("root");

            for (Map<String, Object> obj : list) {
                Commande c = new Commande();

                float id = Float.parseFloat(obj.get("id").toString());
                float quantite = Float.parseFloat(obj.get("quantite").toString());

                float etat = Float.parseFloat(obj.get("etat").toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date convertedCurrentDate = sdf.parse(String.valueOf(obj.get("dateCom")));

                    String dat=sdf.format(convertedCurrentDate );
                    
                        
                    c.setDateCom(convertedCurrentDate);
                } catch (ParseException ex) {
                }
                
                c.setId((int) id);
                c.setEtat((int)etat);
                
                c.setQuantite((int) quantite);

               
                System.out.println(c);
                lstCommandes.add(c);

            }

        } catch (IOException ex) {
        }
        System.out.println(lstCommandes);
        return lstCommandes;

    }
}
