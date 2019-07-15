package com.alain.metier;


import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Entitie;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.UtilisateurDaoImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class CheckForm {

    protected Map<String,String> listErreur = null;

    public static CheckFormResult checkAndSave(HttpServletRequest req, String className, EntityRepository dao){
        Class classBean;
        Map<String,String> listErreurs = null;
        Entitie bean = null;
        try {
            classBean = Class.forName(className);
            bean = (Entitie) classBean.newInstance();
            bean.hydrate(req);
            listErreurs = bean.checkErreurs(dao, req);
            if (listErreurs.isEmpty()) {
                dao.save(bean, req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CheckFormResult result = new CheckFormResult();
        result.setEntitie(bean);
        result.setListErreurs(listErreurs);
        result.setResultat(checkResultListErreurs(listErreurs));
        return result;
    }

    public static Map<String,String> checkConnect(HttpServletRequest req, UtilisateurDaoImpl dao){
        Utilisateur user = dao.findByEmail(req.getParameter("email"));
        String password = req.getParameter("password");
        Map<String,String> listErreur = new HashMap<>();
        if (user == null){
            listErreur.put(user.CHAMP_EMAIL,"Cet email n'existe pas dans notre base");
        }else if (!user.checkPassword(password)){
            listErreur.put(user.CHAMP_PASS,"Le mot de passe et l'email ne correspondent pas");
        }
        return listErreur;
    }

    private static String checkResultListErreurs(Map<String, String> listErreurs) {
        String resultat;
        if (listErreurs.isEmpty()) {
            return resultat = "L'enregistrement a été effectuée";
        } else {
            return resultat = "L'enregistrement a échoué";
        }
    }
}
