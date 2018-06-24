/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import souk.entite.Abonnements;


/**
 *
 * @author ASUS PC
 */
public class AbonnementsServices {
    
    public ArrayList<Abonnements> getListAbonnements(String json) {

        ArrayList<Abonnements> lstAbonnements = new ArrayList<>();
        
        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> abs = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) abs.get("root");

            for (Map<String, Object> obj : list) {
                Abonnements a = new Abonnements();

                float id = Float.parseFloat(obj.get("id").toString());
                String designation = obj.get("designation").toString();
                String description = obj.get("description").toString();
                float prix = Float.parseFloat(obj.get("prix").toString());

                float nbMois = Float.parseFloat(obj.get("nbMois").toString());

                
                
                a.setId((int) id);
                a.setDesignation((String)designation);              
                a.setDescription((String) description);
                a.setPrix((int) prix);
                a.setNbMois((int) nbMois);
               
                System.out.println(a);
                lstAbonnements.add(a);

            }

        } catch (IOException ex) {
        }
        System.out.println(lstAbonnements);
        return lstAbonnements;

    }
}
