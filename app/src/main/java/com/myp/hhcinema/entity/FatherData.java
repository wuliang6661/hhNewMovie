package com.myp.hhcinema.entity;

import java.util.ArrayList;

/**
 * Created by Witness on 2019/6/26
 * Describe:
 */
public class FatherData {
    private String title;
    private ArrayList<ChildrenData> list;// 二级列表数据
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ArrayList<ChildrenData> getList() {
        return list;
    }
    public void setList(ArrayList<ChildrenData> list) {
        this.list = list;
    }
}
