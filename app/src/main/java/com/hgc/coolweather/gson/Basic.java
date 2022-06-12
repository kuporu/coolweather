package com.hgc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Description
 * @Author hanguangchuan
 * Date 2022/6/6 20:56
 *
 * 天气接口返回数据大致格式
 * {
 *     “HeWeather”: [
 *          {
 *              "status":"ok",
 *              "basic":{},
 *              "aqi":{},
 *              "now":{},
 *              "suggestion":{},
 *              "daily_forecast":[]
 *          }
 *     ]
 * }
 *
 * 其中，basic, aqi, now, suggestion 和 daily_forecast 的内部又都会有具体的内容
 *
 * "basic": {
 *      "city": "苏州",
 *      "id": "CN101190401",
 *      "update": {
 *          "loc":"2016-08-08 21:58"
 *      }
 * }
 */
public class Basic {

    /**
     * 使用注解让JSON字段和Java字段之间建立映射关系
     */
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;
    }
}
