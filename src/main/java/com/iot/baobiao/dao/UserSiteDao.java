package com.iot.baobiao.dao;

import com.iot.baobiao.pojo.Site;
import com.iot.baobiao.pojo.UserSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jia on 2016/7/11.
 */

@Repository
public class UserSiteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT = "INSERT INTO user_site(user_id, site_id, sitename, start_url) VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM user_site WHERE user_id = ? AND site_id = ?";
    private static final String SELECT_BY_USER = "SELECT * FROM user_site WHERE user_id = ? ORDER BY site_id ASC";

    private static final class UserSiteRowMapper implements RowMapper<UserSite> {

        public UserSite mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserSite(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("site_id"),
                    resultSet.getString("sitename"),
                    resultSet.getString("start_url")
            );
        }
    }

    //添加一条记录
    public void add(int user_id, int site_id, String siteName, String startUrl) {
        jdbcTemplate.update(INSERT, user_id, site_id, siteName, startUrl);
    }

    public void delete(int user_id, int site_id) {
        jdbcTemplate.update(DELETE, user_id, site_id);
    }

    public List<UserSite> findByUser(int user_id) {
        return jdbcTemplate.query(SELECT_BY_USER, new Object[]{user_id}, new UserSiteRowMapper());
    }
}
