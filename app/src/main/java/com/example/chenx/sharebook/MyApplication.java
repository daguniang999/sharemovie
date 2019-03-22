package com.example.chenx.sharebook;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.crud.LitePalSupport;

import static org.litepal.Operator.initialize;


public class MyApplication extends Application {
    private  static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        LitePal.initialize(mContext);

    }

    public static Context getContext(){
        return mContext;
    }
}
