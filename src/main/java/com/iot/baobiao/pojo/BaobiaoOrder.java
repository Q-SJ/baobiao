package com.iot.baobiao.pojo;

import java.util.Date;

/**
 * Created by jia on 2016/10/9.
 */
public class BaobiaoOrder {
    private int id;
    private int userid;
    private String outtradeno;
    private String tradeno;
    private double amount;
    private Date time;
    private int status;

    public static final int TRADE_SUCCESS = 0;  //交易支付成功
    public static final int TRADE_CLOSED = 2; //未付款交易超时关闭，或支付完成后全额退款
    public static final int WAIT_BUYER_PAY = 1; //交易创建，等待买家付款
    public static final int UNKNOWN_STATE = 3;//未知状态

    public BaobiaoOrder(int userid, String outtradeno, String tradeno, double amount, Date time, int status) {
        this.userid = userid;
        this.outtradeno = outtradeno;
        this.tradeno = tradeno;
        this.amount = amount;
        this.time = time;
        this.status = status;
    }

    public BaobiaoOrder(int id, int userid, String outtradeno, String tradeno, double amount, Date time, int status) {
        this.id = id;
        this.userid = userid;
        this.outtradeno = outtradeno;
        this.tradeno = tradeno;
        this.amount = amount;
        this.time = time;
        this.status = status;
    }

    public BaobiaoOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno;
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
