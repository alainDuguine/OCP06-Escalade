package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.SecteurDaoImpl;
import com.alain.metier.Utilities;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class Secteur extends Entitie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Spot spot;

    @OneToMany (mappedBy = "secteur")
    private List<Voie> voies;
    @OneToMany (mappedBy = "secteur")
    private List<CommentaireSecteur> commentaires;
    @OneToMany (mappedBy = "secteur")
    private List<ComplementSecteur> complements;
    @OneToMany (mappedBy = "secteur")
    private List<PhotoSecteur> photos;

    public Secteur() {
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
        spot.addSecteur(this);
    }

    @Override
    public void hydrate(HttpServletRequest req) {
        this.setNom(Utilities.getValeurChamp(req, "nom"));
        this.setDescription(Utilities.getValeurChamp(req, "description"));
    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (Utilities.isEmpty(this.nom)) {
            listErreur.put("nom", "Veuillez entrer le nom du secteur");
        }
        try {
            if (!checkSecteurExist((SecteurDaoImpl)dao, req).isEmpty()){
                listErreur.put("nom", "Un secteur du même nom existe déjà pour ce spot");
            }
        } catch (Exception e) {
            listErreur.put("nom", "Le spot auquel vous voulez ajouter un secteur n'existe pas.");
        }
        if (Utilities.isEmpty(this.description) || this.description.length() < 10) {
            listErreur.put("description", "Veuillez entrer une description d'au moins 50 caractères");
        }else if (this.description.length() > 2000){
            listErreur.put("description", "Veuillez entrer une description de maximum 2000 caractères.");
        }
        return listErreur;
    }

    private List<Secteur> checkSecteurExist(SecteurDaoImpl dao, HttpServletRequest req) throws Exception {
        return dao.findSecteurInSpot(this.nom, Long.parseLong(req.getParameter("idSpot")));
    }
}

