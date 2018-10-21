package com.example.aryansingh.aryanmoviedb.RoomDatabase;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;

@Database(entities = {Movie.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

}