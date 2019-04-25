package com.example.chenx.sharebook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class MovieFragment extends Fragment {


    private List<Movie_item> movieList=new ArrayList<>();
    private String type="剧情";
    private LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleitem;
    MovieAdapter adapter;
    private boolean isloading=true;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {

//            if(movieList.size()==0){
//                isloading = true;
//                loadingList(true);
//                Log.d("ttt", type);
//
//            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ttt", "onDestroy: "+type);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getArguments().getString("type");
        Log.d("ttt", "onCreate: ");
        adapter=new MovieAdapter(movieList,"Main");
        if(movieList.size()==0){
            isloading = true;
            loadingList(true);
            Log.d("ttt", type);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

      //  Log.d("tttt", "onCreateView: ");
        View view=inflater.inflate(R.layout.movie_fragment,null);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.first_recycler);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_view);
        GridLayoutManager manager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(manager);
       // adapter=new MovieAdapter(movieList,"Main");
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(loading);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isloading){
                    return true;
                }else {
                    return false;
                }
               // return false;
            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isloading=true;


                for(int i=movieList.size();i>0;i--){
                    movieList.remove(0);
                }


                adapter.setover(false);
                loadingList(true);



            }
        });
        return view;

    }

    public MovieFragment(){
    }



    public static final MovieFragment newInstance(String type){
        MovieFragment fragment=new MovieFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        isloading=true;
//        loadingList(true);

       // adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("eeee", String.valueOf(movieList.size())+type);


    }

    public void loadingList(final boolean flag){

        String movieid="";

        if(flag){
            movieid="100000";
        }else if(movieList.size()!=0){
            movieid=movieList.get(movieList.size()-1).id;
        }

        String url=OverAllObject.getAddress() +"?type=movielimit&&movietype="+type+"&&movieid="+movieid;
        Log.d("pppp", url);
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

//                if(flag){
//
//                    for(int i=movieList.size();i>0;i--){
//                        movieList.remove(0);
//                    }
//                  //  Log.d("pppp", "1: "+movieList.size());
//
//
//                }
                final String responseText=response.body().string();
             //   Log.d("pppp", responseText);
                final Movie_List movie=Utility.handleMovieitemResponse(responseText);

                if(movie.movie_list.size()!=0){

                    for(Movie_item movie_item:movie.movie_list){
                        movieList.add(movie_item);

                    }
                 //   Log.d("pppp", "2: "+movieList.size());
                }
                if (movie.movie_list.size()<10){
                    adapter.setover(true);
                }



                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                       adapter.notifyDataSetChanged();
                     //  Log.d("pppp", "3: "+movieList.size());
                       isloading=false;
                       swipeRefreshLayout.setRefreshing(false);


                    }
                });

            }
        });


    }

    private RecyclerView.OnScrollListener loading=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d("wwwwq", ""+lastVisibleitem+" "+adapter.getItemCount());
            if(movieList.size()>=10&&newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleitem+1==adapter.getItemCount()&&!adapter.getover()){
                isloading=true;
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
