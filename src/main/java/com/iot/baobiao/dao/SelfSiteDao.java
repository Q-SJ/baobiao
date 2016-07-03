package com.iot.baobiao.dao;

import com.iot.baobiao.pojo.SelfSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ja on 2016/6/22.
 */

@Repository
public class SelfSiteDao {

    private static final int PAGENUM = 1000;

    private String getOffset(String sql, int page) {
        return sql + " LIMIT " + PAGENUM * page + " , " + PAGENUM;
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
                    resultSet.getString("domain")
            );
        }
    }

    public List<SelfSite> findByUrl(List<String> domains, int page) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("domains", domains);
        String sqlStr = "SELECT * FROM self_site WHERE domain IN (:domains) ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), paramMap, new SelfSiteRowMapper());
    }

    public List<SelfSite> findByUrlAndTime(List<String> domains, Date fromTime, int page) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("domains", domains);
        parameters.addValue("fromtime", fromTime);
        String sqlStr = "SELECT * FROM self_site WHERE domain IN (:domains) AND fetch_time > :fromtime " +
                " ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), parameters, new SelfSiteRowMapper());
    }

    public List<SelfSite> findByUrlAndWord(List<String> domains, List<String> words, int page) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("domains", domains);
        String sqlStr = "SELECT * FROM self_site WHERE domain IN (:domains) ";
        for (int i = 0; i < words.size(); i++) {
            if (i == 0) sqlStr += " AND ";
            sqlStr += (" name LIKE :word" + i + " ");
            if (i != (words.size()-1)) sqlStr += " OR ";
            parameters.addValue("word" + i, "%" + words.get(i) + "%");
        }
        sqlStr += " ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), parameters, new SelfSiteRowMapper());
    }

    public List<SelfSite> findByUrlAndWordAndTime(List<String> domains, Date fromTime, List<String> words, int page) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("domains", domains);
        parameters.addValue("fromtime", fromTime);
        String sqlStr = "SELECT * FROM self_site WHERE domain IN (:domains) ";
        for (int i = 0; i < words.size(); i++) {
            if (i == 0) sqlStr += " AND ";
            sqlStr += (" name LIKE :word" + i + " ");
            if (i != (words.size()-1)) sqlStr += " OR ";
            parameters.addValue("word" + i, "%" + words.get(i) + "%");
        }
        sqlStr += " ORDER BY fetch_time DESC";
        return namedParameterJdbcTemplate.query(getOffset(sqlStr, page), parameters, new SelfSiteRowMapper());
    }
}
