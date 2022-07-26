package com.hgc.coolweather.enums;

/**
 * @Description TODO
 * @systemUser gchan2
 * @Author hanguangchuan
 * @Date 07-25-2022 周一 11:44
 */
public enum ResultCode {

    SUCCESS(1001, "操作成功");

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private ResultCode (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
