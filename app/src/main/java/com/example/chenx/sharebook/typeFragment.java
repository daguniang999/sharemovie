package com.example.chenx.sharebook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chenx.sharebook.gson.Movie_List;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.util.HttpUtil;
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
    SwipeRefreshLayout swipeRefreshLayout;
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
        initList();
      //  adapter.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initList();
                //adapter.notifyDataSetChanged();
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


    private void initList(){

        String url="http://192.168.0.123:6767/service.aspx?type=movielist&&movietype=1";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseText=response.body().string();
                final Movie_List movie=Utility.handleMovieitemResponse(responseText);

                for(Movie_item p:movie.movie_list)
                   movieList.add(p);

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

}
