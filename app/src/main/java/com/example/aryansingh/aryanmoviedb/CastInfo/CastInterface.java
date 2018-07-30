package com.example.aryansingh.aryanmoviedb.CastInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Aryan Singh on 7/24/2018.
 */

public interface CastInterface {

    @GET("person/{person_id}")
    Call<CastDetails> getCastDetails(@Path("person_id")long id, @Query("api_key")String api);

    @GET("person/{person_id}/movie_credits")
    Call<CastMovies> getCastMovieDetails(@Path("person_id")long id, @Query("api_key")String api);

    @GET("person/{person_id}/images")
    Call<CastImages> getCastImages(@Path("person_id")long id, @Query("api_key")String api);

}
