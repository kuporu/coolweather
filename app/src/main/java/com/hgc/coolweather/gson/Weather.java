package com.hgc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description 总实体类，包含其他实体类
 * @Author hanguangchuan
 * Date 2022/6/6 21:28
 */
public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    /**
     * daily_forecast中包含的是一个数组，使用List集合来引用Forecast类
     */
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
