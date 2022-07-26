package com.hgc.coolweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hgc.coolweather.annotation.LoginFilter;
import com.hgc.coolweather.util.SharePreferenceUtil;

public class BootstrapActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;

    public static final int REQUEST_EXTERNAL_RESTORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrup);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        // TODO: 2022/7/20 动态获取写入文件权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_EXTERNAL_RESTORE);
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_RESTORE:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法写入日志", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}