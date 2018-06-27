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

import souk.entite.Evenements;
import souk.entite.User;

/**
 *
 * @author salsa
 */
public class EvenementsServices {

    public ArrayList<Evenements> getListeEvenements(String json) {
        ArrayList<Evenements> lstEvenements = new ArrayList<>();
        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evenements = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) evenements.get("root");

            for (Map<String, Object> obj : list) {
                Evenements e = new Evenements();

                float id = Float.parseFloat(obj.get("id").toString());
                ///  User id_com = obj.get("commercial").toString();
                float prix = Float.parseFloat(obj.get("prix").toString());
                String titre = obj.get("titre").toString();
                String description = obj.get("description").toString();
                float etat = Float.parseFloat(obj.get("etat").toString());
                String lieu = obj.get("lieu").toString();

                String date_deb = String.valueOf(obj.get("dateDeb"));
                String date_fin = String.valueOf(obj.get("dateFin"));
                System.out.println(date_deb);
                DateFormat formatter;
                Date datedeb = new Date();
                Date datefin = new Date();

                formatter = new SimpleDateFormat("yy-MMM-dd");
                try {
                    datedeb = formatter.parse(date_deb);
                    datefin = formatter.parse(date_fin);

                } catch (ParseException ex) {
                }

                e.setDate_deb(datedeb);
                e.setDate_deb(datefin);

                e.setId((int) id);
                e.setDescription(description);
                e.setTitre(titre);
                e.setPrix(prix);
                e.setEtat(etat);
                e.setLieu(lieu);
                lstEvenements.add(e);

            }

        } catch (IOException ex) {
        }

        return lstEvenements;
    }

    public Evenements getEvenementsById(String json) {

        Evenements e = new Evenements();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> evenements = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) evenements.get("root");

            for (Map<String, Object> obj : list) {

                float id = Float.parseFloat(obj.get("id").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                String titre = obj.get("titre").toString();
                String description = obj.get("description").toString();
                float etat = Float.parseFloat(obj.get("etat").toString());
                String lieu = obj.get("lieu").toString();
                String date_deb = String.valueOf(obj.get("dateDeb"));
                String date_fin = String.valueOf(obj.get("dateFin"));
                System.out.println(date_deb);
                DateFormat formatter;
                Date datedeb = new Date();
                Date datefin = new Date();
                formatter = new SimpleDateFormat("yy-MMM-dd");
                try {
                    datedeb = formatter.parse(date_deb);
                    datefin = formatter.parse(date_fin);
                } catch (ParseException ex) {
                }

                e.setId((int) id);
                e.setDescription(description);
                e.setTitre(titre);
                e.setEtat(etat);
                e.setPrix(prix);
                e.setLieu(lieu);

            }

        } catch (IOException ex) {
        }

        return e;

    }

}
