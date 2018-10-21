package com.example.aryansingh.aryanmoviedb.Movies;

import com.example.aryansingh.aryanmoviedb.CastInfo.CastDetails;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.MovieInfo.Cast;
import com.example.aryansingh.aryanmoviedb.MovieInfo.Crew;
import com.example.aryansingh.aryanmoviedb.MovieInfo.ImageResult;
import com.example.aryansingh.aryanmoviedb.MovieInfo.MovieDetails;
import com.example.aryansingh.aryanmoviedb.MovieInfo.SimilarMovies;
import com.example.aryansingh.aryanmoviedb.MovieInfo.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Aryan Singh on 7/21/2018.
 */

public interface MoviesInterface {

    @GET("movie/now_playing")
    Call<MovieResponse> getPlaying(@Query("api_key")String apikey,@Query("language")String language,@Query("page")long page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(@Query("api_key")String apikey,@Query("language")String language,@Query("page")long page);

    @GET("movie/popular")
    Call<MovieResponse> getPopular(@Query("api_key")String apikey,@Query("language")String language,@Query("page")long page);

    @GET("movie/top_rated")
    Call<MovieResponse> topRated(@Query("api_key")String apikey,@Query("language")String language,@Query("page")long page);

//    @GET("movie/{movie_id}/images")
//    Call<ImageResult> getImages(@Query("api_key")String apikey);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id")long id, @Query("api_key")String api);

    @GET("movie/{movie_id}/similar")
    Call<SimilarMovies> getSimilarMovies(@Path("movie_id")long id, @Query("api_key")String api);

    @GET("movie/{movie_id}/credits")
    Call<Cast> getCrew(@Path("movie_id")long id, @Query("api_key")String api);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideo(@Path("movie_id")long id, @Query("api_key")String api );




}
