package com.example.aryansingh.aryanmoviedb.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Aryan Singh on 8/13/2018.
 */

@Entity
public class Movie {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "movie_id")
    public long movie_id;

    public String youtube_path;

    public String type;
    // popular, top rated or what ?

    public String poster_path;


    public Movie(long movie_id, String youtube_path, String type, String poster_path) {
        this.movie_id = movie_id;
        this.youtube_path = youtube_path;
        this.type = type;
        this.poster_path = poster_path;
    }
}
