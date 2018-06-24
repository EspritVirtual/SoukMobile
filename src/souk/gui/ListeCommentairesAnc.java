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

import com.codename1.ui.Container;
import com.codename1.ui.Dialog;

import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;

import com.codename1.ui.util.Resources;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import souk.services.CommentairesAncServices;
import souk.entite.CommentairesAnc;
import souk.services.AnnoncesServices;
import souk.util.SessionUser;

/**
 *
 * @author Boufares
 */
public class ListeCommentairesAnc extends BaseForm {

 public ListeCommentairesAnc(Resources res) {
        super("", BoxLayout.y(), res);
 }
    
    public Container AfficheCommentairesAnc(Resources res, int idannonces) {
        //   super.addSideMenu(res);
        Container cntGeneral = new Container(BoxLayout.y());
        ConnectionRequest connectionc = new ConnectionRequest();

        connectionc.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/" + idannonces);

        NetworkManager.getInstance().addToQueue(connectionc);
        connectionc.setPost(false);
        connectionc.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                CommentairesAncServices ser = new CommentairesAncServices();
                List<CommentairesAnc> list = ser.getListCommentairesAnc(new String(connectionc.getResponseData()));
               // Container cnt = new Container();
                for (CommentairesAnc lst : list) {

                    cntGeneral.add(addButton(res.getImage("contact-b.png"), lst.getContenu(), lst.getDateCmt(), "safa", lst.getId(), res, idannonces));

                }
//refreshTheme();
            }

        });
        return cntGeneral;
    }

    public Container ajoutCommentairesAnc(Resources res, int idannonces, int client) {
        //   super.addSideMenu(res);
        Container cnt = new Container(BoxLayout.y());
        TextArea comAnc = new TextArea();
        Button btnComAnc = new Button("Commenter");
        cnt.add(comAnc);
        cnt.add(btnComAnc);

        btnComAnc.addActionListener((e) -> {
            ConnectionRequest con = new ConnectionRequest();

            con.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/new/" + idannonces + "/" + comAnc.getText() + "/" + client);
            NetworkManager.getInstance().addToQueue(con);
            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    refreshTheme();
                }
            });
        });
        return cnt;
    }

    private Container addButton(Image img, String contenu, Date dateCmt, String username, int idComAnc, Resources res, int idannonces) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
         Container cnt1 = BorderLayout.west(image);
        cnt1.setLeadComponent(image);
        TextArea txtcontenu = new TextArea(contenu);
       txtcontenu.setUIID("NewsTopLine");
        txtcontenu.setEditable(false);
          Label txtdate = new Label(new com.codename1.l10n.SimpleDateFormat("dd-MM-yyyy").format(dateCmt).toString());

     
        System.out.println("id commm" + idComAnc);

        Button btnSupp = new Button( FontImage.createMaterial(FontImage.MATERIAL_DELETE, "del", 3));
        btnSupp.setUIID("Label");
        btnSupp.addActionListener((e) -> {
            System.out.println("id commm" + idComAnc);
            ConnectionRequest conn = new ConnectionRequest();
            conn.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/delete/" + idComAnc);
            NetworkManager.getInstance().addToQueue(conn);
            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    refreshTheme();

                }
            });

        });

        Button btnModif = new Button(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, "modif", 3));
        btnModif.setUIID("Label");
       btnModif.addActionListener((e2) -> {
            TextField tf_contenu = new TextField();
            Button btn_quitter = new Button("Quitter");
            Button btn_valider = new Button("Valider");
            tf_contenu.setText(txtcontenu.getText());
            Dialog dlg = new Dialog("Modification");
            dlg.setLayout(BoxLayout.y());
            Style dlgStyle = dlg.getDialogStyle();
            dlgStyle.setBgTransparency(255);
            dlgStyle.setBgColor(0xffffff);
            dlg.add(contenu);
            Button ok = new Button("Valider");
            ok.getAllStyles().setFgColor(0);
            dlg.add(ok);
            Button quitter = new Button("Quitter");
            quitter.getAllStyles().setFgColor(0);
            dlg.add(quitter);
            quitter.addActionListener((eq) -> {
                dlg.setVisible(false);
                ListeAnnonces Fannonce = new ListeAnnonces(res);
                ConnectionRequest connection = new ConnectionRequest();

                connection.setUrl("http://localhost:8000/api/annonces/all");
                NetworkManager.getInstance().addToQueue(connection);
                connection.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        AnnoncesServices ser = new AnnoncesServices();

                        List<souk.entite.Annonces> list = ser.getListAnnonces(new String(connection.getResponseData()));

                        for (souk.entite.Annonces lst : list) {
                            if (lst.getId() == idannonces) {
                                Fannonce.detailForm(img, lst.getTitre(), lst.getDescription(), lst.getPrix(), lst.getId(), res, "Artisanal", idannonces);

                                int id = SessionUser.getInstance().getId();
                                add(ajoutCommentairesAnc(res, idannonces, id));
                                add(AfficheCommentairesAnc(res, idannonces));

                            }

                        }

                        refreshTheme();
                    }
                });

            });

            ok.addActionListener((eq2) -> {
                System.out.println("ghere");
                ConnectionRequest con = new ConnectionRequest();
                String d = txtcontenu.getText();
                con.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/update/" + idComAnc + contenu);
                NetworkManager.getInstance().addToQueue(con);
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                      //  new ListeCommentairesAnc().show();
                        refreshTheme();
                        Dialog.show("Modification du commentaire", "Modification avec succès.", "OK", null);

                    }
                });

            });
        }
        );

        //  s.setFgColor(0xf f2d55b
        Container cntbtn = new Container();
        cntbtn.add(BoxLayout.encloseX(btnModif, btnSupp));
        cnt1.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txtcontenu,txtdate,cntbtn
                       
                ));
         cnt1.setUIID("Container");
       return cnt1;
    }

}
