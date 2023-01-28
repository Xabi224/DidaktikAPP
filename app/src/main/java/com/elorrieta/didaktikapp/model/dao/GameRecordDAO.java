package com.elorrieta.didaktikapp.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.elorrieta.didaktikapp.model.entities.GameRecord;

import java.time.LocalDate;
import java.util.List;

@Dao
public abstract class GameRecordDAO {

    @Query("SELECT * FROM GameRecord WHERE idGame = :idGame AND date = :date")
    public abstract GameRecord findByGameAndDate(int idGame, LocalDate date);

    @Query("SELECT * FROM GameRecord WHERE idGame = :idGame")
    public abstract GameRecord findByGame(int idGame);

    @Query("SELECT * FROM GameRecord WHERE date = :date")
    public abstract GameRecord findByDate(LocalDate date);

    @Query("SELECT * FROM GameRecord")
    public abstract List<GameRecord> getAll();

    @Insert
    public abstract void insertAll(GameRecord... gameRecords);

    @Update
    public abstract void update(GameRecord gameRecord);

    @Delete
    public abstract void delete(GameRecord gameRecord);

    @Query("DELETE FROM GameRecord WHERE date = :date")
    public abstract void deleteByDate(LocalDate date);

    public void addCompletion(int idGame){
        GameRecord gameRecord = findByGameAndDate(idGame, LocalDate.now());
        if(gameRecord == null){
            gameRecord = new GameRecord(LocalDate.now(), idGame, 1);
            insertAll(gameRecord);
        }else{
            gameRecord.completions++;
            update(gameRecord);
        }
    }

}
