package com.hgc.coolweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.hgc.coolweather.entity.User;
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
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private Intent intent;

    private TextView usernameTextView;

    private TextView passwordTextView;

    private String HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTextView = findViewById(R.id.usernameTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        HOST = getString(R.string.HOST);
        intent = getIntent();
    }


    public void loginEvent(View view) {
        checkUsernameAndPassword(usernameTextView, passwordTextView);
    }

    private void checkUsernameAndPassword(TextView usernameTextView, TextView passwordTextView) {
        User user = new User(usernameTextView.getText().toString(), passwordTextView.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(user);
        HttpUtil.sendPostOkHttpRequest(HOST, json, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                throw new RuntimeException("远程访问失败！", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int code = response.code();
                ResponseBody body = response.body();
                if (Objects.isNull(body) || code != 200) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "登录异常", Toast.LENGTH_SHORT).show());

                } else {
                    // 显示服务器返回的异常结果
                    String bodyString = body.string();
                    try {
                        JSONObject object = new JSONObject(bodyString);
                        if (ResultCode.SUCCESS.getCode() != object.getInt("code")) {
                            String msg = object.getString("msg");
                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show());
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, true, LoginActivity.this);
                    parseJSON(bodyString);
                    finish();
                    // TODO: 2022/7/19 登录成功后返回当前界面（区分 Activity 和 Fragment）
                    if (Objects.isNull(intent.getSerializableExtra("skip"))) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, (Class<?>) intent.getSerializableExtra("skip")));
                    }

                }
            }
        });
    }

    private void parseJSON (String json) {
        // 登录验证成功，将token放入缓存
        try {
            JSONObject jsonObject = new JSONObject(json);
            String token = jsonObject.getJSONObject("data").getString("token");
            SharePreferenceUtil.setToken(SharePreferenceUtil.TOKEN, token, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void quitLogin(View view) {
        finish();
    }
}
