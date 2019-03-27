package com.example.chenx.sharebook;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
//import android.widget.Toolbar;

import com.example.chenx.sharebook.util.LitePalUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private List<Fragment> list;
    private MyPagerAdapter myPagerAdapter;
    private FloatingActionButton floatingActionButton;
    private TextView nav_name;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.movie_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MovieAddActivity.class);
                startActivity(intent);

            }
        });

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

        viewPager=(ViewPager)findViewById(R.id.views);



        list=new ArrayList<Fragment>();
        list.add(new typeFragment("剧情"));
        list.add(new typeFragment("喜剧"));
        list.add(new typeFragment("爱情"));
        list.add(new typeFragment("传记"));
        list.add(new typeFragment("动作"));
        list.add(new typeFragment("历史"));
        list.add(new typeFragment("科幻"));
        list.add(new typeFragment("奇幻"));
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),list);


        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("剧情");
        tabLayout.getTabAt(1).setText("喜剧");
        tabLayout.getTabAt(2).setText("爱情");
        tabLayout.getTabAt(3).setText("传记");
        tabLayout.getTabAt(4).setText("动作");
        tabLayout.getTabAt(5).setText("历史");
        tabLayout.getTabAt(6).setText("科幻");
        tabLayout.getTabAt(7).setText("奇幻");
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_main:
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.nav_collect:
                        break;
                    case R.id.nav_set:
                        break;

                    default:
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        nav_name=(TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        nav_name.setText(LitePalUtil.getPerson().getName());

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
