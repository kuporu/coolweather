package com.hgc.coolweather.aspect;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hgc.coolweather.LoginActivity;
import com.hgc.coolweather.util.CrashHandler;
import com.hgc.coolweather.util.SharePreferenceUtil;

import org.litepal.LitePalApplication;

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());
        LoginSDK.getInstance().init(this, iLogin);
    }

    ILogin iLogin = new ILogin() {
        @Override
        public void login(Context applicationContext, int userDefine, Class<?> skip) {
            switch (userDefine) {
                case 0:
                    Intent intent = new Intent(applicationContext, LoginActivity.class);
                    intent.putExtra("skip", skip);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(applicationContext, "您还没有登录，请登陆后执行", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(applicationContext, "执行失败，因为您还没有登录！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public boolean isLogin(Context applicationContext) {
            return SharePreferenceUtil.getBooleanSp(SharePreferenceUtil.IS_LOGIN, applicationContext);
        }

        @Override
        public void clearLoginStatus(Context applicationContext) {
            SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, applicationContext);
        }
    };

}
