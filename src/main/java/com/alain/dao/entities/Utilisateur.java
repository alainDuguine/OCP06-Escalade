package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.metier.Utilities;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class Utilisateur extends Entitie implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Transient
    private String confirmation;
    @Transient
    private String motDePasse;
    private String encryptedPassword;
    private byte[] salt;
    private String nom;
    private String prenom;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur(String email, String encryptedPassword, String nom, String prenom, String role) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    @OneToMany (mappedBy = "utilisateur")
    private List<Spot> spots;
    @OneToMany (mappedBy = "utilisateur")
    private List<Secteur> secteurs;
    @OneToMany (mappedBy = "utilisateur")
    private List<Voie> voies;
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireSpot> commentaireSpots;
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireSecteur> commentaireSecteurs;
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireVoie> commentaireVoies;
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementSpot> complementSpots;
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementSecteur> complementSecteurs;
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementVoie> complementVoies;
    @OneToMany (mappedBy = "utilisateur")
    private List<Topo> topos;

    @ManyToMany (mappedBy = "empruntUtilisateurs")
    private List<Topo> empruntsTopos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public boolean check() {
        boolean checkOk = true;
        if (!Utilities.checkMail(this.email)) {
            checkOk = false;
        }
        if (checkOk && !Utilities.checkPassword(motDePasse, confirmation)) {
            checkOk = false;
        }
//        if (checkOk && !validNom(nom)) {
//            checkOk = false;
//        }
//        if (checkOk && !validNom(prenom)) {
//            checkOk = false;
//        }
        return checkOk;
    }

    public Map<String, String> checkErreurs(EntityRepository dao) {
        Map<String, String> listErreur = new HashMap<String, String>();

        if (!Utilities.checkMail(this.email)) {
            listErreur.put(Utilities.CHAMP_EMAIL, "Veuillez saisir un email valide");
        }else if(dao.findByEmail(this.email) != null){
            listErreur.put(Utilities.CHAMP_EMAIL, "Cet e-mail est déjà enregistré");
        }
        if (!Utilities.checkPassword(motDePasse, confirmation)) {
            listErreur.put(Utilities.CHAMP_PASS, "Les mots de passes ne correspondent pas");
        }
        if (!Utilities.isEmpty(nom)) {
            listErreur.put(Utilities.CHAMP_NOM,"Veuillez saisir un nom");
        }
        if (!Utilities.isEmpty(prenom)) {
            listErreur.put(Utilities.CHAMP_PRENOM, "Veuillez saisir un prénom");
        }
        return listErreur;
    }

    // Valorise les variables Utilisateurs depuis la requête http
    public void hydrate(HttpServletRequest req) {
        this.setEmail(Utilities.getValeurChamp(req, Utilities.CHAMP_EMAIL));
        this.setMotDePasse(Utilities.getValeurChamp(req, Utilities.CHAMP_PASS));
        this.setConfirmation(Utilities.getValeurChamp(req, Utilities.CHAMP_CONF));
        this.setNom(Utilities.getValeurChamp(req, Utilities.CHAMP_NOM));
        this.setPrenom(Utilities.getValeurChamp(req, Utilities.CHAMP_PRENOM));
        this.setSalt(Utilities.getSalt());
        this.setEncryptedPassword(Utilities.getSecurePassword(this.getMotDePasse(),this.getSalt()));
    }

    public boolean checkPassword(String password){
        String testedPassword = Utilities.getSecurePassword(password, this.getSalt());
        return encryptedPassword.equals(testedPassword);
    }

//    private boolean validNom(String nom){
//        return Utilities.isEmpty(nom);
//    }

}
