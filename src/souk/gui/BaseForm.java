/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import souk.util.SessionUser;

/**
 *
 * @author HAYFA
 */
public class BaseForm extends Form {

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }


    public BaseForm(String title, Layout contentPaneLayout, Resources res) {
        super(title, contentPaneLayout);
        //addSideMenu(res);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        Image img = res.getImage("decorative-mandala-on-a-watercolor-background_1048-5430.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        getToolbar().addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                        new Label(res.getImage("logoo.png"), "PictureWhiteBackgrond"))
        ));

        if (SessionUser.getInstance().getId() != 0) {
            getToolbar().addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_ASSIGNMENT, e -> new ProfilForm(res).show());

            getToolbar().addMaterialCommandToSideMenu("Annonces", FontImage.MATERIAL_ASSIGNMENT, e -> new ListeAnnonces(res).show());
            getToolbar().addMaterialCommandToSideMenu("Commandes", FontImage.MATERIAL_ASSIGNMENT, e -> new CommandesPage(res).show());
            getToolbar().addMaterialCommandToSideMenu("Réclamations", FontImage.MATERIAL_ASSIGNMENT, e -> new ReclamationsPage(res).show());
            getToolbar().addMaterialCommandToSideMenu("Evénements", FontImage.MATERIAL_ASSIGNMENT, e -> new ListeEvenements(res).show());

<<<<<<< HEAD
        } else {

=======
            String rs = SessionUser.getInstance().getRoles();

            String CL = "ROLE_CLIENT";
            String roles = String.valueOf(rs);
            if (roles.indexOf(CL)<=0) {
                System.out.println("roles" + rs);
                getToolbar().addMaterialCommandToSideMenu("Abonnements", FontImage.MATERIAL_ASSIGNMENT, e -> new AbonnementsPage(res).show());

            }

        } else {

>>>>>>> 8aaab745e7f59bf126e611ee84e5021b53f52e0f
            getToolbar().addMaterialCommandToSideMenu("Connexion", FontImage.MATERIAL_ASSIGNMENT, e -> new LoginForm(res).show());

        }

    }

}
