package com.example.chenx.sharebook;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mlist;


    public MyPagerAdapter(FragmentManager fragmentManager,List<Fragment> list){
        super(fragmentManager);
        this.mlist=list;
    }



    @Override
    public Fragment getItem(int i) {

        return mlist.get(i);
        //return null;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }



}
