package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey
    public int idGame;

    public int idPoI;

    public String description;

    public byte[] audio;

    public String gameClass;

    public Game(int idGame, int idPoI, String description, byte[] audio, String gameClass) {
        this.idGame = idGame;
        this.idPoI = idPoI;
        this.description = description;
        this.audio = audio;
        this.gameClass = gameClass;
    }

}

