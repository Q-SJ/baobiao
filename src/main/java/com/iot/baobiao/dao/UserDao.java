package com.iot.baobiao.dao;

import com.iot.baobiao.exception.DuplicateSite;
import com.iot.baobiao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ja on 2016/6/22.
 */

@Repository
public class UserDao {

    private static final String SELECT_BY_USERNAME = " SELECT * FROM user WHERE username = ?";
    private static final String SELECT_BY_PHONENUM = " SELECT * FROM user WHERE phonenum = ?";
    private static final String SELECT_SITE_BY_USERID = " SELECT site FROM user WHERE id = ?";
    private static final String UPDATE_SITE = "UPDATE user SET site = ? WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET username = ?, email = ?, corporation = ?, industry = ? WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //将数据库查询结果集与User对象进行映射
    private static final class UserRowMapper implements RowMapper<User> {

        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("phonenum"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("corporation"),
                    resultSet.getInt("industry"),
                    resultSet.getString("site")
            );
        }
    }

    @CacheEvict(value = "userCache", key = "#user.id")
    public void updateInfo(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getUsername(), user.getEmail(), user.getCorporation(), user.getIndustry(), user.getId());
    }

//    @Cacheable("userCache")
    public User findUserByPhonenum(final String phonenum) {
        return jdbcTemplate.queryForObject(SELECT_BY_PHONENUM, new UserRowMapper(), phonenum);
    }

    @Cacheable("userCache")
    public String findSiteByUserID(final int id) {
        return jdbcTemplate.queryForObject(SELECT_SITE_BY_USERID, String.class, id);
    }

    //增加用户选择的网站
    @CacheEvict(value = "userCache", key = "#user_id")
    public void addSite(final int user_id, final int site_id) {
        String sites = findSiteByUserID(user_id);
        List<String> siteList = Arrays.asList(sites.split(",", 0));

        //如果用户已经添加过此网站，则抛出重复添加网站的异常
        if (siteList.contains("" + site_id)) {
            throw new DuplicateSite();
        }

        sites += (site_id + ",");
        jdbcTemplate.update(UPDATE_SITE, sites, user_id);
    }


    //删除用户选择的网站
    @CacheEvict(value = "userCache", key = "#user_id")
    public void deleteSite(final int user_id, final int site_id) {
        //找到用户添加的网站的id串，并生成一个id的List
        String sites = findSiteByUserID(user_id);
        List<String> siteList_ = Arrays.asList(sites.split(",", 0));
        //Arrays.asList()函数生成的ArrayList是固定长度的，不能添加或者删除元素，否则会抛出UnsupportedOperation异常
        //所以需要重新生成一个List对象
        List<String> siteList = new ArrayList<String>(siteList_);

        Iterator<String> iterator = siteList.iterator();
        String updatedSite = "";
        while (iterator.hasNext()) {
            String site = iterator.next();
            if (site.equals("" + site_id)) {
                iterator.remove();
                continue;
            }
            updatedSite += (site + ",");
        }

        jdbcTemplate.update(UPDATE_SITE, updatedSite, user_id);
    }
}
