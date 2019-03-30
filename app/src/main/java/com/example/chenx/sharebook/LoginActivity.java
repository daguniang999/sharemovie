package com.example.chenx.sharebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chenx.sharebook.db.Person;
import com.example.chenx.sharebook.gson.SimpleBack;
import com.example.chenx.sharebook.util.HttpUtil;
import com.example.chenx.sharebook.util.OverAllObject;
import com.example.chenx.sharebook.util.Utility;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText UserName;
    EditText PassWord;
    Button login;
    Button register;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login:
                String user=UserName.getText().toString().trim();
                String password=PassWord.getText().toString().trim();
                String url=OverAllObject.getAddress() +"?type=login&&id="+user+"&&password="+password;
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String responseText=response.body().string();
                        final SimpleBack simpleBack=Utility.handleSimpleResponse(responseText);
                        if(simpleBack.type.equals("login")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    switch (simpleBack.state){
                                        case "1":
                                            LitePal.getDatabase();
                                            List<Person> list=new ArrayList<>();
                                            list=LitePal.findAll(Person.class);
                                            if(list.size()==0){
                                                Person person=new Person();
                                                person.setName(UserName.getText().toString());
                                                person.setPassword(PassWord.getText().toString());
                                                person.save();
                                              //  Log.d("pppp!", String.valueOf(list.size()));
                                            }else {
                                                Person person=new Person();
                                                person.setName(UserName.getText().toString());
                                                person.setPassword(PassWord.getText().toString());
                                                person.updateAll();
                                            }

                                            Intent Main=new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(Main);
                                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                            break;
                                        case "2":
                                            Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            break;

                                    }
                                }
                            });
                        }

                    }
                });



                break;
            case R.id.login_register:
                Intent register=new Intent(this,RegisterActivity.class);
                startActivity(register);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserName=(EditText)findViewById(R.id.login_username);
        PassWord=(EditText)findViewById(R.id.login_password);
        login=(Button)findViewById(R.id.login_login);
        register=(Button)findViewById(R.id.login_register);

        String userName=UserName.getText().toString();
        String passWord=PassWord.getText().toString();

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        LitePal.getDatabase();
        List<Person> list=new ArrayList<>();
        list=LitePal.findAll(Person.class);
        if(list.size()!=0){
            Intent Main=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(Main);
            finish();

        }else {
          //  Log.d("pppp", String.valueOf( list.size()));
        }

    }
}
