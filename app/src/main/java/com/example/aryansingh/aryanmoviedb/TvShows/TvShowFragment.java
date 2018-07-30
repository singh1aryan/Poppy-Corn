package com.example.aryansingh.aryanmoviedb.TvShows;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.Movies.ShowAll;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShowInfo.TvInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvShowFragment extends Fragment {

    TextView nowShowingTextView1,comingSoonTextView1, popularMoviesTextView1, topRatedTextView1;

    List<TvResult> onTheAirList;
    RecyclerView onTheAir;
    TvAdaptor onTheAirAdaptor;

    List<TvResult> airingTodayList;
    RecyclerView airingToday;
    TvAdaptor airingTodayAdapter;

    List<TvResult>topRatedResultsList;
    RecyclerView topRated;
    TvAdaptor topRatedAdapter;

    List<TvResult> popularMoviesResultsList;
    RecyclerView popularMovies;
    TvAdaptor popularAdapter;

    TextView showAll;


    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_show, container, false);

        nowShowingTextView1 = v.findViewById(R.id.onTheAir1);
        comingSoonTextView1 = v.findViewById(R.id.airingToday1);
        popularMoviesTextView1 = v.findViewById(R.id.popularTvTextView1);
        topRatedTextView1 = v.findViewById(R.id.topRatedTextView1);
        nowShowingTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAllTv.class);
                i.putExtra("type","onTheAir");
                startActivity(i);
            }
        });
        comingSoonTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAllTv.class);
                i.putExtra("type","airingToday");
                startActivity(i);
            }
        });
        topRatedTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAllTv.class);
                i.putExtra("type","topRated");
                startActivity(i);
            }
        });
        popularMoviesTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShowAllTv.class);
                i.putExtra("type","popular");
                startActivity(i);
            }
        });


        Retrofit retrofit = MovieDBClient.getClient();
        TvInterface tvInterface = retrofit.create(TvInterface.class);

        airingToday=v.findViewById(R.id.airingToday);
        airingTodayList=new ArrayList<>();
        airingTodayAdapter= new TvAdaptor(getContext(), airingTodayList, new TvAdaptor.TvClickListener() {
            @Override
            public void onTvClick(View view, int position) {
                TvResult tvResult = airingTodayList.get(position);
                Intent i = new Intent(getActivity(), TvInfo.class);
                i.putExtra("id",tvResult.getId());
                startActivity(i);
            }
        });
        airingToday.setAdapter(airingTodayAdapter);
        airingToday.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        topRated=v.findViewById(R.id.topRated);
        topRatedResultsList=new ArrayList<>();
        topRatedAdapter= new TvAdaptor(getContext(), topRatedResultsList, new TvAdaptor.TvClickListener() {
            @Override
            public void onTvClick(View view, int position) {
                TvResult tvResult = topRatedResultsList.get(position);
                Intent i = new Intent(getActivity(), TvInfo.class);
                i.putExtra("id",tvResult.getId());
                startActivity(i);
            }
        });
        topRated.setAdapter(topRatedAdapter);
        topRated.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        onTheAir=v.findViewById(R.id.onTheAir);
        onTheAirList=new ArrayList<>();
        onTheAirAdaptor= new TvAdaptor(getContext(), onTheAirList, new TvAdaptor.TvClickListener() {
            @Override
            public void onTvClick(View view, int position) {
                TvResult tvResult = onTheAirList.get(position);
                Intent i = new Intent(getActivity(), TvInfo.class);
                i.putExtra("id",tvResult.getId());
                startActivity(i);
            }
        });
        onTheAir.setAdapter(onTheAirAdaptor);
        onTheAir.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        popularMovies=v.findViewById(R.id.popular);
        popularMoviesResultsList=new ArrayList<>();
        popularAdapter= new TvAdaptor(getContext(), popularMoviesResultsList, new TvAdaptor.TvClickListener() {
            @Override
            public void onTvClick(View view, int position) {
                TvResult tvResult = popularMoviesResultsList.get(position);
                Intent i = new Intent(getActivity(), TvInfo.class);
                i.putExtra("id",tvResult.getId());
                startActivity(i);
            }
        });
        popularMovies.setAdapter(popularAdapter);
        popularMovies.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));



        Call<TvResponse> onTheAir =tvInterface.onTheAir(MovieConstants.API_KEY,"en-US",1);
        onTheAir.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if(response.body() != null) {
                    TvResponse tvResponse = response.body();
                    List<TvResult> tvTvResults = tvResponse.getResults();

                    onTheAirList.addAll(tvTvResults);
                    onTheAirAdaptor.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });

        Call<TvResponse> airingToday = tvInterface.airingToday(MovieConstants.API_KEY,"en-US",1);
        airingToday.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                TvResponse tvResponse = response.body();
                List<TvResult> tvTvResults = tvResponse.getResults();

                airingTodayList.addAll(tvTvResults);
                airingTodayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });

        Call<TvResponse> getPopular = tvInterface.getPopular(MovieConstants.API_KEY,"en-US",1);
        getPopular.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                TvResponse tvResponse = response.body();
                List<TvResult> tvTvResults = tvResponse.getResults();

                popularMoviesResultsList.addAll(tvTvResults);
                popularAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });

        Call<TvResponse> topRated = tvInterface.topRated(MovieConstants.API_KEY,"en-US",1);
        topRated.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                TvResponse tvResponse = response.body();
                List<TvResult> tvTvResults = tvResponse.getResults();

                topRatedResultsList.addAll(tvTvResults);
                topRatedAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });


        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
