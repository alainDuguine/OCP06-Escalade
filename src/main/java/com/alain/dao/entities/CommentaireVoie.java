package com.alain.dao.entities;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class CommentaireVoie extends Commentaire{

    @ManyToOne
    private Voie voie;

    public CommentaireVoie() {
    }
}
