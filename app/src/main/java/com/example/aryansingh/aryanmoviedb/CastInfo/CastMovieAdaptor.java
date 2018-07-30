package com.example.aryansingh.aryanmoviedb.CastInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aryansingh.aryanmoviedb.CastInfo.Casts;
import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aryan Singh on 7/21/2018.
 */

public class CastMovieAdaptor extends RecyclerView.Adapter<CastMovieAdaptor.CastViewHolder> {

    public Context context;
    public List<Casts> list;
    public MoviesClickListener listener;

    public CastMovieAdaptor(Context context, List<Casts> list, MoviesClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new CastViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Casts s = list.get(position);
        if(s.getPosterPath()!=null) {
            Picasso.with(context).load(MovieConstants.MOVIE_IMAGE_BASE_URL + s.getPosterPath()).into(holder.contentImageView);
        }
        if(s.getTitle()!=null){
            holder.contentTextView.setText(s.getTitle());
        }
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
        MoviesClickListener moviesClickListener;

        public CastViewHolder(View itemView, MoviesClickListener listener) {
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