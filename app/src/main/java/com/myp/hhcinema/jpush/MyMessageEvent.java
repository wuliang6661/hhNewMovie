package com.myp.hhcinema.jpush;

/**
 * Created by witness on 2018/4/9 0009.
 */

public class MyMessageEvent {

    private String messageType;
    private Object passValue;

    public MyMessageEvent(String messageType, Object passValue) {
        this.messageType = messageType;
        this.passValue = passValue;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMseeageType(String messageType) {
        this.messageType = messageType;
    }

    public Object getPassValue() {
        return passValue;
    }

    public void setPassValue(Object passValue) {
        this.passValue = passValue;
    }
}