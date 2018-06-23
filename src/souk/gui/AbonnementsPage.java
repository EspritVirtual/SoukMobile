/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import java.util.Date;
import java.util.List;
import souk.entite.Abonnements;
import souk.entite.Commande;
import souk.services.AbonnementsServices;
import souk.services.AnnoncesServices;
import souk.services.CommandeService;
import souk.util.SessionUser;

/**
 *
 * @author ASUS PC
 */
public class AbonnementsPage extends BaseForm {

     Form F1,F2;

    public AbonnementsPage(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public AbonnementsPage(Resources res)  {
       
        
        super("Abonnements", BoxLayout.y(), res);
        
        super.addSideMenu(res);
        Container cntlbl = new Container();
        cntlbl.getAllStyles().setPadding(Component.TOP, 80);
        add(cntlbl);
        int id = SessionUser.getInstance().getId();
        
        ConnectionRequest con = new ConnectionRequest();
        
        Label label = new Label("Mes abonnements");
        add(label);
        con.setUrl("http://localhost:8000/api/abonnements/liste");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                AbonnementsServices ser = new AbonnementsServices();
                List<Abonnements> list = ser.getListAbonnements(new String(con.getResponseData()));
                 for(Abonnements lst : list)
                 {
                     int id =  lst.getId();
                     String desg =  lst.getDesignation();
                     String desc =  lst.getDescription();
                     float prix =  lst.getPrix();
                     int nbMois =  lst.getNbMois();
                   
                     addButton(res.getImage("commande.png"),id,desg,desc,prix,nbMois,res);
                   
                 }
                    
                    refreshTheme();
                }
        });
    }
    private void addButton(Image img, int id_ab,String desg, String desc , float prix, int nb, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));

        image.setUIID("Label");

        Container cnt = BorderLayout.west(image);
        cnt.setY(getToolbar().getHeight());
        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        Label txtdesg = new Label(desg);
        Label txtdesc = new Label(desc); 
        Label txtprix = new Label(String.valueOf(prix));
        Label txtnb = new Label(String.valueOf(nb));
        Button delete = new Button("Supprimer");

        

        delete.addActionListener((e) -> {
            ConnectionRequest connection = new ConnectionRequest();
            connection.setUrl("http://localhost:8000/api/abonnements/supprimer/"+id_ab);
            NetworkManager.getInstance().addToQueue(connection);
            connection.addResponseListener(new ActionListener<NetworkEvent>() {

                @Override
                public void actionPerformed(NetworkEvent evt) {
                    new AbonnementsPage(res).show();
                    refreshTheme();
                    Dialog.show("Suppression Abonnement", "Abonnement supprimée avec succès.", "OK",null);
                }
            });

        });
        cnt.setUIID("Container");
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txtdesg, txtdesc, txtprix,txtnb,delete
                ));
        add(cnt);

    }
}
