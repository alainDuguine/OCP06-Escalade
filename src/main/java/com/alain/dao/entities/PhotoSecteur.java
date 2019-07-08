package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class PhotoSecteur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToOne
    private Secteur secteur;
}
