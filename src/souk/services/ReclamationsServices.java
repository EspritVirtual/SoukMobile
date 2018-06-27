
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.text.DateFormat;
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
 * @author Soumaya
 */
public class ReclamationsServices {
    
    
    

     public ArrayList<Reclamations> getListReclamations(String json) {

        ArrayList<Reclamations> lstReclamations = new ArrayList<>();

        try {
            System.out.println(json);
            JSONParser j = new JSONParser();

            Map<String, Object> coms = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) coms.get("root");

            for (Map<String, Object> obj : list) {
                Reclamations rec = new Reclamations();

                float id = Float.parseFloat(obj.get("id").toString());
                float etat = Float.parseFloat(obj.get("etat").toString());
                String contenu = obj.get("contenu").toString();
                String idcomer =obj.get("commercial").toString();
                LinkedHashMap com = (LinkedHashMap)obj.get("commercial");
                User us = new User();
                us.setTitre(com.get("titreCommercial").toString());
                
                LinkedHashMap client = (LinkedHashMap)obj.get("client");
                User us_client = new User();
                float id2 = Float.parseFloat(client.get("id").toString());
                us_client.setId((int)id2);
                us_client.setRoles(String.valueOf(client.get("roles")));
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date convertedCurrentDate = sdf.parse(String.valueOf(obj.get("dateRec")));

                    String dat=sdf.format(convertedCurrentDate );
                    
                        
                    rec.setDateRec(convertedCurrentDate);
                } catch (ParseException ex) {
                }

                rec.setId((int) id);
                rec.setEtat((int)etat);
                rec.setCommercial(us);
                rec.setClient(us_client);
                rec.setContenu(contenu);
                System.out.println(rec);
                lstReclamations.add(rec);

            }

        } catch (IOException ex) {
        }
        System.out.println(lstReclamations);
        return lstReclamations;

    }
    
}
