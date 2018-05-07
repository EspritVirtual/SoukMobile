/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package souk.util;

import souk.entite.User;
import souk.services.UserServices;

/**
 *
 * @author asus
 */
public class SessionUser {

    /**
     * Constructeur privé
     */
    public SessionUser() {
    }

    /**
     * Instance unique non préinitialisée
     */
    private static User INSTANCE = null;

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static  User getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new User();
        }
        return INSTANCE;
    }
        public static  void  setInstance(User u) {
       
            INSTANCE = new User();
        }
       
    
    }
