package com.myp.hhcinema.entity;

import java.io.Serializable;

/**
 * Created by wuliang on 2017/6/6.
 * <p>
 * 用户bean
 */

public class NewMenberNum implements Serializable {

    private int points;
    private int tickets;//券
    private int orders;//票


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}
