package com.myp.hhcinema.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuliang on 2017/6/15.
 * <p>
 * 影片场次
 */

public class MoviesByCidBO implements Serializable {


    /**
     * afterTomorrowScreenList : [{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:26:03","deleteTime":null,"dxId":249471,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":81,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-18 11:35:00","valid":true,"version":0},{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:26:04","deleteTime":null,"dxId":249476,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":86,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-18 14:10:00","valid":true,"version":0},{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:26:04","deleteTime":null,"dxId":249480,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":90,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-18 16:45:00","valid":true,"version":0},{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:26:05","deleteTime":null,"dxId":249485,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":95,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-18 19:20:00","valid":true,"version":0}]
     * cineMovieId : 7
     * cineMovieNum : 001104242010
     * country : null
     * createTime : 2017-06-14 16:12:16
     * deleteTime : null
     * id : 13
     * infoUrl : null
     * Stringroduction : null
     * mediaLib : null
     * modifyTime : null
     * movieDimensional : 2D
     * movieFormat : 数字
     * movieLanguage : 国语
     * movieName : 让子弹飞
     * movieSize : 普通
     * movieSubtitle : 中文
     * picture : null
     * playTime : null
     * preVideo : null
     * startPlay : 2017-06-16
     * todayScreenList : [{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:25:58","deleteTime":null,"dxId":249431,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":41,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-16 11:35:00","valid":true,"version":0},{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:25:59","deleteTime":null,"dxId":249436,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":46,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-16 14:10:00","valid":true,"version":0},{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:25:59","deleteTime":null,"dxId":249440,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":50,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-16 16:45:00","valid":true,"version":0},{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:26:00","deleteTime":null,"dxId":249445,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":55,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-16 19:20:00","valid":true,"version":0}]
     * tomorrowScreenList : [{"allowBook":1,"cineUpdateTime":null,"createTime":"2017-06-15 20:26:01","deleteTime":null,"dxId":249451,"endTime":null,"hallId":437,"hallName":"VIP(I)厅","id":61,"lowestPrice":35,"marketPrice":100,"modifyTime":null,"price":100,"priceType":1,"seatAvailableNum":null,"seatTotalNum":null,"startTime":"2017-06-17 11:35:00","valid":true,"version":0}]
     */


    private String cineMovieId;
    private String cineMovieNum;
    private String country;
    private String createTime;
    private String deleteTime;
    private String id;
    private String infoUrl;
    private String Stringroduction;
    private String mediaLib;
    private String modifyTime;
    private String movieDimensional;
    private String movieFormat;
    private String movieLanguage;
    private String movieName;
    private String movieSize;
    private String movieSubtitle;
    private String playTime;
    private String preVideo;
    private String startPlay;
    private String point;
    private String summary;
    private List<MoviesSessionBO> afterTomorrowScreenList;
    private List<MoviesSessionBO> todayScreenList;
    private List<MoviesSessionBO> tomorrowScreenList;
    private String introduction;
    private String foreignName;
    private String movieType;
    private String sell;
    private List<DirectorBO> dxActors;
    private List<DirectorBO> dxDirectors;
    private String picture;   //海报
    private String photos = "";//剧照，多个剧照要进行显示,链接之间用逗号隔开
    private List<VideoBO> dxVideos;
    private String uniqueName;
    private String lowstPrice;
    private double vipLowstPrice;

    public String getLowstPrice() {
        return lowstPrice;
    }

    public void setLowstPrice(String lowstPrice) {
        this.lowstPrice = lowstPrice;
    }

    public double getVipLowstPrice() {
        return vipLowstPrice;
    }

    public void setVipLowstPrice(double vipLowstPrice) {
        this.vipLowstPrice = vipLowstPrice;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<VideoBO> getDxVideos() {
        return dxVideos;
    }

    public void setDxVideos(List<VideoBO> dxVideos) {
        this.dxVideos = dxVideos;
    }

    public List<DirectorBO> getDxActors() {
        return dxActors;
    }

    public void setDxActors(List<DirectorBO> dxActors) {
        this.dxActors = dxActors;
    }

    public List<DirectorBO> getDxDirectors() {
        return dxDirectors;
    }

    public void setDxDirectors(List<DirectorBO> dxDirectors) {
        this.dxDirectors = dxDirectors;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }


    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCineMovieId() {
        return cineMovieId;
    }

    public void setCineMovieId(String cineMovieId) {
        this.cineMovieId = cineMovieId;
    }

    public String getCineMovieNum() {
        return cineMovieNum;
    }

    public void setCineMovieNum(String cineMovieNum) {
        this.cineMovieNum = cineMovieNum;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getStringroduction() {
        return Stringroduction;
    }

    public void setStringroduction(String Stringroduction) {
        this.Stringroduction = Stringroduction;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getPreVideo() {
        return preVideo;
    }

    public void setPreVideo(String preVideo) {
        this.preVideo = preVideo;
    }

    public String getStartPlay() {
        return startPlay;
    }

    public void setStartPlay(String startPlay) {
        this.startPlay = startPlay;
    }

    public List<MoviesSessionBO> getAfterTomorrowScreenList() {
        return afterTomorrowScreenList;
    }

    public void setAfterTomorrowScreenList(List<MoviesSessionBO> afterTomorrowScreenList) {
        this.afterTomorrowScreenList = afterTomorrowScreenList;
    }

    public List<MoviesSessionBO> getTodayScreenList() {
        return todayScreenList;
    }

    public void setTodayScreenList(List<MoviesSessionBO> todayScreenList) {
        this.todayScreenList = todayScreenList;
    }

    public List<MoviesSessionBO> getTomorrowScreenList() {
        return tomorrowScreenList;
    }

    public void setTomorrowScreenList(List<MoviesSessionBO> tomorrowScreenList) {
        this.tomorrowScreenList = tomorrowScreenList;
    }


}
