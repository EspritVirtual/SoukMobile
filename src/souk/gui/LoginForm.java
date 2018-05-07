/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.FloatingHint;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.TextField;
import com.codename1.ui.Container;
import com.codename1.ui.Toolbar;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

import souk.entite.User;
import souk.services.UserServices;
import souk.util.SessionUser;

/**
 *
 * @author HAYFA
 */
public class LoginForm extends BaseForm {

    private TextField txtlogin;
    private TextField txtPassword;

    public LoginForm(Resources theme) {
        super(new BorderLayout());
        initGuiBuilderComponents(theme);
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        FontImage mat = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "SigninTitle", 3.5f);
        getToolbar().addCommandToLeftBar("", mat, e -> new HomePage(Resources.getGlobalResources()).show());
        getContentPane().setUIID("SignInForm");
    }

    private void initGuiBuilderComponents(Resources res) {
        ///  super(new BorderLayout());

        if (!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout) getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        setUIID("SignIn");

       // add(BorderLayout.NORTH, new Label(res.getImage("Login-arrow-outline.png"), "LogoLabel"));
        txtlogin = new TextField("", "Username", 20, TextField.ANY);
        txtPassword = new TextField("", "Password", 20, TextField.PASSWORD);
        txtlogin.setSingleLineTextArea(false);
        txtPassword.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");

        Container content = BoxLayout.encloseY(
                new FloatingHint(txtlogin),
                createLineSeparator(0x660066),
                new FloatingHint(txtPassword),
                createLineSeparator(0x660066),
                signIn
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();

        signIn.addActionListener((e) -> {
            UserServices ser = new UserServices();
            ConnectionRequest con = new ConnectionRequest();
            String Url = "http://localhost:8000/app_dev.php/api/user/" + getTxtlogin().getText() + "/" + getTxtPassword().getText();
            System.out.println("" + Url);
            con.setUrl(Url);
            NetworkManager.getInstance().addToQueue(con);
            con.addResponseListener(new ActionListener<NetworkEvent>() {

                @Override
                public void actionPerformed(NetworkEvent evt) {
                    UserServices ser = new UserServices();

                    User u = ser.getUserConnecte(new String(con.getResponseData()));
                    SessionUser.setInstance(u);
                    new ListeAnnonces(res).show();
                }
            });

        });
    }

    public TextField getTxtlogin() {
        return txtlogin;
    }

    public void setTxtlogin(TextField txtlogin) {
        this.txtlogin = txtlogin;
    }

    public TextField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(TextField txtPassword) {
        this.txtPassword = txtPassword;
    }

}
