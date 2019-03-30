package com.example.chenx.sharebook;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.util.LitePalUtil;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CollectFragment extends Fragment {

    private TextView Text;
    private int type;
    private RecyclerView recyclerView;
    private List<Movie_item> list=new ArrayList<>();
    private MovieAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.collect_fragment,null);


        recyclerView=(RecyclerView)view.findViewById(R.id.collect_recycler);
        GridLayoutManager manager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(manager);
        adapter=new MovieAdapter(list,"Collect");
        adapter.setType(type);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback=new myItemTouchHelperCallBack(adapter);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        init();

    }

    public CollectFragment(){}



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.type=getArguments().getInt("type");

    }

    public static final CollectFragment newInstance(int type){
        CollectFragment fragment=new CollectFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.deleCollect();
    }

    public void init(){
        switch (type){
            case 0:
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<Movie_item> tempList=LitePalUtil.getVisitedMovie();
                        for(Movie_item movie_item:tempList){
                            list.add(movie_item);
                        }

                        adapter.notifyDataSetChanged();

                    }
                });

                break;
            case 1:
                Handler handler1 = new Handler(Looper.getMainLooper());
                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        List<Movie_item> tempList=LitePalUtil.getCollectMovie();
                        for(Movie_item movie_item:tempList){
                            list.add(movie_item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                break;
            case 2:

                Handler handler2 = new Handler(Looper.getMainLooper());
                handler2.post(new Runnable() {
                    @Override
                    public void run() {
                        List<Movie_item> tempList=LitePalUtil.getWantMovie();
                        for(Movie_item movie_item:tempList){
                            list.add(movie_item);
                        }

                        list=LitePalUtil.getWantMovie();
                        adapter.notifyDataSetChanged();

                    }
                });

                break;
            default :
                break;

        }
    }

}
