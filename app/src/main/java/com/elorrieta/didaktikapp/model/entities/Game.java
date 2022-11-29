package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey
    public int id;

    public int idPlaceOfInterest;

    public String description;

    public Class gameClass;

    public Game(int id, int idPlaceOfInterest, String description, Class gameClass) {
        this.id = id;
        this.idPlaceOfInterest = idPlaceOfInterest;
        this.description = description;
        this.gameClass = gameClass;
    }

}

