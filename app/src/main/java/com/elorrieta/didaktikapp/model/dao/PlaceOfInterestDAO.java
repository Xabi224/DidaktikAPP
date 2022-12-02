package com.elorrieta.didaktikapp.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;
import com.elorrieta.didaktikapp.model.entities.PoIAndGame;

import java.util.List;

@Dao
public interface PlaceOfInterestDAO {

    @Query("SELECT * FROM PlaceOfInterest WHERE idPoI = :id")
    PlaceOfInterest findById(int id);

    @Query("SELECT * FROM PlaceOfInterest WHERE name LIKE :name")
    PlaceOfInterest findByName(String name);

    @Query("SELECT * FROM PlaceOfInterest")
    List<PlaceOfInterest> getAll();

    @Query("SELECT * FROM PlaceOfInterest WHERE idPoI IN (:ids)")
    List<PlaceOfInterest> loadAllByIds(int[] ids);

    @Transaction
    @Query("SELECT * FROM PlaceOfInterest")
    List<PoIAndGame> getAllPoIAndGames();

    @Insert
    void insertAll(PlaceOfInterest... placesOfInterest);

    @Update
    void update(PlaceOfInterest placeOfInterest);

    @Delete
    void delete(PlaceOfInterest placeOfInterest);

}
