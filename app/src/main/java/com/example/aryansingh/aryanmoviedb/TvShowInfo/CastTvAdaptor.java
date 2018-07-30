package com.example.aryansingh.aryanmoviedb.TvShowInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor;
import com.example.aryansingh.aryanmoviedb.CastInfo.Casts;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aryan Singh on 7/30/2018.
 */

    public class CastTvAdaptor extends RecyclerView.Adapter<com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.CastViewHolder> {

        public Context context;
        public List<Casts> list;
        public com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.MoviesClickListener listener;

        public CastTvAdaptor(Context context, List<Casts> list, com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.MoviesClickListener listener){
            this.context = context;
            this.list = list;
            this.listener = listener;
        }

        @NonNull
        @Override
        public com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
            return new com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.CastViewHolder(itemView,listener);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.CastViewHolder holder, int position) {
            Casts s = list.get(position);
//            if(s.getPosterPath()!=null) {
//                Picasso.with(context).load(MovieConstants.MOVIE_IMAGE_BASE_URL + s.getPosterPath()).into(holder.contentImageView);
//            }
//            if(s.getTitle()!=null){
//                holder.contentTextView.setText(s.getTitle());
//            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public interface MoviesClickListener {
            void onMovieClick(View view, int position);
        }

        public static class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            ImageView contentImageView;
            TextView contentTextView;
            com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.MoviesClickListener moviesClickListener;

            public CastViewHolder(View itemView, com.example.aryansingh.aryanmoviedb.CastInfo.CastMovieAdaptor.MoviesClickListener listener) {
                super(itemView);
                moviesClickListener = listener;
                contentImageView = itemView.findViewById(R.id.contentImageView);
                contentTextView = itemView.findViewById(R.id.contentTextView);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                moviesClickListener.onMovieClick(view,getAdapterPosition());
            }
        }
    }