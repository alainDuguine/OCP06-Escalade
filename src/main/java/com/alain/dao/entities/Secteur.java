package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Secteur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

    @ManyToOne
    private Spot spot;

    public Secteur() {
    }
}

