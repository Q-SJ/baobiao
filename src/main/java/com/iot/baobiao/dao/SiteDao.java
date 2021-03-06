package com.iot.baobiao.dao;

import com.iot.baobiao.pojo.Site;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ja on 2016/6/28.
 */

@Repository
public class SiteDao {

    private static final String SELECT_BY_ID = "SELECT * FROM site WHERE id = ?";
    private static final String SELECT_BY_DOMAIN = "SELECT * FROM site WHERE domain = ?";
    private static final String INSERT = "INSERT INTO site(domain, start_url, sitename) VALUES(?, ?, ?)";
    private static final String SELECT_BY_IDS = "SELECT * FROM site WHERE id IN (:ids)";
    private static final String SELECT_ALL = "SELECT domain FROM site";
    private static final String DELETE_SITE = "DELETE FROM site WHERE id = ?";

    private static Log log = LogFactory.getLog(SiteDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class SiteRowMapper implements RowMapper<Site> {

        public Site mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Site(
                    resultSet.getInt("id"),
                    resultSet.getString("domain"),
                    resultSet.getString("start_url"),
                    resultSet.getString("sitename")
            );
        }
    }

    @Cacheable("siteCache")
    public Site findSiteById(int id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new SiteRowMapper(), id);
    }

    @Cacheable("siteCache")
    public List<Site> findSiteByIDs(String ids) {
        List<String> sitesID = Arrays.asList(ids.split(",", 0));
        List<Integer> idList = new ArrayList<Integer>();
        for (String siteID : sitesID) {
            idList.add(Integer.parseInt(siteID));
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ids", idList);
        return namedParameterJdbcTemplate.query(SELECT_BY_IDS, paramMap, new SiteRowMapper());
    }

    @Cacheable("siteCache")
    public Site findSiteByDomain(String domain) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_DOMAIN, new SiteRowMapper(), domain);
        } catch (EmptyResultDataAccessException e) {
            log.info("没有找到域名为" + domain + "的网站！");
            return null;
        }
    }

    @CachePut(value = "siteCache", key = "#result.id")
    @CacheEvict(value = "siteCache", key = "#site.domain")
    public Site insertSite(final Site site) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT, new String[] {"id"});
                ps.setString(1, site.getDomain());
                ps.setString(2, site.getStartUrl());
                ps.setString(3, site.getSiteName());
                return ps;
            }
        }, key);
        site.setId(key.getKey().intValue());
        return site;
    }

    @CacheEvict("siteCache")
    public void deleteSite(int site_id) {
        jdbcTemplate.update(DELETE_SITE, site_id);
    }

    public List<String> findAllSite() {
        return jdbcTemplate.queryForList(SELECT_ALL, String.class);
    }
}
