package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class GameRecord {

    @PrimaryKey
    public LocalDate date;

    public int idGame;

    public int completions;

    public GameRecord(LocalDate date, int idGame, int completions) {
        this.date = date;
        this.idGame = idGame;
        this.completions = completions;
    }

}
