package com.example.aryansingh.aryanmoviedb.Movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.MovieInfo.MovieInfo;
import com.example.aryansingh.aryanmoviedb.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesFragment extends Fragment {

    TextView nowShowingTextView1,comingSoonTextView1, popularMoviesTextView1, topRatedTextView1;
    LinearLayout mainView;

    List<Result> nowShowingResultsList;
    MoviesAdaptor nowShowingAdapter;
    RecyclerView nowShowing;

    List<Result> comingSoonResultsList;
    RecyclerView comingSoon;
    MoviesAdaptor comingSoonAdapter;

    List<Result>topRatedResultsList;
    RecyclerView topRated;
    MoviesAdaptor topRatedMoviesAdapter;

    List<Result> popularMoviesResultsList;
    RecyclerView popularMovies;
    MoviesAdaptor popularMoviesAdapter;
    boolean a,b,c,d;

    private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//           }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movies, container, false);

                mainView = v.findViewById(R.id.mainView);
                nowShowingTextView1 = v.findViewById(R.id.nowShowingTextView1);
                comingSoonTextView1 = v.findViewById(R.id.comingSoonTextView1);
                popularMoviesTextView1 = v.findViewById(R.id.popularMoviesTextView1);
                topRatedTextView1 = v.findViewById(R.id.topRatedTextView1);
//
        nowShowingTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAll.class);
                i.putExtra("type","nowShowing");
                startActivity(i);
            }
        });
        comingSoonTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAll.class);
                i.putExtra("type","comingSoon");
                startActivity(i);
            }
        });
        topRatedTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAll.class);
                i.putExtra("type","topRated");
                startActivity(i);
            }
        });
        popularMoviesTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAll.class);
                i.putExtra("type","popular");
                startActivity(i);
            }
        });

        Retrofit retrofit = MovieDBClient.getClient();
        MoviesInterface moviesInterface =retrofit.create(MoviesInterface.class);

        // Now Showing Recycler View
        nowShowing=v.findViewById(R.id.nowShowing);
        nowShowingResultsList=new ArrayList<>();
        nowShowingAdapter= new MoviesAdaptor(getContext(),nowShowingResultsList, new MoviesAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                //Now Showing Clicks Handled Here

                Result movieResults = nowShowingResultsList.get(position);
                Intent i=new Intent(getContext(), MovieInfo.class);
                i.putExtra("movieId",  movieResults.getId());
                startActivity(i);

            }
        });
        nowShowing.setAdapter(nowShowingAdapter);
        nowShowing.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


//
//        // Coming soon Recycler View
        comingSoon=v.findViewById(R.id.comingSoon);
        comingSoonResultsList=new ArrayList<>();
        comingSoonAdapter= new MoviesAdaptor(getContext(), comingSoonResultsList, new MoviesAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                //Coming Soon Clicks Handled Here
                Result movieResults = comingSoonResultsList.get(position);
                Intent i=new Intent(getContext(), MovieInfo.class);
                i.putExtra("movieId",  movieResults.getId());
                startActivity(i);
            }
        });
        comingSoon.setAdapter(comingSoonAdapter);
        comingSoon.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        // Top Rated Recycler View
        topRated=v.findViewById(R.id.topRated);
        topRatedResultsList=new ArrayList<>();
        topRatedMoviesAdapter= new MoviesAdaptor(getContext(), topRatedResultsList, new MoviesAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                // Top Rated Clicks Handled Here
                Result movieResults = topRatedResultsList.get(position);
                Intent i=new Intent(getContext(), MovieInfo.class);
                i.putExtra("movieId",  movieResults.getId());
                startActivity(i);
            }
        });

        topRated.setAdapter(topRatedMoviesAdapter);
        topRated.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        popularMovies=v.findViewById(R.id.popularMovies);
        popularMoviesResultsList=new ArrayList<>();
        popularMoviesAdapter =new MoviesAdaptor(getContext(), popularMoviesResultsList, new MoviesAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {

                Result movieResults = popularMoviesResultsList.get(position);
                Intent i=new Intent(getContext(), MovieInfo.class);
                i.putExtra("movieId",  movieResults.getId());
                startActivity(i);

            }
        });

        popularMovies.setAdapter(popularMoviesAdapter);
        popularMovies.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        //now showing
        Call<MovieResponse> getPlaying =moviesInterface.getPlaying(MovieConstants.API_KEY,"en-US",1);
        getPlaying.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                List<Result> movieResults=movieResponse.results;

                nowShowingResultsList.addAll(movieResults);
                nowShowingAdapter.notifyDataSetChanged();
                a=true;

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        //coming soon
        Call<MovieResponse> getUpcoming=moviesInterface.getUpcoming(MovieConstants.API_KEY,"en-US",1);
        getUpcoming.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                List<Result>movieResults = movieResponse.results;
                comingSoonResultsList.addAll(movieResults);
                comingSoonAdapter.notifyDataSetChanged();
                b=true;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        //Top Rated
        final Call<MovieResponse> topRated=moviesInterface.topRated(MovieConstants.API_KEY,"en-US",1);
        topRated.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                List<Result> movieResults = movieResponse.results;
                topRatedResultsList.addAll(movieResults);
                topRatedMoviesAdapter.notifyDataSetChanged();
                c=true;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        //popular
        Call<MovieResponse> popular =moviesInterface.getPopular(MovieConstants.API_KEY,"en-US",1);
        popular.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                List<Result>movieResults = movieResponse.results;
                popularMoviesResultsList.addAll(movieResults);
                popularMoviesAdapter.notifyDataSetChanged();
                d=true;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        if(a && b && c && d)
            mainView.setVisibility(View.VISIBLE);

        return v;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
