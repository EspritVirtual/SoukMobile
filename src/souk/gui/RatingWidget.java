/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.components.InteractionDialog;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.io.Util;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

/**
 * Follows the status of the user within the app, after a given amount of time
 * pops up a non-intrusive dialog asking the user to rate the app. If rating is
 * low asks the user to provide feedback, if the rating is high asks the user to
 * rate in the appstore.
 *
 * @author Shai Almog
 */
public class RatingWidget {

    private static RatingWidget instance;
    private boolean running;

    private int iduser;

    public RatingWidget() {
    }

    public Container showReviewWidget(int idannonce,int iduser) {

        //Preferences.set("alreadyRated", true);
        Container f = new Container();
        Slider rate = createStarRankSlider();
        Button ok = new Button("Voter");

        f.add(rate);
        f.add(ok);

        ok.addActionListener(e -> {
      ConnectionRequest conarticle = new ConnectionRequest();
                conarticle.setUrl("http://localhost:8000/api/rating/" + iduser
                        + "/" + rate.getProgress() + "/" + idannonce );

                NetworkManager.getInstance().addToQueue(conarticle);
                conarticle.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        String str = new String(conarticle.getResponseData());
                        System.out.println(str);

                        if (str.indexOf("succes") >= 0) {
                            Dialog.show("Ajout", "Succès de vote.", "OK", null);
                           
                        } else {

                            Dialog.show("Erreur ", "Erreur  .", "OK", null);

                        }
                    }
                });
         //   System.out.println("rate.getProgress()" + rate.getProgress());

        });
        return f;
    }

    public void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    public Slider createStarRankSlider() {
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        Slider starRank = new Slider() {
            public void refreshTheme(boolean merge) {
                // special case when changing the theme while the dialog is showing
                initStarRankStyle(getSliderEmptySelectedStyle(), emptyStar);
                initStarRankStyle(getSliderEmptyUnselectedStyle(), emptyStar);
                initStarRankStyle(getSliderFullSelectedStyle(), fullStar);
                initStarRankStyle(getSliderFullUnselectedStyle(), fullStar);

            }
        };
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(10);
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }

    /**
     * This should be invoked by the stop() method as we don't want rating
     * countdown to proceed when the app isn't running
     */
//    public static void suspendRating() {
//        if (instance != null) {
//            synchronized (instance) {
//                instance.notify();
//            }
//            instance.running = false;
//            instance = null;
//        }
//    }
}
