package com.iot.baobiao.dao;

import com.iot.baobiao.pojo.BaobiaoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by jia on 2016/10/9.
 */

@Repository
public class BaobiaoOrderDao {
    //用于java.util.Date和MySQL datetime数据类型的转换
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String INSERT = "INSERT INTO baobiaoorder(userid, outtradeno, tradeno, amount, time, status) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_OUTTRADENO = "SELECT * FROM baobiaoorder WHERE outtradeno = ?";
    private static final String UPDATE_TRADE_STATUS = "UPDATE baobiaoorder SET status = ? WHERE outtradeno = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final class BaobiaoOrderMapper implements RowMapper<BaobiaoOrder> {

        @Override
        public BaobiaoOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            return new BaobiaoOrder(
                    resultSet.getInt("id"),
                    resultSet.getInt("userid"),
                    resultSet.getString("outtradeno"),
                    resultSet.getString("tradeno"),
                    resultSet.getDouble("amount"),
                    resultSet.getDate("time"),
                    resultSet.getInt("status")
            );
        }
    }

    public void insertOrder(final BaobiaoOrder order) {
        jdbcTemplate.update(INSERT, order.getUserid(), order.getOuttradeno(), order.getTradeno(), order.getAmount(), sdf.format(order.getTime()), order.getStatus());
    }

    public BaobiaoOrder findOrderByOuttradeno(final String outtradeno) {
        return jdbcTemplate.queryForObject(SELECT_BY_OUTTRADENO, new BaobiaoOrderMapper(),outtradeno);
    }

    public void modifyStatus(final int status, final String outtradeno) {
        jdbcTemplate.update(UPDATE_TRADE_STATUS, status, outtradeno);
    }
}
