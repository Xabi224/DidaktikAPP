package com.elorrieta.didaktikapp.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.elorrieta.didaktikapp.model.dao.PlaceOfInterestDAO;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;

@Database(entities = {PlaceOfInterest.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceOfInterestDAO placeOfInterestDao();
}
