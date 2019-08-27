package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.UtilisateurDaoImpl;
import com.alain.metier.Utilities;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class Utilisateur extends Entitie implements Serializable{

    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_USERNAME = "username";
    private static final String CHAMP_PASS = "password";
    private static final String CHAMP_CONF = "confirmation";
    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_PRENOM = "prenom";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    @Transient
    private String confirmation;
    @Transient
    private String motDePasse;
    private String encryptedPassword;
    private byte[] salt;
    private String nom;
    private String prenom;
    private boolean admin = false;

    @OneToMany (mappedBy = "utilisateur")
    private List<Spot> spots = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<Secteur> secteurs = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<Voie> voies = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireSpot> commentaireSpots = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireSecteur> commentaireSecteurs = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireVoie> commentaireVoies = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementSpot> complementSpots = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementSecteur> complementSecteurs = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementVoie> complementVoies = new ArrayList<>();
    @OneToMany (mappedBy = "utilisateur")
    private List<Topo> topos = new ArrayList<>();

    @ManyToMany (mappedBy = "empruntUtilisateurs")
    private List<Topo> empruntsTopos = new ArrayList<>();

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Utilisateur() {
    }

    public Utilisateur(String email, String username, String encryptedPassword) {
        this.email = email;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    public void hydrate(HttpServletRequest req) {
        this.setEmail(Utilities.getValeurChamp(req, CHAMP_EMAIL));
        this.setMotDePasse(Utilities.getValeurChamp(req, CHAMP_PASS));
        this.setConfirmation(Utilities.getValeurChamp(req, CHAMP_CONF));
        this.setUsername(Utilities.getValeurChamp(req, CHAMP_USERNAME));
        this.setNom(Utilities.getValeurChamp(req, CHAMP_NOM));
        this.setPrenom(Utilities.getValeurChamp(req, CHAMP_PRENOM));
        this.setSalt(Utilities.getSalt());
        this.setEncryptedPassword(Utilities.getSecurePassword(this.getMotDePasse(),this.getSalt()));
    }

    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (!Utilities.checkMail(this.email)) {
            listErreur.put(CHAMP_EMAIL, "Veuillez saisir un email valide");
        }else if(((UtilisateurDaoImpl)dao).findByEmail(this.email) != null) {
            listErreur.put(CHAMP_EMAIL, "Cet e-mail est déjà enregistré");
        }else if (this.username.length() < 2){
            listErreur.put(CHAMP_USERNAME, "Le nom d'utilisateur doit être d'au moins deux caractères.");
        }else if(((UtilisateurDaoImpl)dao).findByUsername(this.username) != null){
            listErreur.put(CHAMP_USERNAME, "Ce nom d'utilisateur est déjà pris.");
        }
        if (!Utilities.checkPassword(motDePasse, confirmation)) {
            listErreur.put(CHAMP_PASS, "Les mots de passes ne correspondent pas");
        }
        return listErreur;
    }

    public boolean checkPassword(String password){
        String testedPassword = Utilities.getSecurePassword(password, this.getSalt());
        return encryptedPassword.equals(testedPassword);
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public static String getChampEmail() {
        return CHAMP_EMAIL;
    }

    public static String getChampPass() {
        return CHAMP_PASS;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getPrenom() {
        return prenom;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }

    public List<CommentaireSpot> getCommentaireSpots() {
        return commentaireSpots;
    }

    public void setCommentaireSpots(List<CommentaireSpot> commentaireSpots) {
        this.commentaireSpots = commentaireSpots;
    }

    public List<CommentaireSecteur> getCommentaireSecteurs() {
        return commentaireSecteurs;
    }

    public void setCommentaireSecteurs(List<CommentaireSecteur> commentaireSecteurs) {
        this.commentaireSecteurs = commentaireSecteurs;
    }

    public List<CommentaireVoie> getCommentaireVoies() {
        return commentaireVoies;
    }

    public void setCommentaireVoies(List<CommentaireVoie> commentaireVoies) {
        this.commentaireVoies = commentaireVoies;
    }

    public List<ComplementSpot> getComplementSpots() {
        return complementSpots;
    }

    public void setComplementSpots(List<ComplementSpot> complementSpots) {
        this.complementSpots = complementSpots;
    }

    public List<ComplementSecteur> getComplementSecteurs() {
        return complementSecteurs;
    }

    public void setComplementSecteurs(List<ComplementSecteur> complementSecteurs) {
        this.complementSecteurs = complementSecteurs;
    }

    public List<ComplementVoie> getComplementVoies() {
        return complementVoies;
    }

    public void setComplementVoies(List<ComplementVoie> complementVoies) {
        this.complementVoies = complementVoies;
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos = topos;
    }

    public List<Topo> getEmpruntsTopos() {
        return empruntsTopos;
    }

    public void setEmpruntsTopos(List<Topo> empruntsTopos) {
        this.empruntsTopos = empruntsTopos;
    }

    public void addSpot(Spot spot) {
        this.spots.add(spot);
    }

    public void addSecteur(Secteur secteur){
        this.secteurs.add(secteur);
    }

    public void addVoie(Voie voie){
        this.voies.add(voie);
    }

    public void addCommentaireSpot(CommentaireSpot commentaireSpot){
        this.commentaireSpots.add(commentaireSpot);
        commentaireSpot.setUtilisateur(this);
        commentaireSpot.setUsername(this.getUsername());
    }

}
