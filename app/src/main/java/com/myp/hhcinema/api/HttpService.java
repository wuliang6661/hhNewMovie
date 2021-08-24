package com.myp.hhcinema.api;

import com.myp.hhcinema.entity.BaseDingResult;
import com.myp.hhcinema.entity.aCinemaSeatTableBO;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放鼎新服务器的所有接口数据
 */

interface HttpService {

    String URL = "http://api.open.yinghezhong.com/";     //正式环境
    String _AUCHTODE = "#h8S63Ntm4";   //正式环境
    String PID = "11206";               //正式环境
//    String URL = "http://api.platform.yinghezhong.com/";   //测试服
//    String _AUCHTODE = "c3J7%^d5fA";   //测试
//    String PID = "90148";           //测试


    /**
     * 获取某场次座位状态
     */
    @FormUrlEncoded
    @POST("/play/seat-status")
    Observable<BaseDingResult<List<aCinemaSeatTableBO>>> getCinemaSeatStatus(
            @Field("format") String format,
            @Field("cid") String cid,
            @Field("pid") String pid,
            @Field("play_id") String playId,
            @Field("play_update_time") String updateTime,
            @Field("_sig") String _sig);


}
