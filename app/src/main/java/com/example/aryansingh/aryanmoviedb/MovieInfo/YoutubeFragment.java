package com.example.aryansingh.aryanmoviedb.MovieInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.MovieDBClient;
import com.example.aryansingh.aryanmoviedb.Movies.MoviesInterface;
import com.example.aryansingh.aryanmoviedb.PlayerConfig;
import com.example.aryansingh.aryanmoviedb.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class YoutubeFragment extends Fragment{
    private FragmentActivity myContext;

    long id;
    private OnFragmentInteractionListener mListener;
    Retrofit retrofit = MovieDBClient.getClient();
    final MoviesInterface moviesInterface = retrofit.create(MoviesInterface.class);

    public YoutubeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;

        }

        super.onAttach(activity);
    }


    public static YoutubeFragment newInstance(String param1, String param2) {
        YoutubeFragment fragment = new YoutubeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_youtube, container, false);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            id = bundle.getLong("id");
        }


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(PlayerConfig.YOUTUBE_API, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    Call<VideoResponse> videoResponseCall = moviesInterface.getVideo(id, MovieConstants.API_KEY);
                    videoResponseCall.enqueue(new Callback<VideoResponse>() {
                        @Override
                        public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                            VideoResponse videoResponse = response.body();
                            List<Result> results = videoResponse.getResults();
                            if (results.size() > 0) {
                                player.loadVideo(results.get(0).getKey());
                                player.play();
                            }
                        }

                        @Override
                        public void onFailure(Call<VideoResponse> call, Throwable t) {

                        }
                    });
                }
            }


            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });



//

            return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

//