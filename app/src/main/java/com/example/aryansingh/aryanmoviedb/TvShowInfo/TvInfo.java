package com.example.aryansingh.aryanmoviedb.TvShowInfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aryansingh.aryanmoviedb.CastInfo.CastInfo;
import com.example.aryansingh.aryanmoviedb.MainActivity;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.MovieInfo.*;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesFragment;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesInterface;
import com.example.aryansingh.aryanmoviedb.NetflixFragment;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShows.TvInterface;
import com.example.aryansingh.aryanmoviedb.TvShows.TvShowFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvInfo extends AppCompatActivity implements MoreLikeThis.OnFragmentInteractionListener,EpisodesFragment.OnFragmentInteractionListener{
    Bundle bundle;
    ImageView backdrop,poster,like,mylist,share;
    TextView tv_title,genreTextView,tv_rating,tv_info_overview
            ,tv_release_date,showMore,tv_seasons;
    String genre = "";
    long seasonCount,episodeCount;
    ArrayList<Long> likes = new ArrayList<>();
    ArrayList<Long> mylists = new ArrayList<>();
    ProgressBar progressBar;

    RecyclerView tv_info_cast_recycler_view;
    ArrayList<com.example.aryansingh.aryanmoviedb.MovieInfo.Cast> castArrayList;
    CastAdaptor castAdaptor;

    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_info);

        final Intent intent = getIntent();
        id = intent.getLongExtra("id",0);


        bundle = new Bundle();
        TabLayout tabLayout = findViewById(R.id.tabLayout1);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00ff00"));
        tabLayout.addTab(tabLayout.newTab().setText("EPISODES"));
        tabLayout.addTab(tabLayout.newTab().setText("MORE LIKE THIS"));
        //tabLayout.addTab(tabLayout.newTab().setText(""));

        final ViewPager viewPager = findViewById(R.id.pager1);
        final PagerAdaptor adaptor = new PagerAdaptor(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adaptor);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tv_seasons = findViewById(R.id.tv_seasons);
        //tv_duration = findViewById(R.id.tv_duration);
        showMore = findViewById(R.id.showMore);
        tv_title = findViewById(R.id.tv_title);
        genreTextView = findViewById(R.id.genreTextView);
        tv_rating = findViewById(R.id.tv_rating);
        tv_info_overview = findViewById(R.id.tv_info_overview);
        tv_release_date = findViewById(R.id.tv_release_date);
        like = findViewById(R.id.like);
        share = findViewById(R.id.share);
        mylist = findViewById(R.id.mylist);
        backdrop = findViewById(R.id.backdrop);
        progressBar = findViewById(R.id.progressBar);
        poster = findViewById(R.id.poster);
        tv_info_cast_recycler_view = findViewById(R.id.tv_info_cast_recycler_view);
        castArrayList = new ArrayList<>();


        Retrofit retrofit = MovieDBClient.getClient();
        TvInterface tvInterface = retrofit.create(TvInterface.class);


        castAdaptor = new CastAdaptor(this, castArrayList, new CastAdaptor.MoviesClickListener() {
            @Override
            public void onMovieClick(View view, int position) {
                com.example.aryansingh.aryanmoviedb.MovieInfo.Cast castResults = castArrayList.get(position);
                Intent i=new Intent(TvInfo.this, CastInfo.class);
                i.putExtra("castId",  castResults.getId());
                startActivity(i);
            }
        });
        tv_info_cast_recycler_view.setAdapter(castAdaptor);
        tv_info_cast_recycler_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showMore.getText().equals("Hide")){
                    showMore.setText("Show More");
                    tv_info_overview.setMaxLines(4);
                }
                else{
                    tv_info_overview.setMaxLines(1000);
                    showMore.setText("Hide");
                }
            }
        });

        mylist.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(mylists.contains(id)){
                    mylists.remove(id);
                    mylist.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
                    Toast.makeText(TvInfo.this,"Removed from your List",Toast.LENGTH_LONG).show();
                }
                else{
                    mylists.add(id);
                    mylist.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
                    Toast.makeText(TvInfo.this,"Added to your List",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(TvInfo.this,"Removed from Favorites",Toast.LENGTH_LONG).show();
                }
                else{
                    likes.add(id);
                    like.setImageDrawable(getDrawable(R.drawable.ic_thumb_up_blue_24dp));
                    Toast.makeText(TvInfo.this,"Added to Favorites",Toast.LENGTH_LONG).show();
                }
            }
        });

        final retrofit2.Call<com.example.aryansingh.aryanmoviedb.MovieInfo.Cast> crewCall = tvInterface.getCrew(id,MovieConstants.API_KEY);
        crewCall.enqueue(new Callback<com.example.aryansingh.aryanmoviedb.MovieInfo.Cast>() {
            @Override
            public void onResponse(retrofit2.Call<com.example.aryansingh.aryanmoviedb.MovieInfo.Cast> call, Response<com.example.aryansingh.aryanmoviedb.MovieInfo.Cast> response) {
                com.example.aryansingh.aryanmoviedb.MovieInfo.Cast crewList = response.body();
                castArrayList.clear();
                castArrayList.addAll(crewList.getCast());
                castAdaptor.notifyDataSetChanged();

            }
            @Override
            public void onFailure(retrofit2.Call<com.example.aryansingh.aryanmoviedb.MovieInfo.Cast> call, Throwable t) {

            }
        });

        retrofit2.Call<TvDetails> tvDetailsCall = tvInterface.getTvDetails(id, MovieConstants.API_KEY);
        tvDetailsCall.enqueue(new Callback<TvDetails>() {
            @Override
            public void onResponse(retrofit2.Call<TvDetails> call, Response<TvDetails> response) {
                TvDetails tvDetails = response.body();
                seasonCount = tvDetails.getNumberOfSeasons();

                    if (tvDetails.getBackdropPath() != null) {
                        Picasso.with(TvInfo.this).load(MovieConstants.SLIDER_BASE_URL + tvDetails.getBackdropPath()).into(backdrop);
                        progressBar.setVisibility(View.GONE);
                    }
                if (tvDetails.getPosterPath() != null) {
                    Picasso.with(TvInfo.this).load(MovieConstants.MOVIE_IMAGE_BASE_URL + tvDetails.getPosterPath()).into(poster);
                    //progressBar.setVisibility(View.GONE);
                }
                    if (tvDetails.getName() != null) {
                        tv_title.setText(tvDetails.getName());
                    }
                    if(tvDetails.getOverview()!=null){
                        tv_info_overview.setText(tvDetails.getOverview());
                    }
                    if(tvDetails.getVoteAverage()!=null){
                        tv_rating.setText("" + tvDetails.getVoteAverage() + "");
                    }
                    if(tvDetails.getFirstAirDate()!=null){
                        String s = tvDetails.getFirstAirDate().substring(0,4);
                        tv_release_date.setText(s);
                    }
                if(tvDetails.getGenres().size()!=0) {
                    for (int i = 0; i < tvDetails.getGenres().size(); i++) {
                        genre = genre.concat(tvDetails.getGenres().get(i).getName() + " . ");
                    }
                    genre = genre.substring(0,genre.length()-2);
                    genreTextView.setText(genre + "");
                }
                if(tvDetails.getNumberOfSeasons() != null){
                    tv_seasons.setText(tvDetails.getNumberOfSeasons() + " Seasons");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<TvDetails> call, Throwable t) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class PagerAdaptor extends FragmentStatePagerAdapter {

        int nTabs;

        public PagerAdaptor(FragmentManager fm, int nTabs) {
            super(fm);
            this.nTabs = nTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    EpisodesFragment episodesFragment = new EpisodesFragment();
                    bundle.putLong("id",id);
                    episodesFragment.setArguments(bundle);
                    return episodesFragment;
                case 1:
                    MoreLikeThis moreLikeThis = new MoreLikeThis();
                    bundle.putLong("id",id);
                    moreLikeThis.setArguments(bundle);
                    return moreLikeThis;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return nTabs;
        }
    }



}
