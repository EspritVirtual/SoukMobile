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
    private Object ta;
  public void ajoutTask(User user) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/souk/web/app_dev.php/api/user/'"+ user.getUsername() + "'/'" + user.getPassword()+"'";
        con.setUrl(Url);

        System.out.println("succes");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

            
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
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

                user.setId((int) id);

                user.setUsername(obj.get("username").toString());
              
                System.out.println(e);
                

            }

        } catch (IOException ex) {
        }
       // System.out.println(listEtudiants);
        return user;

    }

}
