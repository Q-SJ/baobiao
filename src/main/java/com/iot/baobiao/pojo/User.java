package com.iot.baobiao.pojo;

/**
 * Created by ja on 2016/6/22.
 */
public class User {
    private int id;
    private String phonenum;
    private String username;
    private String password;
    private String email;
    private String corporation;
    private int industry;
    private String sites;

    public User() {
    }

    public User(String phonenum, String password) {
        this.phonenum = phonenum;
        this.password = password;
    }

    public User(int id, String username, String email, String corporation, int industry) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.corporation = corporation;
        this.industry = industry;
    }

    public User(int id, String phonenum, String username, String password, String email, String corporation, int industry, String sites) {
        this.id = id;
        this.phonenum = phonenum;
        this.username = username;
        this.password = password;
        this.email = email;
        this.corporation = corporation;
        this.industry = industry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }
}
