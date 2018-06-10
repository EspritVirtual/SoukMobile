
package souk.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                String dat = String.valueOf(obj.get("dateRec"));
                DateFormat formatter;
                Date date=new Date();
                formatter = new SimpleDateFormat("yy-MMM-dd");

                
                
                rec.setId((int) id);
                rec.setEtat((int)etat);
                rec.setContenu(contenu);
                rec.setDateRec(date);

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
