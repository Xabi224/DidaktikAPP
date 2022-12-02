package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PoIAndGame {

    @Embedded
    public PlaceOfInterest placeOfInterest;

    @Relation(
            parentColumn = "idPoI",
            entityColumn = "idPoI"
    )
    public Game game;

    public PoIAndGame(PlaceOfInterest placeOfInterest, Game game) {
        this.placeOfInterest = placeOfInterest;
        this.game = game;
    }
}