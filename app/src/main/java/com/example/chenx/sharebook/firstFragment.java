package com.example.chenx.sharebook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chenx.sharebook.gson.Movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class firstFragment extends Fragment {


    private List<Movie> movieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_1,null);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.first_recycler);
        initList();
        GridLayoutManager manager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(manager);
        MovieAdapter adapter=new MovieAdapter(movieList);

        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // Log.d("ffff", "onViewCreated: ");
       // super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        //Log.d("ffff", "onDestroyView: ");
        super.onDestroyView();
    }


    private void initList(){

        movieList=new ArrayList<>();
        for(int i=0;i<10;i++){
            Movie movie=new Movie();
            movie.setId(1);
            movie.setUploader("官方");
            movie.setName(String.valueOf(i));
            movie.setSummary("草好看的");
            movieList.add(movie);
        }

    }

}
