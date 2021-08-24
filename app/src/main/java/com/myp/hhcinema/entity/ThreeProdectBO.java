package com.myp.hhcinema.entity;

import java.util.List;

/**
 * Created by Witness on 2019/3/20
 * Describe:
 */
public class ThreeProdectBO {

    private List<SubListBean> subList;

    public List<SubListBean> getSubList() {
        return subList;
    }

    public void setSubList(List<SubListBean> subList) {
        this.subList = subList;
    }

    public static class SubListBean {
        /**
         * activityNumber : null
         * activityPrice : null
         * businessId : 1
         * cardDiscountType : 2
         * cinemaId : 11
         * counterPrice : null
         * createTime : 2019-03-02 15:55:05
         * deleteTime : null
         * dxId : 20
         * goodsCode : null
         * goodsNum : p20160704052647
         * id : 269
         * imageUrl : https://hhyy.happydoit.com/upload/201901/itemDefaut.jpg
         * indexShow : true
         * inventory : 609
         * itemClassId : null
         * listOrder : 0
         * merdesc :
         * modifyTime : 2019-03-20 17:40:30
         * name : 饮料组们
         * number : null
         * onlinePrice : 500.9
         * originalPrice : null
         * overInventorySellFlag : 0
         * packageDetail : []
         * partnerPrice : null
         * type : package
         * unit : 套
         * updateTime : 2016-11-23 18:14:59
         * valid : true
         * version : 3
         */

        private long activityNumber;
        private String activityPrice;
        private int businessId;
        private int cardDiscountType;
        private int cinemaId;
        private Object counterPrice;
        private String createTime;
        private Object deleteTime;
        private int dxId;
        private Object goodsCode;
        private String goodsNum;
        private int id;
        private String imageUrl;
        private boolean indexShow;
        private int inventory;
        private Object itemClassId;
        private int listOrder;
        private String merdesc;
        private String modifyTime;
        private String name;
        private Object number;
        private double onlinePrice;
        private Object originalPrice;
        private int overInventorySellFlag;
        private Object partnerPrice;
        private String type;
        private String unit;
        private String updateTime;
        private boolean valid;
        private int version;
        private List<?> packageDetail;

        public long getActivityNumber() {
            return activityNumber;
        }

        public void setActivityNumber(long activityNumber) {
            this.activityNumber = activityNumber;
        }

        public String getActivityPrice() {
            return activityPrice;
        }

        public void setActivityPrice(String activityPrice) {
            this.activityPrice = activityPrice;
        }

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public int getCardDiscountType() {
            return cardDiscountType;
        }

        public void setCardDiscountType(int cardDiscountType) {
            this.cardDiscountType = cardDiscountType;
        }

        public int getCinemaId() {
            return cinemaId;
        }

        public void setCinemaId(int cinemaId) {
            this.cinemaId = cinemaId;
        }

        public Object getCounterPrice() {
            return counterPrice;
        }

        public void setCounterPrice(Object counterPrice) {
            this.counterPrice = counterPrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(Object deleteTime) {
            this.deleteTime = deleteTime;
        }

        public int getDxId() {
            return dxId;
        }

        public void setDxId(int dxId) {
            this.dxId = dxId;
        }

        public Object getGoodsCode() {
            return goodsCode;
        }

        public void setGoodsCode(Object goodsCode) {
            this.goodsCode = goodsCode;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isIndexShow() {
            return indexShow;
        }

        public void setIndexShow(boolean indexShow) {
            this.indexShow = indexShow;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public Object getItemClassId() {
            return itemClassId;
        }

        public void setItemClassId(Object itemClassId) {
            this.itemClassId = itemClassId;
        }

        public int getListOrder() {
            return listOrder;
        }

        public void setListOrder(int listOrder) {
            this.listOrder = listOrder;
        }

        public String getMerdesc() {
            return merdesc;
        }

        public void setMerdesc(String merdesc) {
            this.merdesc = merdesc;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }

        public double getOnlinePrice() {
            return onlinePrice;
        }

        public void setOnlinePrice(double onlinePrice) {
            this.onlinePrice = onlinePrice;
        }

        public Object getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Object originalPrice) {
            this.originalPrice = originalPrice;
        }

        public int getOverInventorySellFlag() {
            return overInventorySellFlag;
        }

        public void setOverInventorySellFlag(int overInventorySellFlag) {
            this.overInventorySellFlag = overInventorySellFlag;
        }

        public Object getPartnerPrice() {
            return partnerPrice;
        }

        public void setPartnerPrice(Object partnerPrice) {
            this.partnerPrice = partnerPrice;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public List<?> getPackageDetail() {
            return packageDetail;
        }

        public void setPackageDetail(List<?> packageDetail) {
            this.packageDetail = packageDetail;
        }
    }
}
