package com.example.chenx.sharebook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie_item;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie_item> movieList;
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


   public MovieAdapter(List<Movie_item> list){
        movieList=list;
   }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie_item movie=movieList.get(i);
        viewHolder.movieName.setText(movie.name);
        viewHolder.moviesummary.setText(movie.summary);
        viewHolder.movieuploadr.setText(movie.uploader);
        Glide.with(mContext).load(movie.url).into(viewHolder.movieImage);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       if(mContext==null){
           mContext=viewGroup.getContext();
       }
       final View view=LayoutInflater.from(mContext).inflate(R.layout.movie_item,viewGroup,false);
       final ViewHolder holder=new ViewHolder(view);

       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position=holder.getAdapterPosition();

               Intent intent=new Intent(mContext,CommentActivity.class);
               Movie_item movie_item=movieList.get(position);
               intent.putExtra("movie_id",movie_item.id);
               intent.putExtra("movie_name",movie_item.name);
               intent.putExtra("movie_uploader",movie_item.uploader);
               intent.putExtra("movie_summary",movie_item.summary);
               mContext.startActivity(intent);


           }
       });

       return holder;
        // return null;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
