package com.elorrieta.didaktikapp.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.elorrieta.didaktikapp.model.entities.Game;

import java.util.List;

@Dao
public interface GameDAO {

    @Query("SELECT * FROM Game WHERE idGame = :id")
    Game findById(int id);

    @Query("SELECT * FROM Game WHERE idPoI = :idPoI")
    Game findByPoI(int idPoI);

    @Query("SELECT * FROM Game")
    List<Game> getAll();

    @Insert
    void insertAll(Game... games);

    @Update
    void update(Game game);

    @Delete
    void delete(Game game);

    @Query("SELECT idGame FROM Game WHERE gameClass = :gameClass")
    int findIdByClass(String gameClass);

}
