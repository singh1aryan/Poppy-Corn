package com.example.aryansingh.aryanmoviedb.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.aryansingh.aryanmoviedb.CastInfo.CastInfo;
import com.example.aryansingh.aryanmoviedb.MainActivity;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieInfo.MovieInfo;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.TvInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    ItemAdapter itemAdapter;
    RecyclerView recyclerView;
    List<Result> items;
    ProgressBar progressBar;
    String s = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        s = intent.getStringExtra("search");

        items = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        itemAdapter = new ItemAdapter(this, items, new ItemAdapter.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                Result result = items.get(position);
                if(result.getMediaType().equals("movie")){
                    Intent i = new Intent(SearchActivity.this, MovieInfo.class);
                    i.putExtra("movieId",result.getId());
                    startActivity(i);
                }
                if(result.getMediaType().equals("tv")){
                    Intent i = new Intent(SearchActivity.this, TvInfo.class);
                    i.putExtra("id",result.getId());
                    startActivity(i);
                }
                if(result.getMediaType().equals("person")){
                    Intent i = new Intent(SearchActivity.this, CastInfo.class);
                    i.putExtra("id",result.getId());
                    startActivity(i);
                }
            }
        });
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,3));

        updateListOfResults(s);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu,menu);
//
//        MenuItem menuItem = menu.findItem(R.id.search_icon);
//        searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setQueryHint("Type your query");
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                if(!s.isEmpty()) {
//                    updateListOfResults(s);
//                }
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                // keyword
//                return true;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

    private void updateListOfResults(String s) {
//
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SearchInterface searchInterface = retrofit.create(SearchInterface.class);
        Call<SearchResponse> call = searchInterface.search(MovieConstants.API_KEY,s);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if(searchResponse != null){
                    List<Result> searchResultsApi = searchResponse.getResults();

                    items.clear();
                    items.addAll(searchResultsApi);
                    itemAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(SearchActivity.this, "No results to show", Toast.LENGTH_LONG).show();
                    items.clear();
                    itemAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
