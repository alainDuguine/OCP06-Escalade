package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.VoieDaoImpl;
import com.alain.metier.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente un objet voie ajouté sur le site
 * Il est associé à un secteur
 * et peut contenir des photos et des voies
 */
@Entity
@Table
public class Voie extends Entitie implements Serializable {

    private static final Logger logger = LogManager.getLogger("Voie");

    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_COTATION = "cotation";
    private static final String CHAMP_ALTITUDE = "altitude";
    private static final String CHAMP_LONGUEUR = "longueur";
    private static final String CHAMP_DESCRIPTION = "description";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (length=50)
    private String nom;
    private int altitude;
    private int nbLongueurs;
    @Column (length = 2000)
    private String description;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Secteur secteur;
    @ManyToOne(fetch=FetchType.EAGER)
    private Cotation cotation;

    @OneToMany (mappedBy = "voie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentaireVoie> commentaires = new ArrayList<>();
    @OneToMany (mappedBy = "voie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComplementVoie> complements = new ArrayList<>();
    @OneToMany (mappedBy = "voie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoVoie> photos = new ArrayList<>();

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Voie() {
    }

    public Voie(String nom, int altitude, int nbLongueurs, String description, Utilisateur utilisateur, Secteur secteur, Cotation cotation, List<CommentaireVoie> commentaires, List<ComplementVoie> complements, List<PhotoVoie> photos) {
        this.nom = nom;
        this.altitude = altitude;
        this.nbLongueurs = nbLongueurs;
        this.description = description;
        this.utilisateur = utilisateur;
        this.secteur = secteur;
        this.cotation = cotation;
        this.commentaires = commentaires;
        this.complements = complements;
        this.photos = photos;
    }
    /* ********************************************************************************************
     **** METHODS          ************************************************************************
     ******************************************************************************************** */


    @Override
    public void hydrate(HttpServletRequest req) {
        logger.info("Valorisation des champs d'un objet voie");
        this.setNom(Utilities.getValeurChamp(req, CHAMP_NOM));
        if(!(req.getParameter((CHAMP_ALTITUDE)).isEmpty())) {
            this.setAltitude(Integer.parseInt(req.getParameter(CHAMP_ALTITUDE)));
        }else{
            this.setAltitude(0);
        }
        if(!(req.getParameter((CHAMP_LONGUEUR)).isEmpty())) {
            this.setNbLongueurs(Integer.parseInt(req.getParameter(CHAMP_LONGUEUR)));
        }else{
            this.setNbLongueurs(0);
        }
        this.setDescription(Utilities.getValeurChamp(req, CHAMP_DESCRIPTION));
    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        logger.info("Vérification des champs");
        Map<String, String> listErreur = new HashMap<>();

        if (Utilities.isEmpty(this.nom)) {
            listErreur.put(CHAMP_NOM, "Veuillez entrer le nom de la voie");
        }
        if (!checkVoieExist((VoieDaoImpl)dao, req).isEmpty()){
            listErreur.put(CHAMP_NOM, "Une voie du même nom existe déjà pour ce secteur");
        }
        if (Utilities.isEmpty(this.description) || this.description.length() < 10) {
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description d'au moins 50 caractères");
        }else if (this.description.length() > 2000){
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description de maximum 2000 caractères.");
        }
        logger.info("Erreurs : " + listErreur.size() + " : " + listErreur.toString());
        return listErreur;

    }

    /**
     * On ne peut pas publier 2x la même voie dans un spot
     * Vérifie si la voie existe déjà dans le spot
     * @param dao dataAccessObbject
     * @param req pour récupérer les infos nécessaires
     * @return liste des résultats
     */
    private List<Voie> checkVoieExist(VoieDaoImpl dao, HttpServletRequest req){
        if (this.getId() != null){
            return dao.findVoieInSecteurForUpdate(this.getId(), this.nom, this.getSecteur().getId());
        }
        return dao.findVoieInSecteur(this.nom, Long.parseLong(req.getParameter("idElement")));
    }

    /**
     * Ajoute une association avec une photo
     * @param photo à associer
     */
    public void addPhoto(PhotoVoie photo) {
        logger.info("Association avec la photo");
        photo.setVoie(this);
        this.photos.add(photo);
    }

    /**
     * Supprime une association avec une photo
     * @param photo à dissocier
     */
    public void removePhoto(PhotoVoie photo) {
        logger.info("Suppression de l'association avec la photo " + photo.getId());
        this.photos.removeIf(photoVoie -> photoVoie.getId().equals(photo.getId()));
    }

    /**
     * Supprime une association avec un secteur
     */
    public void removeSecteur() {
        logger.info("Suppression de l'association avec le secteur" + this.secteur.getId());
        this.secteur = null;
    }

    /**
     * Ajoute une association avec un secteur
     * @param secteur à associer
     */
    public void setSecteur(Secteur secteur) {
        logger.info("Association du secteur " + secteur.getId());
        this.secteur = secteur;
        secteur.addVoie(this);
    }

    /**
     * Ajoute une association avec une cotation
     * @param cotation à associer
     */
    public void setCotation(Cotation cotation) {
        logger.info("Association de la cotation " + cotation.getId());
        this.cotation = cotation;
        cotation.addVoies(this);
    }

    /**
     * Ajoute une association avec un utilisateur
     * @param utilisateur à associer
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        logger.info("Association avec l'utilisateur " + utilisateur.getId());
        this.utilisateur = utilisateur;
        utilisateur.addVoie(this);
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public Cotation getCotation() {
        return cotation;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getNbLongueurs() {
        return nbLongueurs;
    }

    public void setNbLongueurs(int nbLongueurs) {
        this.nbLongueurs = nbLongueurs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public List<CommentaireVoie> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<CommentaireVoie> commentaires) {
        this.commentaires = commentaires;
    }

    public List<ComplementVoie> getComplements() {
        return complements;
    }

    public void setComplements(List<ComplementVoie> complements) {
        this.complements = complements;
    }

    public List<PhotoVoie> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVoie> photos) {
        this.photos = photos;
    }

}
