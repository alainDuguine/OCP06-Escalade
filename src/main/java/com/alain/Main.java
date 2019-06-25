package com.alain;

import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.UtilisateurDaoImpl;

public class Main {

    public static void main(String[] args) {
        UtilisateurDaoImpl dao = new UtilisateurDaoImpl();

        Utilisateur user1 = new Utilisateur("alain_duguine@hotmail.fr","pouet","Duguine","Alain","admin");
        Utilisateur user2 = new Utilisateur("Jean_julien@hotmail.fr","pouet","Jean","Julien","user");

        System.out.println(dao.save(user1).toString());
        System.out.println(dao.save(user2).toString());
    }
}
