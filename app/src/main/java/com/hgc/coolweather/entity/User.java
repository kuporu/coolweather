package com.hgc.coolweather.entity;

/**
 * @Description TODO
 * @systemUser gchan2
 * @Author hanguangchuan
 * @Date 07-18-2022 周一 10:19
 */
public class User {

    private String name;

    private String password;

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
