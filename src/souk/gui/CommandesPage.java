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
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import souk.entite.Annonces;
import souk.entite.Commande;
import souk.services.AnnoncesServices;
import souk.services.CommandeService;
import souk.util.SessionUser;

/**
 *
 * @author nour
 */
public class CommandesPage extends BaseForm {
    Form f;

    public CommandesPage(Resources res)  {
       
        
        super("Commandes", BoxLayout.y(), res);
        int id = SessionUser.getInstance().getId();
        
        ConnectionRequest con = new ConnectionRequest();
        
        con.setUrl("http://localhost:8000/api/commandes/liste/"+id);
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CommandeService ser = new CommandeService();
                List<Commande> list = ser.getListCommandes(new String(con.getResponseData()));
                 for(Commande lst : list)
                 {
                     Date d =  lst.getDateCom();
                     String etat = "";
                     if ( lst.getEtat() == 0 ){
                         etat = "En attente" ;
                         
                     } else{
                         etat = "Confirmé";
                     }
                    addButton(res.getImage("commande.png"), etat,lst.getDateCom());}
                    refreshTheme();
                }
        });

    }


   

    private void addButton(Image img, String title, Date date) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        
        
        Label ta2 = new Label(new SimpleDateFormat("dd-MM-yyyy").format(date).toString());

        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        
        

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,ta2
                ));
        add(cnt);
        image.addActionListener(e -> ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

   
}
