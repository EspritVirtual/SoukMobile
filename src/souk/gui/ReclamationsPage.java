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
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import souk.entite.Reclamations;
import souk.entite.User;
import souk.services.ReclamationsServices;
import souk.services.UserServices;
import souk.util.SessionUser;

/**
 *
 * @author Soumaya
 */
public class ReclamationsPage extends BaseForm{
    
     Form f, insert;

    public ReclamationsPage(Resources res)  {
       
        super("Reclamation", BoxLayout.y(), res);
        
        super.addSideMenu(res);
        Container cntlbl = new Container();
        cntlbl.getAllStyles().setPadding(Component.TOP, 80);
        
        
        // Insertion reclamation
        
        
            
           
           
        
         
        
        // fin Insertion
        Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ADD, s);
        FontImage icon2 = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        this.getToolbar().addCommandToRightBar("", icon, (e)->{ new ReclamationInsertPage(res).show();});
        add(cntlbl);
        int id = SessionUser.getInstance().getId();
        ConnectionRequest con = new ConnectionRequest();
   
        con.setUrl("http://localhost:8000/api/reclamations/allReclamations");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ReclamationsServices ser = new ReclamationsServices();
                 List<Reclamations> list = ser.getListReclamations(new String(con.getResponseData()));
                System.out.println(list);
                String admin = "ROLE_ADMIN";
                String client = "ROLE_CLIENT";
                 for(Reclamations lst : list)
                 {
                     
                     String etat = "";
                     if ( lst.getEtat() == 0 ){
                         etat = "Encours" ;
                         
                     } else if( lst.getEtat() == 1 ){
                         etat = "Accpter";
                     } else{
                         etat = "Rejeter";
                     }
                     
        if(SessionUser.getInstance().getRoles().toLowerCase().contains(client.toLowerCase())){
            if(lst.getClient().getId()==id)
             addButton(res.getImage("reclamation.jpg"), etat,lst.getContenu(),lst.getCommercial().getTitre(),lst.getDateRec(),lst.getId(),res);
        }
                 else
                    addButton(res.getImage("reclamation.jpg"), etat,lst.getContenu(),lst.getCommercial().getTitre(),lst.getDateRec(),lst.getId(),res);
                 }
                         
            refreshTheme();
                }
        });

    }

   
    private void addButton(Image img, String etat,String contenu,String idCommer,Date date,int id,Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        
        TextArea et = new TextArea(etat);
        Label tidCom = new Label(String.valueOf(idCommer));
        Label tcontenu = new Label(String.valueOf(contenu));
        Label tdate = new Label();
        
        System.out.println("date Page1 : "+date);
        String dateStr = String.valueOf(date);
        System.out.println("date Page2 : "+date);
        tdate.setText(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd yyyy");
        try{
            Date convertedCurrentDate = sdf.parse(String.valueOf(date));
            
            String dat=sdf.format(convertedCurrentDate );
            System.out.println("date Page3 : "+ dat);
          
        }catch(ParseException ex){
            
        }
        
        String roles = SessionUser.getInstance().getRoles();
        System.out.println("roles" + roles);
        String admin = "ROLE_ADMIN";
        String client = "ROLE_CLIENT";
        if(roles.toLowerCase().contains(client.toLowerCase())){
     
            Button bedit = new Button("Modifier");
        
            bedit.addActionListener((e)->{
  
                        Label lbl_commerciale = new Label("Commerciale :");                       
                        Label lbl_contenu = new Label("Contenu :");
                        TextField tf_commer = new TextField();
                        TextField tf_contenu = new TextField();
                        tf_commer.setText(tidCom.getText());
                        tf_contenu.setText(tcontenu.getText());
                        
                        Dialog dlg = new Dialog("Modification");
                        dlg.setLayout(BoxLayout.y());
                        Style dlgStyle = dlg.getDialogStyle();
                        dlgStyle.setBgTransparency(255);
                        dlgStyle.setBgColor(0xffffff);
                        Picker datePicker = new Picker();
                        datePicker.setType(Display.PICKER_TYPE_DATE);
                        com.codename1.l10n.SimpleDateFormat formatter = new com.codename1.l10n.SimpleDateFormat("yyyy-MM-dd");
                        datePicker.setFormatter(formatter);   
                        dlg.add(lbl_commerciale);
                        dlg.add(tf_commer);
                        dlg.add(lbl_contenu);
                        dlg.add(tf_contenu);

                        Button ok = new Button("Valider");
                        ok.getAllStyles().setFgColor(0);
                        dlg.add(ok);
                        Button quitter = new Button("Quitter");
                        quitter.getAllStyles().setFgColor(0);
                        dlg.add(quitter);
                        quitter.addActionListener((eq)->{
                            dlg.setVisible(false);
                            new ReclamationsPage(res).show();
                            refreshTheme();
                        });
                        
                        
                        ok.addActionListener((eq2)->{
                            ConnectionRequest con = new ConnectionRequest();
                            String commercial = tf_commer.getText();
                            String contenuu = tf_contenu.getText();
                            con.setUrl("http://localhost:8000/api/reclamations/edit/"+id+"/"+contenuu);
                            System.out.println("URL : http://localhost:8000/api/reclamations/edit/"+id+"/"+contenuu);
                            NetworkManager.getInstance().addToQueue(con);
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    
                                    
                                    Dialog.show("Modification Reclamation", "reclamation modifié avec succès.", "OK",null);
                                    dlg.setVisible(false);

                                    new ReclamationsPage(res).show();
                                    refreshTheme();
                                }
                            });
                        });
                        dlg.showDialog();
                    
 });
           cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        et,tidCom,tcontenu,tdate,bedit
                ));
        }else if(roles.toLowerCase().contains(admin.toLowerCase())){
            
            Button bconfirm = new Button("Accepter");
            bconfirm.addActionListener((e1)->{
                if(etat.equals("Encours")){
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost:8000/api/reclamations/accepter/"+id);
                    NetworkManager.getInstance().addToQueue(con);
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            new ReclamationsPage(res).show();
                            refreshTheme();
                            Dialog.show("Accepter Reclamation", "reclamation accepté avec succès.", "OK",null);
                          
                            
                        }
                    }); 
                }else{
                    Dialog.show("Accepter Reclamation", "Cette reclamation est déjà accepté", "OK",null);
                }

            });
            
            
            Button brejeter = new Button("Rejeter");
            brejeter.addActionListener((e1)->{
                if(etat.equals("Encours")){
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost:8000/api/reclamations/rejeter/"+id);
                    NetworkManager.getInstance().addToQueue(con);
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            new ReclamationsPage(res).show();
                            refreshTheme();
                            Dialog.show("Rejeter Reclamation", "reclamation rejeter avec succès.", "OK",null);
                           
                            
                        }
                    }); 
                }else{
                    Dialog.show("Rejeter Reclamation", "Cette reclamation est déjà rejeté", "OK",null);
                }

            });
            if(etat.equals("Encours")){
            cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        et,tidCom,tcontenu,tdate,bconfirm,brejeter
                ));
        }else {cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        et,tidCom,tcontenu,tdate
                        ));}
        }
        

        et.setUIID("NewsTopLine");
        et.setEditable(false);
        
        

        
        add(cnt);
        
         
    
    }
   
}