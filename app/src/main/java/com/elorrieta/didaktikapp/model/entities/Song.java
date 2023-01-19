package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Song {

    @PrimaryKey
    public int idSong;

    public String lyrics;

    public String fillLyrics;

    public byte[] shortAudio;

    public byte[] longAudio;
}
