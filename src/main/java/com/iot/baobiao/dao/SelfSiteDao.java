package com.iot.baobiao.dao;

import com.iot.baobiao.pojo.SelfSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ja on 2016/6/22.
 */

@Repository
public class SelfSiteDao {

    private static final int PAGENUM = 1000;

    //用于java.util.Date和MySQL datetime数据类型的转换
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private String getOffset(String sql, int page) {
        return sql + " LIMIT " + PAGENUM * page + " , " + PAGENUM;
    }

    private List<Integer> stringToIntList(String ids) {
        List<String> sitesID = Arrays.asList(ids.split(",", 0));
        List<Integer> idList = new ArrayList<Integer>();
        for (String siteID : sitesID) {
            idList.add(Integer.parseInt(siteID));
        }
        return idList;
    }

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class SelfSiteRowMapper implements RowMapper<SelfSite> {

        public SelfSite mapRow(ResultSet resultSet, int i) throws SQLException {
            return new SelfSite(
                    resultSet.getString("url"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("category"),
                    resultSet.getInt("order_index"),
                    resultSet.getDate("fetch_time"),
                    resultSet.getString("text_value"),
                    resultSet.getString("html_value"),
                    resultSet.getDate("date_value"),
                    resultSet.getDouble("num_value"),
                    resultSet.getString("server_num"),
                    resultSet.getString("domain"),
                    resultSet.getInt("url_id")
            );
        }
    }

    public List<SelfSite> findByUrl(String ids, int page) {
        //ids即为用户表中的sites字段，注意最后面有一个逗号

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ids", stringToIntList(ids));
        String sqlStr = "SELECT * FROM self_site WHERE url_id IN (:ids) ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), paramMap, new SelfSiteRowMapper());
    }

    public List<SelfSite> findByUrlAndTime(String ids, Date fromTime, int page) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", stringToIntList(ids));
        parameters.addValue("fromtime", sdf.format(fromTime));
        String sqlStr = "SELECT * FROM self_site WHERE url_id IN (:ids) AND fetch_time > :fromtime " +
                " ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), parameters, new SelfSiteRowMapper());
    }

    public List<SelfSite> findByUrlAndWord(String ids, List<String> words, int page) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", stringToIntList(ids));
        String sqlStr = "SELECT * FROM self_site WHERE url_id IN (:ids) ";
        for (int i = 0; i < words.size(); i++) {
            if (i == 0) sqlStr += " AND (";
            sqlStr += (" name LIKE :word" + i + " ");
            if (i != (words.size() - 1)) sqlStr += " OR ";
            parameters.addValue("word" + i, "%" + words.get(i) + "%");
        }
        sqlStr += " ) ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), parameters, new SelfSiteRowMapper());
    }

    public List<SelfSite> findByUrlAndWordAndTime(String ids, Date fromTime, List<String> words, int page) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", stringToIntList(ids));
        parameters.addValue("fromtime", sdf.format(fromTime));
        String sqlStr = "SELECT * FROM self_site WHERE url_id IN (:ids) ";
        for (int i = 0; i < words.size(); i++) {
            if (i == 0) sqlStr += " AND (";
            sqlStr += (" name LIKE :word" + i + " ");
            if (i != (words.size() - 1)) sqlStr += " OR ";
            parameters.addValue("word" + i, "%" + words.get(i) + "%");
        }
        sqlStr += " ) AND fetch_time > :fromtime ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), parameters, new SelfSiteRowMapper());
    }
}
