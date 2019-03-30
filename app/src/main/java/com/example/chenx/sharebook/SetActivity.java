package com.example.chenx.sharebook;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ActivityCollector.addActivity(this);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_set);
            setSupportActionBar(toolbar);

        }
        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Button quit=(Button)findViewById(R.id.button_quit);
        quit.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                ActivityCollector.removeActivity(this);
                finish();
                break;
                default:
                    break;
        }
        return true;

        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_quit:
                Intent intent=new Intent(SetActivity.this,LoginActivity.class);
                intent.putExtra("quit","quit");
                startActivity(intent);
                ActivityCollector.finishAll();
                break;


                default:
                    break;


        }




    }
}
