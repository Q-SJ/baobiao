package com.iot.baobiao.pojo;

import java.util.Date;

/**
 * Created by ja on 2016/6/22.
 */
public class SelfSite {
    private String url;
    private String code;
    private String name;
    private String category;
    private int order_index;
    private Date fetch_time;
    private String text_value;
    private String html_value;
    private Date date_value;
    private double num_value;
    private String server_num;
    private String domain;

    public SelfSite() {
    }

    public SelfSite(String url, String code, String name, String category, int order_index, Date fetch_time, String text_value, String html_value, Date date_value, double num_value, String server_num, String domain) {
        this.url = url;
        this.code = code;
        this.name = name;
        this.category = category;
        this.order_index = order_index;
        this.fetch_time = fetch_time;
        this.text_value = text_value;
        this.html_value = html_value;
        this.date_value = date_value;
        this.num_value = num_value;
        this.server_num = server_num;
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOrder_index() {
        return order_index;
    }

    public void setOrder_index(int order_index) {
        this.order_index = order_index;
    }

    public Date getFetch_time() {
        return fetch_time;
    }

    public void setFetch_time(Date fetch_time) {
        this.fetch_time = fetch_time;
    }

    public String getText_value() {
        return text_value;
    }

    public void setText_value(String text_value) {
        this.text_value = text_value;
    }

    public String getHtml_value() {
        return html_value;
    }

    public void setHtml_value(String html_value) {
        this.html_value = html_value;
    }

    public Date getDate_value() {
        return date_value;
    }

    public void setDate_value(Date date_value) {
        this.date_value = date_value;
    }

    public double getNum_value() {
        return num_value;
    }

    public void setNum_value(double num_value) {
        this.num_value = num_value;
    }

    public void setNum_value(int num_value) {
        this.num_value = num_value;
    }

    public String getServer_num() {
        return server_num;
    }

    public void setServer_num(String server_num) {
        this.server_num = server_num;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
