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

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.util.LitePalUtil;
import com.example.chenx.sharebook.util.OverAllObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements ItemTouchHelperAdapter {


    private List<Movie_item> movieList;
    private Context mContext;
    private ListPopupWindow listPopupWindow;
    private String activity;
    private int type;
    private List<Movie_item> deleList=new ArrayList<>();

    public  void deleCollect(){
        LitePalUtil.deleteCollect(deleList,type);
    }

    @Override
    public void onIteamDelete(int position) {

        Movie_item movie_item=movieList.get(position);
        deleList.add(movie_item);
        movieList.remove(position);
        notifyItemChanged(position);
        notifyDataSetChanged();

    }

    @Override
    public void onIteamInsert(int position) {
        movieList.add(position,deleList.get(deleList.size()-1));
        deleList.remove(deleList.size()-1);
        notifyItemChanged(position);
        notifyDataSetChanged();

    }

    public  void setType(int type){
        this.type=type;
    }

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


   public MovieAdapter(List<Movie_item> list,String activity){
        movieList=list;
        this.activity=activity;

   }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie_item movie=movieList.get(i);
        viewHolder.movieName.setText(movie.name);
        viewHolder.moviesummary.setText(movie.summary);
        viewHolder.movieuploadr.setText(movie.uploader);
        Glide.with(mContext).load(OverAllObject.getImageUrl(movie.url)).into(viewHolder.movieImage);

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


               final android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
               handler.post(new Runnable() {
                   @Override
                   public void run() {

                       listPopupWindow = new ListPopupWindow(mContext);
                       listPopupWindow.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,new
                               String[]{"Add to Visited","Add to Want","Add to Collect"}));
                       listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                       listPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                       listPopupWindow.setAnchorView(holder.cardView);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点 此处show_btn为按钮
                       listPopupWindow.setModal(true);//设置是否是模式
                       listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                                   switch (position) {
                                       case 0:
                                           LitePalUtil.saveVisitedMovie(movieList.get(holder.getAdapterPosition()));
                                           break;
                                       case 1:
                                           LitePalUtil.saveWantMovie(movieList.get(holder.getAdapterPosition()));
                                           break;
                                       case 2:
                                           LitePalUtil.saveCollectMovie(movieList.get(holder.getAdapterPosition()));
                                           break;
                                       default:
                                           break;
                                   }

                                   listPopupWindow.dismiss();


                           }
                       });
                       if(activity.equals("Main")) {
                           listPopupWindow.show();
                       }



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
