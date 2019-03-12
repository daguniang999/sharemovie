package com.example.chenx.sharebook;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieList;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView movieImage;
        TextView movieName;
        TextView moviesummary;
        TextView movieuploadr;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view.findViewById(R.id.card_movie_item);
            moviesummary=(TextView)view.findViewById(R.id.movie_summary);
            movieName=(TextView)view.findViewById(R.id.movie_name);
            movieImage=(ImageView)view.findViewById(R.id.movie_image);
            movieuploadr=(TextView)view.findViewById(R.id.movie_uploader);
        }


   }


   public MovieAdapter(List<Movie> list){
        movieList=list;
   }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie=movieList.get(i);
        viewHolder.movieName.setText(movie.getName());
        viewHolder.moviesummary.setText(movie.getSummary());
        viewHolder.movieuploadr.setText(movie.getUploader());
        Glide.with(mContext).load(movie.getUrl()).into(viewHolder.movieImage);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       if(mContext==null){
           mContext=viewGroup.getContext();
       }
       View view=LayoutInflater.from(mContext).inflate(R.layout.movie_item,viewGroup,false);
       final ViewHolder holder=new ViewHolder(view);


       return holder;
        // return null;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
