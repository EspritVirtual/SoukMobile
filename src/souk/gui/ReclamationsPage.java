/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import souk.entite.Reclamations;
import souk.services.ReclamationsServices;
import souk.util.SessionUser;

/**
 *
 * @author Soumaya
 */
public class ReclamationsPage extends BaseForm{
    
     Form f;

    public ReclamationsPage(Resources res)  {
       
        super("Reclamation", BoxLayout.y(), res);
        int id = SessionUser.getInstance().getId();
        ConnectionRequest con = new ConnectionRequest();
   
        con.setUrl("http://localhost:8000/api/reclamations/liste/"+id);
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ReclamationsServices ser = new ReclamationsServices();
                 List<Reclamations> list = ser.getListReclamations(new String(con.getResponseData()));
                System.out.println(list);
                 for(Reclamations lst : list)
                 {
                     Date d =  lst.getDateRec();
                     String etat = "";
                     if ( lst.getEtat() == 0 ){
                         etat = "Encours" ;
                         
                     } else if( lst.getEtat() == 1 ){
                         etat = "Accpter";
                     } else{
                         etat = "Rejeter";
                     }
                     
                    addButton(res.getImage("imgreclamation1.png"), etat ,lst.getDateRec() , res);}
            refreshTheme();
                }
        });

    }

   
    private void addButton(Image img, String title,Date date,Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        Label ta2 = new Label(new SimpleDateFormat("dd-MM-yyyy").format(date).toString());
        Button bsupp = new Button("Supprimer");
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);


        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,ta2,bsupp
                ));
        add(cnt);
        image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

   
}
