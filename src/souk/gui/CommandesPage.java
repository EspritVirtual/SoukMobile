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
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    Form f,f2;

    public CommandesPage(Resources res)  {
       
        
        super("Commandes", BoxLayout.y(), res);
        
        super.addSideMenu(res);
        Container cntlbl = new Container();
        cntlbl.getAllStyles().setPadding(Component.TOP, 80);
        add(cntlbl);
        int id = SessionUser.getInstance().getId();
        
        ConnectionRequest con = new ConnectionRequest();
        
        Label label = new Label("Mes commandes");
        add(label);
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
                     int qu =  lst.getQuantite();
                     String etat = "";
                     if ( lst.getEtat() == 0 ){
                         etat = "Etat : En attente" ;
                         
                     } else{
                         etat = "Etat : Confirmé";
                     }
                    addButton(res.getImage("commande.png"), etat,qu,lst.getDateCom(),lst.getId(),res);
                 }
                    refreshTheme();
                }
        });
        
    }
    private void addButton(Image img, String etat,int quantite, Date date,int id,Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        //cnt.setLeadComponent(image);
        TextArea et = new TextArea(etat);
        Label tq = new Label(String.valueOf(quantite));

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
            
            SimpleDateFormat format1 = new SimpleDateFormat("EE MMM dd hh:mm:ss z yyyy");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format1.parse(dat));
            System.out.println(format.format(c.getTime()));
            tdate.setText(format.format(c.getTime()));
        }catch(ParseException ex){
            
        }
        
        
        
        
        
        
        String roles = SessionUser.getInstance().getRoles();
        System.out.println("roles" + roles);
        String com = "ROLE_COM";
        String client = "ROLE_CLIENT";
        if(roles.toLowerCase().contains(client.toLowerCase())){
            
            Button bsupp = new Button("Supprimer");
            Button bedit = new Button("Modifier");
            bsupp.addActionListener((e1)->{
                if(etat.equals("Etat : En attente")){
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost:8000/api/commandes/annuler/"+id);
                    NetworkManager.getInstance().addToQueue(con);
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            new CommandesPage(res).show();
                            refreshTheme();
                            Dialog.show("Suppression Commande", "Commande supprimée avec succès.", "OK",null);

                        }
                    }); 
                }else{
                    Dialog.show("Suppression Commande", "Vous ne pouvez pas supprimer une commande confirmé", "OK",null);
                }

            });
            
            bedit.addActionListener((e)->{
                if(etat.equals("Etat : En attente")){
                        
                        Label lbl_date = new Label("Date :");                       
                        Label lbl_quantite = new Label("Quantite :");
                        TextField tf_date = new TextField();
                        TextField tf_quantite = new TextField();
                        Button btn_quitter = new Button("Quitter"); 
                        Button btn_valider = new Button("Valider");
                        tf_date.setText(tdate.getText()); 
                        tf_quantite.setText(tq.getText());
                        
                        Dialog dlg = new Dialog("Modification");
                        dlg.setLayout(BoxLayout.y());
                        Style dlgStyle = dlg.getDialogStyle();
                        dlgStyle.setBgTransparency(255);
                        dlgStyle.setBgColor(0xffffff);
                        Picker datePicker = new Picker();
                        datePicker.setType(Display.PICKER_TYPE_DATE);
                        com.codename1.l10n.SimpleDateFormat formatter = new com.codename1.l10n.SimpleDateFormat("yyyy-MM-dd");
                        datePicker.setFormatter(formatter);    
                        dlg.add(lbl_date);
                        dlg.add(datePicker);
                        dlg.add(lbl_quantite);
                        dlg.add(tf_quantite);

                        Button ok = new Button("Valider");
                        ok.getAllStyles().setFgColor(0);
                        dlg.add(ok);
                        Button quitter = new Button("Quitter");
                        quitter.getAllStyles().setFgColor(0);
                        dlg.add(quitter);
                        quitter.addActionListener((eq)->{
                            dlg.setVisible(false);
                            new CommandesPage(res).show();
                            refreshTheme();
                        });
                        
                        
                        ok.addActionListener((eq2)->{
                            System.out.println("ghere");
                            ConnectionRequest con = new ConnectionRequest();
                            String d = datePicker.getText();
                            String qt = tf_quantite.getText();
                            con.setUrl("http://localhost:8000/api/commandes/modifier/"+id+"/"+d+"/"+qt);
                            System.out.println("URL : http://localhost:8000/api/commandes/modifier/"+id+"/"+d+"/"+qt);
                            NetworkManager.getInstance().addToQueue(con);
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    
                                    
                                    Dialog.show("Modification Commande", "Commande modifié avec succès.", "OK",null);
                                    dlg.setVisible(false);

                                    new CommandesPage(res).show();
                                    refreshTheme();
                                }
                            });
                        });
                        dlg.showDialog();
                    
                }else{
                    Dialog.show("Modification Commande", "Vous ne pouvez pas modifier une commande confirmé", "OK",null);
                }

            });
            cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        et,tdate,tq,bedit,bsupp
                ));
        }else if(roles.toLowerCase().contains(com.toLowerCase())){
            
            Button bconfirm = new Button("Confirmer");
            bconfirm.addActionListener((e1)->{
                if(etat.equals("Etat : En attente")){
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost:8000/api/commandes/confirmer/"+id);
                    NetworkManager.getInstance().addToQueue(con);
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            new CommandesPage(res).show();
                            refreshTheme();
                            Dialog.show("Confirmation Commande", "Commande confirmée avec succès.", "OK",null);
                            Message m = new Message("Votre commande a été validée avec succès");
                            //m.getAttachments().put(textAttachmentUri, "text/plain");
                            //m.getAttachments().put(imageAttachmentUri, "image/png");
                            Display.getInstance().sendMessage(new String[] {"nourelhouda.banbia@gmail.com"}, "Commande", m);
                            
                        }
                    }); 
                }else{
                    Dialog.show("Confirmation Commande", "Cette commande est déjà confirmée", "OK",null);
                }

            });
            cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        et,tdate,tq,bconfirm
                ));
        }
        

        et.setUIID("NewsTopLine");
        et.setEditable(false);
        
        

        
        add(cnt);
            
    }
    
   
}
