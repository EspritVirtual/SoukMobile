package souk.gui;

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
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import souk.entite.Annonces;
import souk.services.AnnoncesServices;

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
        con.setUrl("http://localhost:8000/api/annonces/allAnnonces");
        NetworkManager.getInstance().addToQueue(con);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                AnnoncesServices ser = new AnnoncesServices();
                Container cntIndex = new Container();
                List<Annonces> list = ser.getListAnnonces(new String(con.getResponseData()));
                for (Annonces lst : list) {
                    addButton(res.getImage("large.jpg"), lst.getTitre(), lst.getDateCreation(), lst.getPrix(), cntIndex);
                }
                add(cntIndex);
                refreshTheme();
            }
        });
        
    }
    
    private void addButton(Image img, String title, Date date, float prix, Container cntIndex) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        
        image.setUIID("Label");
        
        Container cnt = BorderLayout.west(image);
        cnt.setY(getToolbar().getHeight());
        FontImage iconDetail = FontImage.createMaterial(FontImage.MATERIAL_ARROW_FORWARD, "Détail", 3);
        TextArea txttitle = new TextArea(title);
        txttitle.setEditable(false);
        System.out.println(date);
        Label lbldate = new Label(new SimpleDateFormat("dd-MM-yyyy").format(date).toString());
        
        Button btnDet = new Button(iconDetail);
        btnDet.setUIID("Label");
          btnDet.addActionListener((e)->{
               cntIndex.setVisible(false);

            });
        cnt.setUIID("Container");
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        txttitle, lbldate, btnDet
                ));
        cntIndex.add(cnt);
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);
        
        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
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
                                        FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );
        
        swipe.addTab("", page1);
        
    }
    
}
