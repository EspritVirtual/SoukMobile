/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;

import com.codename1.ui.util.Resources;
import java.util.List;
import souk.services.AnnoncesServices;
import souk.util.SessionUser;

/**
 *
 * @author HAYFA
 */
public class HomePage extends BaseForm {

    public HomePage(Resources res) {

        super("Accueil", BoxLayout.y(), res);

        super.addSideMenu(res);
        getToolbar().setUIID("Container");

        getContentPane().setUIID("SignInForm");
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("large.jpg"), spacer1, " ", "", "Souk el Medina ");
        addTab(swipe, res.getImage("large2.jpg"), spacer2, "", "", "Bienvenu");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        getToolbar().setUIID(swipe.getUIID());
        // getToolbar().setTitleComponent(LayeredLayout.encloseIn(swipe, radioContainer));
    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }

        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        // FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );
        swipe.addTab("", page1);
    }
//    private Container annoncesContainer = new Container(new BorderLayout());
//    private MultiButton gui_Multi_Button_1 = new MultiButton();
//    private MultiButton gui_LA = new MultiButton();
//    private Container gui_imageContainer1 = new Container(new BorderLayout());
//    private Container gui_Container_2 = new Container(new BorderLayout());
//    private TextArea gui_Text_Area_1 = new TextArea();
//    private Button gui_Button_1 = new Button();
//    private Label gui_separator1 = new Label();
//    private Container gui_null_1_1 = new Container(new BorderLayout());
//    private MultiButton gui_null_1_1_1 = new MultiButton();
//    private MultiButton gui_newYork = new MultiButton();
//    private Container gui_imageContainer2 = new Container(new BorderLayout());
//    private Container gui_Container_3 = new Container(new BorderLayout());
//    private TextArea gui_Text_Area_2 = new TextArea();
//    private Button gui_Button_2 = new Button();
//    private Label gui_Label_1_1_1 = new Label();
//
//    private void AddAnnonces(Resources res, Image img, String titre, String date, float prix) {
//
//      
//    }// </editor-fold>

}
