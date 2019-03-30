package com.example.chenx.sharebook;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> AList=new ArrayList<>();
    public static void addActivity(Activity activity){
        AList.add(activity);
    }

    public static void removeActivity(Activity activity){
        AList.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:AList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        AList.clear();
    }


}
