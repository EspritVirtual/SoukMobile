/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.util;

    
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;

/**
 *
 * @author haifa
 */
public class UploadFile {
    
    private static String newName = null;
    
    public static String uploadImage(String filePath, String oldName) throws Exception {
        try {
            ConnectionRequest connReq = new ConnectionRequest();
            connReq.setPost(true);
            connReq.setContentType("application/json");
            connReq.setUrl("http://localhost/SoukLemdina/web/social/uniqWS");
            connReq.addResponseListener((e) -> {
                UploadFile.newName = new String(connReq.getResponseData());
                UploadFile.newName = UploadFile.newName.substring(1, UploadFile.newName.length()-1);
                System.out.println(UploadFile.newName);
            });
            NetworkManager.getInstance().addToQueueAndWait(connReq);
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
        String url;
        if (oldName != null) {
            url = "http://localhost/SoukLemdina/uploadImagesMob.php?old=" + oldName;
        } else {
            url = "http://localhost/SoukLemdina/uploadImagesMob.php";
        }
        
        MultipartRequest cr = new MultipartRequest();
        cr.setUrl(url);
        cr.setPost(true);
        String mime = "image/jpeg";
        cr.addData("file", filePath, mime);
        cr.setFilename("file", UploadFile.newName);//any unique name you want
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        cr.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return UploadFile.newName;
        
    }
}

