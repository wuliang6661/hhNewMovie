package com.myp.hhcinema.entity;

/**
 * Created by Witness on 2019/7/9
 * Describe:
 */
public class MessageBO {
    /**
     * viewSwitch : false
     * appUserId : 33
     * valid : true
     * messageLink : https://ruiduo.happydoit.com/message/article/articles/read?id=9
     * businessId : 39
     * consultPicture :
     * cinemaId : 31
     * title : 姻缘新增管理
     * description : 文章描述
     * messageDate : 2019-06-25
     * id : 185
     * version : 0
     * createTime : 2019-07-09 17:26:26
     * advisoryType : 0
     * message : 文章描述
     * systemType : 2
     */

    private boolean viewSwitch;
    private int appUserId;
    private boolean valid;
    private String messageLink;
    private int businessId;
    private String consultPicture;
    private int cinemaId;
    private String title;
    private String description;
    private String messageDate;
    private int id;
    private int version;
    private String createTime;
    private int advisoryType;  /* 消息类型  0资讯 1退款相关 2充值 3优惠券相关 4影票相关 5卖品相关 */
    private String message;
    private int systemType;

    public boolean isViewSwitch() {
        return viewSwitch;
    }

    public void setViewSwitch(boolean viewSwitch) {
        this.viewSwitch = viewSwitch;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessageLink() {
        return messageLink;
    }

    public void setMessageLink(String messageLink) {
        this.messageLink = messageLink;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getConsultPicture() {
        return consultPicture;
    }

    public void setConsultPicture(String consultPicture) {
        this.consultPicture = consultPicture;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAdvisoryType() {
        return advisoryType;
    }

    public void setAdvisoryType(int advisoryType) {
        this.advisoryType = advisoryType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }
}
