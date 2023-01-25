package com.elorrieta.didaktikapp.model.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.elorrieta.didaktikapp.model.entities.Song;

@Dao
public interface SongDAO {

    @Query("SELECT * FROM Song WHERE idSong = :id")
    Song findById(int id);

    @Query("SELECT COUNT(*) FROM Song")
    int lastSong();

}
