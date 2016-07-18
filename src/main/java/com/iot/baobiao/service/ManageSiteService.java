package com.iot.baobiao.service;

import com.iot.baobiao.dao.SiteDao;
import com.iot.baobiao.dao.UserDao;
import com.iot.baobiao.dao.UserSiteDao;
import com.iot.baobiao.exception.SiteNotFoundException;
import com.iot.baobiao.pojo.Site;
import com.iot.baobiao.pojo.UserSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ja on 2016/6/22.
 */

@Service
public class ManageSiteService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SiteDao siteDao;

    @Autowired
    private UserSiteDao userSiteDao;

    //增加自选网站
    public void addUserSite(int user_id, Site s) {

        //先查看数据库中是否已有该网站信息，如果没有需要添加到site表里面
        Site site = null;
        try {
            site = siteDao.findSiteByDomain(s.getDomain());
        } catch (Exception e) {

        }

        //如果没有查找到网站信息
        if (site == null) {
            siteDao.insertSite(s);
        } else {
            //如果找到了网站信息
            s.setId(site.getId());
        }
        userDao.addSite(user_id, s.getId());
        userSiteDao.add(user_id, s.getId(), s.getSiteName(), s.getStartUrl());
    }

    //删除自选网站
    public void deleteUserSite(int user_id, int site_id) {
        userDao.deleteSite(user_id, site_id);
        userSiteDao.delete(user_id, site_id);
    }

    //查询用户的自选网站，以列表的形式返回网站具体内容以展示给用户
    public Map<String, Object> queryUserSite(int user_id) {
        //先通过用户id查找出用户添加的网站的id串，再通过这个网站的id串查找对应的网站名称
        try {
            String siteids = userDao.findSiteByUserID(user_id);
            if (siteids == null || siteids.equals("")) {
                throw new SiteNotFoundException();
            }

            Map<String, Object> map = new HashMap<String, Object>();
            List<Site> siteList = siteDao.findSiteByIDs(siteids);
            map.put("sites", siteList);
            List<UserSite> userSiteList = userSiteDao.findByUser(user_id);
            map.put("sitenames", userSiteList);
            return map;

        } catch (EmptyResultDataAccessException e) {
            throw new SiteNotFoundException();
        }
    }


    //查询用户的自选网站，返回一个网站的id串以便查找数据
    public String queryUserSiteIDS(int user_id) {
        String siteids = userDao.findSiteByUserID(user_id);
        if (siteids == null || siteids.equals("")) {
            throw new SiteNotFoundException();
        }
        return siteids;
    }

    //返回数据库中所有的Domain List，以便客户端进行自动补齐
    public List<String> queryDomainList() {
        return siteDao.findAllSite();
    }
}
