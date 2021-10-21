package edu.weber.cs.w01113559.cs3270a8.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance != null)
            return instance;

        instance = Room.databaseBuilder(context, AppDatabase.class, "course-database").build();

        return instance;

    }

    public abstract CourseDAO courseDAO();
}
