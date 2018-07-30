package com.example.aryansingh.aryanmoviedb.TvShows;

import com.example.aryansingh.aryanmoviedb.MovieInfo.Cast;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.SeasonDetails;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.SimilarTv;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.TvCast;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.TvDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Aryan Singh on 7/21/2018.
 */

public interface TvInterface {
    @GET("tv/airing_today")
    Call<TvResponse> airingToday(@Query("api_key")String api_key, @Query("language")String language, @Query("page")long page);

    @GET("tv/on_the_air")
    Call<TvResponse> onTheAir(@Query("api_key")String api_key,@Query("language")String language,@Query("page")long page);

    @GET("tv/popular")
    Call<TvResponse> getPopular(@Query("api_key")String api_key,@Query("language")String language,@Query("page")long page);

    @GET("tv/top_rated")
    Call<TvResponse> topRated(@Query("api_key")String api_key,@Query("language")String language,@Query("page")long page);

    @GET("tv/{tv_id}")
    Call<TvDetails> getTvDetails(@Path("tv_id")long id, @Query("api_key")String api);


    @GET("tv/{tv_id}/season/{season_number}")
    Call<SeasonDetails> getSeasonDetails(@Path("tv_id")long id,@Path("season_number")long number, @Query("api_key")String api);


    @GET("tv/{tv_id}/similar")
    Call<SimilarTv> getSimilarTv(@Path("tv_id")long id, @Query("api_key")String api,@Query("language")String language,@Query("page")long page);
//
    @GET("tv/{tv_id}/credits")
    Call<Cast> getCrew(@Path("tv_id")long id, @Query("api_key")String api);

}
