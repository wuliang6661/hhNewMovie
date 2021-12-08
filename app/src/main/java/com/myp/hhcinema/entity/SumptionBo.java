package com.myp.hhcinema.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/29.
 */
public class SumptionBo implements Serializable {

    private Long id;
    private String payDate;
    private String orderType;
    private int payStatus;
    private String payPrice;

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public double getTicketRealPrice() {
        return ticketRealPrice;
    }

    public void setTicketRealPrice(double ticketRealPrice) {
        this.ticketRealPrice = ticketRealPrice;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    private String ticketNum;
    private double ticketRealPrice;

}
