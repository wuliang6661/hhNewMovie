package com.myp.hhcinema.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/11.
 */

public class threelandingBo implements Serializable {
    private Object code;
    private UserBO data;
    private String message;
    private int status;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public UserBO getData() {
        return data;
    }

    public void setData(UserBO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}





