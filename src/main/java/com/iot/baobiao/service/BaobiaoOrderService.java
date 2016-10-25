package com.iot.baobiao.service;

import com.iot.baobiao.dao.BaobiaoOrderDao;
import com.iot.baobiao.pojo.BaobiaoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jia on 2016/10/9.
 */

@Service
public class BaobiaoOrderService {
    @Autowired
    private BaobiaoOrderDao baobiaoOrderDao;

    public void insertOrder(final BaobiaoOrder order) {
        baobiaoOrderDao.insertOrder(order);
    }

    public BaobiaoOrder findOrderByOuttradeno(final String outtradeno) {
        return baobiaoOrderDao.findOrderByOuttradeno(outtradeno);
    }

    public void modifyTradeStatus(final int status, final String outtradeno) {
        baobiaoOrderDao.modifyStatus(status, outtradeno);
    }
}
