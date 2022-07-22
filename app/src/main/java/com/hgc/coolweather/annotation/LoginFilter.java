package com.hgc.coolweather.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginFilter {

    int userDefine() default 0;

    // 用于登陆后跳转 Activity(Fragment)
    Class<?> skip();
}