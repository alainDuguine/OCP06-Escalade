package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Utilisateur implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String role;

    public Utilisateur() {
    }

}
