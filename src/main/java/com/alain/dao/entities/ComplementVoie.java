package com.alain.dao.entities;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class ComplementVoie extends Complement {

    @ManyToOne
    private Voie voie;

    public ComplementVoie() {
    }
}
