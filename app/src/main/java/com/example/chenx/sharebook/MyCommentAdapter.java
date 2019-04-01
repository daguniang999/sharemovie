package com.example.chenx.sharebook;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.gson.Movie_comment;
import com.example.chenx.sharebook.util.OverAllObject;

import java.util.List;
import java.util.logging.Handler;
import java.util.regex.Matcher;

public class MyCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private static final int TYPE_TITILE=0;
    private static final int TYPE_COMMENT=1;
    private static final int TYPE_FOOTER=2;
    private static final int TYPE_END=3;

    private Context mContent;
    private List<Movie_comment> movieCommentList;
    private Movie_item commenttTitle;
    private android.graphics.Matrix matrix=new android.graphics.Matrix();
    private android.graphics.Matrix saveMatrix=new android.graphics.Matrix();
    private static final int NONE=0;//三种模式
    private static final int DRAG=1;
    private static final int ZOOM=2;
    private  Boolean MOVE=false;
    private int mode=NONE;//模式
    private PointF startPoint=new PointF();//第一个按下的点
    private PointF midPoint=new PointF();//二个点的重心
    private float oriDis=1f;//二点之间的距离


    private float distance(MotionEvent event){ //计算二点之间的距离
        float x=event.getX(0)-event.getX(1);
        float y=event.getY(0)-event.getY(1);
        return Float.valueOf(String.valueOf(Math.sqrt(x*x+y*y)));
    }

    private PointF middle(MotionEvent event){
        float x=event.getX(0)+event.getX(1);
        float y=event.getY(0)+event.getY(1);
        return new PointF(x/2,y/2);
    }



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

        public TitleViewHolder(final View view){
            super(view);
            movieImage=(ImageView)view.findViewById(R.id.image_comment);
            movieName=(TextView)view.findViewById(R.id.movie_name);
            movieSummary=(TextView)view.findViewById(R.id.movie_summary);
            movieUploder=(TextView)view.findViewById(R.id.movie_uploader);


            movieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final AlertDialog.Builder builder=new AlertDialog.Builder(mContent,R.style.Theme_AppCompat_Light_NoActionBar);
                    final Dialog dialog=new Dialog(mContent);
                    dialog.setContentView(R.layout.dialog_bigpic);
                    final ImageView imageView=(ImageView)dialog.findViewById(R.id.image_bigpic);

                    //
                    imageView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()&MotionEvent.ACTION_MASK){
                                case MotionEvent.ACTION_DOWN:
                                    matrix.set(imageView.getImageMatrix());
                                    saveMatrix.set(matrix);
                                    startPoint.set(event.getX(),event.getY());
                                    mode=DRAG;
                                    break;
                                case MotionEvent.ACTION_POINTER_DOWN:
                                    oriDis=distance(event);
                                    if(oriDis>10f){
                                        saveMatrix.set(matrix);
                                        midPoint=middle(event);
                                        mode=ZOOM;
                                    }

                                    break;

                                case MotionEvent.ACTION_UP:
                                case MotionEvent.ACTION_POINTER_UP:
                                    if(mode==DRAG&&!MOVE){
                                        dialog.dismiss();
                                    }
                                    mode=NONE;
                                    MOVE=false;
                                    break;

                                case MotionEvent.ACTION_MOVE:
                                    MOVE=true;
                                    if(mode==DRAG){
                                        matrix.set(saveMatrix);
                                        matrix.postTranslate(event.getX()-startPoint.x,event.getY()-startPoint.y);
                                    }else if (mode==ZOOM){

                                        float newDist=distance(event);
                                        if(newDist>10f){
                                            matrix.set(saveMatrix);
                                            float scale=newDist/oriDis;
                                            matrix.postScale(scale,scale,midPoint.x,midPoint.y);
                                        }

                                    }

                                    break;

                            }

                            imageView.setImageMatrix(matrix);
                            return true;
                        }
                    });
                    Glide.with(mContent).load(OverAllObject.getImageUrl(commenttTitle.url)).into(imageView);
                    dialog.show();
                    Window win = dialog.getWindow();
                    win.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = win.getAttributes();
                    lp.width = WindowManager.LayoutParams.FILL_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    win.setAttributes(lp);

                }
            });

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
