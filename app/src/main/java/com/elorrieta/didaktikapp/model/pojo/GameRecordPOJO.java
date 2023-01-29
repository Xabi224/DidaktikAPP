package com.elorrieta.didaktikapp.model.pojo;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class GameRecordPOJO {

    private final int idGame;
    private final LocalDate date;
    private final String name;
    private final int completions;

    public GameRecordPOJO(int idGame, LocalDate date, String name, int completions) {
        this.idGame = idGame;
        this.date = date;
        this.name = name;
        this.completions = completions;
    }

    public int getIdGame() {
        return idGame;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getCompletions() {
        return completions;
    }

    @NonNull
    @Override
    public String toString(){
        return date.toString() + ": " + name + " - " + completions;
    }

}
