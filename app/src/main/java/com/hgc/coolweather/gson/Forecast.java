package com.hgc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Description
 * @Author hanguangchuan
 * Date 2022/6/6 21:20
 *
 * "daily_forecast": [
 *      {
 *          "date": "2016-08-08",
 *          "cond": {
 *              "txt_d": "降雨"
 *          }
 *          "tmp": {
 *              "max": "34",
 *              "min": "27"
 *          }
 *      },
 *      {
 *          "date": "2016-08-08",
 *          "cond": {
 *              "txt_d": "降雨"
 *          }
 *          "tmp": {
 *              "max": "34",
 *              "min": "27"
 *          }
 *      },
 *      ...
 * ]
 *
 * daily_forecast 中包含的是一个数组，数组中的每一项都代表着未来一天的天气信息
 * 针对这种情况，只需要定义出单日天气的实体类就可以，然后在声明实体类引用的时候使
 * 用集合类型来进行声明
 */
public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {

        public  String max;

        public String min;
    }

    public class More {

        @SerializedName("txt_d")
        public String info;
    }
}
