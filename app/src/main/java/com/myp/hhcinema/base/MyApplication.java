package com.myp.hhcinema.base;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.entity.CityBO;
import com.myp.hhcinema.entity.ConfirmPayBO;
import com.myp.hhcinema.entity.HotSellBO;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.service.AdvanceLoadX5Service;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.OKHttpUpdateHttpService;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.Utils;
import com.myp.hhcinema.util.baidumap.LocationService;
import com.myp.hhcinema.wxapi.WXUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.smtt.sdk.QbSdk;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.ArrayList;
import java.util.List;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * 作者 by wuliang 时间 16/10/26.
 * <p>
 * 程序的application
 */

public class MyApplication extends Application {

    public LocationService locationService;
    public static ConditionEnum isLogin = ConditionEnum.NOLOGIN;   //默认未登陆
    public static SPUtils spUtils;   //缓存类型
    public static UserBO user;    //程序使用用户
    public static CinemaBo cinemaBo;   //当前选择影院
    public static String SESSIONID;    //保存的持久化sessionId，用于H5的session同步
    public static List<MoviesByCidBO> movies;   //持久化的正在热映列表
    public static CityBO cityBO;    //当前选中的城市
    public static LockSeatsBO orderBO;
    public static List<HotSellBO> hotSellBO;//持久化热卖商品
    public static int prodectWay = 0;//送餐方式，0 自取  1  送至影厅门口
    public static String prodectWayString = "";//送到几号厅
    public static String selectFeatureAppNo = "";//选中某个场次的FeatureAppNo
    public static int selectedId = -1;//选中的优惠券id
    public static int selectedProdectId = -1;//选中的卖品优惠券
    public static int selectedSingleProdectId = -1;//单独选中的卖品优惠券
    public static String alertPhoto = "";//用户首次注册之后会有一个奖品的图片
    public static ConfirmPayBO confirmPayBO;//确认订单的信息
    public static boolean isSuccess = true;//是否请求成功
    public static boolean locateSuccess = false;//是否获取定位权限
    public static int badgeNum = 0;//app角标数字
    public static boolean priceIsOk = false;//价格是否已经算好
    public static boolean isIntent = false;//是否是从订单界面回来过
    public static ProdectBO prodectBO = null;//购物车中的商品数据
    public static ArrayList<String> goodsId = new ArrayList<>();//选中的食品id
    public static boolean isOk = true;//卖品详情弹窗是否可以打开
    public static String partOne = "";//卖品满减活动
    public static String partTwo = "";//卖品满减活动
    public static String partThree = "";//卖品满减活动

    public static int currentVersionCode;
    public static String currentVersionName;


    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
        getVersionCode();
        /***初始化工具类*/
        Utils.init(this);
        /**初始化SharedPreferences缓存*/
        spUtils = new SPUtils(LocalConfiguration.SP_NAME);
        /***注册分享*/
        ShareSDK.initSDK(this);
        /***注册极光推送*/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        /***注册微信登陆，支付服务*/
        WXUtils.registerWX(this);
        /***初始化定位sdk，建议在Application中创建*/
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        initImageLoader();
        preinitX5WebCore();
        //预加载x5内核
        Intent intent = new Intent(this, AdvanceLoadX5Service.class);
        startService(intent);
//        setQ5sDK();
        /** 版本更新 **/
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(this))         //设置默认公共请求参数
                .param("appKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                            Toast.makeText(MyApplication.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);
    }

    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
    }

    /**
     * 初始化X5内核
     */
    private void setQ5sDK() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.E(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 初始化ImageLoader，这个适用在大图片加载中
     */
    public void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 获取软件版本号
     *
     * @return
     */
    private void getVersionCode() {
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            currentVersionCode = packInfo.versionCode;
            currentVersionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
