package com.hgc.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * @Description 县
 * @Author hanguangchuan
 * Date 2022/6/4 17:45
 */
public class County extends LitePalSupport {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 县名
     */
    private String countyName;

    /**
     * 接口地点对应天气id
     */
    private String weatherId;

    /**
     * 对应市id
     */
    private Integer cityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
