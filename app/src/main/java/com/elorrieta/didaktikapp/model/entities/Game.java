package com.elorrieta.didaktikapp.model.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey
    public int idGame;

    public int idPoI;

    @Nullable
    public String description;

    @Nullable
    public byte[] audio;

    @Nullable
    public String descriptionExtra;

    @Nullable
    public byte[] audioExtra;

    public String gameClass;

    public Game(int idGame, int idPoI, String description, byte[] audio, String gameClass) {
        this.idGame = idGame;
        this.idPoI = idPoI;
        this.description = description;
        this.audio = audio;
        this.gameClass = gameClass;
    }

    public Game(int idGame, int idPoI, String description, byte[] audio, String descriptionExtra, byte[] audioExtra, String gameClass) {
        this.idGame = idGame;
        this.idPoI = idPoI;
        this.description = description;
        this.audio = audio;
        this.descriptionExtra = descriptionExtra;
        this.audioExtra = audioExtra;
        this.gameClass = gameClass;
    }

}

