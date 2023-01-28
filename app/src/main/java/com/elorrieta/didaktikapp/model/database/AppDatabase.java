package com.elorrieta.didaktikapp.model.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.elorrieta.didaktikapp.model.converters.DateConverter;
import com.elorrieta.didaktikapp.model.dao.GameDAO;
import com.elorrieta.didaktikapp.model.dao.GameRecordDAO;
import com.elorrieta.didaktikapp.model.dao.PlaceOfInterestDAO;
import com.elorrieta.didaktikapp.model.dao.SongDAO;
import com.elorrieta.didaktikapp.model.entities.Game;
import com.elorrieta.didaktikapp.model.entities.GameRecord;
import com.elorrieta.didaktikapp.model.entities.PlaceOfInterest;
import com.elorrieta.didaktikapp.model.entities.Song;

@Database(entities = {PlaceOfInterest.class, Game.class, Song.class, GameRecord.class},
        version = 12,
        autoMigrations = {
                @AutoMigration(from = 11, to = 12)
        })
@TypeConverters({DateConverter.class})
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
    public abstract SongDAO songDao();
    public abstract GameRecordDAO gameRecordDao();
}
