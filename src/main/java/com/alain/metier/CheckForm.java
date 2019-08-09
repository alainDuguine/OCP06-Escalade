package com.alain.metier;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Entitie;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.UtilisateurDaoImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getResultat() {
        return resultat;
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
           listErreurs.put("server", "Une erreur système est apparue, merci de réessayer plus tard");
        }
        this.setEntitie(bean);
    }

    public void checkAndSavePhoto(HttpServletRequest req, String className, EntityRepository dao){
        Class classBean;
        Photo photo;
        String erreurPhoto = null;
        try {
            List<Part> parts = (List<Part>) req.getParts();
            for (Part part : parts){
                if ((part.getName().equals("photo")) && (!part.getSubmittedFileName().isEmpty())) {
                    classBean = Class.forName(className);
                    photo = (Photo) classBean.newInstance();
                    photo.uploadPhoto(part, parts.indexOf(part));
                    if (photo.getErreur() == null) {
                        //Ajoute les associations bidirectionelles
                        dao.save(photo, req);
                    }else{
                        erreurPhoto = photo.getErreur();
                    }
                }
            }
        } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            erreurPhoto += "Les fichiers ne doivent pas excéder une taille de 4 Mo";
        }
        if (erreurPhoto != null){
            this.listErreurs.put("photo", erreurPhoto);
        }
    }

    public void checkConnect(HttpServletRequest req, UtilisateurDaoImpl dao){
        this.entitie = dao.findByEmail(req.getParameter("email"));
        String password = req.getParameter("password");
        if (this.entitie == null){
            this.listErreurs.put(Utilisateur.getChampEmail(),"Cet email n'existe pas dans notre base");
        }else if (!((Utilisateur) this.entitie).checkPassword(password)){
            this.listErreurs.put(Utilisateur.getChampPass(),"Le mot de passe et l'email ne correspondent pas");
        }
        this.setResultat(checkResultListErreurs(this.listErreurs));
    }

    public String checkResultListErreurs(Map<String, String> listErreurs) {
        if (listErreurs.isEmpty()) {
            return resultat  = "L'enregistrement a été effectuée";
        } else {
            return resultat = "L'enregistrement a échoué";
        }
    }

}
