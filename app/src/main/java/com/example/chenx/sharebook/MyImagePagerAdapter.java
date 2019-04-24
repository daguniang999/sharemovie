package com.example.chenx.sharebook;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyImagePagerAdapter extends PagerAdapter  {

    private ArrayList<View> pagerview=new ArrayList<>();
    public MyImagePagerAdapter(ArrayList<View> list){
        pagerview=list;

    }


    @Override
    public int getCount() {
        return pagerview.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);

        container.removeView(pagerview.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View temp=pagerview.get(position);
        // temp.findViewById(R.i)
        container.addView(pagerview.get(position));
        return pagerview.get(position);
        //  return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }


}
