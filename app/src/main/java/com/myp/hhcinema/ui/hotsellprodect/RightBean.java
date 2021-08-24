package com.myp.hhcinema.ui.hotsellprodect;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fatchao
 * 日期  2017-07-24.
 * 邮箱  fat_chao@163.com
 */

public class RightBean implements Parcelable {
    private String name;
    private String titleName;
    private String tag;
    private boolean isTitle;
    private String imgsrc;
    private String price;
    private String listintPrice;
    private String id;
    private String merdesc;
    private double activityPrice;//活动价格
    private int activityNumber;//活动可买数量
    private int personNum;

    public RightBean(String name) {
        this.name = name;
    }

    protected RightBean(Parcel in) {
        name = in.readString();
        price = in.readString();
        listintPrice = in.readString();
        id = in.readString();
        titleName = in.readString();
        tag = in.readString();
        isTitle = in.readByte() != 0;
        imgsrc = in.readString();
        merdesc = in.readString();
        activityPrice = in.readDouble();
        activityNumber = in.readInt();
        personNum = in.readInt();
    }

    public static final Creator<RightBean> CREATOR = new Creator<RightBean>() {
        @Override
        public RightBean createFromParcel(Parcel in) {
            return new RightBean(in);
        }

        @Override
        public RightBean[] newArray(int size) {
            return new RightBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getListintPrice() {
        return listintPrice;
    }

    public void setListintPrice(String listintPrice) {
        this.listintPrice = listintPrice;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getMerdesc() {
        return merdesc;
    }

    public void setMerdesc(String merdesc) {
        this.merdesc = merdesc;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(listintPrice);
        dest.writeString(id);
        dest.writeString(titleName);
        dest.writeString(tag);
        dest.writeByte((byte) (isTitle ? 1 : 0));
        dest.writeString(imgsrc);
        dest.writeString(merdesc);
        dest.writeDouble(activityPrice);
        dest.writeInt(activityNumber);
        dest.writeInt(personNum);
    }
}
