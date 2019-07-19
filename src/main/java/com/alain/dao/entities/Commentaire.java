package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Commentaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateHeure;
    private String contenu;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}