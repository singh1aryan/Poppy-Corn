package com.example.aryansingh.aryanmoviedb.CastInfo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.MovieInfo.Cast;
import com.example.aryansingh.aryanmoviedb.MovieInfo.CastAdaptor;
import com.example.aryansingh.aryanmoviedb.MovieInfo.Crew;
import com.example.aryansingh.aryanmoviedb.MovieInfo.MovieInfo;
import com.example.aryansingh.aryanmoviedb.MovieInfo.SimilarMovies;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesAdaptor;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesInterface;
import com.example.aryansingh.aryanmoviedb.Movies.Result;
import com.example.aryansingh.aryanmoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CastInfo extends AppCompatActivity {

    TextView cast_info_pob,cast_info_dob,cast_info_gender,cast_info_biography,popularity,titleTextView,showMore;
    ImageView cast_image, like, share, mylist;
    RecyclerView movie_info_cast_recycler_view;
    CastMovieAdaptor castMovieAdaptor;
    List<Casts> castMoviesList; //  we need the

    ArrayList<Long> likes = new ArrayList<>();
    ArrayList<Long> mylists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_info);

        Intent i = getIntent();
        final long id = i.getLongExtra("castId",0); // the id of the cast which we clicked

        showMore = findViewById(R.id.showMore);
        titleTextView = findViewById(R.id.titleTextView);
        cast_image = findViewById(R.id.cast_image);
        cast_info_dob = findViewById(R.id.cast_info_dob);
        cast_info_gender = findViewById(R.id.cast_info_gender);
        cast_info_pob = findViewById(R.id.cast_info_pob);
        cast_info_biography = findViewById(R.id.cast_info_biography);
        popularity = findViewById(R.id.popularity);

        like = findViewById(R.id.like);
        share = findViewById(R.id.share);
        mylist = findViewById(R.id.mylist);
        movie_info_cast_recycler_view = findViewById(R.id.movie_info_cast_recycler_view);

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mylist.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(mylists.contains(id)){
                    mylists.remove(id);
                    mylist.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
                    Toast.makeText(CastInfo.this,"Removed from your List",Toast.LENGTH_LONG).show();
                }
                else{
                    mylists.add(id);
                    mylist.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
                    Toast.makeText(CastInfo.this,"Added to your List",Toast.LENGTH_LONG).show();
                }
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(likes.contains(id)){
                    likes.remove(id);
                    like.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_black_24dp));
                    Toast.makeText(CastInfo.this,"Removed from Favorites",Toast.LENGTH_LONG).show();
                }
                else{
                    likes.add(id);
                    like.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_blue_24dp));
                    Toast.makeText(CastInfo.this,"Added to Favorites",Toast.LENGTH_LONG).show();
                }
            }
        });

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showMore.getText().equals("Hide")){
                    showMore.setText("Show More");
                    cast_info_biography.setMaxLines(4);
                }
                else{
                    cast_info_biography.setMaxLines(1000);
                    showMore.setText("Hide");
                }
            }
        });

        Retrofit retrofit = MovieDBClient.getClient();
        CastInterface castInterface = retrofit.create(CastInterface.class);

        // we made the movies adaptor because we need that only
        castMoviesList = new ArrayList<>();
        castMovieAdaptor = new CastMovieAdaptor(this, castMoviesList, new CastMovieAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                Casts castmovieResults = castMoviesList.get(position);
                Intent i=new Intent(CastInfo.this, MovieInfo.class);
                i.putExtra("movieId", castmovieResults.getId());
                startActivity(i);
            }
        });

        movie_info_cast_recycler_view.setAdapter(castMovieAdaptor);
        movie_info_cast_recycler_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        retrofit2.Call<CastDetails> castDetailsCall = castInterface.getCastDetails(id, MovieConstants.API_KEY);
        castDetailsCall.enqueue(new Callback<CastDetails>() {
            @Override
            public void onResponse(retrofit2.Call<CastDetails> call, Response<CastDetails> response) {
                CastDetails castDetails = response.body();
                if(castDetails.getProfilePath()!=null){
                    Picasso.with(CastInfo.this).load(MovieConstants.MOVIE_IMAGE_BASE_URL + castDetails.getProfilePath()).into(cast_image);
                }
                if(castDetails.getBirthday()!=null){
                    String f = castDetails.getBirthday();
                    f = f.substring(0,4);
                    int age = (2018 - Integer.parseInt(f));
                    cast_info_dob.setText("Age :  " + age);
                }
                if(castDetails.getGender()!=null){
                    if(castDetails.getGender() == 2){
                        cast_info_gender.setText("Gender :  Male");
                    }
                    else{
                        cast_info_gender.setText("Gender :  Female");
                    }
                }
                if(castDetails.getName()!=null){
                    titleTextView.setText(castDetails.getName() + "");
                }
                if(castDetails.getPlaceOfBirth()!=null){
                    cast_info_pob.setText("Birth Place :  " +castDetails.getPlaceOfBirth() + "");
                }
                if(castDetails.getBiography()!=null){
                    cast_info_biography.setText(castDetails.getBiography() + "");
                }
                if(castDetails.getPopularity()!=null){
                    popularity.setText("Popularity :  " + castDetails.getPopularity() + "");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CastDetails> call, Throwable t) {

            }
        });

        retrofit2.Call<CastMovies> castMoviesCall = castInterface.getCastMovieDetails(id,MovieConstants.API_KEY);
        castMoviesCall.enqueue(new Callback<CastMovies>() {
            @Override
            public void onResponse(retrofit2.Call<CastMovies> call, Response<CastMovies> response) {
                CastMovies castMovies = response.body();
                castMoviesList.clear();
                castMoviesList.addAll(castMovies.getCast());
                castMovieAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<CastMovies> call, Throwable t) {

            }
        });
    }
}
