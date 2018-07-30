package com.example.aryansingh.aryanmoviedb.Movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aryan Singh on 7/21/2018.
 */

public class MovieResponse {
    @SerializedName("results")
    public List<Result> results;
}
