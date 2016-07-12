package com.iot.baobiao.pojo;

/**
 * Created by jia on 2016/7/11.
 */
public class UserSite {
    private int id;
    private int user_id;
    private int site_id;
    private String sitename;
    private String start_url;

    public UserSite(int id, int user_id, int site_id, String sitename, String start_url) {
        this.id = id;
        this.user_id = user_id;
        this.site_id = site_id;
        this.sitename = sitename;
        this.start_url = start_url;
    }

    public UserSite() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getStart_url() {
        return start_url;
    }

    public void setStart_url(String start_url) {
        this.start_url = start_url;
    }
}
