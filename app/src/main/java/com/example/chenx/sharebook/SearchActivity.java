package com.example.chenx.sharebook;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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

public class SearchActivity extends AppCompatActivity {

    private android.support.v7.widget.SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private List<Movie_item> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//这句代码使启用Activity回退功能，并显示Toolbar上的左侧回退图标

        recyclerView=(RecyclerView)findViewById(R.id.search_recycler);

        searchAdapter=new SearchAdapter(list);
        GridLayoutManager manager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(searchAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);//指定Toolbar上的视图文件
        MenuItem menuItem = menu.findItem(R.id.ab_search);
        searchView=(android.support.v7.widget.SearchView) menuItem.getActionView();

        searchView.setQueryHint("搜索");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String url=OverAllObject.getAddress() +"?type=movielist&&moviename="+s;
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(SearchActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText=response.body().string();
                        final Movie_List movie=Utility.handleMovieitemResponse(responseText);

                        while(list.size()!=0){
                            list.remove(0);
                        }

                        if(movie.movie_list.size()!=0){

                            for(Movie_item movie_item:movie.movie_list){
                                list.add(movie_item);

                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                searchAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });



                return true;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();//真正实现回退功能的代码
                break;
            default:break;

        }

        return super.onOptionsItemSelected(item);
    }




}
