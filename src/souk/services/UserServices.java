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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                //récuperer role
//                 ArrayList<String> role = <String> obj.get("roles");
//                 System.out.println(role.get("ROLE_USER").toString());
//                e.setRoles(role.get("ROLE_USER").toString());
                
                System.out.println(e);
                

            }

        } catch (IOException ex) {
        }
       // System.out.println(listEtudiants);
        return user;

    }

}
