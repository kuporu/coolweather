package com.hgc.coolweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.hgc.coolweather.enums.ResultCode;
import com.hgc.coolweather.util.HttpUtil;
import com.hgc.coolweather.util.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String getWeatherIdUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWeatherIdUrl = getString(R.string.getWeatherIdUrl);

        // TODO: 2022/7/19 如果登录用户下可以查询到weather_id，则直接显现该weather_id对应的天气信息
        HttpUtil.sendHeadOkHttpRequest(getWeatherIdUrl, SharePreferenceUtil.getToken(SharePreferenceUtil.TOKEN, this), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                throw new RuntimeException("远程访问错误");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == 200 && !Objects.isNull(response.body())) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getInt("code") == ResultCode.SUCCESS.getCode()) {
                            String weatherId = jsonObject.getString("data");
                            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                            intent.putExtra("weather_id", weatherId);
                            finish();
                            startActivity(intent);
                        } else {
                            String msg = jsonObject.getString("msg");
                            runOnUiThread(() -> Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 如果缓存中有"weather"，直接显示天气界面
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    if (prefs.getString("weather", null) != null) {
                        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        // TODO: 2022/7/25 解决日志记录问题
//        throw new RuntimeException("查看日志是否记录成功");
    }
}