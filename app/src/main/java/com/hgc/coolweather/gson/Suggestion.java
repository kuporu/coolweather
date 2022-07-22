package com.hgc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Description
 * @Author hanguangchuan
 * Date 2022/6/6 21:11
 *
 * "suggestion": {
 *      "comf": {
 *          "txt": "白天天气较热，虽然有雨，但仍然无法消弱较高气温给人们带来的暑意，这种天气会让您改到不很舒适。"
 *      },
 *      "cw": {
 *          "txt": "不宜洗车，未来24小时内有雨"
 *      },
 *      "sport": {
 *          "txt": "有降水，且风力较强，推荐您在室内进行较低强度运动"
 *      }
 * }
 */
public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort {

        @SerializedName("txt")
        public String info;
    }

    public class CarWash {

        @SerializedName("txt")
        public String info;
    }

    public class Sport {

        @SerializedName("txt")
        public String info;
    }
}
