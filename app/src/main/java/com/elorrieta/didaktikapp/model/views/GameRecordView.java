package com.elorrieta.didaktikapp.model.views;

import androidx.room.DatabaseView;

import java.time.LocalDate;

@DatabaseView("SELECT IdGame, Date, Name, Completions FROM GameRecord NATURAL JOIN Game NATURAL JOIN PlaceOfInterest")
public class GameRecordView {

    public int idGame;
    public LocalDate date;
    public String name;
    public int completions;

}
