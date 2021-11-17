package com.myp.hhcinema.config;

/**
 * Created by wuliang on 2017/3/6.
 * <p>
 * 程序的公共环境配置，或公共字段存放
 */

public class LocalConfiguration {

    //app的SharedPreferences缓存名字
    public static final String SP_NAME = "CINEMA_SP";
    public static  String SESSION ="";
    // 测试微信应用的 appId
//    public static final String WEIXIN_APP_ID = "wxb18d1464762457a0";
//    public static final String APP_SECRET = "da24ec8811c4a4a2d71ae352a37af0f0";

    //正式微信appId
    public static final String WEIXIN_APP_ID = "wxf5fe7fc4468b9bcb";
    public static final String APP_SECRET = "1fe017384e6daf01b5b0982f342728b8";

    //QQ的appId
    public static final String QQ_APP_ID = "1106256112";

    public static String orderNum;   //支付成功的订单号，微信支付时要用
    public static String cardcode;
    public static String isShouye;
    /**
     * 版本下载到手机的位置
     */
    public static final String DOWNLOAD_PATH = "/data/data/com.myp.hhcinema/download";
    public static final String appFileName = "cinema.apk";  //版本
    public static int isVoucher = -1;
    public static int ordertype = -1;//订单类型  0 电影票订单  1 食品订单

    public static int isCanBuy = 1;//卖品是否可以点击
    public static String message = "";//提示信息

    /**
     * 隐私协议
     */
    public static final String YINSI_H5= "http://hhyy.happydoit.com/yinsi/yinsi.htm";

}
