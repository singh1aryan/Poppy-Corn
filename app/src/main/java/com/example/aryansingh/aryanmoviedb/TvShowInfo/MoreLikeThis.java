package com.example.aryansingh.aryanmoviedb.TvShowInfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aryansingh.aryanmoviedb.EndlessRecyclerViewScrollListener;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShows.TvAdaptor;
import com.example.aryansingh.aryanmoviedb.TvShows.TvInterface;
import com.example.aryansingh.aryanmoviedb.TvShows.TvResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MoreLikeThis extends Fragment {

    ArrayList<TvResult> similarMoviesList = new ArrayList<>();
    TvAdaptor tvAdaptor;
    RecyclerView recyclerView;
    long id;
    private EndlessRecyclerViewScrollListener scrollListener;
    Retrofit retrofit = MovieDBClient.getClient();
    TvInterface tvInterface = retrofit.create(TvInterface.class);

    private OnFragmentInteractionListener mListener;

    public MoreLikeThis() {
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
        View v = inflater.inflate(R.layout.fragment_more_like_this, container, false);


        Bundle bundle = getArguments();
        if(bundle!=null) {
            id = bundle.getLong("id");
        }
        
        recyclerView = v.findViewById(R.id.tv_info_episodes_recycler_view);
        tvAdaptor = new TvAdaptor(getContext(), similarMoviesList, new TvAdaptor.TvClickListener() {
            @Override
            public void onTvClick(View view, int position) {
                TvResult tvResult = similarMoviesList.get(position);
                Intent i = new Intent(getActivity(), TvInfo.class);
                i.putExtra("id",tvResult.getId());
                startActivity(i);
            }
        });
        recyclerView.setAdapter(tvAdaptor);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };


        Call<SimilarTv> similarTvCall = tvInterface.getSimilarTv(id, MovieConstants.API_KEY,"english",1);
        similarTvCall.enqueue(new Callback<SimilarTv>() {
            @Override
            public void onResponse(Call<SimilarTv> call, Response<SimilarTv> response) {
                SimilarTv similarTv = response.body();
                List<TvResult> resultList = similarTv.getResults();

                similarMoviesList.clear();
                similarMoviesList.addAll(resultList);
                tvAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SimilarTv> call, Throwable t) {

            }
        });


        return v;
    }

    private void loadNextDataFromApi(int page) {
        page++;
        Call<SimilarTv> similarTvCall = tvInterface.getSimilarTv(id, MovieConstants.API_KEY,"english",page);
        similarTvCall.enqueue(new Callback<SimilarTv>() {
            @Override
            public void onResponse(Call<SimilarTv> call, Response<SimilarTv> response) {
                SimilarTv similarTv = response.body();
                List<TvResult> resultList = similarTv.getResults();

                similarMoviesList.clear();
                similarMoviesList.addAll(resultList);
                tvAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SimilarTv> call, Throwable t) {

            }
        });

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
