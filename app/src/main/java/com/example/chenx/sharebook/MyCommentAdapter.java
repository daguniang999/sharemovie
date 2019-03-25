package com.example.chenx.sharebook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.gson.Movie_comment;
import com.example.chenx.sharebook.util.OverAllObject;

import java.util.List;

public class MyCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_TITILE=0;
    private static final int TYPE_COMMENT=1;
    private static final int TYPE_FOOTER=2;
    private static final int TYPE_END=3;

    private Context mContent;
    private List<Movie_comment> movieCommentList;
    private Movie_item commenttTitle;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof TitleViewHolder){
            if(commenttTitle==null){
                return;
            }

            ((TitleViewHolder )viewHolder).movieName.setText(commenttTitle.name);
            ((TitleViewHolder )viewHolder).movieUploder.setText(commenttTitle.uploader);
            ((TitleViewHolder )viewHolder).movieSummary.setText(commenttTitle.summary);
           // ((TitleViewHolder )viewHolder).movieName.setText(commenttTitle.url);
           // Log.d("eeeepp", commenttTitle.url);
            Glide.with(mContent).load(OverAllObject.getImageUrl(commenttTitle.url)).into(((TitleViewHolder) viewHolder).movieImage);


        }else if(viewHolder instanceof CommentViewHolder){
            Movie_comment movie_comment=movieCommentList.get(i-1);
            if(movie_comment==null) {
                return;
            }

            ((CommentViewHolder)viewHolder).commentName.setText(movie_comment.name);
            ((CommentViewHolder)viewHolder).commentTime.setText(movie_comment.uploadtime);
            ((CommentViewHolder)viewHolder).commentContent.setText(movie_comment.content);

        }



    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(mContent==null){
            mContent=viewGroup.getContext();
        }

        if(i==TYPE_TITILE){
            View view=LayoutInflater.from(mContent).inflate(R.layout.comment_title,viewGroup,false);
            TitleViewHolder titleViewHolder=new TitleViewHolder(view);
            return titleViewHolder;
        }else if(i==TYPE_COMMENT){
            View view=LayoutInflater.from(mContent).inflate(R.layout.comment_items,viewGroup,false);
            CommentViewHolder commentViewHolder=new CommentViewHolder(view);
            return commentViewHolder;
        }



        return null;
    }




    class TitleViewHolder extends RecyclerView.ViewHolder{

        ImageView movieImage;
        TextView movieName;
        TextView movieUploder;
        TextView movieSummary;

        public TitleViewHolder(View view){
            super(view);
            movieImage=(ImageView)view.findViewById(R.id.image_comment);
            movieName=(TextView)view.findViewById(R.id.movie_name);
            movieSummary=(TextView)view.findViewById(R.id.movie_summary);
            movieUploder=(TextView)view.findViewById(R.id.movie_uploader);

        }

    }

    class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView commentName;
        TextView commentTime;
        TextView commentContent;


        public CommentViewHolder(View view){
            super(view);
            commentName=(TextView)view.findViewById(R.id.comment_name);
            commentTime=(TextView)view.findViewById(R.id.comment_time);
            commentContent=(TextView)view.findViewById(R.id.comment_content);


        }

    }




    public MyCommentAdapter(List<Movie_comment> list){

        movieCommentList=list;

    }


    public void setTitle(Movie_item movie){
        commenttTitle=movie;
    }


    @Override
    public int getItemCount() {

        return  movieCommentList.size()+1;

    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_TITILE;
        }

        return TYPE_COMMENT;
    }
}
