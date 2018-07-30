package com.example.aryansingh.aryanmoviedb.TvShowInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.R;
import com.example.aryansingh.aryanmoviedb.TvShows.TvAdaptor;
import com.example.aryansingh.aryanmoviedb.TvShows.TvResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aryan Singh on 7/30/2018.
 */

public class EpisodeAdaptor extends RecyclerView.Adapter<EpisodeAdaptor.EpisodeViewHolder>{

    public Context context;
    public List<Episode> list;
    public EpisodeClickListener listener;

    public EpisodeAdaptor(Context context, List<Episode> list, EpisodeClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EpisodeAdaptor.EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.episode_layout,parent,false);
        return new EpisodeAdaptor.EpisodeViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdaptor.EpisodeViewHolder holder, int position) {

        Episode episode = list.get(position);
        if(episode.getName()!=null){
            holder.episode_title.setText((position + 1)+". " + episode.getName());
        }
        if(episode.getStillPath()!=null){
            Picasso.with(context).load(MovieConstants.MOVIE_IMAGE_BASE_URL + episode.getStillPath()).into(holder.poster);
        }
        if(episode.getOverview()!=null){
            holder.episode_overview.setText(episode.getOverview());
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface EpisodeClickListener {
        void onTvClick(View view, int position);
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView poster;
        TextView episode_overview,episode_runtime,episode_title;
        EpisodeClickListener episodeClickListener;

        public EpisodeViewHolder(View itemView, EpisodeClickListener listener) {
            super(itemView);
            episodeClickListener = listener;
            episode_overview = itemView.findViewById(R.id.episode_overview);
            //episode_runtime = itemView.findViewById(R.id.episode_runtime);
            episode_title = itemView.findViewById(R.id.episode_title);
            poster = itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            episodeClickListener.onTvClick(view,getAdapterPosition());
        }
    }
}
