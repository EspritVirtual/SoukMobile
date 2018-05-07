/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
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

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("root");

            for (Map<String, Object> obj : list) {
                Annonces e = new Annonces();

                float id = Float.parseFloat(obj.get("id").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                String titre = obj.get("titre").toString();

                e.setId((int) id);
                e.setTitre(titre);
                e.setPrix(prix);

                System.out.println(e);
                lstAnnonces.add(e);

            }

        } catch (IOException ex) {
        }
        System.out.println(lstAnnonces);
        return lstAnnonces;

    }
}
