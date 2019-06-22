package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Topo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateEdition;
    private String description;
    private Boolean disponible;

    public Topo() {
    }

}
