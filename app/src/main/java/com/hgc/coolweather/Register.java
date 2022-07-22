package com.hgc.coolweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hgc.coolweather.entity.User;
import com.hgc.coolweather.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button register;

    private EditText username;

    private EditText password;

    private String REGISTER_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        username = findViewById(R.id.addUserName);
        password = findViewById(R.id.addUserPassword);
        REGISTER_URL = getString(R.string.register_url);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        User user = new User(username.getText().toString(), password.getText().toString());
        Gson gson = new Gson();
        HttpUtil.sendPostOkHttpRequest(REGISTER_URL, gson.toJson(user), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                throw new RuntimeException(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == 200) {
                    ResponseBody body = response.body();
                    if (!Objects.isNull(body)) {
                        try {
                            JSONObject object = new JSONObject(body.string());
                            if (object.getInt("code") == 200) {
                                runOnUiThread(() -> Toast.makeText(Register.this, "注册成功", Toast.LENGTH_LONG).show());

                                finish();
                                startActivity(new Intent(Register.this, LoginActivity.class));
                            } else {
                                runOnUiThread(() -> Toast.makeText(Register.this, "用户名已存在", Toast.LENGTH_LONG).show());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}