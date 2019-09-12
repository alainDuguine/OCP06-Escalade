package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.SpotDaoImpl;
import com.alain.metier.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
public class Spot extends Entitie implements Serializable {

    private static final Logger logger = LogManager.getLogger("Spot");

    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_ADRESSE = "adresse";
    private static final String CHAMP_VILLE = "ville";
    private static final String CHAMP_DEPARTEMENT = "departement";
    private static final String CHAMP_DESCRIPTION = "description";


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (length=50)
    private String nom;
    private String adresse;
    @Column (length = 2000)
    private String description;
    private Boolean officiel = false;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Departement departement;
    @ManyToOne
    private Ville ville;

    @OneToMany (mappedBy ="spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Secteur> secteurs = new ArrayList<>();
    @OneToMany (mappedBy = "spot", cascade = CascadeType.ALL)
    private List<CommentaireSpot> commentaires = new ArrayList<>();
    @OneToMany (mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoSpot> photos = new ArrayList<>();
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComplementSpot> complements = new ArrayList<>();
    @ManyToMany (mappedBy = "spots")
    private List<Topo> topos = new ArrayList<>();

    /* ***********************************************************************************************
       **** CONSTRUCTORS      ************************************************************************
       *********************************************************************************************** */

    public Spot() {
    }

    public Spot(String nom, String adresse, String description, Boolean officiel, Utilisateur utilisateur, Departement departement, Ville ville, List<Secteur> secteurs, List<CommentaireSpot> commentaires, List<PhotoSpot> photos, List<ComplementSpot> complements, List<Topo> topos) {
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.officiel = officiel;
        this.utilisateur = utilisateur;
        this.departement = departement;
        this.ville = ville;
        this.secteurs = secteurs;
        this.commentaires = commentaires;
        this.photos = photos;
        this.complements = complements;
        this.topos = topos;
    }

    /* ***********************************************************************************************
       **** METHODS           ************************************************************************
       *********************************************************************************************** */

    @Override
    public void hydrate(HttpServletRequest req) {
        logger.info("Valorisation des champs d'un objet spot");
        this.setNom(Utilities.getValeurChamp(req, CHAMP_NOM));
        this.setAdresse(Utilities.getValeurChamp(req, CHAMP_ADRESSE));
        this.setDescription(Utilities.getValeurChamp(req, CHAMP_DESCRIPTION));
        if(req.getParameter("officiel") != null){
            this.setOfficiel(true);
        }else{
            this.setOfficiel(false);
        }
}

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        logger.info("Vérification des champs");
        Map<String, String> listErreur = new HashMap<>();

        if (Utilities.isEmpty(this.nom)) {
            listErreur.put(CHAMP_NOM, "Veuillez entrer le nom du spot");
        }
        if (!checkSpotExist((SpotDaoImpl)dao, req).isEmpty()){
            listErreur.put(CHAMP_NOM, "Un spot du même nom existe déjà dans ce département");
        }
        if (Utilities.isEmpty(this.adresse)) {
            listErreur.put(CHAMP_ADRESSE, "Veuillez entrer l'adresse du spot");
        }
        if (Utilities.isEmpty(this.description) || this.description.length() < 10) {
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description d'au moins 50 caractères");
        }else if (this.description.length() > 2000){
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description de maximum 2000 caractères.");
        }
        logger.info("Erreurs : " + listErreur.size() + " : " + listErreur.toString());
        return listErreur;
    }

    private List<Spot> checkSpotExist(SpotDaoImpl dao, HttpServletRequest req) {
        if (this.getId() != null){
                return dao.findSpotInDepartementForUpdate(this.getId(), this.nom, req.getParameter(CHAMP_DEPARTEMENT));
        }
        return dao.findSpotInDepartement(this.nom, req.getParameter(CHAMP_DEPARTEMENT));
    }

    // Tous ces itérateurs sont utilisés pour supprimer l'association 1 à n pour une suppression propre

    /**
     * Iterator pour enlever une photo d'un spot
     * @param photo
     */
    public void removePhoto(Photo photo) {
        logger.info("Suppression de l'association avec la photo :" + photo.getId());
        this.photos.removeIf(photoSpot -> photoSpot.getId().equals(photo.getId()));
    }

    /**
     * Iterator pour enlever un commentaire d'un spot
     * @param commentaire
     */
    public void removeCommentaire(CommentaireSpot commentaire) {
        logger.info("Suppression de l'association avec le commentaire :" + commentaire.getId());
        this.commentaires.removeIf(commentaireSpot -> commentaireSpot.getId() == commentaire.getId());
    }

    /**
     * Iterator pour enlever un Spot d'un Topo
     * @param
     */
    public void removeFromTopo(Topo topo) {
        logger.info("Suppression de l'association avec le topo :" + topo.getId());
        this.topos.removeIf(topoInSpot -> topoInSpot.getId() == topo.getId());
    }

    /**
     * Iterator pour enlever un secteur d'un spot
     * @param secteur
     */
    public void removeSecteur(Secteur secteur) {
        logger.info("Suppression de l'association avec le secteur :" + secteur.getId());
        this.secteurs.removeIf(secteurInSpot -> secteurInSpot.getId() == secteur.getId());
    }

    /**
     * Ajoute un topo à la liste s'il n'est pas déjà présent
     * @param topo
     */
    public void addTopo(Topo topo) {
        logger.info("Ajout d'association avec le topo :" + topo.getId());
        if(!this.topos.contains(topo)) {
            this.topos.add(topo);
        }
    }

    /**
     * Supprime tous les liens vers le spot dans les topos
     */
    public void removeAllTopos() {
        logger.info("Suppression de toutes les associations avec les topos du spot");
        for (Topo topo : topos) {
            topo.removeSpot(this);
        }
        this.topos.clear();
    }

    /**
     * Iterator pour enlever un topo d'un spot
     * @param
     */
    public void removeTopo(Topo topo) {
        logger.info("Suppression de l'association avec le topo " + topo.getId());
        this.topos.removeIf(topoInList -> topoInList.getId() == topo.getId());
    }

    /**
     * Ajoute un commentaire au spot
     * @param commentaire
     */
    public void addCommentaire(CommentaireSpot commentaire) {
        logger.info("Ajout d'association avec le commentaire " + commentaire.getId());
        commentaire.setSpot(this);
        this.commentaires.add(commentaire);
    }

    public void addPhoto(PhotoSpot photo){
        logger.info("Ajout d'association avec la photo " + photo.getId());
        photo.setSpot(this);
        this.photos.add(photo);
    }

    public void setDepartement(Departement departement) {
        logger.info("Ajout d'association avec le departement " + departement.getCode());
        this.departement = departement;
        departement.addSpot(this);
    }

    public void setVille(Ville ville) {
        logger.info("Ajout d'association avec la ville " + ville.getId());
        this.ville = ville;
        ville.addSpot(this);
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        logger.info("Ajout d'association avec l'utilisateur " + utilisateur.getId());
        this.utilisateur = utilisateur;
        utilisateur.addSpot(this);
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

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addSecteur(Secteur secteur){
        this.secteurs.add(secteur);
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getOfficiel() {
        return officiel;
    }

    public void setOfficiel(Boolean officiel) {
        this.officiel = officiel;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Departement getDepartement() {
        return departement;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<CommentaireSpot> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<CommentaireSpot> commentaires) {
        this.commentaires = commentaires;
    }

    public List<PhotoSpot> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoSpot> photos) {
        this.photos = photos;
    }

    public List<ComplementSpot> getComplements() {
        return complements;
    }

    public void setComplements(List<ComplementSpot> complements) {
        this.complements = complements;
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos = topos;
    }

    public Ville getVille() {
        return ville;
    }

}
