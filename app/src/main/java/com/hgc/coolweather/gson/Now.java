package com.hgc.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Description
 * @Author hanguangchuan
 * Date 2022/6/6 21:09
 *
 * "now": {
 *     "tmp": "29",
 *     "cond": {
 *         "txt": "阵雨"
 *     }
 * }
 */
public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }
}
