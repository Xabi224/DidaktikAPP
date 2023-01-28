package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class GameAndRecord {

    @Embedded
    public Game game;

    @Relation(
            parentColumn = "idGame",
            entityColumn = "idGame"
    )
    public GameRecord gameRecord;

    public GameAndRecord(Game game, GameRecord gameRecord) {
        this.game = game;
        this.gameRecord = gameRecord;
    }
}
