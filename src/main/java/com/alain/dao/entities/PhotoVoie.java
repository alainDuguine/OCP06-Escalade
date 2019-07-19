package com.alain.dao.entities;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "id")
class PhotoVoie extends Photo {

    @ManyToOne
    private Voie voie;

}
