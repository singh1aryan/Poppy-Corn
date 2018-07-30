package com.example.aryansingh.aryanmoviedb.Movies;

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

import com.example.aryansingh.aryanmoviedb.MovieConstants;
import com.example.aryansingh.aryanmoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aryan Singh on 7/21/2018.
 */

public class MoviesAdaptor extends RecyclerView.Adapter<MoviesAdaptor.MoviesViewHolder> {

    public Context context;
    public List<Result> list;
    public MoviesClickListener listener;

    public MoviesAdaptor(Context context, List<Result> list, MoviesClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new MoviesViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Result s = list.get(position);
//        if(s.getPoster_path()==null) {
//            Picasso.with(context).load(R.drawable.no_image).into(holder.contentImageView);
        if(s.getPosterPath()!=null) {
            Picasso.with(context).load(MovieConstants.MOVIE_IMAGE_BASE_URL + s.getPosterPath()).into(holder.contentImageView);
        }
//        if(s.getReleaseDate()!=null){
//            holder.contentTextView.setText(s.getTitle()+" ("+s.getReleaseDate().substring(0,4)+")");
//        }else
        if(s.getTitle() != null)
        {
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

    public static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView contentImageView;
        TextView contentTextView;
        MoviesClickListener moviesClickListener;

        public MoviesViewHolder(View itemView, MoviesClickListener listener) {
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
