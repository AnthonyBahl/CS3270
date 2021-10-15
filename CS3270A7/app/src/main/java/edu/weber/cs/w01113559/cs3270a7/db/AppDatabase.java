package edu.weber.cs.w01113559.cs3270a7.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public AppDatabase getInstance(Context context) {
        if (context != null) {
            return instance;
        }

        instance = Room.databaseBuilder(context, AppDatabase.class, "course-database").build();

        return instance;

    }
}
