/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.List;
import souk.entite.Reclamations;
import souk.entite.User;
import souk.services.UserServices;
import souk.util.SessionUser;

/**
 *
 * @author Soumaya
 */
public class ReclamationInsertPage extends BaseForm {
    public ReclamationInsertPage(Resources res)  {
       
        super("Ajouter une réclamation", BoxLayout.y(), res);
        super.addSideMenu(res);
        Container cntlbl = new Container();
        cntlbl.getAllStyles().setPadding(Component.TOP, 80);
        
        ConnectionRequest conuser = new ConnectionRequest();
   
        conuser.setUrl("http://localhost:8000/api/userAll");
        NetworkManager.getInstance().addToQueue(conuser);
        conuser.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                UserServices ser = new UserServices();
                 List<User> list = ser.getListUser(new String(conuser.getResponseData()));
                System.out.println(list);
                
                String com = "ROLE_COM";
                 ComboBox combo = new ComboBox();
                 ArrayList<String> lstidCom = new ArrayList<>();
                 for(User lst : list)
                 {
                if(lst.getRoles().toLowerCase().contains(com.toLowerCase())){
                    combo.addItem(lst.getTitre());
                    lstidCom.add(String.valueOf(lst.getId()));
                   
                }
                 }
                 Label lbl_commerciale = new Label(".");  
                 Label lbl2_commerciale = new Label(".");  
                 Label lbl3_commerciale = new Label(".");
                 Label lbl4_commerciale = new Label("Commercial");
                 Label lbl_contenu= new Label("Contenu :");  
                 TextField tf_contenu = new TextField();
                 add(lbl_commerciale);
                 add(lbl2_commerciale);
                 add(lbl3_commerciale);
                 add(lbl4_commerciale);
                 add(combo);
                 add(lbl_contenu);
                 
                 add(tf_contenu);
                 Button Val = new Button("Valider");
                    Val.getAllStyles().setFgColor(0);
                    add(Val);
                    Val.addActionListener((eq1)->{
                        ConnectionRequest con = new ConnectionRequest();
                            int id = SessionUser.getInstance().getId();
                            
                            int id_commercial = (int)Float.parseFloat(lstidCom.get(combo.getSelectedIndex()));
                            String contenuu = tf_contenu.getText();
                            con.setUrl("http://localhost:8000/api/reclamations/newRec/"+id+"/"+id_commercial+"/"+contenuu);
                            System.out.println("http://localhost:8000/api/reclamations/newRec/"+id+"/"+id_commercial+"/"+contenuu);
                            NetworkManager.getInstance().addToQueue(con);
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    Dialog.show("Nouvelle Reclamation", "reclamation ajouter avec succès.", "OK",null);
                                    ConnectionRequest conSMS = new ConnectionRequest();
                                    conSMS.setUrl("https://rest.nexmo.com/sms/json?api_key=7caf2eb0&api_secret=lPT3jsaSbRY0N2fO&to=21621824665&from=Souk El Mdina&text=Bonjour,Votre réclamation est envoyé avec succés.");
                                    NetworkManager.getInstance().addToQueue(conSMS);
                                    setVisible(false);
                                    new ReclamationsPage(res).show();
                                    refreshTheme();
                                }
                            });
                    });
                 Button quitter = new Button("Quitter");
                 quitter.getAllStyles().setFgColor(0);
                    add(quitter);
                    quitter.addActionListener((eq)->{
                            setVisible(false);
                            new ReclamationsPage(res).show();
                            refreshTheme();
                        });
            
            
            }
                     
        });
        
        
        
    
    }
}
