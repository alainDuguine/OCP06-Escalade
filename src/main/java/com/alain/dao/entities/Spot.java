package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Spot implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    @Column (length = 5)
    private String codePostal;
    private String ville;
    private String departement;
    private String region;
    private String pays;
    private Boolean officiel;

    public Spot() {
    }

}
