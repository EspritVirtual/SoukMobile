package souk.gui;

import com.codename1.ext.filechooser.FileChooser;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import souk.entite.Annonces;
import souk.entite.Categories;
import souk.entite.CommentairesAnc;
import souk.services.AnnoncesServices;
import souk.services.CategorieService;
import souk.services.CommentairesAncServices;
import java.io.IOException;
import souk.util.SessionUser;

/**
 *
 * @author HAYFA
 */
public class ListeAnnonces extends BaseForm {

    Container cntIndex = new Container();
    String fileName;

    public ListeAnnonces(Resources res) {
        super("Annonces", BoxLayout.y(), res);

        super.addSideMenu(res);
        Container cntlbl = new Container();

        cntlbl.getAllStyles().setPadding(Component.TOP, 100);
        add(cntlbl);
        Button ancAjouter = new Button("Ajouter");
        cntIndex.add(ancAjouter);

        ancAjouter.addActionListener((e) -> {
            cntIndex.removeAll();
            CrudAnnonce ajouter = new CrudAnnonce(res);
            ajouter.AjouterForm(res);
            ajouter.show();
            refreshTheme();
        });
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:8000/api/annonces/all");

        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                AnnoncesServices ser = new AnnoncesServices();

                List<Annonces> list = ser.getListAnnonces(new String(con.getResponseData()));
                for (Annonces lst : list) {
                    addButton(res.getImage("large.jpg"), lst.getTitre(), lst.getDateCreation(), lst.getPrix(), cntIndex, lst.getId(), res);
                }
                add(cntIndex);
                refreshTheme();
            }
        });

    }

    public ListeAnnonces(Resources res, int idannonces) {
        super("Annonces", BoxLayout.y(), res);

        super.addSideMenu(res);
        ConnectionRequest connection = new ConnectionRequest();

        connection.setUrl("http://localhost:8000/api/annonces/all");
        NetworkManager.getInstance().addToQueue(connection);
        connection.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                AnnoncesServices ser = new AnnoncesServices();

                List<Annonces> list = ser.getListAnnonces(new String(connection.getResponseData()));

                for (Annonces lst : list) {
                    if (lst.getId() == idannonces) {
                        detailForm(res.getImage("large.jpg"), lst.getTitre(), lst.getDescription(), lst.getPrix(), lst.getId(), res, "Artisanal", idannonces);

                        ListeCommentairesAnc lstCommentaire = new ListeCommentairesAnc();

                        int id = SessionUser.getInstance().getId();
                        add(lstCommentaire.ajoutCommentairesAnc(res, idannonces, id));
                        AfficheCommentairesAnc(res, idannonces);

                    }

                }

                refreshTheme();
            }
        });

    }

    private void addButton(Image img, String title, Date date, float prix, Container cntIndex, int idannonces, Resources res) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));

        image.setUIID("Label");

        Container cnt = BorderLayout.west(image);
        ///  cnt.setY(getToolbar().getHeight());
        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        TextArea txttitle = new TextArea(title);
        txttitle.setEditable(false);

        Label lbldate = new Label(new SimpleDateFormat("dd-MM-yyyy").format(date).toString());

        Button btnSupp = new Button(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "del", 3));
        btnSupp.setUIID("Label");
        btnSupp.addActionListener((e) -> {

            ConnectionRequest conn = new ConnectionRequest();
            conn.setUrl("http://localhost:8000/api/annonces/supprimer/" + idannonces);
            NetworkManager.getInstance().addToQueue(conn);
            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    Dialog.show("Supression du commentaire", "Suppression avec succès.", "OK", null);
                    new ListeAnnonces(res).show();

                }
            });

        });
        Button btnDet = new Button(iconDetail);
        btnDet.setUIID("Label");
        btnDet.addActionListener((e) -> {
           cntIndex.setVisible(false);
            ConnectionRequest connection = new ConnectionRequest();

            connection.setUrl("http://localhost:8000/api/annonces/all");
            NetworkManager.getInstance().addToQueue(connection);
            connection.addResponseListener(new ActionListener<NetworkEvent>() {

                @Override
                public void actionPerformed(NetworkEvent evt) {
                     
                    AnnoncesServices ser = new AnnoncesServices();

                    List<Annonces> list = ser.getListAnnonces(new String(connection.getResponseData()));

                    for (Annonces anc : list) {
                        if (anc.getId() == idannonces) {

                            detailForm(img, anc.getTitre(), anc.getDescription(), anc.getPrix(), anc.getId(), res, "Artisanal", idannonces);

                            ListeCommentairesAnc lstCommentaire = new ListeCommentairesAnc();

                            int id = SessionUser.getInstance().getId();
                            add(lstCommentaire.ajoutCommentairesAnc(res, idannonces, id));
                            AfficheCommentairesAnc(res, idannonces);

                         //  refreshTheme();
                        }
                    }
                }
            });

        });
        Button btnModif = new Button(FontImage.createMaterial(FontImage.MATERIAL_UPDATE, "modif", 3));
        btnModif.setUIID("Label");

        btnModif.addActionListener((e2) -> {
            ///    cntIndex.setVisible(false);
            ConnectionRequest connection = new ConnectionRequest();

            connection.setUrl("http://localhost:8000/api/annonces/all");
            NetworkManager.getInstance().addToQueue(connection);
            connection.addResponseListener(new ActionListener<NetworkEvent>() {

                @Override
                public void actionPerformed(NetworkEvent evt) {
                    AnnoncesServices ser = new AnnoncesServices();

                    List<Annonces> list = ser.getListAnnonces(new String(connection.getResponseData()));

                    for (Annonces anc : list) {
                        if (anc.getId() == idannonces) {
                            CrudAnnonce modifier = new CrudAnnonce(res);

                            modifier.ModifForm(anc.getTitre(), anc.getDescription(), anc.getPrix(), res, anc.getId());
                            modifier.show();
                            refreshTheme();

                           
                        }
                    }
                }
            });

        });

        cnt.setUIID("Container");
        Container cntbtn = new Container();
        cntbtn.add(BoxLayout.encloseX(
                btnDet, btnModif, btnSupp
        ));
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txttitle, lbldate, cntbtn
                ));
        cntIndex.add(cnt);

    }

    public void detailForm(Image img, String title, String description, float prix, int idannonces, Resources res, String categorie, int annonces) {

        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }

        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        add(LayeredLayout.encloseIn(
                sl
        ));

        Label lblPrix = new Label(String.valueOf(prix));

        lblPrix.setUIID("TextFieldBlack");
        addStringValue(categorie + "/" + title, lblPrix);
        FontImage iconoarr = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        Label lblDescription = new Label(iconoarr);

        lblDescription.setUIID("TextFieldBlack");
        addStringValue(lblDescription, description);
        FontImage iconphone = FontImage.createMaterial(FontImage.MATERIAL_PHONE, "Détail", 3);

        Label lblphone = new Label(iconphone);

        lblDescription.setUIID("TextFieldBlack");
        addStringValue(lblphone, "97541238");
        FontImage iconadresse = FontImage.createMaterial(FontImage.MATERIAL_MAP, "Détail", 3);

        Label lbladresse = new Label(iconadresse);

        lblDescription.setUIID("TextFieldBlack");
        addStringValue(lbladresse, "Soukra");

        String roles = SessionUser.getInstance().getRoles();
        int id = SessionUser.getInstance().getId();
        System.out.println("roles" + roles);
        String client = "ROLE_CLIENT";
        if (roles.toLowerCase().contains(client.toLowerCase())) {

            //  if(roles.indexOf(client)>=0 ){
            Button cmd = new Button("Commander");
            cmd.addActionListener((e) -> {

                Label lbl_date = new Label("Date :");
                Label lbl_quantite = new Label("Quantite :");
                TextField tf_date = new TextField();
                TextField tf_quantite = new TextField();
                Button btn_quitter = new Button("Quitter");
                Button btn_valider = new Button("Valider");
                tf_date.setHint("Date");
                tf_quantite.setHint("Quantite");

                Dialog dlg = new Dialog("Nouvelle commande");
                dlg.setLayout(BoxLayout.y());
                Style dlgStyle = dlg.getDialogStyle();
                dlgStyle.setBgTransparency(255);
                dlgStyle.setBgColor(0xffffff);
                Picker datePicker = new Picker();
                datePicker.setType(Display.PICKER_TYPE_DATE);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
                quitter.addActionListener((eq) -> {
                    dlg.setVisible(false);
                    new CommandesPage(res).show();
                    refreshTheme();
                });

                ok.addActionListener((eq2) -> {
                    ConnectionRequest con = new ConnectionRequest();
                    String d = datePicker.getText();
                    String qt = tf_quantite.getText();
                    con.setUrl("http://localhost:8000/api/commandes/new/" + idannonces + "/" + d + "/" + qt + "/" + id);
                    NetworkManager.getInstance().addToQueue(con);
                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {

                            Dialog.show("Nouvelle Commande", "Commande ajoutée avec succès.", "OK", null);
                            dlg.setVisible(false);

                            new CommandesPage(res).show();
                            refreshTheme();
                        }
                    });
                });
                dlg.showDialog();

            });
            add(cmd);

        }
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addStringValue(Component v, String s) {
        add(BorderLayout.east(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

///////////////////////////////////////////
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
                    ListeCommentairesAnc lstCommentaire = new ListeCommentairesAnc();

                    lstCommentaire.addBut(res.getImage("contact-b.png"), lst.getContenu(), lst.getDateCmt(), "safa", lst.getId(), res, idannonces, cntIndex);

                }
                add(cntIndex);
                refreshTheme();
            }

        });
        /// return cntGeneral;
    }

}
