package com.hgc.coolweather.aspect;

import android.content.Context;

import com.hgc.coolweather.annotation.LoginFilter;
import com.hgc.coolweather.execption.AnnotationException;
import com.hgc.coolweather.execption.NoInitException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class LoginFilterAspect {
    private static final String TAG = "LoginFilterAspect";
    // && @annotation(loginFilter)
    @Pointcut("execution(@com.hgc.coolweather.annotation.LoginFilter * *(..))")
    public void loginFilter() {}

    @Around("loginFilter()")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        ILogin iLogin = LoginAssistant.getInstance().getiLogin();
        if (iLogin == null) {
            throw new NoInitException("LoginSDK 没有初始化！");
        }

        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new AnnotationException("LoginFilter 注解只能用于方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        LoginFilter loginFilter = methodSignature.getMethod().getAnnotation(LoginFilter.class);
        if (loginFilter == null) {
            return;
        }

        Context param = LoginAssistant.getInstance().getApplicationContext();

        if (iLogin.isLogin(param)) {
            joinPoint.proceed();
        } else {
            iLogin.login(param, loginFilter.userDefine(), loginFilter.skip());
        }

    }

}