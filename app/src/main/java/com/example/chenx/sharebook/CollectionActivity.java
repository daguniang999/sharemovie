package com.example.chenx.sharebook;

import android.app.AlertDialog;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class CollectionActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar bottomNavigationBar;
    private int lastPosition=0;
    private FragmentManager fragmentManager=getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    @Override
    public void onTabSelected(int position) {
        lastPosition=position;
        switch (position){
            case 0:
                fragmentTransaction=fragmentManager.beginTransaction();
                CollectFragment collectFragment0=CollectFragment.newInstance(0);
                fragmentTransaction.replace(R.id.collect_fragment,collectFragment0);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction=fragmentManager.beginTransaction();
                CollectFragment collectFragment1=CollectFragment.newInstance(1);
                fragmentTransaction.replace(R.id.collect_fragment,collectFragment1);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction=fragmentManager.beginTransaction();
                CollectFragment collectFragment2=CollectFragment.newInstance(2);
                fragmentTransaction.replace(R.id.collect_fragment,collectFragment2);
                fragmentTransaction.commit();
                break;
                default:
                    break;



        }



    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
                default:break;

        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ActivityCollector.addActivity(this);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            Toolbar toolbar=findViewById(R.id.collect_toolbar);
            setSupportActionBar(toolbar);

        }

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.menu_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.white);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.pass, "看过"))
                .addItem(new BottomNavigationItem(R.drawable.collect, "收藏"))
                .addItem(new BottomNavigationItem(R.drawable.future, "想看"))
                .setFirstSelectedPosition(0)
                .initialise();
        setDefault();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void setDefault(){
        fragmentTransaction=fragmentManager.beginTransaction();
        CollectFragment collectFragment=CollectFragment.newInstance(0);
        fragmentTransaction.replace(R.id.collect_fragment,collectFragment);
        fragmentTransaction.commit();
    }





}
