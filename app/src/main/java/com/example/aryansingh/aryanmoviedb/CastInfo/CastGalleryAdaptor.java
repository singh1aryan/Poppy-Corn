package com.example.aryansingh.aryanmoviedb.CastInfo;

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
import com.example.aryansingh.aryanmoviedb.Search.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CastGalleryAdaptor extends RecyclerView.Adapter<CastGalleryAdaptor.MoviesViewHolder> {

    public Context context;
    public List<Profile> list;
    public CastGalleryAdaptor.MoviesClickListener listener;
//
//    boolean movie,tv,person;

    public CastGalleryAdaptor(Context context, List<Profile> list, CastGalleryAdaptor.MoviesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CastGalleryAdaptor.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cast_gallery_layout, parent, false);
        return new CastGalleryAdaptor.MoviesViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CastGalleryAdaptor.MoviesViewHolder holder, int position) {

       Profile profile = list.get(position);

       if(profile.getFilePath()!=null) {
           Picasso.with(context).load(MovieConstants.MOVIE_IMAGE_BASE_URL + profile.getFilePath()).into(holder.backdrop);
       }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface MoviesClickListener {
        void onMovieClick(View view, int position);
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView backdrop;
        CastGalleryAdaptor.MoviesClickListener moviesClickListener;

        public MoviesViewHolder(View view, CastGalleryAdaptor.MoviesClickListener listener) {
            super(view);
            moviesClickListener = listener;
            backdrop = view.findViewById(R.id.backdrop);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            moviesClickListener.onMovieClick(view, getAdapterPosition());
        }
    }
}