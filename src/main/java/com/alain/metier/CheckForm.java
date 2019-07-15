package com.alain.metier;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Entitie;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.UtilisateurDaoImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class CheckForm {

    private Entitie entitie;
    private Map<String,String> listErreurs = new HashMap<>();
    private String resultat;

    public Entitie getEntitie() {
        return entitie;
    }

    public void setEntitie(Entitie entitie) {
        this.entitie = entitie;
    }

    public Map<String, String> getListErreurs() {
        return listErreurs;
    }

    public void setListErreurs(Map<String, String> listErreurs) {
        this.listErreurs = listErreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public void checkAndSave(HttpServletRequest req, String className, EntityRepository dao){
        Class classBean;
        Entitie bean=null;
        try {
            classBean = Class.forName(className);
            bean = (Entitie) classBean.newInstance();
            bean.hydrate(req);
            this.listErreurs = bean.checkErreurs(dao, req);
            if (this.listErreurs.isEmpty()) {
                dao.save(bean, req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setEntitie(bean);
        this.setResultat(checkResultListErreurs(this.listErreurs));
    }

    public void checkConnect(HttpServletRequest req, UtilisateurDaoImpl dao){
        this.entitie = dao.findByEmail(req.getParameter("email"));
        String password = req.getParameter("password");
        if (this.entitie == null){
            this.listErreurs.put(((Utilisateur) this.entitie).CHAMP_EMAIL,"Cet email n'existe pas dans notre base");
        }else if (!((Utilisateur) this.entitie).checkPassword(password)){
            this.listErreurs.put(((Utilisateur) this.entitie).CHAMP_PASS,"Le mot de passe et l'email ne correspondent pas");
        }
        this.setResultat(checkResultListErreurs(this.listErreurs));
    }

    private String checkResultListErreurs(Map<String, String> listErreurs) {
        if (listErreurs.isEmpty()) {
            return resultat  = "L'enregistrement a été effectuée";
        } else {
            return resultat = "L'enregistrement a échoué";
        }
    }
}
