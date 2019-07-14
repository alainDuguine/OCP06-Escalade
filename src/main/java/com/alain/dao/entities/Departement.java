package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Departement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String nom;

    @OneToMany (mappedBy = "departement")
    private List<Spot> spots;
    @OneToMany (mappedBy = "departement")
    private List<Ville> villes;
}
