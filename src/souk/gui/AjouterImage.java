/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.gui;

import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.components.InfiniteProgress;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author HAYFA
 */
public class AjouterImage extends BaseForm {
    String fileName;

    public AjouterImage(Resources res,int id) {
        super("Image", BoxLayout.y(), res);

        super.addSideMenu(res);
         Container cntlbl = new Container();

        cntlbl.getAllStyles().setPadding(Component.TOP, 100);
        add(cntlbl);
        Button img = new Button("Image");

        addStringValue("Image ", img);

        ActionListener callback = e -> {
            if (e != null && e.getSource() != null) {
                String filePathe = (String) e.getSource();
                System.out.println(filePathe);
                fileName = filePathe.substring(filePathe.lastIndexOf('/') + 1, filePathe.length());;
                System.out.println(fileName);
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

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
