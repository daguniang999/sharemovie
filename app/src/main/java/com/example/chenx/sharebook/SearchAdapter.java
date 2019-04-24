package com.example.chenx.sharebook;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.gson.SimpleBack;
import com.example.chenx.sharebook.util.HttpUtil;
import com.example.chenx.sharebook.util.LitePalUtil;
import com.example.chenx.sharebook.util.OverAllObject;
import com.example.chenx.sharebook.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        private List<Movie_item> movieList;
        private Context mContext;
        private int type;

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



        public SearchAdapter(List<Movie_item> list){
            movieList=list;
        }



        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                Movie_item movie = movieList.get(i);
                ((ViewHolder) viewHolder).movieName.setText(movie.name);
                ((ViewHolder) viewHolder).moviesummary.setText(movie.summary);
                ((ViewHolder) viewHolder).movieuploadr.setText(movie.uploader);
                Glide.with(mContext).load(OverAllObject.getImageUrl(movie.url)).into(((ViewHolder) viewHolder).movieImage);

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {

            if (mContext == null) {
                mContext = viewGroup.getContext();
            }

            final ViewHolder holder;
            final View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, viewGroup, false);
            holder = new ViewHolder(view);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = holder.getAdapterPosition();
                    final Movie_item movie_item = movieList.get(position);
                    Intent intent = new Intent(mContext, CommentActivity.class);

                    intent.putExtra("movie_id", movie_item.id);
                    intent.putExtra("movie_name", movie_item.name);
                    intent.putExtra("movie_uploader", movie_item.uploader);
                    intent.putExtra("movie_summary", movie_item.summary);
                    intent.putExtra("movie_url", movie_item.url);
                    mContext.startActivity(intent);
                }
            });

            return holder;
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
}

