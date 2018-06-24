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
public class ListeCommentairesAnc extends Form {

    public void AfficheCommentairesAnc(Resources res, int idannonces) {

        ConnectionRequest connectionc = new ConnectionRequest();

        connectionc.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/" + idannonces);

        NetworkManager.getInstance().addToQueue(connectionc);
        //connectionc.setPost(false);
        connectionc.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                Container cntIndex = new Container();

                CommentairesAncServices ser = new CommentairesAncServices();
                List<CommentairesAnc> list = ser.getListCommentairesAnc(new String(connectionc.getResponseData()));
                // Container cnt = new Container();
                for (CommentairesAnc lst : list) {

                    addBut(res.getImage("contact-b.png"), lst.getContenu(), lst.getDateCmt(), "safa", lst.getId(), res, idannonces, cntIndex);

                }
                add(cntIndex);
                refreshTheme();
            }

        });
        /// return cntGeneral;
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
                    rechargerlapagedetailannonce(res, idannonces);

                }
            });
        });
        return cnt;
    }

    public void addBut(Image img, String contenu, Date dateCmt, String username, int idComAnc, Resources res, int idannonces, Container cntIndex) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));

        image.setUIID("Label");

        Container cnt = BorderLayout.west(image);
///        cnt.setY(getToolbar().getHeight());
        TextArea txtcontenu = new TextArea(contenu);
        txtcontenu.setEditable(true);

        Label lbldate = new Label(new SimpleDateFormat("dd-MM-yyyy").format(dateCmt).toString());

        Button btnSupp = new Button(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "del", 3));
        btnSupp.setUIID("Label");
        btnSupp.addActionListener((e) -> {
            System.out.println("id commm" + idComAnc);
            ConnectionRequest conn = new ConnectionRequest();
            conn.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/delete/" + idComAnc);
            NetworkManager.getInstance().addToQueue(conn);
            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    Dialog.show("Supression du commentaire", "Suppression avec succès.", "OK", null);
                    rechargerlapagedetailannonce(res, idannonces);

                }
            });

        });
        Button btnModif = new Button(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, "modif", 3));
        btnModif.setUIID("Label");
        btnModif.addActionListener((e2) -> {
            System.out.println("btn" + idComAnc);
            ConnectionRequest con = new ConnectionRequest();
            String d = txtcontenu.getText();
            con.setUrl("http://localhost:8000/api/commentaire/commentaireAnc/update/" + idComAnc + "/" + txtcontenu.getText());
            NetworkManager.getInstance().addToQueue(con);
            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    //  new ListeCommentairesAnc().show();
                    refreshTheme();

                    Dialog.show("Modification du commentaire", "Modification avec succès.", "OK", null);
                    rechargerlapagedetailannonce(res, idannonces);
                }
            });

        });

        cnt.setUIID("Container");
        Container cntbtn = new Container();
        cntbtn.add(BoxLayout.encloseX(btnModif, btnSupp));
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txtcontenu, lbldate, cntbtn
                ));
        cntIndex.add(cnt);

    }

    private void rechargerlapagedetailannonce(Resources res, int idannonces) {
        ListeAnnonces Fannonce = new ListeAnnonces(res,idannonces);
                  Fannonce.show();
        

    }

}
