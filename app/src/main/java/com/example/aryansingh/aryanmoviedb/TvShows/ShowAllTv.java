package com.example.aryansingh.aryanmoviedb.TvShows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aryansingh.aryanmoviedb.EndlessRecyclerViewScrollListener;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.MovieInfo.MovieInfo;
import com.example.aryansingh.aryanmoviedb.Movies.MovieResponse;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesAdaptor;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesInterface;
import com.example.aryansingh.aryanmoviedb.Movies.Result;
import com.example.aryansingh.aryanmoviedb.Movies.ShowAll;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.TvInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowAllTv extends AppCompatActivity {

    TvAdaptor showAllAdaptor;
    RecyclerView showAll;
    List<TvResult> showAllList;
    int page=1;
    Retrofit retrofit = MovieDBClient.getClient();
    final TvInterface tvInterface =retrofit.create(TvInterface.class);
    private EndlessRecyclerViewScrollListener scrollListener;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        showAll = findViewById(R.id.recyclerView);
        showAllList = new ArrayList<>();
        showAllAdaptor = new TvAdaptor(this, showAllList, new TvAdaptor.TvClickListener() {
            @Override
            public void onTvClick(View view, int position) {
                TvResult movieResults = showAllList.get(position);
                Intent i = new Intent(ShowAllTv.this, TvInfo.class);
                i.putExtra("id",  movieResults.getId());
                startActivity(i);
            }
        });
        showAll.setAdapter(showAllAdaptor);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        showAll.setLayoutManager(gridLayoutManager);
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

        if(s.equals("onTheAir")){
            Call<TvResponse> onTheAir =tvInterface.onTheAir(MovieConstants.API_KEY,"en-US",1);
            onTheAir.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    if(response.body() != null) {
                        TvResponse tvResponse = response.body();
                        List<TvResult> tvTvResults = tvResponse.getResults();

                        showAllList.addAll(tvTvResults);
                        showAllAdaptor.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });
        }

        if(s.equals("airingToday")){
            Call<TvResponse> airingToday = tvInterface.airingToday(MovieConstants.API_KEY,"en-US",1);
            airingToday.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    showAllList.addAll(tvTvResults);
                    showAllAdaptor.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });
        }
        if(s.equals("popular")){
            Call<TvResponse> getPopular = tvInterface.getPopular(MovieConstants.API_KEY,"en-US",1);
            getPopular.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    showAllList.addAll(tvTvResults);
                    showAllAdaptor.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });

        }
        if(s.equals("topRated")){
            Call<TvResponse> topRated = tvInterface.topRated(MovieConstants.API_KEY,"en-US",1);
            topRated.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    showAllList.addAll(tvTvResults);
                    showAllAdaptor.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

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
        if(s.equals("onTheAir")){
            page++;
            Call<TvResponse> onTheAir =tvInterface.onTheAir(MovieConstants.API_KEY,"en-US",page);
            onTheAir.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    if(response.body() != null) {
                        TvResponse tvResponse = response.body();
                        List<TvResult> tvTvResults = tvResponse.getResults();

                        showAllList.addAll(tvTvResults);
                        showAllAdaptor.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });
        }

        if(s.equals("airingToday")){
            page++;
            Call<TvResponse> airingToday = tvInterface.airingToday(MovieConstants.API_KEY,"en-US",page);
            airingToday.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    showAllList.addAll(tvTvResults);
                    showAllAdaptor.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });
        }
        if(s.equals("popular")){
            page++;
            Call<TvResponse> getPopular = tvInterface.getPopular(MovieConstants.API_KEY,"en-US",page);
            getPopular.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    showAllList.addAll(tvTvResults);
                    showAllAdaptor.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });

        }
        if(s.equals("topRated")){
            page++;
            Call<TvResponse> topRated = tvInterface.topRated(MovieConstants.API_KEY,"en-US",page);
            topRated.enqueue(new Callback<TvResponse>() {
                @Override
                public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    showAllList.addAll(tvTvResults);
                    showAllAdaptor.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<TvResponse> call, Throwable t) {

                }
            });
        }
    }
}
