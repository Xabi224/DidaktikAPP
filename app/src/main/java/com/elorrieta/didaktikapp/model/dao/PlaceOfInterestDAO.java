package com.elorrieta.didaktikapp.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;

import java.util.List;

@Dao
public interface PlaceOfInterestDAO {

    @Query("SELECT * FROM PlaceOfInterest WHERE id = :id")
    PlaceOfInterest findById(int id);

    @Query("SELECT * FROM PlaceOfInterest WHERE name LIKE :name")
    PlaceOfInterest findByName(String name);

    @Query("UPDATE PlaceOfInterest SET name = :name, latitude = :latitude, longitude = :longitude WHERE id = :id")
    void updateById(int id, String name, double latitude, double longitude);

    @Query("SELECT * FROM PlaceOfInterest")
    List<PlaceOfInterest> getAll();

    @Query("SELECT * FROM PlaceOfInterest WHERE id IN (:ids)")
    List<PlaceOfInterest> loadAllByIds(int[] ids);

    @Insert
    void insertAll(PlaceOfInterest... placesOfInterest);

    @Delete
    void delete(PlaceOfInterest placeOfInterest);

}
