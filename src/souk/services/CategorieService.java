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
import souk.entite.Categories;

/**
 *
 * @author HAYFA
 */
public class CategorieService {

    public ArrayList<Categories> getListCategorie(String json) {

        ArrayList<Categories> lstCategorie = new ArrayList<>();
        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> categories = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) categories.get("root");

            for (Map<String, Object> obj : list) {
                Categories e = new Categories();

                float id = Float.parseFloat(obj.get("id").toString());
                String designation = obj.get("designation").toString();

                e.setId((int) id);
                e.setDesignation(designation);

                lstCategorie.add(e);

            }

        } catch (IOException ex) {
        }

        return lstCategorie;

    }
}
