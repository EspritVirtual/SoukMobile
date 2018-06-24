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

import java.util.Date;
import java.util.List;

import souk.entite.Annonces;
import souk.services.AnnoncesServices;
import souk.util.SessionUser;

/**
 *
 * @author HAYFA
 */
public class ListeAnnonces extends BaseForm {

    public ListeAnnonces(Resources res) {
        super("Annonces", BoxLayout.y(), res);

        super.addSideMenu(res);
        Container cntlbl = new Container();

        cntlbl.getAllStyles().setPadding(Component.TOP, 100);
        add(cntlbl);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:8000/api/annonces/all");

        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                AnnoncesServices ser = new AnnoncesServices();
                Container cntIndex = new Container();
                List<Annonces> list = ser.getListAnnonces(new String(con.getResponseData()));
                for (Annonces lst : list) {
                    addButton(res.getImage("large.jpg"), lst.getTitre(), lst.getDateCreation(), lst.getPrix(), cntIndex, lst.getId(), res);
                }
                add(cntIndex);
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
        cnt.setY(getToolbar().getHeight());
        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        TextArea txttitle = new TextArea(title);
        txttitle.setEditable(false);

        Label lbldate = new Label(new SimpleDateFormat("dd-MM-yyyy").format(date).toString());

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

                    for (Annonces lst : list) {
                        if (lst.getId() == idannonces) {
                            detailForm(img, lst.getTitre(), lst.getDescription(), lst.getPrix(), lst.getId(), res, "Artisanal", idannonces);

                            ListeCommentairesAnc lstCommentaire = new ListeCommentairesAnc(res);

                            int id = SessionUser.getInstance().getId();
                            add(lstCommentaire.ajoutCommentairesAnc(res, idannonces, id));
                            add(lstCommentaire.AfficheCommentairesAnc(res, idannonces));

                        }

                    }

                    refreshTheme();
                }
            });

        });
        cnt.setUIID("Container");
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txttitle, lbldate, btnDet
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


}
