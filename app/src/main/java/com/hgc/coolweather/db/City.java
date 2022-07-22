package com.hgc.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * @Description 城市
 * @Author hanguangchuan
 * Date 2022/6/4 17:42
 */
public class City extends LitePalSupport {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 接口城市编码
     */
    private Integer cityCode;

    /**
     * 所属省份id
     */
    private Integer provinceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }
}
