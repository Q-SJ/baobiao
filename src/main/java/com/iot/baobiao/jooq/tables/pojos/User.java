/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = 484562403;

    private Integer id;
    private String  phonenum;
    private String  username;
    private String  password;
    private String  email;
    private String  corporation;
    private Integer industry;
    private String  sites;
    private String  keyword;

    public User() {}

    public User(User value) {
        this.id = value.id;
        this.phonenum = value.phonenum;
        this.username = value.username;
        this.password = value.password;
        this.email = value.email;
        this.corporation = value.corporation;
        this.industry = value.industry;
        this.sites = value.sites;
        this.keyword = value.keyword;
    }

    public User(
        Integer id,
        String  phonenum,
        String  username,
        String  password,
        String  email,
        String  corporation,
        Integer industry,
        String  sites,
        String  keyword
    ) {
        this.id = id;
        this.phonenum = phonenum;
        this.username = username;
        this.password = password;
        this.email = email;
        this.corporation = corporation;
        this.industry = industry;
        this.sites = sites;
        this.keyword = keyword;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhonenum() {
        return this.phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorporation() {
        return this.corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public Integer getIndustry() {
        return this.industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getSites() {
        return this.sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(phonenum);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(email);
        sb.append(", ").append(corporation);
        sb.append(", ").append(industry);
        sb.append(", ").append(sites);
        sb.append(", ").append(keyword);

        sb.append(")");
        return sb.toString();
    }
}
