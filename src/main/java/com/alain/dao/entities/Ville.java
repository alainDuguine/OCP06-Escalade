//package com.alain.dao.entities;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.List;
//
//@Entity
//@Table
//public class Ville implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nom;
//
//    @ManyToOne
//    private Departement departement;
//
//    @OneToMany
//    private List<Spot> spots;
//}
