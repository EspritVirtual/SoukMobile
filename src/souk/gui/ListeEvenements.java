/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.ScaleImageLabel;
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
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import souk.entite.Annonces;
import souk.entite.Evenements;
import souk.gui.BaseForm;
import souk.services.AnnoncesServices;
import souk.services.EvenementsServices;
import souk.util.SessionUser;

/**
 *
 * @author salsa
 */
public class ListeEvenements extends BaseForm{
    
    public List<Evenements> list = new ArrayList<>() ;
    Evenements evenement = new Evenements();
    
     public ListeEvenements(Resources res) {
        super("Evenements", BoxLayout.y(), res);

        super.addSideMenu(res);
        Container cntlbl = new Container();

        cntlbl.getAllStyles().setPadding(Component.TOP, 100);
        add(cntlbl);
        ConnectionRequest con = new ConnectionRequest();
        Label label = new Label("Liste des Evenements");
         System.out.println("Salsa"+SessionUser.getInstance().getRoles());
        String com = "ROLE_COM";
        if(SessionUser.getInstance().getRoles().toLowerCase().contains(com.toLowerCase())) {
            con.setUrl("http://localhost:8000/api/evenements/allEvents/user/"+SessionUser.getInstance().getId());
        } else {
            con.setUrl("http://localhost:8000/api/evenements/allEvents");
        }
//        System.out.println("url : "+"http://localhost:8000/api/evenements/allEvents");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                EvenementsServices ser = new EvenementsServices();
                System.out.println(con.getUrl());
                Container cntIndex = new Container();
                 list = ser.getListeEvenements(new String(con.getResponseData()));
                for (Evenements lst : list) {
                    String etat = "";
                    if ( lst.getEtat() == 0 ){
                         etat = "Etat : Disponible" ;
                         
                     } else{
                         etat = "Etat : Non Disponible";
                     }
                                  
                    
                  addButton(res.getImage("large.jpg"), lst.getTitre(),etat, lst.getDate_deb(),lst.getDate_fin(), lst.getPrix(), cntIndex, lst.getId(), res);
                }
                add(cntIndex);
                refreshTheme();
            }
        });

    }
      private void addButton(Image img, String title, String etat, Date dateDeb,Date dateFin, float prix, Container cntIndex, int idevenements, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));

        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        Label et = new Label(etat);
          Label tq = new Label(String.valueOf(prix));
        
        cnt.setY(getToolbar().getHeight());
        
//        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        Label txttitle = new Label(title);
        txttitle.setEnabled(focusScrolling);
        
        Label lbldate = new Label(new SimpleDateFormat("dd-MM-yyyy").format(dateDeb).toString()
                );   
       
        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        Button btnDet = new Button(iconDetail);
        btnDet.setUIID("Label");
        btnDet.addActionListener((e) -> {
                                System.out.println(list.size());

            for (Evenements lst : list) {
                if (lst.getId() == idevenements) {
                    evenement.equals(lst);
                }
            }
            cntIndex.removeAll();
        Button imageDetail = new Button(img.fill(Display.getInstance().convertToPixels(14f), Display.getInstance().convertToPixels(11.5f)));

        imageDetail.setUIID("Label");
        Container cntDetail = BorderLayout.west(imageDetail);
        String etatDet = "";
        if ( evenement.getEtat() == 0 ){
             etatDet = "Etat : Disponible" ;

         } else{
             etatDet = "Etat : Non Disponible";
         }
        Label etDetail = new Label(etatDet);
          Label tqDetail = new Label(String.valueOf(prix));
          Label lieuDetail = new Label(evenement.getLieu());
            System.out.println("lieu"+evenement.getLieu());
        
        cntDetail.setY(getToolbar().getHeight());
        
//        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        Label txttitleDetail = new Label(evenement.getTitre());
        txttitle.setEnabled(focusScrolling);
        
        Label lbldateDetail = new Label(new SimpleDateFormat("dd-MM-yyyy").format(evenement.getDate_deb()).toString()
                );   
                cntDetail.setUIID("Container");
        cntDetail.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txttitleDetail, lbldateDetail ,etDetail,tqDetail,lieuDetail
                ));
            cntIndex.add(cntDetail);
            refreshTheme();
            
        });
        //cnt.add(btnDet);
        cnt.setUIID("Container");
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txttitle, lbldate ,et,tq,btnDet
                ));

        
        cntIndex.add(cnt);

    }

}
