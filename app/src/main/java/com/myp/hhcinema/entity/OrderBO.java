package com.myp.hhcinema.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuliang on 2017/6/19.
 * <p>
 * 订单bean
 */

public class OrderBO implements Serializable {

    /**
     * activityCode : null
     * activityPriceNum : null
     * beforeTicketPrice : 82.0
     * buyResponse : null
     * canRefund : null
     * card : null
     * cineMovieNum : 001105902015
     * cinema : null
     * cinemaId : 31
     * cinemaName : 电商平台测试影院电商平台测试影院电商平台测试影院
     * cinemaNumber : 10009999
     * comboList : null
     * coupon : null
     * createTime : 2019-01-23 10:08:55
     * deleteTime : null
     * dxAppUser : {"age":null,"birthday":null,"businessid":null,"city":null,"collectNum":null,"commentNum":null,"country":null,"createTime":"2019-01-20 14:35:23","deleteTime":null,"dxInsiderInfo":[],"gender":1,"header":"","id":3,"insider":null,"lastsign":null,"loginDate":"2019-01-23 10:15:12","mobile":"17376551017","modifyTime":"2019-01-23 10:15:12","nickName":"17376551017","password":"e10adc3949ba59abbe56e057f20f883e","points":null,"province":null,"qqUserId":null,"sign":null,"signdays":null,"uuid":"9afb9ce2-a740-4d91-b848-0264e29e5052","valid":true,"version":91,"wantseeNum":null,"watchedNum":null,"wbUserId":null,"wxOpenId":null,"wxUserId":null}
     * dxMovie : {"alias":"我的青春期","cineMovieId":341,"cineMovieNum":"001105902015","comment":null,"commentRecord":null,"content":null,"country":"","createTime":"2019-01-10 15:32:59","deleteTime":null,"dxActors":[],"dxDirectors":[],"dxVideos":[],"foreignName":null,"id":2,"infoUrl":"","introduction":"","level":"","mediaLib":"","modifyTime":"2019-01-23 10:00:00","movieDimensional":"2D","movieFormat":"数字","movieLanguage":"国语","movieName":"我的青春期","movieSize":"普通","movieSubtitle":"中文","movieType":null,"photos":"","picture":"","playTime":120,"point":0,"preVideo":"","product":"","publisher":"时代华纳","score":null,"screenPlanList":null,"sell":null,"shareurl":null,"startPlay":"2015-12-31 00:00:00","stopshow":null,"summary":null,"title":null,"type":"喜剧片","uniqueName":null,"valid":true,"version":271}
     * firstReportTime : null
     * hallId : 50
     * hallName : 1号全景声厅
     * id : 72
     * lowestPrice : null
     * marketPrice : 41.0
     * memo :
     * merOrder : {"address":null,"beforeActivityPrice":null,"beforeTicketPrice":20,"canRefund":null,"coupon":null,"createTime":"2019-01-23 10:08:55","deleteTime":null,"deliveryType":0,"details":[{"createTime":"2019-01-23 10:08:55","deleteTime":null,"id":74,"itemCombo":{"bussinessid":null,"cinemaId":31,"createTime":"2019-01-23 09:36:04","deleteTime":null,"id":2,"imageUrl":"https://hyg.happydoit.com/upload/201901/23SHeGiJ.jpg","merchandiseList":[{"businessId":1,"cardDiscountType":2,"cinemaId":31,"counterPrice":9,"createTime":"2019-01-16 16:44:06","deleteTime":null,"dxId":3,"goodsNum":null,"id":3,"imageUrl":null,"inventory":111,"itemClassId":null,"listOrder":0,"modifyTime":"2019-01-16 16:44:07","name":"常规套餐3","number":null,"onlinePrice":33,"originalPrice":null,"overInventorySellFlag":0,"packageDetail":[],"type":"package","unit":"","updateTime":"2018-12-29 17:31:50","valid":true,"version":1}],"modifyTime":null,"name":"双人套餐","originalPrice":30,"personNum":2,"salePrice":20,"sort":1,"status":1,"valid":true,"version":0},"merchandise":null,"modifyTime":null,"number":1,"price":20,"totalPrice":20,"type":2,"valid":true,"version":0}],"disPrice":20,"featureAppNo":null,"id":51,"isReady":1,"merTicket":null,"merTicketList":null,"merchandiseInfo":"[{\"id\":2,\"number\":1}]","modifyTime":null,"phone":null,"qrCode":null,"ticketFlag1":null,"ticketFlag2":null,"totalNumber":1,"totalPrice":20,"valid":true,"version":0}
     * modifyTime : 2019-01-23 10:09:22
     * orderExpireSecond : 1548210055
     * orderName : null
     * orderNum : 4b06abadcb904420190123100855
     * orderPhone : 17376551017
     * orderResponse : 下单成功
     * orderType : 0
     * partnerPrice : null
     * payDate : 2019-01-23 10:09:17
     * payNotifyResponse : null
     * payOrderNum : null
     * payPrice : 102.0
     * payResponse : null
     * payStatus : 1
     * payType : 1
     * playId : 349
     * playName : 2019-01-23 10:40:00
     * playUpdateTime : 2019-01-10 13:56:17
     * poundage : 1.0
     * qrCode : null
     * refundCode : null
     * refundCount : 0
     * refundFee : null
     * refundHandFee : null
     * refundId : null
     * refundResponse : null
     * refundStatus : 0
     * refundTime : null
     * reportStatus : 0
     * seatId : 11006,11007
     * seatLockFlag : 820933107743
     * seatStatus : 0
     * seatTicket : null
     * seatTicketList : null
     * seats : 10_4,10_5
     * serviceCharge : 0.0,0.0
     * shareUrl : null
     * subsidyCount : 0.0
     * subsidyType : null
     * successReportTime : null
     * ticketFlag1 : 462865
     * ticketFlag2 : 754823
     * ticketNum : 2
     * ticketOriginPrice : 82.0
     * ticketRealPrice : 102.0
     * totalDisprice : 82.0
     * valid : true
     * version : 1
     */

