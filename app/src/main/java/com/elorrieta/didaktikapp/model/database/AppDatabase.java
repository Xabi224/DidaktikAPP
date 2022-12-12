package com.elorrieta.didaktikapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.elorrieta.didaktikapp.model.dao.GameDAO;
import com.elorrieta.didaktikapp.model.dao.PlaceOfInterestDAO;
import com.elorrieta.didaktikapp.model.entities.Game;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;

@Database(entities = {PlaceOfInterest.class, Game.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "didaktikapp")
                            .createFromAsset("database/didaktikapp.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract PlaceOfInterestDAO placeOfInterestDao();
    public abstract GameDAO gameDao();
}
