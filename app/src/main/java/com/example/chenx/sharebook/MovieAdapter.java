package com.example.chenx.sharebook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.util.OverAllObject;

import java.net.URL;
import java.util.List;
import java.util.logging.Handler;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie_item> movieList;
    private Context mContext;
    private ListPopupWindow listPopupWindow;

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
//        final ViewHolder viewHolder1=viewHolder;
//        viewHolder.movieImage.setImageBitmap();
//      //  Bitmap bitmap=BitmapFactory.decodeStream(url.openStream());
//        //viewHolder.movieImage.setImageURI();
//        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {

                Glide.with(mContext).load(OverAllObject.getImageUrl(movie.url)).into(viewHolder.movieImage);

//            }
//        });
//
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
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
               intent.putExtra("movie_url",movie_item.url);
               mContext.startActivity(intent);


           }
       });


       holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               

               android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
               handler.post(new Runnable() {
                   @Override
                   public void run() {

                       listPopupWindow = new ListPopupWindow(mContext);
                       listPopupWindow.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,new
                               String[]{"hello","world","welcom"}));
                       listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                       listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                       listPopupWindow.setAnchorView(holder.cardView);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点 此处show_btn为按钮
                       listPopupWindow.setModal(true);//设置是否是模式
                       listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                               //Log.d("qwqw", "onItemClick: ");
                               switch (position){
                                   case 0:
                                       Log.d("qwqw", "1");
                                       break;
                                   case 1:
                                       Log.d("qwqw", "2");
                                       break;
                                   case 2:
                                       Log.d("qwqw", "3");
                                       break;
                               }
                               //   notifyItemChanged(position,movieList.size()-position);
                           }
                       });
                       listPopupWindow.show();




                   }
               });

               return false;
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
