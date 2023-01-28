package com.elorrieta.didaktikapp.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Native;
import java.time.LocalDate;

@Entity(primaryKeys = {"date", "idGame"})
public class GameRecord {

    @NonNull
    public LocalDate date;

    public int idGame;

    public int completions;

    public GameRecord(@NonNull LocalDate date, int idGame, int completions) {
        this.date = date;
        this.idGame = idGame;
        this.completions = completions;
    }

}
