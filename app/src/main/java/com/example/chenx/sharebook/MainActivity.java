package com.example.chenx.sharebook;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private List<Fragment> list;
    private MyPagerAdapter myPagerAdapter;

    private String [] titles={"1","2","3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);
            setSupportActionBar(toolbar);

          //  actionBar.setTitle("fsfaa");
        }
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.detail);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("fds"));
        tabLayout.addTab(tabLayout.newTab().setText("fds"));
        tabLayout.addTab(tabLayout.newTab().setText("fdsf"));

        viewPager=(ViewPager)findViewById(R.id.views);
        list=new ArrayList<Fragment>();
        list.add(new firstFragment());
        list.add(new secondFragment());
        list.add(new thirdFragment());
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),list);


        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("First");
        tabLayout.getTabAt(1).setText("Second");
        tabLayout.getTabAt(2).setText("Third");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }

        return true;

        // return super.onOptionsItemSelected(item);
    }
}
