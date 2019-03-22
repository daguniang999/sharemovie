package com.example.chenx.sharebook;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chenx.sharebook.gson.SimpleBack;
import com.example.chenx.sharebook.util.HttpUtil;
import com.example.chenx.sharebook.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText UserName;
    EditText PassWord;
    Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        UserName=(EditText)findViewById(R.id.register_username);
        PassWord=(EditText)findViewById(R.id.register_password);
        Register=(Button)findViewById(R.id.register_register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=UserName.getText().toString().trim();
                String password=PassWord.getText().toString();
                String url="http://10.63.105.142:6767/service.aspx?type=register&&id="+username+"&&password="+password;
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText=response.body().string();
                        final SimpleBack simpleBack=Utility.handleSimpleResponse(responseText);
                        if(simpleBack.type.equals("register")){


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    switch (simpleBack.state)
                                    {
                                        case "1":
                                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                            finish();
                                            break;
                                        case "2":
                                            Toast.makeText(RegisterActivity.this,"已被注册",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "3":
                                            Toast.makeText(RegisterActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                                            break;
                                        default :
                                            break;

                                    }
                                }
                            });

                        }


                    }
                });
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case android.R.id.home:
                finish();
                break;
           default:
               break;
       }

         return true;
    }
}
