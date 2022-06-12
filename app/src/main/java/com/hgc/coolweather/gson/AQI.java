package com.hgc.coolweather.gson;

/**
 * @Description
 * @Author hanguangchuan
 * Date 2022/6/6 21:06
 *
 * "aqi":{
 *      "city": {
 *          "aqi": "44",
 *          "pm25": "13"
 *      }
 * }
 */
public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;
    }
}
