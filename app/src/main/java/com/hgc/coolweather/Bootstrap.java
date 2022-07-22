package com.hgc.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hgc.coolweather.annotation.LoginFilter;
import com.hgc.coolweather.util.SharePreferenceUtil;

public class Bootstrap extends AppCompatActivity implements View.OnClickListener {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrup);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @LoginFilter(skip = MainActivity.class)
    @Override
    public void onClick(View v) {
        /*
        点击登录按钮，如果已经登录，跳转到主页面，如果未登录跳转到登录页面
         */
        startActivity(new Intent(this, MainActivity.class));
    }

    public void register(View view) {
        startActivity(new Intent(this, Register.class));
    }
}