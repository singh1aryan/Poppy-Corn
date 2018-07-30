package com.example.aryansingh.aryanmoviedb.TvShows;

/**
 * Created by Aryan Singh on 7/27/2018.
 */

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
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Created by Aryan Singh on 7/21/2018.
 */

public class TvAdaptor extends RecyclerView.Adapter<TvAdaptor.TvViewHolder> {

    public Context context;
    public List<TvResult> list;
    public TvAdaptor.TvClickListener listener;

    public TvAdaptor(Context context, List<TvResult> list, TvAdaptor.TvClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        return new TvAdaptor.TvViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdaptor.TvViewHolder holder, int position) {

        TvResult s = list.get(position);
////        if(s.getPoster_path()==null) {
////            Picasso.with(context).load(R.drawable.no_image).into(holder.contentImageView);
        if(s.getPosterPath()!=null) {
            Picasso.with(context).load(MovieConstants.MOVIE_IMAGE_BASE_URL + s.getPosterPath()).into(holder.contentImageView);
        }
//        if(s.getReleaseDate()!=null){
//            holder.contentTextView.setText(s.getTitle()+" ("+s.getReleaseDate().substring(0,4)+")");
//        }else
//        {
//
//        }
        if(s.getName()!=null){
            holder.contentTextView.setText(s.getName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface TvClickListener {
        void onTvClick(View view, int position);
    }

    public static class TvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView contentImageView;
        TextView contentTextView;
        TvAdaptor.TvClickListener tvClickListener;

        public TvViewHolder(View itemView, TvAdaptor.TvClickListener listener) {
            super(itemView);
            tvClickListener = listener;
            contentImageView = itemView.findViewById(R.id.contentImageView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            tvClickListener.onTvClick(view,getAdapterPosition());
        }
    }
}

