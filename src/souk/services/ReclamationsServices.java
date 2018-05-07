
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import souk.entite.Commande;
import souk.entite.Reclamations;

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

                
                
                rec.setId((int) id);
                rec.setEtat((int)etat);
                rec.setContenu(contenu);
                

               // e.setEtat(obj.get("state").toString());
              //e.setNom(obj.get("name").toString());
                System.out.println(rec);
                lstReclamations.add(rec);

            }

        } catch (IOException ex) {
        }
        System.out.println(lstReclamations);
        return lstReclamations;

    }
    
}
