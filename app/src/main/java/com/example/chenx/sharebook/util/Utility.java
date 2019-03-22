package com.example.chenx.sharebook.util;

import android.app.ProgressDialog;
import android.util.Log;

import com.example.chenx.sharebook.gson.Comment_List;
import com.example.chenx.sharebook.gson.Movie_List;
import com.example.chenx.sharebook.gson.Movie_item;
import com.example.chenx.sharebook.gson.Movie_comment;
import com.example.chenx.sharebook.gson.SimpleBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static Movie_List handleMovieitemResponse(String response){
        try{
                JSONObject jsonObject=new JSONObject(response);
                String movieContent=jsonObject.toString();

            Log.d("wwww", movieContent);
            return new Gson().fromJson(movieContent,Movie_List.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    public static Comment_List handleMovieCommentResponse(String response){

        try{

            JSONObject jsonObject=new JSONObject(response);
            String commentcontent=jsonObject.toString();
            return new Gson().fromJson(commentcontent,Comment_List.class);

        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }

    public static SimpleBack handleSimpleResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            String simplecontent=jsonObject.toString();
            return new Gson().fromJson(simplecontent,SimpleBack.class);


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }


}
