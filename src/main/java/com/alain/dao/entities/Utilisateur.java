package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.UtilisateurDaoImpl;
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

    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "password";
    private static final String CHAMP_CONF = "confirmation";
    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_PRENOM = "prenom";

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
    private boolean admin = false;

    public Utilisateur() {
    }

    public Utilisateur(String email, String encryptedPassword, String nom, String prenom) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.nom = nom;
        this.prenom = prenom;
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

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }

    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (!Utilities.checkMail(this.email)) {
            listErreur.put(CHAMP_EMAIL, "Veuillez saisir un email valide");
        }else if(((UtilisateurDaoImpl)dao).findByEmail(this.email) != null){
            listErreur.put(CHAMP_EMAIL, "Cet e-mail est déjà enregistré");
        }
        if (!Utilities.checkPassword(motDePasse, confirmation)) {
            listErreur.put(CHAMP_PASS, "Les mots de passes ne correspondent pas");
        }
        if (Utilities.isEmpty(nom)) {
            listErreur.put(CHAMP_NOM,"Veuillez saisir un nom");
        }
        if (Utilities.isEmpty(prenom)) {
            listErreur.put(CHAMP_PRENOM, "Veuillez saisir un prénom");
        }
        return listErreur;
    }

    // Valorise les variables Utilisateurs depuis la requête http
    public void hydrate(HttpServletRequest req) {
        this.setEmail(Utilities.getValeurChamp(req, CHAMP_EMAIL));
        this.setMotDePasse(Utilities.getValeurChamp(req, CHAMP_PASS));
        this.setConfirmation(Utilities.getValeurChamp(req, CHAMP_CONF));
        this.setNom(Utilities.getValeurChamp(req, CHAMP_NOM));
        this.setPrenom(Utilities.getValeurChamp(req, CHAMP_PRENOM));
        this.setSalt(Utilities.getSalt());
        this.setEncryptedPassword(Utilities.getSecurePassword(this.getMotDePasse(),this.getSalt()));
    }

    public boolean checkPassword(String password){
        String testedPassword = Utilities.getSecurePassword(password, this.getSalt());
        return encryptedPassword.equals(testedPassword);
    }

}
