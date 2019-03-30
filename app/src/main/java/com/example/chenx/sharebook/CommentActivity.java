package com.example.chenx.sharebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chenx.sharebook.gson.Comment_List;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.gson.Movie_comment;
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

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyCommentAdapter adapter;
    private List<Movie_comment> movie_commentList=new ArrayList<>();
    private FloatingActionButton addbutton;
    private String movieID;
    private Movie_item Mymovie;
    private ImageView pic;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ActivityCollector.addActivity(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.comment_toolbar);
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_comment);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadComment();

            }
        });





        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_comment);
        GridLayoutManager manager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(manager);


        Intent intent=getIntent();
        Mymovie=new Movie_item();
        adapter=new MyCommentAdapter(movie_commentList);

        Mymovie.name=intent.getStringExtra("movie_name");
        Mymovie.uploader=intent.getStringExtra("movie_uploader");
        Mymovie.summary=intent.getStringExtra("movie_summary");
        Mymovie.url=intent.getStringExtra("movie_url");
        movieID=intent.getStringExtra("movie_id");
        loadComment();
        Log.d("eeee", Mymovie.name+movieID+Mymovie.uploader+Mymovie.summary);
        adapter.setTitle(Mymovie);

        recyclerView.setAdapter(adapter);

        addbutton=(FloatingActionButton)findViewById(R.id.comment_add);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(CommentActivity.this,"fadsfs",Toast.LENGTH_SHORT).show();
                LayoutInflater inflater=getLayoutInflater();
                final View layout=inflater.inflate(R.layout.dialog_add,(ViewGroup)findViewById(R.id.dialog_add));
                new AlertDialog.Builder(CommentActivity.this).setTitle("添加评论").setView(layout)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editText=layout.findViewById(R.id.comment_add_content);
                                String uploader=LitePalUtil.getPerson().getName();
                                String content=editText.getText().toString();
                                String url=OverAllObject.getAddress()+"?type=addcomment&&movieid="+movieID+"&&content="+content+"&&name="+uploader;
                                HttpUtil.sendOkHttpRequest(url, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(CommentActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final SimpleBack simpleBack=Utility.handleSimpleResponse(response.body().string());
                                        if(simpleBack.type.equals("addcomment"))

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(simpleBack.state.equals("1")){
                                                    Toast.makeText(CommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(CommentActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                });


                                //Toast.makeText(CommentActivity.this,editText.getText(),Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消",null).show();
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

       return true;
    }


    public void loadComment(){

        final String url=OverAllObject.getAddress()+"?type=commentlist&&movieid="+movieID;
        Log.d("eeee", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CommentActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                String responseText=response.body().string();
                Log.d("eeee", responseText);
                Comment_List list=Utility.handleMovieCommentResponse(responseText);
                if(Integer.valueOf(list.count)>=1) {
                    movie_commentList.clear();
                    for (Movie_comment comment : list.comment_list) {
                        movie_commentList.add(comment);
                    }
                    Log.d("eeee", list.count);
                }else {
                    Log.d("eeee", list.count);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
