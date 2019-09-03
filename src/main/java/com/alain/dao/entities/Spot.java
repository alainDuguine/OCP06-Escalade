package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.SpotDaoImpl;
import com.alain.metier.Utilities;
import javax.persistence.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
//@MultipartConfig
public class Spot extends Entitie implements Serializable {
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
    @OneToMany (mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentaireSpot> commentaires = new ArrayList<>();
    @OneToMany (mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoSpot> photos = new ArrayList<>();
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComplementSpot> complements = new ArrayList<>();
    @ManyToMany (mappedBy = "spot")
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
        return listErreur;
    }

    private List<Spot> checkSpotExist(SpotDaoImpl dao, HttpServletRequest req) {
        if (this.getId() != null){
                return dao.findSpotInDepartementForUpdate(this.getId(), this.nom, req.getParameter(CHAMP_DEPARTEMENT));
        }
        return dao.findSpotInDepartement(this.nom, req.getParameter(CHAMP_DEPARTEMENT));
    }

    /**
     * Iterator pour enlever une photo d'un spot
     * @param photo
     */
    public void removePhoto(Photo photo) {
        this.photos.removeIf(photoSpot -> photoSpot.getId().equals(photo.getId()));
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

    public void addPhoto(PhotoSpot photo){
        photo.setSpot(this);
        this.photos.add(photo);
    }

    public void addCommentaire(CommentaireSpot commentaire) {
        commentaire.setSpot(this);
        this.commentaires.add(commentaire);
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
        departement.addSpot(this);
    }

    public void setVille(Ville ville) {
        this.ville = ville;
        ville.addSpot(this);
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

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        utilisateur.addSpot(this);
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
