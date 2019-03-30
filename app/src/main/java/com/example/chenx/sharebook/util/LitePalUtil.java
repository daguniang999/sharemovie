package com.example.chenx.sharebook.util;

import android.util.Log;

import com.example.chenx.sharebook.db.CollectMovie;
import com.example.chenx.sharebook.db.Person;
import com.example.chenx.sharebook.db.VisitedMovie;
import com.example.chenx.sharebook.db.WantMovie;
import com.example.chenx.sharebook.gson.Movie_item;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LitePalUtil {

    public static Person getPerson(){
        List<Person> list=new ArrayList<>();
        list=LitePal.findAll(Person.class);
        if(list.size()!=0) {

            return list.get(0);
        }

        return null;

    }

    public static void deleteCollect(List<Movie_item> List,int type){
        switch (type){
            case 0:
                for(Movie_item movie_item :List){
                    LitePal.deleteAll(VisitedMovie.class,"name=?",movie_item.name);
                }

                break;
            case 1:
                for(Movie_item movie_item :List) {
                    LitePal.deleteAll(CollectMovie.class, "name=?", movie_item.name);
                }
                break;
            case 2:
                for(Movie_item movie_item :List) {
                    LitePal.deleteAll(WantMovie.class, "name=?", movie_item.name);
                }
                break;
                default:
                    break;


        }


    }


    public static void saveVisitedMovie(Movie_item movie_item){
       // Log.d("cccc", movie_item.name);
        VisitedMovie visitedMovie=new VisitedMovie();

        visitedMovie.setName(movie_item.name);
        visitedMovie.setSummary(movie_item.summary);
        visitedMovie.setType(movie_item.type);
        visitedMovie.setUploader(movie_item.uploader);
        visitedMovie.setUploadtime(movie_item.uploadtime);
        visitedMovie.setUrl(movie_item.url);
        visitedMovie.save();

    }

    public static void saveWantMovie(Movie_item movie_item){
        //Log.d("cccc", movie_item.name);
        WantMovie wantMovie=new WantMovie();

        wantMovie.setName(movie_item.name);
        wantMovie.setSummary(movie_item.summary);
        wantMovie.setType(movie_item.type);
        wantMovie.setUploader(movie_item.uploader);
        wantMovie.setUploadtime(movie_item.uploadtime);
        wantMovie.setUrl(movie_item.url);
        wantMovie.save();

    }

    public static void saveCollectMovie(Movie_item movie_item){
      //  Log.d("cccc", movie_item.name);
        CollectMovie collectMovie =new CollectMovie();

        collectMovie.setName(movie_item.name);
        collectMovie.setSummary(movie_item.summary);
        collectMovie.setType(movie_item.type);
        collectMovie.setUploader(movie_item.uploader);
        collectMovie.setUploadtime(movie_item.uploadtime);
        collectMovie.setUrl(movie_item.url);
        collectMovie.save();

    }

    public static List<Movie_item> getVisitedMovie(){
       // Log.d("cccc","!!");
        List<Movie_item> mlist=new ArrayList<>();
        List<VisitedMovie> list=new ArrayList<>();
        list=LitePal.findAll(VisitedMovie.class);
        for(VisitedMovie visitedMovie:list){
            Movie_item item=new Movie_item();

            item.name=visitedMovie.getName();
            item.uploader=visitedMovie.getUploader();
            item.uploadtime=visitedMovie.getUploadtime();
            item.type=visitedMovie.getType();
            item.summary=visitedMovie.getSummary();
            item.url=visitedMovie.getUrl();
            mlist.add(item);
        //    Log.d("cccc", item.name);
        }

        return mlist;


    }

    public static List<Movie_item> getWantMovie(){
       // Log.d("cccc","!!");
        List<Movie_item> mlist=new ArrayList<>();
        List<WantMovie> list=new ArrayList<>();
        list=LitePal.findAll(WantMovie.class);
        for(WantMovie wantMovie:list){
            Movie_item item=new Movie_item();

            item.name=wantMovie.getName();
            item.uploader=wantMovie.getUploader();
            item.uploadtime=wantMovie.getUploadtime();
            item.type=wantMovie.getType();
            item.summary=wantMovie.getSummary();
            item.url=wantMovie.getUrl();
            mlist.add(item);
           // Log.d("cccc", item.name);
        }

        return mlist;


    }

    public static List<Movie_item> getCollectMovie(){
       // Log.d("cccc","!!");
        List<Movie_item> mlist=new ArrayList<>();
        List<CollectMovie> list=new ArrayList<>();
        list=LitePal.findAll(CollectMovie.class);
        for(CollectMovie collectMovie:list){
            Movie_item item=new Movie_item();

            item.name=collectMovie.getName();
            item.uploader=collectMovie.getUploader();
            item.uploadtime=collectMovie.getUploadtime();
            item.type=collectMovie.getType();
            item.summary=collectMovie.getSummary();
            item.url=collectMovie.getUrl();
            mlist.add(item);
           // Log.d("cccc", item.name);
        }

        return mlist;

    }

}
