package com.iot.baobiao.controller;

import com.iot.baobiao.pojo.SelfSite;
import com.iot.baobiao.pojo.Site;
import com.iot.baobiao.service.DataService;
import com.iot.baobiao.service.ManageSiteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    private static Log log = LogFactory.getLog(SiteController.class);

    @RequestMapping("/{user_id}/add")
    public Map<String, Object> addSite(@PathVariable int user_id, @RequestParam String url, @RequestParam(required = false) String sitename) {
        log.debug(String.format("/{%d}/add", user_id));
        Map<String, Object> map = new HashMap<String, Object>();
        Site site;
        try {
            if (!(url.startsWith("http://") || url.startsWith("https://"))) url = "http://" + url;
            URL u = new URL(url);
            site = new Site(u.getHost(), url, sitename);
        } catch (MalformedURLException e) {
            map.put("status", "error");
            map.put("message", "输入的网址格式错误！");
            return map;
        }

        manageSiteService.addUserSite(user_id, site);
        map.put("status", "ok");
        map.put("site_id", site.getId());
        return map;
    }

    @RequestMapping("/{user_id}/delete")
    public Map<String, String> deleteSite(@PathVariable int user_id, @RequestParam int site_id) {
        log.debug(String.format("/{%d}/delete", user_id));

        Map<String, String> map = new HashMap<String, String>();
        manageSiteService.deleteUserSite(user_id, site_id);
        map.put("status", "ok");
        return map;
    }

    @RequestMapping("/{user_id}/query")
    public Map<String, Object> querySite(@PathVariable int user_id) {
        log.debug(String.format("/{%d}/query", user_id));

        Map<String, Object> sites = manageSiteService.queryUserSite(user_id);

        return sites;
    }

    @RequestMapping("/{user_id}/data")
    public Map<String, List<SelfSite>> fetchData(@PathVariable int user_id, @RequestParam int page, @RequestParam(required = false) String fromTime,
                                    @RequestParam(required = false) String words) {
        log.debug(String.format("/{%d}/data", user_id));

        Map<String, List<SelfSite>> map = new HashMap<String, List<SelfSite>>();

        String ids = manageSiteService.queryUserSiteIDS(user_id);
        List<String> wordList = null;
        if (words != null) {
            wordList = Arrays.asList(words.split(","));
        }
        Long timestamp = fromTime == null ? -1 : Long.parseLong(fromTime);
        Date time = timestamp == -1 ? null : new Date(timestamp);
        map.put("data", dataService.fetchData(ids, time, wordList, page));
        return map;
    }

    @RequestMapping("/{user_id}/non_data")
    public Map<String, List<SelfSite>> fetchNonData(@PathVariable int user_id, @RequestParam int page, @RequestParam(required = false) String fromTime,
                                    @RequestParam(required = false) String words) {
        log.debug(String.format("/{%d}/non_data", user_id));

        Map<String, List<SelfSite>> map = new HashMap<String, List<SelfSite>>();

        String ids = manageSiteService.queryUserSiteIDS(user_id);
        List<String> wordList = null;
        if (words != null) {
            wordList = Arrays.asList(words.split(","));
        }
        Long timestamp = fromTime == null ? -1 : Long.parseLong(fromTime);
        Date time = timestamp == -1 ? null : new Date(timestamp);
        map.put("data", dataService.fetchNonData(ids, time, wordList, page));
        return map;
    }

    @RequestMapping("/domains")
    public List<String> fetchDomainList() {
        return manageSiteService.queryDomainList();
    }

    @RequestMapping("/deleteSiteByUrl")
    public Map<String, String> deleteSiteByUrl(@RequestParam String url) {
        Map<String, String> map = new HashMap<String, String>();
        String message;
        if (!(url.startsWith("http://") || url.startsWith("https://"))) url = "http://" + url;
        try {
            URL u = new URL(url);
            String domain = u.getHost();
            message = manageSiteService.deleteSiteByDomain(domain);
        } catch (MalformedURLException e) {
            map.put("status", "error");
            map.put("message", "输入的网址格式错误！");
            return map;
        }
        map.put("status", "ok");
        map.put("message", message);
        log.info("删除网站" + url + "的结果是：" + message);
        return map;
    }

    @RequestMapping("/deleteSiteBySiteid")
    public Map<String, String> deleteSiteBySiteid(@RequestParam int siteid) {
        Map<String, String> map = new HashMap<String, String>();
        String message;
        message = manageSiteService.deleteSiteBySiteid(siteid);
        map.put("status", "ok");
        map.put("message", message);
        log.info("删除网站" + siteid + "的结果是：" + message);
        return map;
    }
}
