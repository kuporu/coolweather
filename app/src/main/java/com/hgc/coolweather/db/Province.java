package com.hgc.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * @Description 省份表
 * @Author hanguangchuan
 * Date 2022/6/4 17:35
 */
public class Province extends LitePalSupport {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 接口省份编码
     */
    private Integer provinceCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }
}
