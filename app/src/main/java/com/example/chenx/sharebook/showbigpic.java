package com.example.chenx.sharebook;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chenx.sharebook.util.OverAllObject;

public class showbigpic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbigpic);

        ImageView imageView=(ImageView)findViewById(R.id.image_bigpic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(showbigpic.this);
            }
        });

        Intent intent=getIntent();
        String url=intent.getStringExtra("pic_url");
        Glide.with(showbigpic.this).load(OverAllObject.getImageUrl(url)).into(imageView);

    }
}
