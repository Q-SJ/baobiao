package com.iot.baobiao.pojo;

/**
 * Created by ja on 2016/6/28.
 */
public class Site {

    private int id;
    private String domain;
    private String startUrl;
    private String siteName;

    public Site() {
    }

    public Site(String domain, String startUrl, String siteName) {
        this.domain = domain;
        this.startUrl = startUrl;
        this.siteName = siteName;
    }

    public Site(int id, String domain, String startUrl, String siteName) {
        this.id = id;
        this.domain = domain;
        this.startUrl = startUrl;
        this.siteName = siteName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
