/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import souk.entite.Reclamations;
import souk.entite.User;

/**
 *
 * @author HAYFA
 */
public class UserServices {

     public User getUserConnecte(String json) {

        User user = new User();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> userConnecter = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) userConnecter.get("root");

            for (Map<String, Object> obj : list) {
                User e = new User();

                float id = Float.parseFloat(obj.get("id").toString());
                String roles = String.valueOf(obj.get("roles"));
                user.setId((int) id);

                user.setUsername(obj.get("username").toString());
                user.setRoles(roles);
                
                
                System.out.println(e);
                

            }

        } catch (IOException ex) {
        }
       // System.out.println(listEtudiants);
        return user;

    }
     
     public ArrayList<User> getListUser(String json) {

        ArrayList<User> lstuser = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> coms = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) coms.get("root");

            for (Map<String, Object> obj : list) {
               
                
                User user = new User();

                float id = Float.parseFloat(obj.get("id").toString());
                String roles = String.valueOf(obj.get("roles"));
                user.setId((int) id);
                if(obj.get("titreCommercial") != null)
                user.setTitre(obj.get("titreCommercial").toString());
                user.setUsername(obj.get("username").toString());
                user.setRoles(roles);
                lstuser.add(user);

            }

        } catch (IOException ex) {
        }
        System.out.println(lstuser);
        return lstuser;

    }

}
