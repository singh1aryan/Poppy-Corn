package com.example.aryansingh.aryanmoviedb.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aryan Singh on 7/30/2018.
 */

public interface SearchInterface {

    @GET("multi")
    Call<SearchResponse> search(@Query("api_key") String key, @Query("query") String query);
}
