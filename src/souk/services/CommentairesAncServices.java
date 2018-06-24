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
import souk.entite.CommentairesAnc;

/**
 *
 * @author Boufares
 */
public class CommentairesAncServices {

    public ArrayList<CommentairesAnc> getListCommentairesAnc(String json) {

        ArrayList<CommentairesAnc> lstCommentaires = new ArrayList<>();
        try {
         JSONParser j = new JSONParser();

             Map<String, Object> coms = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) coms.get("root");
            for (Map<String, Object> obj : list) {
                CommentairesAnc c = new CommentairesAnc();

                float id = Float.parseFloat(obj.get("id").toString());
    
                String contenu = (String) obj.get("contenu");
                String dat = String.valueOf(obj.get("date_cmt"));
                System.out.println(dat);
                DateFormat formatter;
                Date date = new Date();
                formatter = new SimpleDateFormat("yy-MMM-dd");
                try {
                    date = formatter.parse(dat);
                } catch (ParseException ex) {
                }
                
                                
               /// récuperer role
//                 ArrayList<String> role = <String> obj.get("roles");
//                 System.out.println(role.get("ROLE_USER").toString());
//                e.setRoles(role.get("ROLE_USER").toString());
//       
                System.out.println(c.toString());
                c.setId((int) id);
                c.setContenu(contenu);
                c.setDateCmt(date);
                System.out.println(c);
                lstCommentaires.add(c);
            }
        } catch (IOException ex) {
        }
 return lstCommentaires;
    }
}
