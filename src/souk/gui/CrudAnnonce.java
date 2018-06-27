/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ext.filechooser.FileChooser;
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
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import souk.entite.Categories;
import souk.entite.CommentairesAnc;
import souk.services.CategorieService;
import souk.services.CommentairesAncServices;
import souk.util.SessionUser;

/**
 *
 * @author HAYFA
 */
public class CrudAnnonce extends BaseForm {

    String fileName;

    public CrudAnnonce(Resources res) {
        super("Annonces", BoxLayout.y(), res);
        super.addSideMenu(res);
        Container cntlbl = new Container();

        cntlbl.getAllStyles().setPadding(Component.TOP, 100);
        add(cntlbl);
    }

    public void AjouterForm(Resources res) {

        final ComboBox comboCategorie = getListCategorie();

        addStringValue("Categorie ", comboCategorie);

        TextField txtTitre = new TextField();

        txtTitre.setUIID("TextFieldBlack");
        addStringValue("Titre ", txtTitre);
        TextField txtprix = new TextField();

        txtprix.setUIID("TextFieldBlack");
        addStringValue("Prix ", txtprix);

        TextArea txtDescription = new TextArea();

        txtDescription.setUIID("TextAriaBlack");
        addStringValue("Description ", txtDescription);
        Button img = new Button("Image");

        addStringValue("Image ", img);

        ActionListener callback = e -> {
            if (e != null && e.getSource() != null) {
                String filePathe = (String) e.getSource();
                System.out.println(filePathe);
                fileName = filePathe.substring(filePathe.lastIndexOf('/') + 1, filePathe.length());

                System.out.println("fillllllle" + fileName);
                //  Now do something with this file
                MultipartRequest cr = new MultipartRequest();
                String filePath = filePathe;
                cr.setUrl("http://localhost:8000/uploads/upload.php");
                cr.setPost(true);
                String mime = "image/jpeg";
                try {
                    cr.addData("file", filePath, mime);
                    System.out.println(mime);
                } catch (IOException ex) {

                }
                cr.setFilename("file", fileName);//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
            }
        };
        img.addActionListener(e -> {
            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }
        });

        int id = SessionUser.getInstance().getId();
        Button ancAjouter = new Button("Ajouter");
        add(ancAjouter);
        ancAjouter.addActionListener(e -> {
            if (txtTitre.getText().equals("") || txtprix.getText().equals("") || txtDescription.getText().equals("") || fileName.equals("")) {
                Dialog.show("Erreur!", "Veuillez remplir tous les champs: ", "OK", null);
            } else {

                String idcat = comboCategorie.getSelectedItem().toString().substring(0, comboCategorie.getSelectedItem().toString().indexOf("-"));
                System.out.println("cate" + idcat);
                ConnectionRequest conarticle = new ConnectionRequest();
                conarticle.setUrl("http://localhost:8000/api/annonces/ajouter/" + id
                        + "/" + txtprix.getText() + "/" + idcat + "/" + txtTitre.getText() + "/" + txtDescription.getText() + "/" + fileName);

                NetworkManager.getInstance().addToQueue(conarticle);
                conarticle.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String str = new String(conarticle.getResponseData());
                        System.out.println(str);

                        if (str.indexOf("succes") >= 0) {
                            Dialog.show("Ajout", "Succès d'ajout.", "OK", null);
                            rechargerlapagedetailannonce(res);
                        } else {

                            Dialog.show("Erreur ", "Veuillez verifier vos données .", "OK", null);

                        }
                    }
                });
            }
        });
    }

    public void ModifForm(String title, String description, float prix, Resources res, int annonces) {

        final ComboBox comboCategorie = getListCategorie();

        // comboCategorie.
        /// addStringValue("Categorie ", comboCategorie);
        TextField txtprix = new TextField();

        txtprix.setUIID("TextFieldBlack");
        txtprix.setText(String.valueOf(prix));
        addStringValue("Prix ", txtprix);

        TextField txtTitre = new TextField();

        txtTitre.setUIID("TextFieldBlack");
        txtTitre.setText(title);
        addStringValue("Titre ", txtTitre);

        TextArea txtDescription = new TextArea();

        txtDescription.setUIID("TextAriaBlack");
        addStringValue("Description ", txtDescription);

        txtDescription.setText(description);

        int id = SessionUser.getInstance().getId();
        Button ancModifier = new Button("Modifier");
        add(ancModifier);
        ancModifier.addActionListener(e -> {
            if (txtTitre.getText().equals("") || txtprix.getText().equals("") || txtDescription.getText().equals("")) {
                Dialog.show("Erreur!", "Veuillez remplir tous les champs: ", "OK", null);
            } else {

                /// String idcat = comboCategorie.getSelectedItem().toString().substring(0, comboCategorie.getSelectedItem().toString().indexOf("-"));
                /// System.out.println("cate" + idcat);
                ConnectionRequest conarticle = new ConnectionRequest();
                conarticle.setUrl("http://localhost:8000/api/annonces/modifier/" + annonces
                        + "/" + txtprix.getText() + "/" + txtDescription.getText() + "/" + txtTitre.getText());

                NetworkManager.getInstance().addToQueue(conarticle);
                conarticle.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String str = new String(conarticle.getResponseData());
                        if (str.indexOf(str) >= 0) {
                            System.out.println(str);
                            Dialog.show("Modification", "Succès de modification.", "OK", null);
                            rechargerlapagedetailannonce(res);
                        } else {
                            Dialog.show("Erreur!", "Veuillez verifier vos donnée : ", "OK", null);

                        }
                    }
                });
            }

        });

    }

    private final ComboBox getListCategorie() {
        ComboBox comboCategorie = new ComboBox();
        Map<Double, String> catById = new HashMap<>();

        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://localhost:8000/api/categorie/all");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                CategorieService ser = new CategorieService();

                List<Categories> Categories = ser.getListCategorie(new String(con.getResponseData()));

//                for (Categories lst : list) {
//
//                    comboCategorie.addItem(lst.getDesignation());
//                }
                for (Categories categ : Categories) {
                    catById.put(Double.parseDouble(String.valueOf(categ.getId())), categ.getDesignation());
                }
                for (Map.Entry<Double, String> cleval : catById.entrySet()) {
                    String cle = cleval.getKey().toString().substring(0, cleval.getKey().toString().indexOf("."));
                    comboCategorie.addItem(cle + "-" + cleval.getValue());
                }
            }
        });

        return comboCategorie;
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

    private void rechargerlapagedetailannonce(Resources res) {
        ListeAnnonces Fannonce = new ListeAnnonces(res);
        Fannonce.show();

    }
}
