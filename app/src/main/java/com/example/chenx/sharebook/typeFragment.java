package com.example.chenx.sharebook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chenx.sharebook.gson.Movie_List;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.util.HttpUtil;
import com.example.chenx.sharebook.util.OverAllObject;
import com.example.chenx.sharebook.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class typeFragment extends Fragment {


    private List<Movie_item> movieList=new ArrayList<>();
    private String type;
    private LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleitem;
    MovieAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_1,null);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.first_recycler);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_view);


        GridLayoutManager manager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(manager);
        adapter=new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(loading);


        loadingList(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadingList(true);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return view;

    }

    public typeFragment(){
    }

    public typeFragment(String type){
        this.type=type;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void loadingList(final boolean flag){
        String movieid="";



        if(flag){
            movieid="100000";
        }else if(movieList.size()!=0){
            movieid=movieList.get(movieList.size()-1).id;
        }

        String url=OverAllObject.getAddress() +"?type=movielimit&&movietype="+type+"&&movieid="+movieid;
        //Log.d("rrrr", url);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(flag){

                  //  Log.d("rrrr", String.valueOf(movieList.size()));
                    int p=movieList.size();
                    for(int i=0;i<p;i++){
                        movieList.remove(0);
                   //     Log.d("rrrr", "!!!!");
                    }
                }
                final String responseText=response.body().string();
                final Movie_List movie=Utility.handleMovieitemResponse(responseText);

                if(movie.movie_list.size()!=0){

                    for(Movie_item movie_item:movie.movie_list){
                        movieList.add(movie_item);

                    }
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();



                    }
                });

            }
        });


    }

    private RecyclerView.OnScrollListener loading=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleitem+1==adapter.getItemCount()){

                loadingList(false);


            }


        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
            lastVisibleitem=linearLayoutManager.findLastVisibleItemPosition();

        }
    };


}
