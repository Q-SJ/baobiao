package com.iot.baobiao.controller;

import com.iot.baobiao.pojo.SelfSite;
import com.iot.baobiao.pojo.Site;
import com.iot.baobiao.service.DataService;
import com.iot.baobiao.service.ManageSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by ja on 2016/6/22.
 */

//负责网站添加、删除及查询的请求
@RestController
public class SiteController {

    @Autowired
    private ManageSiteService manageSiteService;

    @Autowired
    private DataService dataService;

    @RequestMapping("/{user_id}/add")
    public Map<String, Object> addSite(@PathVariable int user_id, @RequestParam String url, @RequestParam(required = false) String sitename) {
        String str = String.format("/{%d}/add", user_id);
        System.out.println(str);
        Map<String, Object> map = new HashMap<String, Object>();
        Site site = null;
        try {
            if (!(url.startsWith("http://") || url.startsWith("https://"))) url = "http://" + url;
            URL u = new URL(url);
            site = new Site(u.getHost(), url, sitename);
        } catch (MalformedURLException e) {
            map.put("status", "error");
            map.put("message", "输入的网址错误！");
            return map;
        }

        manageSiteService.addUserSite(user_id, site);
        map.put("status", "ok");
        map.put("site_id", site.getId());
        return map;
    }

    @RequestMapping("/{user_id}/delete")
    public Map<String, String> deleteSite(@PathVariable int user_id, @RequestParam int site_id) {
        String str = String.format("/{%d}/delete", user_id);
        System.out.println(str);

        Map<String, String> map = new HashMap<String, String>();
        manageSiteService.deleteUserSite(user_id, site_id);
        map.put("status", "ok");
        return map;
    }

    @RequestMapping("/{user_id}/query")
    public List<Site> querySite(@PathVariable int user_id) {
        String str = String.format("/{%d}/query", user_id);
        System.out.println(str);

        List<Site> siteList = manageSiteService.queryUserSite(user_id);
        return siteList;
    }

    @RequestMapping("/{user_id}/data")
    public List<SelfSite> fetchData(@PathVariable int user_id, @RequestParam int page, @RequestParam(required = false) String fromTime,
                                    @RequestParam(required = false) String words) {
        String str = String.format("/{%d}/data", user_id);
        System.out.println(str);

        String ids = manageSiteService.queryUserSiteIDS(user_id);
        List<String> wordList = null;
        if (words != null) {
            wordList = Arrays.asList(words.split(","));
        }
        Long timestamp = fromTime == null ? -1 : Long.parseLong(fromTime);
        Date time = timestamp == -1 ? null : new Date(timestamp);
        return dataService.fetchData(ids, time, wordList, page);
    }
}
