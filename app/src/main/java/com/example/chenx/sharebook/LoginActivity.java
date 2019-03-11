package com.example.chenx.sharebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login:
                Intent Main=new Intent(this,MainActivity.class);
                startActivity(Main);
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
        TextView UserName=(TextView)findViewById(R.id.login_username);
        TextView PassWord=(TextView)findViewById(R.id.login_password);
        Button login=(Button)findViewById(R.id.login_login);
        Button register=(Button)findViewById(R.id.login_register);

        String userName=UserName.getText().toString();
        String passWord=PassWord.getText().toString();

        login.setOnClickListener(this);
        register.setOnClickListener(this);






    }
}
