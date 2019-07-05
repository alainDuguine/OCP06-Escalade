package com.alain.metier;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Entitie;
import com.alain.dao.entities.Utilisateur;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;



public class CheckForm {

//    public static boolean checkAndSave(HttpServletRequest req, String className, String daoImpl){
    public static Map<String,String> checkAndSave(HttpServletRequest req, String className, EntityRepository dao){
        Class classBean = null;
        Map<String,String> listErreur = null;
        try {
            classBean = Class.forName(className);
            Entitie bean = (Entitie) classBean.newInstance();
//          On passe le dao en paramètre, sinon il reconstruit la base de données à chaque fois
//            classDaoImpl = Class.forName(daoImpl);
//            EntityRepository dao = (EntityRepository) classDaoImpl.newInstance();
            bean.hydrate(req);
            listErreur = bean.checkErreurs(dao);
            if (listErreur.isEmpty()) {
                dao.save(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listErreur;
    }

    public static Map<String,String> checkConnect(HttpServletRequest req, EntityRepository dao){
       Utilisateur user = (Utilisateur) dao.findByEmail(req.getParameter("email"));
       String password = req.getParameter("password");
       Map<String,String> listErreur = new HashMap<>();
       if (user == null){
           listErreur.put(Utilities.CHAMP_EMAIL,"Cet email n'existe pas dans notre base");
       }else if (!user.checkPassword(password)){
           listErreur.put(Utilities.CHAMP_PASS,"Le mot de passe et l'email ne correspondent pas");
       }
       return listErreur;
    }
}
