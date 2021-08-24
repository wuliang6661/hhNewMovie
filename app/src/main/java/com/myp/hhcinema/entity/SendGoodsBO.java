package com.myp.hhcinema.entity;

import java.util.List;

/**
 * Created by Witness on 2019/6/26
 * Describe:
 */
public class SendGoodsBO {

    /**
     * code : noTicket
     * data : {"hallName":"","seats":"","hallList":["一号厅","10号霸王厅","1厅","看看到底几个字大厅","奔驰IMAX厅","VIP(I)厅","压测","4D影厅","4号厅","bieming","测试影厅1号","测试影厅2号","测试影厅3号","242","243","121","爱在西元前厅","百得利之星-奔驰厅","7号厅","8号厅","9号厅"],"movieName":"","playTime":""}
     * message :
     * status : 1
     */

    private String code;
    private DataBean data;
    private String message;
    private int status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * hallName :
         * seats :
         * hallList : ["一号厅","10号霸王厅","1厅","看看到底几个字大厅","奔驰IMAX厅","VIP(I)厅","压测","4D影厅","4号厅","bieming","测试影厅1号","测试影厅2号","测试影厅3号","242","243","121","爱在西元前厅","百得利之星-奔驰厅","7号厅","8号厅","9号厅"]
         * movieName :
         * playTime :
         */

        private String hallName;
        private String seats;
        private String movieName;
        private String playTime;
        private List<String> hallList;

        public String getHallName() {
            return hallName;
        }

        public void setHallName(String hallName) {
            this.hallName = hallName;
        }

        public String getSeats() {
            return seats;
        }

        public void setSeats(String seats) {
            this.seats = seats;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getPlayTime() {
            return playTime;
        }

        public void setPlayTime(String playTime) {
            this.playTime = playTime;
        }

        public List<String> getHallList() {
            return hallList;
        }

        public void setHallList(List<String> hallList) {
            this.hallList = hallList;
        }
    }
}
