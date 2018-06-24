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
                     
                     String etat = "";
                     if ( lst.getEtat() == 0 ){
                         etat = "Encours" ;
                         
                     } else if( lst.getEtat() == 1 ){
                         etat = "Accpter";
                     } else{
                         etat = "Rejeter";
                     }
                     
                    addButton(res.getImage("imgreclamation1.png"), etat,lst.getContenu(),lst.getDateRec(),lst.getId(),res);}
            refreshTheme();
                }
        });

    }

   
    private void addButton(Image img, String etat,String contenu,Date date,int id,Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        
        TextArea et = new TextArea(etat);
      
        Label tcontenu = new Label(String.valueOf(contenu));
        Label tdate = new Label();
        
        System.out.println("date Page1 : "+date);
        String dateStr = String.valueOf(date);
        System.out.println("date Page2 : "+date);
        tdate.setText(dateStr);
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd hh:mm:ss z yyyy");
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
                            con.setUrl("http://localhost:8000/api/reclamations/edit/"+id+"/"+commercial+"/"+contenu);
                            System.out.println("URL : http://localhost:8000/api/reclamations/edit/"+id+"/"+commercial+"/"+contenuu);
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
                        et,tcontenu,tdate,bedit
                ));
        }else if(roles.toLowerCase().contains(admin.toLowerCase())){
            
            Button bconfirm = new Button("Confirmer");
            bconfirm.addActionListener((e1)->{
                if(etat.equals("Etat : En attente")){
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost:8000/api/reclamations/accepter/"+id);
                    NetworkManager.getInstance().addToQueue(con);
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            new ReclamationsPage(res).show();
                            refreshTheme();
                            Dialog.show("Confirmation Reclamation", "reclamation confirmée avec succès.", "OK",null);
                            Message m = new Message("Votre reclamation a été validée avec succès");
                            Display.getInstance().sendMessage(new String[] {"soumaya.zammali@gmail.com"}, "reclamation", m);
                            
                        }
                    }); 
                }else{
                    Dialog.show("Confirmation Reclamation", "Cette reclamation est déjà confirmée", "OK",null);
                }

            });
            cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        et,tcontenu,tdate,bconfirm
                ));
        }
        

        et.setUIID("NewsTopLine");
        et.setEditable(false);
        
        

        
        add(cnt);
            
    }
}