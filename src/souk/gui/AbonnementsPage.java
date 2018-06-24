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
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import java.util.List;
import souk.entite.Abonnements;
import souk.services.AbonnementsServices;
import souk.util.SessionUser;

/**
 *
 * @author ASUS PC
 */
public class AbonnementsPage extends BaseForm {

    Form F1, F2;

    public AbonnementsPage(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public AbonnementsPage(Resources res) {

        super("Abonnements", BoxLayout.y(), res);

        super.addSideMenu(res);
        Container cntlbl = new Container();
        cntlbl.getAllStyles().setPadding(Component.TOP, 80);
        add(cntlbl);
        int id = SessionUser.getInstance().getId();

        ConnectionRequest con = new ConnectionRequest();

        Label label = new Label("Mes abonnements");
        add(label);
        getToolbar().addCommandToOverflowMenu("Ajouter", null, (ev) -> {
            AjouterForm(res);
        });
        con.setUrl("http://localhost:8000/api/abonnements/liste");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                AbonnementsServices ser = new AbonnementsServices();
                List<Abonnements> list = ser.getListAbonnements(new String(con.getResponseData()));
                for (Abonnements lst : list) {
                    int id = lst.getId();
                    String desg = lst.getDesignation();
                    String desc = lst.getDescription();
                    float prix = lst.getPrix();
                    int nbMois = lst.getNbMois();

                    addButton(res.getImage("commande.png"), id, desg, desc, prix, nbMois, res);

                }

                refreshTheme();
            }
        });
    }

    private void addButton(Image img, int id_ab, String desg, String desc, float prix, int nb, Resources res) {
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
        Button edit = new Button("Modifier");

        delete.addActionListener((e) -> {
            ConnectionRequest connection = new ConnectionRequest();
            connection.setUrl("http://localhost:8000/api/abonnements/supprimer/" + id_ab);
            NetworkManager.getInstance().addToQueue(connection);
            connection.addResponseListener(new ActionListener<NetworkEvent>() {

                @Override
                public void actionPerformed(NetworkEvent evt) {
                    new AbonnementsPage(res).show();
                    refreshTheme();
                    Dialog.show("Suppression Abonnement", "Abonnement supprimé avec succès.", "OK", null);
                }
            });

        });
       
        edit.addActionListener((eq2) -> {
            cnt.removeAll();
            System.out.println("souk.gui.AbonnementsPage.addButton()" + id_ab);
            ModifierForm(res, id_ab, desg, desc, prix, nb);
            refreshTheme();
        });

        cnt.setUIID("Container");
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txtdesg, txtdesc, txtprix, txtnb, delete, edit
                ));
        add(cnt);
    }

    private void ModifierForm(Resources res, int id, String desgination, String description, float prix, int nbMois) {

        TextField txt_desg = new TextField();
        TextField txt_desc = new TextField();
        TextField txt_prix = new TextField();
        TextField txt_nbmois = new TextField();

        txt_desg.setUIID("TextFieldBlack");
        addStringValue("Designation ", txt_desg);
        txt_desg.setText(desgination);

        txt_desc.setUIID("TextFieldBlack");
        addStringValue("Description  ", txt_desc);
        txt_desc.setText(description);

        txt_prix.setUIID("TextFieldBlack");
        addStringValue("Prix  ", txt_prix);
        txt_prix.setText(String.valueOf(prix));

        txt_nbmois.setUIID("TextFieldBlack");
        addStringValue("Nombre de mois ", txt_nbmois);
        txt_nbmois.setText(String.valueOf(nbMois));

        Button AbModifier = new Button("Modifier");
        add(AbModifier);
        AbModifier.addActionListener((e) -> {

            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost:8000/api/abonnements/modifier/" + id + "/" + txt_desg.getText() + "/" + txt_desc.getText() + "/"
                    + txt_prix.getText() + "/" + txt_nbmois.getText());
            NetworkManager.getInstance().addToQueue(con);
            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    Dialog.show("Abonnement modifié", "Abonnement modifié avec succès.", "OK", null);
                    new AbonnementsPage(res).show();
                    refreshTheme();
                }
            });
        });

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void AjouterForm(Resources res) {

        TextField txt_desg = new TextField();
        TextField txt_desc = new TextField();
        TextField txt_prix = new TextField();
        TextField txt_nbmois = new TextField();

        txt_desg.setUIID("TextFieldBlack");
        addStringValue("Designation :", txt_desg);

        txt_desc.setUIID("TextFieldBlack");
        addStringValue("Description :", txt_desc);

        txt_prix.setUIID("TextFieldBlack");
        addStringValue("Prix  :", txt_prix);

        txt_nbmois.setUIID("TextFieldBlack");
        addStringValue("Nombre de mois ", txt_nbmois);

        Button AbAjouter = new Button("Ajouter");
        add(AbAjouter);
        AbAjouter.addActionListener((e) -> {

            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost:8000/api/abonnements/new/" + txt_desg.getText() + "/" + txt_desc.getText() + "/"
                    + txt_prix.getText() + "/" + txt_nbmois.getText());
            NetworkManager.getInstance().addToQueue(con);
            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {

                    Dialog.show("Abonnement ajouté", "Abonnement ajouté avec succès.", "OK", null);
                    new AbonnementsPage(res).show();
                    refreshTheme();
                }
            });
        });

    }
}
