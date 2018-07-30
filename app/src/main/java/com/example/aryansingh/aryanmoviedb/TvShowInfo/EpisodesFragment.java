package com.example.aryansingh.aryanmoviedb.TvShowInfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesInterface;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShows.TvInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EpisodesFragment extends Fragment {

    Spinner spinner;

    long id;
    long seasons,episodes;
    ArrayList<Episode> episodeArrayList;
    EpisodeAdaptor episodeAdaptor;
    RecyclerView episodes_recycler;

    private OnFragmentInteractionListener mListener;

    TvInterface tvInterface;

    public EpisodesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_episodes, container, false);
        episodes_recycler = v.findViewById(R.id.tv_info_episodes_recycler_view);

        episodeArrayList = new ArrayList<>();
        episodeAdaptor = new EpisodeAdaptor(getContext(), episodeArrayList, new EpisodeAdaptor.EpisodeClickListener() {
            @Override
            public void onTvClick(View view, int position) {

            }
        });
        episodes_recycler.setAdapter(episodeAdaptor);
        episodes_recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        //episodes_recycler.setNestedScrollingEnabled(false);


        Retrofit retrofit = MovieDBClient.getClient();
        tvInterface = retrofit.create(TvInterface.class);

        Bundle bundle = getArguments();
        if(bundle!=null) {
            id = bundle.getLong("id");
        }

        spinner = v.findViewById(R.id.spinner);

        retrofit2.Call<TvDetails> tvDetailsCall = tvInterface.getTvDetails(id, MovieConstants.API_KEY);
        tvDetailsCall.enqueue(new Callback<TvDetails>() {
            @Override
            public void onResponse(retrofit2.Call<TvDetails> call, Response<TvDetails> response) {
                TvDetails tvDetails = response.body();
                seasons = tvDetails.getNumberOfSeasons();
                episodes = tvDetails.getNumberOfEpisodes();

                String[] season = new String[(int)seasons];
                for(int i=0; i<(int)seasons;i++){
                    season[i]= "Season"+(i+1);
                }
//              String[] items = season.toArray(new String[season.size()]);

                //String[] items = {"Season 1","Season 2"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,season);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<TvDetails> call, Throwable t) {

            }
        });



//
//


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id1) {

                retrofit2.Call<SeasonDetails> seasonDetailsCall = tvInterface.getSeasonDetails(id,position+1, MovieConstants.API_KEY);
                seasonDetailsCall.enqueue(new Callback<SeasonDetails>() {
                    @Override
                    public void onResponse(retrofit2.Call<SeasonDetails> call, Response<SeasonDetails> response) {
                        SeasonDetails seasonDetails = response.body();
                        List<Episode> episodeList = seasonDetails.getEpisodes();

                        episodeArrayList.clear();
                        episodeArrayList.addAll(episodeList);
                        episodeAdaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<SeasonDetails> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
