/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.List;
import souk.services.AnnoncesServices;

/**
 *
 * @author HAYFA
 */
public class Annonces  extends BaseForm {

    private Container annoncesContainer = new Container(new BorderLayout());
    private MultiButton gui_Multi_Button_1 = new MultiButton();
    private MultiButton gui_LA = new MultiButton();
    private Container gui_imageContainer1 = new Container(new BorderLayout());
    private Container gui_Container_2 = new Container(new BorderLayout());
    private TextArea gui_Text_Area_1 = new TextArea();
    private Button gui_Button_1 = new Button();
    private Label gui_separator1 = new Label();
    private Container gui_null_1_1 = new Container(new BorderLayout());
    private MultiButton gui_null_1_1_1 = new MultiButton();
    private MultiButton gui_newYork = new MultiButton();
    private Container gui_imageContainer2 = new Container(new BorderLayout());
    private Container gui_Container_3 = new Container(new BorderLayout());
    private TextArea gui_Text_Area_2 = new TextArea();
    private Button gui_Button_2 = new Button();
    private Label gui_Label_1_1_1 = new Label();

    public Annonces(Form f ,Resources res) {
        super("Annonces", BoxLayout.y(), res);

        super.addSideMenu(res);
       
//        gui_separator1.setShowEvenIfBlank(true);
//        gui_Label_1_1_1.setShowEvenIfBlank(true);

//        ScaleImageLabel sl = new ScaleImageLabel(res.getImage("skate-park.jpg"));
//        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        gui_imageContainer1.add(BorderLayout.CENTER, sl);
//        sl = new ScaleImageLabel(res.getImage("bridge.jpg"));
//        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        gui_imageContainer2.add(BorderLayout.CENTER, sl);

//        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {
//        });
//
//        FontImage.setMaterialIcon(gui_LA, FontImage.MATERIAL_LOCATION_ON);
//        gui_LA.setIconPosition(BorderLayout.EAST);
//
//        FontImage.setMaterialIcon(gui_newYork, FontImage.MATERIAL_LOCATION_ON);
//        gui_newYork.setIconPosition(BorderLayout.EAST);

        gui_Text_Area_2.setRows(2);
        gui_Text_Area_2.setColumns(100);
        gui_Text_Area_2.setGrowByContent(false);
        gui_Text_Area_2.setEditable(false);
        gui_Text_Area_1.setRows(2);
        gui_Text_Area_1.setColumns(100);
        gui_Text_Area_1.setGrowByContent(false);
        gui_Text_Area_1.setEditable(false);
        
        
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost:8000/api/annonces/allAnnonces");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                AnnoncesServices ser = new AnnoncesServices();
                List<souk.entite.Annonces> list = ser.getListAnnonces(new String(con.getResponseData()));
                 for(souk.entite.Annonces lst : list)
                 {
                AddAnnonces(res,res.getImage("bridge.jpg"), lst.getTitre(), lst.getDateCreation().toString(), lst.getPrix());}

            }
        });
    }
   private void AddAnnonces(Resources res,Image img,String titre, String date , float prix  ) {
//        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//        setTitle("Annonces");
//        setName("Annonces");
        addComponent(annoncesContainer);
        annoncesContainer.setName("Container_1");
        annoncesContainer.addComponent(BorderLayout.CENTER, gui_Multi_Button_1);
        annoncesContainer.addComponent(BorderLayout.EAST, gui_LA);
        gui_Multi_Button_1.setUIID("Label");
        gui_Multi_Button_1.setName("Multi_Button_1");
        gui_Multi_Button_1.setIcon(res.getImage("contact-c.png"));
        gui_Multi_Button_1.setPropertyValue("line1", "Ami Koehler");
        gui_Multi_Button_1.setPropertyValue("line2", "@dropperidiot");
        gui_Multi_Button_1.setPropertyValue("uiid1", "Label");
        gui_Multi_Button_1.setPropertyValue("uiid2", "RedLabel");
        gui_LA.setUIID("Label");
        gui_LA.setName("LA");
        gui_LA.setPropertyValue("line1", date);
        gui_LA.setPropertyValue("line2", "in Los Angeles");
        gui_LA.setPropertyValue("uiid1", "SlightlySmallerFontLabel");
        gui_LA.setPropertyValue("uiid2", "RedLabelRight");
        
            
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        gui_imageContainer1.add(BorderLayout.CENTER, sl);
        addComponent(gui_imageContainer1);
        gui_imageContainer1.setName("imageContainer1");
        gui_imageContainer1.addComponent(BorderLayout.SOUTH, gui_Container_2);
        gui_Container_2.setName("Container_2");
        gui_Container_2.addComponent(BorderLayout.CENTER, gui_Text_Area_1);
        gui_Container_2.addComponent(BorderLayout.EAST, gui_Button_1);
//        gui_Text_Area_1.setText("The park is a favorite among skaters in California and it definitely deserves it. The park is complete with plenty of smooth banks to gain a ton of speed in the flow bowl.");
//        gui_Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
//        gui_Text_Area_1.setName("Text_Area_1");
        gui_Button_1.setText("");
        gui_Button_1.setUIID("Label");
        gui_Button_1.setName("Button_1");
        FontImage.setMaterialIcon(gui_Button_1, "".charAt(0));
      
        addComponent(gui_separator1);
        addComponent(gui_null_1_1);
       
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initGuiBuilderComponents(Resources res ) {
//        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//        setTitle("Annonces");
//        setName("Annonces");
//        addComponent(annoncesContainer);
//        annoncesContainer.setName("Container_1");
//        annoncesContainer.addComponent(BorderLayout.CENTER, gui_Multi_Button_1);
//        annoncesContainer.addComponent(BorderLayout.EAST, gui_LA);
//        gui_Multi_Button_1.setUIID("Label");
//        gui_Multi_Button_1.setName("Multi_Button_1");
//        gui_Multi_Button_1.setIcon(res.getImage("contact-c.png"));
//        gui_Multi_Button_1.setPropertyValue("line1", "Ami Koehler");
//        gui_Multi_Button_1.setPropertyValue("line2", "@dropperidiot");
//        gui_Multi_Button_1.setPropertyValue("uiid1", "Label");
//        gui_Multi_Button_1.setPropertyValue("uiid2", "RedLabel");
//        gui_LA.setUIID("Label");
//        gui_LA.setName("LA");
//        gui_LA.setPropertyValue("line1", "3 minutes ago");
//        gui_LA.setPropertyValue("line2", "in Los Angeles");
//        gui_LA.setPropertyValue("uiid1", "SlightlySmallerFontLabel");
//        gui_LA.setPropertyValue("uiid2", "RedLabelRight");
//        addComponent(gui_imageContainer1);
//        gui_imageContainer1.setName("imageContainer1");
//        gui_imageContainer1.addComponent(BorderLayout.SOUTH, gui_Container_2);
//        gui_Container_2.setName("Container_2");
//        gui_Container_2.addComponent(BorderLayout.CENTER, gui_Text_Area_1);
//        gui_Container_2.addComponent(BorderLayout.EAST, gui_Button_1);
//        gui_Text_Area_1.setText("The park is a favorite among skaters in California and it definitely deserves it. The park is complete with plenty of smooth banks to gain a ton of speed in the flow bowl.");
//        gui_Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
//        gui_Text_Area_1.setName("Text_Area_1");
//        gui_Button_1.setText("");
//        gui_Button_1.setUIID("Label");
//        gui_Button_1.setName("Button_1");
//        com.codename1.ui.FontImage.setMaterialIcon(gui_Button_1, "".charAt(0));
//      
//        addComponent(gui_separator1);
//        addComponent(gui_null_1_1);
//       
   // }// </editor-fold>

}