    private String activityCode;
    private String activityPriceNum;
    private String beforeTicketPrice;
    private String buyResponse;
    private String canRefund;
    private String card;
    private String cineMovieNum;
    private String cinema;
    private String cinemaId;
    private String cinemaName;
    private String cinemaNumber;
    private String comboList;
    private String coupon;
    private String createTime;
    private String deleteTime;
    private DxAppUserBean dxAppUser;
    private DxMovieBean dxMovie;
    private String firstReportTime;
    private String hallId;
    private String hallName;
    private String id;
    private String lowestPrice;
    private String marketPrice;
    private String memo;
    private MerOrderBean merOrder;
    private String modifyTime;
    private int orderExpireSecond;
    private String orderName;
    private String orderNum;
    private String orderPhone;
    private String orderResponse;
    private int orderType;
    private String partnerPrice;
    private String payDate;
    private String payNotifyResponse;
    private String payOrderNum;
    private String payPrice;
    private String payResponse;
    private int payStatus;
    private int payType;
    private String playName;
    private String playUpdateTime;
    private double poundage;
    private String qrCode;
    private String refundCode;
    private int refundCount;
    private String refundFee;
    private String refundHandFee;
    private String refundId;
    private String refundResponse;
    private int refundStatus;
    private String refundTime;
    private int reportStatus;
    private String seatId;
    private String seatLockFlag;
    private int seatStatus;
    private String seatTicket;
    private String seatTicketList;
    private String seats;
    private String serviceCharge;
    private String shareUrl;
    private double subsidyCount;
    private String subsidyType;
    private String successReportTime;
    private String ticketFlag1;
    private String ticketFlag2;
    private String ticketNum;
    private String ticketOriginPrice;
    private String ticketRealPrice;
    private String totalDisprice;
    private boolean valid;
    private int version;
    private String movieDimensional;//影片放映类型（如：3D，2D）
    private String movieSize;//屏幕尺寸（如：IMAX，普通）
    private String movieLanguage;//配音

    public String getMovieDimensional() {
        return movieDimensional;
    }

