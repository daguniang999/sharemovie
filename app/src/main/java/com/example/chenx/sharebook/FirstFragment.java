package com.example.chenx.sharebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private static ViewPager viewPager;
    private ViewPager PreviewPager;
    private ImageView[] diandianArray;
    private LinearLayout mLinearLayout;
    private ArrayList<View> pagerview=new ArrayList<>();

    private CardView juqingButton;
    private CardView xijuButton;
    private CardView aiqingButton;
    private CardView zhuanjiButton;
    private CardView dongzuoButton;
    private CardView lishiButton;
    private CardView kehuanButton;
    private CardView qiguanButton;
    private CardView moviewfind;
    private TextView textfind;

    public FirstFragment(ViewPager viewPager){
        PreviewPager=viewPager;
        Log.d("ttt", "FirstFragment: ");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
     //   Log.d("ttt", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  Log.d("ttt", "onDestroy: ");
    }

    public FirstFragment(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_1:PreviewPager.setCurrentItem(1);
                break;
            case R.id.card_2:PreviewPager.setCurrentItem(2);
                break;
            case R.id.card_3:PreviewPager.setCurrentItem(3);
                break;
            case R.id.card_4:PreviewPager.setCurrentItem(4);
                break;
            case R.id.card_5:PreviewPager.setCurrentItem(5);
                break;
            case R.id.card_6:PreviewPager.setCurrentItem(6);
                break;
            case R.id.card_7:PreviewPager.setCurrentItem(7);
                break;
            case R.id.card_8:PreviewPager.setCurrentItem(8);
                break;
            case R.id.text_find:
            case R.id.card_find:
                Intent intent=new Intent(getContext(),SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.d("ttt", "onCreate: ");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.first_fragment,null);
        viewPager=(ViewPager)view.findViewById(R.id.viewpager_pic);
        mLinearLayout=(LinearLayout)view.findViewById(R.id.layout);


        View view1=inflater.inflate(R.layout.scroll_pic,null);
        ImageView imageView=(ImageView)view1.findViewById(R.id.scroll_image);


        imageView.setImageResource(R.drawable.scroll);

        View view2=inflater.inflate(R.layout.scroll_pic,null);
        ImageView imageView1=(ImageView)view2.findViewById(R.id.scroll_image);
        imageView1.setImageResource(R.drawable.scroll);



        View view3=inflater.inflate(R.layout.scroll_pic,null);
        ImageView imageView2=(ImageView)view3.findViewById(R.id.scroll_image);
        imageView2.setImageResource(R.drawable.scroll);


        pagerview.clear();
        pagerview.add(view1);
        pagerview.add(view2);
        pagerview.add(view3);


        diandianArray =new ImageView[pagerview.size()];
        for(int i=0;i<diandianArray.length;i++){
            ImageView imageView3=new ImageView(getContext());
            diandianArray[i]=imageView3;
            if(i==0){
               diandianArray[i].setBackgroundResource(R.drawable.pointtwo);
            }else{
                diandianArray[i].setBackgroundResource(R.drawable.pointone);
            }

            LinearLayout.LayoutParams liner=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            liner.width=30;
            liner.height = 30;
            liner.leftMargin = 5;
            liner.rightMargin = 5;
            liner.bottomMargin = 3;
            liner.topMargin=3;
            mLinearLayout.addView(imageView3,liner);

            juqingButton=(CardView) view.findViewById(R.id.card_1);
            juqingButton.setOnClickListener(this);
            xijuButton=(CardView)view.findViewById(R.id.card_2);
            xijuButton.setOnClickListener(this);
            aiqingButton=(CardView)view.findViewById(R.id.card_3);
            aiqingButton.setOnClickListener(this);
            zhuanjiButton=(CardView)view.findViewById(R.id.card_4);
            zhuanjiButton.setOnClickListener(this);
            dongzuoButton=(CardView)view.findViewById(R.id.card_5);
            dongzuoButton.setOnClickListener(this);
            lishiButton=(CardView)view.findViewById(R.id.card_6);
            lishiButton.setOnClickListener(this);
            kehuanButton=(CardView)view.findViewById(R.id.card_7);
            kehuanButton.setOnClickListener(this);
            qiguanButton=(CardView)view.findViewById(R.id.card_8);
            qiguanButton.setOnClickListener(this);
            moviewfind=(CardView)view.findViewById(R.id.card_find);
            moviewfind.setOnClickListener(this);
            textfind=(TextView)view.findViewById(R.id.text_find);
            textfind.setOnClickListener(this);
            //RecyclerView recyclerView1=(RecyclerView)view.findViewById(R.id.rela);
          //  recyclerView1.setOnClickListener(this);

        }



        MyImagePagerAdapter myImagePagerAdapter=new MyImagePagerAdapter(pagerview);
        viewPager.setAdapter(myImagePagerAdapter);
        viewPager.addOnPageChangeListener(this);
        return view;

    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

        for(int j = 0; j < diandianArray.length; j++){
            diandianArray[j].setBackgroundResource(R.drawable.pointtwo);
            if(j!= i){
                diandianArray[j].setBackgroundResource(R.drawable.pointone);
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
