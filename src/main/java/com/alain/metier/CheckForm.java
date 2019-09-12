package com.alain.metier;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Entitie;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.UtilisateurDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckForm {

    private static final Logger logger = LogManager.getLogger("CheckForm");
    private Entitie entitie;
    private Map<String,String> listErreurs = new HashMap<>();
    private boolean resultat;


    /* ********************************************************************************************
     **** METHODS           ***********************************************************************
     ******************************************************************************************** */

    /**
     * Méthode générique utilisée pour créer un objet "Entitie" le valoriser vérifier les erreurs et le sauvegarder dans la base de données
     * @param req httpServletRequest contenant les paramètres
     * @param className nom de la classe de l'objet
     * @param dao utilisé pour appeler les méthodes de sauvegarde en base de données
     */
    public void checkAndSave(HttpServletRequest req, String className, EntityRepository dao){
        Class classBean;
        Entitie bean=null;
        try {
            classBean = Class.forName(className);
            bean = (Entitie) classBean.newInstance();
            logger.info("Création d'un objet :" + className);
            bean.hydrate(req);
            this.listErreurs = bean.checkErreurs(dao, req);
            if (this.listErreurs.isEmpty()) {
                dao.save(bean, req);
                logger.info("Sauvegarde de l'objet réussie :" + className + ", id :" + bean.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            listErreurs.put("server", "Une erreur système est apparue, merci de réessayer plus tard");
            logger.warn("Erreur lors de la sauvegarde d'un objet " + className + " en BDD : " + Arrays.toString(e.getStackTrace()));
        }
        this.setEntitie(bean);
        this.setResultat(checkResultListErreurs(listErreurs));
    }

    /**
     * Méthode générique pour sauvegarder les photos depuis la requete Http
     * @param req httpServletRequest contenant les paramètres
     * @param className nom de la classe de la photo
     * @param dao utilisé pour appeler les méthodes de sauvegarde en base de données
     */
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
                    logger.info("Création d'un objet photo :" + className);
                    photo.uploadPhoto(part, parts.indexOf(part));
                    if (photo.getErreur() == null) {
                        dao.save(photo, req);
                        logger.info("Sauvegarde de la photo réussie :" + className + ", id :" + photo.getId());
                    }else{
                        erreurPhoto = photo.getErreur();
                    }
                }
            }
        }  catch (ServletException e) {
            erreurPhoto += "Les fichiers ne doivent pas excéder une taille de 4 Mo";
            logger.warn("Photo trop volumineuse " + Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            e.printStackTrace();
            listErreurs.put("server", "Une erreur système est apparue, merci de réessayer plus tard");
            logger.error("Erreur lors de la sauvegarde d'une photo en BDD : " + Arrays.toString(e.getStackTrace()));
        }
        if (erreurPhoto != null){
            this.listErreurs.put("photo", erreurPhoto);
        }
        this.setResultat(checkResultListErreurs(listErreurs));
    }


    /**
     * Méthode générique pour modifier les objets depuis la requete Http
     * @param req httpServletRequest contenant les paramètres
     * @param dao utilisé pour appeler les méthodes de sauvegarde en base de données
     * @param id identifiant en base de donnée de l'objet
     */
    public void checkAndUpdate(HttpServletRequest req, EntityRepository dao, Long id){
        Entitie bean=null;
        try {
            bean = (Entitie) dao.findOne(id);
            bean.hydrate(req);
            this.listErreurs = bean.checkErreurs(dao, req);
            if (this.listErreurs.isEmpty()) {
                dao.update(bean, req);
                logger.info("Modification d'un objet réussie :" + bean.getClass() + ", id : " + bean.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur lors de la modification d'un objet en BDD : " + bean.getClass() + ", id : " + bean.getId() + ", erreur :" + Arrays.toString(e.getStackTrace()));
            listErreurs.put("server", "Une erreur système est apparue, merci de réessayer plus tard");
        }
        this.setEntitie(bean);
        this.setResultat(checkResultListErreurs(listErreurs));
    }

    /**
     * Vérifie les données de connexion d'un utilisateur
     * @param req httpServletRequest contenant les paramètres
     * @param dao utilisé pour appeler les méthodes de recherche en base de données
     */
    public void checkConnect(HttpServletRequest req, UtilisateurDaoImpl dao){
        this.entitie = dao.findByEmail(req.getParameter("email"));
        String password = req.getParameter("password");
        if (this.entitie == null){
            this.listErreurs.put(Utilisateur.getChampEmail(),"Cet email n'existe pas dans notre base");
            logger.info("Connexion échouée :" + req.getParameter("email") + " inconnu");
        }else if (!((Utilisateur) this.entitie).checkPassword(password)){
            this.listErreurs.put(Utilisateur.getChampPass(),"Le mot de passe et l'email ne correspondent pas");
            logger.info("Connexion échouée :" + req.getParameter("email") + " et " + req.getParameter("password") + " ne correspondent pas");
        }
        this.setResultat(checkResultListErreurs(this.listErreurs));
    }

    public boolean checkResultListErreurs(Map<String, String> listErreurs) {
        return listErreurs.isEmpty();
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS **************************************************************************
     ************************************************************************************************/

    public Entitie getEntitie() {
        return entitie;
    }

    public void setEntitie(Entitie entitie) {
        this.entitie = entitie;
    }

    public Map<String, String> getListErreurs() {
        return listErreurs;
    }

    public boolean isResultat() {
        return resultat;
    }

    public void setResultat(boolean resultat) {
        this.resultat = resultat;
    }
}