    public void setMovieDimensional(String movieDimensional) {
        this.movieDimensional = movieDimensional;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public void setMovieSize(String movieSize) {
        this.movieSize = movieSize;
    }

    public String getMovieSize() {
        return movieSize;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }


    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityPriceNum() {
        return activityPriceNum;
    }

    public void setActivityPriceNum(String activityPriceNum) {
        this.activityPriceNum = activityPriceNum;
    }

    public String getBeforeTicketPrice() {
        return beforeTicketPrice;
    }

    public void setBeforeTicketPrice(String beforeTicketPrice) {
        this.beforeTicketPrice = beforeTicketPrice;
    }

    public String getBuyResponse() {
        return buyResponse;
    }

    public void setBuyResponse(String buyResponse) {
        this.buyResponse = buyResponse;
    }

    public String getCanRefund() {
        return canRefund;
    }

    public void setCanRefund(String canRefund) {
        this.canRefund = canRefund;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCineMovieNum() {
        return cineMovieNum;
    }

    public void setCineMovieNum(String cineMovieNum) {
        this.cineMovieNum = cineMovieNum;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaNumber() {
        return cinemaNumber;
    }

    public void setCinemaNumber(String cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }

    public String getComboList() {
        return comboList;
    }

    public void setComboList(String comboList) {
        this.comboList = comboList;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public DxAppUserBean getDxAppUser() {
        return dxAppUser;
    }

    public void setDxAppUser(DxAppUserBean dxAppUser) {
        this.dxAppUser = dxAppUser;
    }

    public DxMovieBean getDxMovie() {
        return dxMovie;
    }

    public void setDxMovie(DxMovieBean dxMovie) {
        this.dxMovie = dxMovie;
    }

    public String getFirstReportTime() {
        return firstReportTime;
    }

    public void setFirstReportTime(String firstReportTime) {
        this.firstReportTime = firstReportTime;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public MerOrderBean getMerOrder() {
        return merOrder;
    }

    public void setMerOrder(MerOrderBean merOrder) {
        this.merOrder = merOrder;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getOrderExpireSecond() {
        return orderExpireSecond;
    }

    public void setOrderExpireSecond(int orderExpireSecond) {
        this.orderExpireSecond = orderExpireSecond;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderResponse() {
        return orderResponse;
    }

    public void setOrderResponse(String orderResponse) {
        this.orderResponse = orderResponse;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getPartnerPrice() {
        return partnerPrice;
    }

    public void setPartnerPrice(String partnerPrice) {
        this.partnerPrice = partnerPrice;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayNotifyResponse() {
        return payNotifyResponse;
    }

    public void setPayNotifyResponse(String payNotifyResponse) {
        this.payNotifyResponse = payNotifyResponse;
    }

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPayResponse() {
        return payResponse;
    }

    public void setPayResponse(String payResponse) {
        this.payResponse = payResponse;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }


    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPlayUpdateTime() {
        return playUpdateTime;
    }

    public void setPlayUpdateTime(String playUpdateTime) {
        this.playUpdateTime = playUpdateTime;
    }

    public double getPoundage() {
        return poundage;
    }

    public void setPoundage(double poundage) {
        this.poundage = poundage;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public int getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(int refundCount) {
        this.refundCount = refundCount;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundHandFee() {
        return refundHandFee;
    }

    public void setRefundHandFee(String refundHandFee) {
        this.refundHandFee = refundHandFee;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundResponse() {
        return refundResponse;
    }

    public void setRefundResponse(String refundResponse) {
        this.refundResponse = refundResponse;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public int getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(int reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatLockFlag() {
        return seatLockFlag;
    }

    public void setSeatLockFlag(String seatLockFlag) {
        this.seatLockFlag = seatLockFlag;
    }

    public int getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(int seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getSeatTicket() {
        return seatTicket;
    }

    public void setSeatTicket(String seatTicket) {
        this.seatTicket = seatTicket;
    }

    public String getSeatTicketList() {
        return seatTicketList;
    }

    public void setSeatTicketList(String seatTicketList) {
        this.seatTicketList = seatTicketList;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public double getSubsidyCount() {
        return subsidyCount;
    }

    public void setSubsidyCount(double subsidyCount) {
        this.subsidyCount = subsidyCount;
    }

    public String getSubsidyType() {
        return subsidyType;
    }

    public void setSubsidyType(String subsidyType) {
        this.subsidyType = subsidyType;
    }

    public String getSuccessReportTime() {
        return successReportTime;
    }

    public void setSuccessReportTime(String successReportTime) {
        this.successReportTime = successReportTime;
    }

    public String getTicketFlag1() {
        return ticketFlag1;
    }

    public void setTicketFlag1(String ticketFlag1) {
        this.ticketFlag1 = ticketFlag1;
    }

    public String getTicketFlag2() {
        return ticketFlag2;
    }

    public void setTicketFlag2(String ticketFlag2) {
        this.ticketFlag2 = ticketFlag2;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getTicketOriginPrice() {
        return ticketOriginPrice;
    }

    public void setTicketOriginPrice(String ticketOriginPrice) {
        this.ticketOriginPrice = ticketOriginPrice;
    }

    public String getTicketRealPrice() {
        return ticketRealPrice;
    }

    public void setTicketRealPrice(String ticketRealPrice) {
        this.ticketRealPrice = ticketRealPrice;
    }

    public String getTotalDisprice() {
        return totalDisprice;
    }

    public void setTotalDisprice(String totalDisprice) {
        this.totalDisprice = totalDisprice;
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

    public static class DxAppUserBean implements Serializable{
        /**
         * age : null
         * birthday : null
         * businessid : null
         * city : null
         * collectNum : null
         * commentNum : null
         * country : null
         * createTime : 2019-01-20 14:35:23
         * deleteTime : null
         * dxInsiderInfo : []
         * gender : 1
         * header :
         * id : 3
         * insider : null
         * lastsign : null
         * loginDate : 2019-01-23 10:15:12
         * mobile : 17376551017
         * modifyTime : 2019-01-23 10:15:12
         * nickName : 17376551017
         * password : e10adc3949ba59abbe56e057f20f883e
         * points : null
         * province : null
         * qqUserId : null
         * sign : null
         * signdays : null
         * uuid : 9afb9ce2-a740-4d91-b848-0264e29e5052
         * valid : true
         * version : 91
         * wantseeNum : null
         * watchedNum : null
         * wbUserId : null
         * wxOpenId : null
         * wxUserId : null
         */

        private String age;
        private String birthday;
        private String businessid;
        private String city;
        private String collectNum;
        private String commentNum;
        private String country;
        private String createTime;
        private String deleteTime;
        private int gender;
        private String header;
        private int id;
        private String insider;
        private String lastsign;
        private String loginDate;
        private String mobile;
        private String modifyTime;
        private String nickName;
        private String password;
        private String points;
        private String province;
        private String qqUserId;
        private String sign;
        private String signdays;
        private String uuid;
        private boolean valid;
        private int version;
        private String wantseeNum;
        private String watchedNum;
        private String wbUserId;
        private String wxOpenId;
        private String wxUserId;
        private List<?> dxInsiderInfo;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBusinessid() {
            return businessid;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(String collectNum) {
            this.collectNum = collectNum;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInsider() {
            return insider;
        }

        public void setInsider(String insider) {
            this.insider = insider;
        }

        public String getLastsign() {
            return lastsign;
        }

        public void setLastsign(String lastsign) {
            this.lastsign = lastsign;
        }

        public String getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(String loginDate) {
            this.loginDate = loginDate;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getQqUserId() {
            return qqUserId;
        }

        public void setQqUserId(String qqUserId) {
            this.qqUserId = qqUserId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSigndays() {
            return signdays;
        }

        public void setSigndays(String signdays) {
            this.signdays = signdays;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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

        public String getWantseeNum() {
            return wantseeNum;
        }

        public void setWantseeNum(String wantseeNum) {
            this.wantseeNum = wantseeNum;
        }

        public String getWatchedNum() {
            return watchedNum;
        }

        public void setWatchedNum(String watchedNum) {
            this.watchedNum = watchedNum;
        }

        public String getWbUserId() {
            return wbUserId;
        }

        public void setWbUserId(String wbUserId) {
            this.wbUserId = wbUserId;
        }

        public String getWxOpenId() {
            return wxOpenId;
        }

        public void setWxOpenId(String wxOpenId) {
            this.wxOpenId = wxOpenId;
        }

        public String getWxUserId() {
            return wxUserId;
        }

        public void setWxUserId(String wxUserId) {
            this.wxUserId = wxUserId;
        }

        public List<?> getDxInsiderInfo() {
            return dxInsiderInfo;
        }

        public void setDxInsiderInfo(List<?> dxInsiderInfo) {
            this.dxInsiderInfo = dxInsiderInfo;
        }
    }

    public static class DxMovieBean implements Serializable{
        /**
         * alias : 我的青春期
         * cineMovieId : 341
         * cineMovieNum : 001105902015
         * comment : null
         * commentRecord : null
         * content : null
         * country :
         * createTime : 2019-01-10 15:32:59
         * deleteTime : null
         * dxActors : []
         * dxDirectors : []
         * dxVideos : []
         * foreignName : null
         * id : 2
         * infoUrl :
         * introduction :
         * level :
         * mediaLib :
         * modifyTime : 2019-01-23 10:00:00
         * movieDimensional : 2D
         * movieFormat : 数字
         * movieLanguage : 国语
         * movieName : 我的青春期
         * movieSize : 普通
         * movieSubtitle : 中文
         * movieType : null
         * photos :
         * picture :
         * playTime : 120
         * point : 0.0
         * preVideo :
         * product :
         * publisher : 时代华纳
         * score : null
         * screenPlanList : null
         * sell : null
         * shareurl : null
         * startPlay : 2015-12-31 00:00:00
         * stopshow : null
         * summary : null
         * title : null
         * type : 喜剧片
         * uniqueName : null
         * valid : true
         * version : 271
         */

        private String alias;
        private int cineMovieId;
        private String cineMovieNum;
        private String comment;
        private String commentRecord;
        private String content;
        private String country;
        private String createTime;
        private String deleteTime;
        private String foreignName;
        private int id;
        private String infoUrl;
        private String introduction;
        private String level;
        private String mediaLib;
        private String modifyTime;
        private String movieDimensional;
        private String movieFormat;
        private String movieLanguage;
        private String movieName;
        private String movieSize;
        private String movieSubtitle;
        private String movieType;
        private String photos;
        private String picture;
        private int playTime;
        private double point;
        private String preVideo;
        private String product;
        private String publisher;
        private String score;
        private String screenPlanList;
        private String sell;
        private String shareurl;
        private String startPlay;
        private String stopshow;
        private String summary;
        private String title;
        private String type;
        private String uniqueName;
        private boolean valid;
        private int version;
        private List<?> dxActors;
        private List<?> dxDirectors;
        private List<?> dxVideos;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getCineMovieId() {
            return cineMovieId;
        }

        public void setCineMovieId(int cineMovieId) {
            this.cineMovieId = cineMovieId;
        }

        public String getCineMovieNum() {
            return cineMovieNum;
        }

        public void setCineMovieNum(String cineMovieNum) {
            this.cineMovieNum = cineMovieNum;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommentRecord() {
            return commentRecord;
        }

        public void setCommentRecord(String commentRecord) {
            this.commentRecord = commentRecord;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
        }

        public String getForeignName() {
            return foreignName;
        }

        public void setForeignName(String foreignName) {
            this.foreignName = foreignName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInfoUrl() {
            return infoUrl;
        }

        public void setInfoUrl(String infoUrl) {
            this.infoUrl = infoUrl;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMediaLib() {
            return mediaLib;
        }

        public void setMediaLib(String mediaLib) {
            this.mediaLib = mediaLib;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getMovieDimensional() {
            return movieDimensional;
        }

        public void setMovieDimensional(String movieDimensional) {
            this.movieDimensional = movieDimensional;
        }

        public String getMovieFormat() {
            return movieFormat;
        }

        public void setMovieFormat(String movieFormat) {
            this.movieFormat = movieFormat;
        }

        public String getMovieLanguage() {
            return movieLanguage;
        }

        public void setMovieLanguage(String movieLanguage) {
            this.movieLanguage = movieLanguage;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getMovieSize() {
            return movieSize;
        }

        public void setMovieSize(String movieSize) {
            this.movieSize = movieSize;
        }

        public String getMovieSubtitle() {
            return movieSubtitle;
        }

        public void setMovieSubtitle(String movieSubtitle) {
            this.movieSubtitle = movieSubtitle;
        }

        public String getMovieType() {
            return movieType;
        }

        public void setMovieType(String movieType) {
            this.movieType = movieType;
        }

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getPlayTime() {
            return playTime;
        }

        public void setPlayTime(int playTime) {
            this.playTime = playTime;
        }

        public double getPoint() {
            return point;
        }

        public void setPoint(double point) {
            this.point = point;
        }

        public String getPreVideo() {
            return preVideo;
        }

        public void setPreVideo(String preVideo) {
            this.preVideo = preVideo;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getScreenPlanList() {
            return screenPlanList;
        }

        public void setScreenPlanList(String screenPlanList) {
            this.screenPlanList = screenPlanList;
        }

        public String getSell() {
            return sell;
        }

        public void setSell(String sell) {
            this.sell = sell;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public String getStartPlay() {
            return startPlay;
        }

        public void setStartPlay(String startPlay) {
            this.startPlay = startPlay;
        }

        public String getStopshow() {
            return stopshow;
        }

        public void setStopshow(String stopshow) {
            this.stopshow = stopshow;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUniqueName() {
            return uniqueName;
        }

        public void setUniqueName(String uniqueName) {
            this.uniqueName = uniqueName;
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

        public List<?> getDxActors() {
            return dxActors;
        }

        public void setDxActors(List<?> dxActors) {
            this.dxActors = dxActors;
        }

        public List<?> getDxDirectors() {
            return dxDirectors;
        }

        public void setDxDirectors(List<?> dxDirectors) {
            this.dxDirectors = dxDirectors;
        }

        public List<?> getDxVideos() {
            return dxVideos;
        }

        public void setDxVideos(List<?> dxVideos) {
            this.dxVideos = dxVideos;
        }
    }

    public static class MerOrderBean implements Serializable{
        /**
         * address : null
         * beforeActivityPrice : null
         * beforeTicketPrice : 20.0
         * canRefund : null
         * coupon : null
         * createTime : 2019-01-23 10:08:55
         * deleteTime : null
         * deliveryType : 0
         * details : [{"createTime":"2019-01-23 10:08:55","deleteTime":null,"id":74,"itemCombo":{"bussinessid":null,"cinemaId":31,"createTime":"2019-01-23 09:36:04","deleteTime":null,"id":2,"imageUrl":"https://hyg.happydoit.com/upload/201901/23SHeGiJ.jpg","merchandiseList":[{"businessId":1,"cardDiscountType":2,"cinemaId":31,"counterPrice":9,"createTime":"2019-01-16 16:44:06","deleteTime":null,"dxId":3,"goodsNum":null,"id":3,"imageUrl":null,"inventory":111,"itemClassId":null,"listOrder":0,"modifyTime":"2019-01-16 16:44:07","name":"常规套餐3","number":null,"onlinePrice":33,"originalPrice":null,"overInventorySellFlag":0,"packageDetail":[],"type":"package","unit":"","updateTime":"2018-12-29 17:31:50","valid":true,"version":1}],"modifyTime":null,"name":"双人套餐","originalPrice":30,"personNum":2,"salePrice":20,"sort":1,"status":1,"valid":true,"version":0},"merchandise":null,"modifyTime":null,"number":1,"price":20,"totalPrice":20,"type":2,"valid":true,"version":0}]
         * disPrice : 20.0
         * featureAppNo : null
         * id : 51
         * isReady : 1
         * merTicket : null
         * merTicketList : null
         * merchandiseInfo : [{"id":2,"number":1}]
         * modifyTime : null
         * phone : null
         * qrCode : null
         * ticketFlag1 : null
         * ticketFlag2 : null
         * totalNumber : 1
         * totalPrice : 20.0
         * valid : true
         * version : 0
         */

        private String address;
        private String beforeActivityPrice;
        private String beforeTicketPrice;
        private String canRefund;
        private String coupon;
        private String createTime;
        private String deleteTime;
        private int deliveryType;
        private String disPrice;
        private String featureAppNo;
        private int id;
        private int isReady;
        private String merTicket;
        private String merTicketList;
        private String merchandiseInfo;
        private String modifyTime;
        private String phone;
        private String qrCode;
        private String ticketFlag1;
        private String ticketFlag2;
        private int totalNumber;
        private String totalPrice;
        private boolean valid;
        private int version;
        private List<DetailsBean> details;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBeforeActivityPrice() {
            return beforeActivityPrice;
        }

        public void setBeforeActivityPrice(String beforeActivityPrice) {
            this.beforeActivityPrice = beforeActivityPrice;
        }

        public String getBeforeTicketPrice() {
            return beforeTicketPrice;
        }

        public void setBeforeTicketPrice(String beforeTicketPrice) {
            this.beforeTicketPrice = beforeTicketPrice;
        }

        public String getCanRefund() {
            return canRefund;
        }

        public void setCanRefund(String canRefund) {
            this.canRefund = canRefund;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
        }

        public int getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(int deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getDisPrice() {
            return disPrice;
        }

        public void setDisPrice(String disPrice) {
            this.disPrice = disPrice;
        }

        public String getFeatureAppNo() {
            return featureAppNo;
        }

        public void setFeatureAppNo(String featureAppNo) {
            this.featureAppNo = featureAppNo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsReady() {
            return isReady;
        }

        public void setIsReady(int isReady) {
            this.isReady = isReady;
        }

        public String getMerTicket() {
            return merTicket;
        }

        public void setMerTicket(String merTicket) {
            this.merTicket = merTicket;
        }

        public String getMerTicketList() {
            return merTicketList;
        }

        public void setMerTicketList(String merTicketList) {
            this.merTicketList = merTicketList;
        }

        public String getMerchandiseInfo() {
            return merchandiseInfo;
        }

        public void setMerchandiseInfo(String merchandiseInfo) {
            this.merchandiseInfo = merchandiseInfo;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getTicketFlag1() {
            return ticketFlag1;
        }

        public void setTicketFlag1(String ticketFlag1) {
            this.ticketFlag1 = ticketFlag1;
        }

        public String getTicketFlag2() {
            return ticketFlag2;
        }

        public void setTicketFlag2(String ticketFlag2) {
            this.ticketFlag2 = ticketFlag2;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
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

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean implements Serializable{
            /**
             * createTime : 2019-01-23 10:08:55
             * deleteTime : null
             * id : 74
             * itemCombo : {"bussinessid":null,"cinemaId":31,"createTime":"2019-01-23 09:36:04","deleteTime":null,"id":2,"imageUrl":"https://hyg.happydoit.com/upload/201901/23SHeGiJ.jpg","merchandiseList":[{"businessId":1,"cardDiscountType":2,"cinemaId":31,"counterPrice":9,"createTime":"2019-01-16 16:44:06","deleteTime":null,"dxId":3,"goodsNum":null,"id":3,"imageUrl":null,"inventory":111,"itemClassId":null,"listOrder":0,"modifyTime":"2019-01-16 16:44:07","name":"常规套餐3","number":null,"onlinePrice":33,"originalPrice":null,"overInventorySellFlag":0,"packageDetail":[],"type":"package","unit":"","updateTime":"2018-12-29 17:31:50","valid":true,"version":1}],"modifyTime":null,"name":"双人套餐","originalPrice":30,"personNum":2,"salePrice":20,"sort":1,"status":1,"valid":true,"version":0}
             * merchandise : null
             * modifyTime : null
             * number : 1
             * price : 20.0
             * totalPrice : 20.0
             * type : 2
             * valid : true
             * version : 0
             */

            private String createTime;
            private String deleteTime;
            private int id;
            private ItemComboBean itemCombo;
            private MerchandiseBean merchandise;
            private String modifyTime;
            private String number;
            private String price;
            private String totalPrice;
            private int type;
            private boolean valid;
            private int version;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDeleteTime() {
                return deleteTime;
            }

            public void setDeleteTime(String deleteTime) {
                this.deleteTime = deleteTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public ItemComboBean getItemCombo() {
                return itemCombo;
            }

            public void setItemCombo(ItemComboBean itemCombo) {
                this.itemCombo = itemCombo;
            }

            public MerchandiseBean getMerchandise() {
                return merchandise;
            }

            public void setMerchandise(MerchandiseBean merchandise) {
                this.merchandise = merchandise;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public static class MerchandiseBean implements Serializable{
                /**
                 * businessId : 1
                 * cardDiscountType : 2
                 * cinemaId : 31
                 * counterPrice : 11
                 * createTime : 2019-01-16 16:44:06
                 * deleteTime : null
                 * dxId : 2
                 * goodsNum : null
                 * id : 2
                 * imageUrl : https://hyg.happydoit.com/upload/201901/18KjGlIt.jpg
                 * inventory : 111
                 * itemClassId : 2
                 * listOrder : 0
                 * modifyTime : 2019-01-18 13:46:10
                 * name : 常规套餐2
                 * number : null
                 * onlinePrice : 22
                 * originalPrice : 20
                 * overInventorySellFlag : 0
                 * packageDetail : {}
                 * type : package
                 * unit :
                 * updateTime : 2018-12-29 17:32:40
                 * valid : true
                 * version : 2
                 */

                private int businessId;
                private int cardDiscountType;
                private int cinemaId;
                private int counterPrice;
                private String createTime;
                private String deleteTime;
                private int dxId;
                private String goodsNum;
                private int id;
                private String imageUrl;
                private int inventory;
                private String itemClassId;
                private int listOrder;
                private String modifyTime;
                private String name;
                private String number;
                private String onlinePrice;
                private String originalPrice;
                private int overInventorySellFlag;
                private List<PackageDetailBean> packageDetail;
                private String type;
                private String unit;
                private String updateTime;
                private boolean valid;
                private int version;

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

                public int getCounterPrice() {
                    return counterPrice;
                }

                public void setCounterPrice(int counterPrice) {
                    this.counterPrice = counterPrice;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getDeleteTime() {
                    return deleteTime;
                }

                public void setDeleteTime(String deleteTime) {
                    this.deleteTime = deleteTime;
                }

                public int getDxId() {
                    return dxId;
                }

                public void setDxId(int dxId) {
                    this.dxId = dxId;
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

                public int getInventory() {
                    return inventory;
                }

                public void setInventory(int inventory) {
                    this.inventory = inventory;
                }

                public String getItemClassId() {
                    return itemClassId;
                }

                public void setItemClassId(String itemClassId) {
                    this.itemClassId = itemClassId;
                }

                public int getListOrder() {
                    return listOrder;
                }

                public void setListOrder(int listOrder) {
                    this.listOrder = listOrder;
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

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getOnlinePrice() {
                    return onlinePrice;
                }

                public void setOnlinePrice(String onlinePrice) {
                    this.onlinePrice = onlinePrice;
                }

                public String getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(String originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public int getOverInventorySellFlag() {
                    return overInventorySellFlag;
                }

                public void setOverInventorySellFlag(int overInventorySellFlag) {
                    this.overInventorySellFlag = overInventorySellFlag;
                }

                public List<PackageDetailBean> getPackageDetail() {
                    return packageDetail;
                }

                public void setPackageDetail(List<PackageDetailBean> packageDetail) {
                    this.packageDetail = packageDetail;
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

                public static class PackageDetailBean {
                }
            }


            public static class ItemComboBean implements Serializable{
                /**
                 * bussinessid : null
                 * cinemaId : 31
                 * createTime : 2019-01-23 09:36:04
                 * deleteTime : null
                 * id : 2
                 * imageUrl : https://hyg.happydoit.com/upload/201901/23SHeGiJ.jpg
                 * merchandiseList : [{"businessId":1,"cardDiscountType":2,"cinemaId":31,"counterPrice":9,"createTime":"2019-01-16 16:44:06","deleteTime":null,"dxId":3,"goodsNum":null,"id":3,"imageUrl":null,"inventory":111,"itemClassId":null,"listOrder":0,"modifyTime":"2019-01-16 16:44:07","name":"常规套餐3","number":null,"onlinePrice":33,"originalPrice":null,"overInventorySellFlag":0,"packageDetail":[],"type":"package","unit":"","updateTime":"2018-12-29 17:31:50","valid":true,"version":1}]
                 * modifyTime : null
                 * name : 双人套餐
                 * originalPrice : 30.0
                 * personNum : 2
                 * salePrice : 20.0
                 * sort : 1.0
                 * status : 1
                 * valid : true
                 * version : 0
                 */

                private String bussinessid;
                private int cinemaId;
                private String createTime;
                private String deleteTime;
                private int id;
                private String imageUrl;
                private String modifyTime;
                private String name;
                private String originalPrice;
                private int personNum;
                private String salePrice;
                private String sort;
                private int status;
                private boolean valid;
                private int version;
                private List<MerchandiseListBean> merchandiseList;

                public String getBussinessid() {
                    return bussinessid;
                }

                public void setBussinessid(String bussinessid) {
                    this.bussinessid = bussinessid;
                }

                public int getCinemaId() {
                    return cinemaId;
                }

                public void setCinemaId(int cinemaId) {
                    this.cinemaId = cinemaId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getDeleteTime() {
                    return deleteTime;
                }

                public void setDeleteTime(String deleteTime) {
                    this.deleteTime = deleteTime;
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

                public String getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(String originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public int getPersonNum() {
                    return personNum;
                }

                public void setPersonNum(int personNum) {
                    this.personNum = personNum;
                }

                public String getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(String salePrice) {
                    this.salePrice = salePrice;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
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

                public List<MerchandiseListBean> getMerchandiseList() {
                    return merchandiseList;
                }

                public void setMerchandiseList(List<MerchandiseListBean> merchandiseList) {
                    this.merchandiseList = merchandiseList;
                }

                public static class MerchandiseListBean implements Serializable{
                    /**
                     * businessId : 1
                     * cardDiscountType : 2
                     * cinemaId : 31
                     * counterPrice : 9.0
                     * createTime : 2019-01-16 16:44:06
                     * deleteTime : null
                     * dxId : 3
                     * goodsNum : null
                     * id : 3
                     * imageUrl : null
                     * inventory : 111
                     * itemClassId : null
                     * listOrder : 0
                     * modifyTime : 2019-01-16 16:44:07
                     * name : 常规套餐3
                     * number : null
                     * onlinePrice : 33.0
                     * originalPrice : null
                     * overInventorySellFlag : 0
                     * packageDetail : []
                     * type : package
                     * unit :
                     * updateTime : 2018-12-29 17:31:50
                     * valid : true
                     * version : 1
                     */

                    private int businessId;
                    private int cardDiscountType;
                    private int cinemaId;
                    private String counterPrice;
                    private String createTime;
                    private String deleteTime;
                    private int dxId;
                    private String goodsNum;
                    private int id;
                    private String imageUrl;
                    private int inventory;
                    private String itemClassId;
                    private int listOrder;
                    private String modifyTime;
                    private String name;
                    private String number;
                    private String onlinePrice;
                    private String originalPrice;
                    private int overInventorySellFlag;
                    private String type;
                    private String unit;
                    private String updateTime;
                    private boolean valid;
                    private int version;
                    private List<?> packageDetail;

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

                    public String getCounterPrice() {
                        return counterPrice;
                    }

                    public void setCounterPrice(String counterPrice) {
                        this.counterPrice = counterPrice;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getDeleteTime() {
                        return deleteTime;
                    }

                    public void setDeleteTime(String deleteTime) {
                        this.deleteTime = deleteTime;
                    }

                    public int getDxId() {
                        return dxId;
                    }

                    public void setDxId(int dxId) {
                        this.dxId = dxId;
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

                    public int getInventory() {
                        return inventory;
                    }

                    public void setInventory(int inventory) {
                        this.inventory = inventory;
                    }

                    public String getItemClassId() {
                        return itemClassId;
                    }

                    public void setItemClassId(String itemClassId) {
                        this.itemClassId = itemClassId;
                    }

                    public int getListOrder() {
                        return listOrder;
                    }

                    public void setListOrder(int listOrder) {
                        this.listOrder = listOrder;
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

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public String getOnlinePrice() {
                        return onlinePrice;
                    }

                    public void setOnlinePrice(String onlinePrice) {
                        this.onlinePrice = onlinePrice;
                    }

                    public String getOriginalPrice() {
                        return originalPrice;
                    }

                    public void setOriginalPrice(String originalPrice) {
                        this.originalPrice = originalPrice;
                    }

                    public int getOverInventorySellFlag() {
                        return overInventorySellFlag;
                    }

                    public void setOverInventorySellFlag(int overInventorySellFlag) {
                        this.overInventorySellFlag = overInventorySellFlag;
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
        }
    }
}
