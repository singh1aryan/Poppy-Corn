package com.example.aryansingh.aryanmoviedb.Movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.example.aryansingh.aryanmoviedb.EndlessRecyclerViewScrollListener;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.MovieInfo.MovieInfo;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.RecyclerViewItemDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowAll extends AppCompatActivity {

    MoviesAdaptor showAllAdaptor;
    RecyclerView showAll;
    List<Result> showAllList;
    boolean isScrolling = true;
    int currentItems,totalItems, scrolledOutItems;
    int page=1;
    Retrofit retrofit = MovieDBClient.getClient();
    final MoviesInterface moviesInterface =retrofit.create(MoviesInterface.class);
    private EndlessRecyclerViewScrollListener scrollListener;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        showAll = findViewById(R.id.recyclerView);
        showAllList = new ArrayList<>();
        showAllAdaptor = new MoviesAdaptor(this, showAllList, new MoviesAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                Result movieResults = showAllList.get(position);
                Intent i=new Intent(ShowAll.this, MovieInfo.class);
                i.putExtra("movieId",  movieResults.getId());
                startActivity(i);
            }
        });
        showAll.setAdapter(showAllAdaptor);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        showAll.setLayoutManager(gridLayoutManager);

//        int spaceInPixels = 0;
//        showAll.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        showAll.addOnScrollListener(scrollListener);

        Intent i = getIntent();
        s = i.getStringExtra("type");
        if(s.equals("nowShowing")){

            Call<MovieResponse> getPlaying =moviesInterface.getPlaying(MovieConstants.API_KEY,"english",page);
            getPlaying.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    MovieResponse movieResponse=response.body();
                    List<Result> movieResults=movieResponse.results;

                    showAllList.addAll(movieResults);
                    showAllAdaptor.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }

        if(s.equals("topRated")){
            Call<MovieResponse> getPlaying =moviesInterface.topRated(MovieConstants.API_KEY,"english",page);
            getPlaying.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    MovieResponse movieResponse=response.body();
                    List<Result> movieResults=movieResponse.results;

                    showAllList.addAll(movieResults);
                    showAllAdaptor.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        if(s.equals("comingSoon")){
            Call<MovieResponse> getPlaying =moviesInterface.getUpcoming(MovieConstants.API_KEY,"english",page);
            getPlaying.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    MovieResponse movieResponse=response.body();
                    List<Result> movieResults=movieResponse.results;

                    showAllList.addAll(movieResults);
                    showAllAdaptor.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        if(s.equals("popular")){
            Call<MovieResponse> getPlaying =moviesInterface.getPopular(MovieConstants.API_KEY,"english",page);
            getPlaying.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    MovieResponse movieResponse=response.body();
                    List<Result> movieResults=movieResponse.results;

                    showAllList.addAll(movieResults);
                    showAllAdaptor.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
    }

public void loadNextDataFromApi(int offset) {
    // Send an API request to retrieve appropriate paginated data
    //  --> Send the request including an offset value (i.e `page`) as a query parameter.
    //  --> Deserialize and construct new model objects from the API response
    //  --> Append the new data objects to the existing set of items inside the array of items
    //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    if(s.equals("nowShowing")){
        page++;
        Call<MovieResponse> getPlaying =moviesInterface.getPlaying(MovieConstants.API_KEY,"english",page);
        getPlaying.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse=response.body();
                List<Result> movieResults=movieResponse.results;

                showAllList.addAll(movieResults);
                showAllAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    if(s.equals("topRated")){
        page++;
        Call<MovieResponse> getPlaying =moviesInterface.topRated(MovieConstants.API_KEY,"english",page);
        getPlaying.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse=response.body();
                List<Result> movieResults=movieResponse.results;

                showAllList.addAll(movieResults);
                showAllAdaptor.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    if(s.equals("comingSoon")){
        page++;
        Call<MovieResponse> getPlaying =moviesInterface.getUpcoming(MovieConstants.API_KEY,"english",page);
        getPlaying.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse=response.body();
                List<Result> movieResults=movieResponse.results;

                showAllList.addAll(movieResults);
                showAllAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    if(s.equals("popular")){
        page++;
        Call<MovieResponse> getPlaying =moviesInterface.getPopular(MovieConstants.API_KEY,"english",page);
        getPlaying.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse=response.body();
                List<Result> movieResults=movieResponse.results;

                showAllList.addAll(movieResults);
                showAllAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
}
