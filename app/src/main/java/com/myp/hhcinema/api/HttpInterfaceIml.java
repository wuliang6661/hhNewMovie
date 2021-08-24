package com.myp.hhcinema.api;

import com.myp.hhcinema.entity.ActivityBO;
import com.myp.hhcinema.entity.AdBO;
import com.myp.hhcinema.entity.AppVersionBO;
import com.myp.hhcinema.entity.BaseResult;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.CardBgBO;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.entity.CommentBO;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.ConfirmPayBO;
import com.myp.hhcinema.entity.CouponDetailBO;
import com.myp.hhcinema.entity.CouponNumBo;
import com.myp.hhcinema.entity.CriticBO;
import com.myp.hhcinema.entity.FavourBO;
import com.myp.hhcinema.entity.FeedBackListBO;
import com.myp.hhcinema.entity.GameBO;
import com.myp.hhcinema.entity.HotSellBO;
import com.myp.hhcinema.entity.HotSellBannerBO;
import com.myp.hhcinema.entity.HotWireBO;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.LunBoAndBO;
import com.myp.hhcinema.entity.LunBoBO;
import com.myp.hhcinema.entity.MessageBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.entity.MoviesCommentBO;
import com.myp.hhcinema.entity.MoviesSoonBO;
import com.myp.hhcinema.entity.NewMenberNum;
import com.myp.hhcinema.entity.OpenCardBO;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.entity.OrderDetailBO;
import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.PayCardBO;
import com.myp.hhcinema.entity.PicVerificBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.RechBo;
import com.myp.hhcinema.entity.RefundBO;
import com.myp.hhcinema.entity.ResuBo;
import com.myp.hhcinema.entity.SendGoodsBO;
import com.myp.hhcinema.entity.SessionBO;
import com.myp.hhcinema.entity.ShareBO;
import com.myp.hhcinema.entity.ShopBO;
import com.myp.hhcinema.entity.ShopOrderBO;
import com.myp.hhcinema.entity.SubmitPrdectOrderBO;
import com.myp.hhcinema.entity.SumptionBo;
import com.myp.hhcinema.entity.ThreeProdectBO;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.entity.UserCouponBO;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.entity.preferentialnumberBo;
import com.myp.hhcinema.entity.threelandingBo;
import com.myp.hhcinema.ui.Prizesreading.HomeTopBean;
import com.myp.hhcinema.entity.RechatBo;
import com.myp.hhcinema.util.rx.RxResultHelper;
import com.myp.hhcinema.util.rx.RxHelper;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by wuliang on 2017/6/6.
 * <p>
 * 所有后台接口的实现类
 */

public class HttpInterfaceIml {

    private static HttpInterface service;
    private static String pageNum = "20";   //这里设置所有分页的项目一次拿20条

    /**
     * 获取代理对象
     */
    private static HttpInterface getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpInterface.class, HttpInterface.URL);
        return service;
    }

    /**
     * 用户登陆
     */
    public static Observable<UserBO> userLogin(String phone, String password, String uuid) {
        return getService().userLogin(phone, password, uuid).compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 广告
     */
    public static Observable<AdBO> getAdImage() {
        return getService().getAdImage().compose(RxResultHelper.<AdBO>httpRusult());
    }

    /**
     * 积分商城会员卡背景图片
     */
    public static Observable<CardBgBO> getCardImage(String cinemaId) {
        return getService().getCardImage(cinemaId).compose(RxResultHelper.<CardBgBO>httpRusult());
    }

    /**
     * 积分商城获取卖品前三个信息
     */
    public static Observable<ThreeProdectBO> getProdect(String cinemaId) {
        return getService().getProdect(cinemaId).compose(RxResultHelper.<ThreeProdectBO>httpRusult());
    }

    /*
    * 授权成功的判断
    *
    * */
public static Observable<threelandingBo> userLoginid(String wxUserId, String wbUserId, String qqUserId) {
    return getService().userLoginid(wxUserId,wbUserId,qqUserId).compose(RxHelper.<threelandingBo>httpRusult());
}
    /**
     * 退出登录
     */
    public static Observable<CommonBean> userLogout() {
        return getService().userlogout().compose(RxHelper.<CommonBean>httpRusult());
    }


    /**
     * 用户注册
     */
    public static Observable<UserBO> userRegister(String cinemaId,String phone, String password, String verification,
                                                  String sex) {
        return getService().userRegister(cinemaId,phone, password, verification, sex)
                .compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 获取验证码
     */
    public static Observable<String> userVerification(String phone, String verificationType,String verifycode) {
        return getService().userVerfication(phone, verificationType,verifycode).compose(RxResultHelper.<String>httpRusult());
    }

    /**
     * 获取图文验证码
     */
    public static Observable<PicVerificBO> picVerification() {
        return getService().picVerfication().compose(RxResultHelper.<PicVerificBO>httpRusult());
    }

    /**
     * 验证用户
     */
    public static Observable<UserBO> userVerifuser(String phone, String versition) {
        return getService().userVerifuser(phone, versition).compose(RxResultHelper.<UserBO>httpRusult());
    }
    /**
     * 第三方登录验证用户
     */
    public static Observable<threelandingBo> phonebinding(String header,String phone,String wxUserId,String wbUserId,String qqUserId, String versition) {
        return getService().phonebinding(header,phone,wxUserId,wbUserId ,qqUserId,versition).compose(RxHelper.<threelandingBo>httpRusult());
    }
    /**
     * 第三方注册
     */
    public static Observable<threelandingBo> thirdregist(String cinemaId,String mobile,String password,String header,String nickName,String gender,String wxUserId,String wbUserId,String qqUserId) {
        return getService().thirdregist(cinemaId,mobile,password,header,nickName,gender,wxUserId,wbUserId,qqUserId).compose(RxHelper.<threelandingBo>httpRusult());
    }
    /**
     * 修改密码
     */
    public static Observable<UserBO> userUpdatePass(String password, String pwd) {
        return getService().userUpdatePass(password, pwd).compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 上传头像
     */
    public static Observable<UserBO> userUpdateImg(RequestBody body) {
        return getService().uploadFile(body).compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 修改昵称
     */
    public static Observable<UserBO> userUpdateName(String name) {
        return getService().updateName(name).compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 修改性别
     */
    public static Observable<UserBO> userUpdateSex(int sex) {
        return getService().updateUserSex(sex).compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 绑定会员
     */
    public static Observable<CardBO> cardBindUser(String cinemaId, String card, String pwd) {
        return getService().cardBindUser(cinemaId, card, pwd).compose(RxResultHelper.<CardBO>httpRusult());
    }

    /**
     * 解绑会员卡
     */
    public static Observable<CommonBean> unBindCard(String cardNumber) {
        return getService().unBindCard(cardNumber).compose(RxHelper.<CommonBean>httpRusult());
    }

    /**
     * 获取用户会员卡
     */
    public static Observable<List<CardBO>> cardUser(String cinemaId) {
        return getService().cardUser(cinemaId).compose(RxResultHelper.<List<CardBO>>httpRusult());
    }


    /**
     *  开通会员卡(支付宝)
     */
    public static Observable<PayBO> bindCard(String openCard,String cinemaId,String cardNum,String password,String username,
                                              String sex,String birthday,String idcard,String rechargeMoney) {
        return getService().bindCard(openCard,cinemaId,cardNum,password,username,sex,birthday,idcard,rechargeMoney).compose(RxResultHelper.<PayBO>httpRusult());
    }

    /**
     *  开通会员卡(微信)
     */
    public static Observable<WXPayBO>bindCardWechat(String openCard,String cinemaId,String cardNum,String password,String username,
                                             String sex,String birthday,String idcard,String rechargeMoney) {
        return getService().bindCardWechat(openCard,cinemaId,cardNum,password,username,sex,birthday,idcard,rechargeMoney).compose(RxResultHelper.<WXPayBO>httpRusult());
    }

    /**
     *  获取开通会员卡需要充值的金额
     */
    public static Observable<OpenCardBO> getCardPay(String cinemaId) {
        return getService().getCardPay(cinemaId).compose(RxResultHelper.<OpenCardBO>httpRusult());
    }

    /**
     * 获取影院列表
     */
    public static Observable<List<CinemaBo>> cinemaList(String city, String longitude, String latitude) {
        return getService().cinemaList(city, longitude, latitude).compose(RxResultHelper.<List<CinemaBo>>httpRusult());
    }


    /**
     * 获取城市列表
     */
    public static Observable<List<String>> cityList() {
        return getService().cityList("app").compose(RxResultHelper.<List<String>>httpRusult());
    }


    /**
     * 查询轮播图
     */
    public static Observable<List<LunBoBO>> lunboList(String source, String cinemaId) {
        return getService().lunboList(source, cinemaId).compose(RxResultHelper.<List<LunBoBO>>httpRusult());
    }

    public static Observable<LunBoAndBO> lunboandList(String source, String cinemaId) {
        return getService().lunboandList(source, cinemaId).compose(RxResultHelper.<LunBoAndBO>httpRusult());
    }

    /**
     * 获取正在热映影片
     */
    public static Observable<List<MoviesByCidBO>> moviesHot(String cinameId) {
        return getService().moviesHot(cinameId).compose(RxResultHelper.<List<MoviesByCidBO>>httpRusult());
    }

    /**
     * 获取即将热映影片
     */
    public static Observable<List<MoviesSoonBO>> moviesSoon(String cinameId) {
        return getService().moviesSoon(cinameId).compose(RxResultHelper.<List<MoviesSoonBO>>httpRusult());
    }

    /**
     * 获取场次信息
     */
    public static Observable<List<MoviesByCidBO>> moviesCid(String cinemaId) {
        return getService().moviesByCid(cinemaId).compose(RxResultHelper.<List<MoviesByCidBO>>httpRusult());
    }
    /**
     * 获取热卖小食
     */
    public static Observable<List<HotSellBO>> getHotSell(String appUserId,String cinemaCode) {
        return getService().getHotSell(appUserId,cinemaCode).compose(RxResultHelper.<List<HotSellBO>>httpRusult());
    }

    /**
     * 获取热卖小食轮播图
     */
    public static Observable<List<HotSellBannerBO>> getBanner(String cinemaCode, String category) {
        return getService().getBanner(cinemaCode,category).compose(RxResultHelper.<List<HotSellBannerBO>>httpRusult());
    }

    /**
     * 修改食品价格
     */
    public static Observable<ProdectBO> getOrderPrice(String merchandiseinfo,String merTicketId,String cinemaId,String changePayType) {
        return getService().getOrderPrice(merchandiseinfo,merTicketId,cinemaId,changePayType).compose(RxResultHelper.<ProdectBO>httpRusult());
    }

    /**
     * 获取卖品优惠满减活动信息
     */
    public static Observable<ActivityBO> getActivity(String cinemaId) {
        return getService().getActivity(cinemaId).compose(RxResultHelper.<ActivityBO>httpRusult());
    }

    /**
     * 卖品送至座位相关接口
     */
    public static Observable<SendGoodsBO> getSendParam(String cinemaId, String appUserId) {
        return getService().getSendParam(cinemaId,appUserId).compose(RxHelper.<SendGoodsBO>httpRusult());
    }

    /**
     * 提交食品订单
     */
    public static Observable<SubmitPrdectOrderBO> submitProdectOrder(String merchandiseinfo,String memo,String cinemaNumber,String merTicketId,
                                                                     String payPrice,String vipPayPrice,String cinemaId,int deliveryType,String address,String phone) {
        return getService().submitProdectOrder(merchandiseinfo,memo,cinemaNumber,merTicketId,payPrice,vipPayPrice,cinemaId,deliveryType,address,phone).compose(RxResultHelper.<SubmitPrdectOrderBO>httpRusult());
    }

    /**
     * 提交订单
     */
    public static Observable<ConfirmPayBO> orderSubmit(String areaId,String changePayType,String reduceActivityId,String newCinemaId,String orderName, String seats, String seatId, String ticketOriginPrice, String ticketNum,
                                                       String cinemaNumber, String hallId, String playId, String cineMovieNum, String seatTicketId,
                                                       String merchandiseInfo, String merTicketId,String orderPhone,String memo,String preferPrice,
                                                       String globalPreferPrice,String globalCanBuyNum,String merDisprice,String merVipDisprice) {
        return getService().orderSubmit( areaId,changePayType,reduceActivityId,newCinemaId,orderName, seats, seatId,ticketOriginPrice,ticketNum,cinemaNumber,hallId,
                playId,cineMovieNum,seatTicketId,merchandiseInfo,merTicketId,orderPhone,memo,preferPrice,
                globalPreferPrice,globalCanBuyNum,merDisprice,merVipDisprice).compose(RxResultHelper.<ConfirmPayBO>httpRusult());
    }

    /**
     * 修改订单价格
     */
//    public static Observable<LockSeatsBO> modifyOrderPrice(String changePayType,String playId,String cinemaId,String partnerPrice,String marketPrice,String activityPriceNum,String ticketNum,
//                                                           String beforeTicketPrice,String totalDisprice,String payPrice,String seatTicketId,
//                                                           String merTicketId,String merchandiseInfo,String appUserId,String preferPrice,
//                                                           String globalPreferPrice,String globalCanBuyNum) {
//        return getService().modifyOrderPrice(changePayType,playId,cinemaId, partnerPrice, marketPrice, activityPriceNum, ticketNum,
//                beforeTicketPrice, totalDisprice, payPrice, seatTicketId, merTicketId,merchandiseInfo,appUserId,preferPrice,globalPreferPrice,globalCanBuyNum).compose(RxResultHelper.<LockSeatsBO>httpRusult());
//    }


    /**
     * 改版修改订单价格
     */
    public static Observable<LockSeatsBO> modifyOrderPrice(String changePayType,String reduceActivityId,String playId,String cinemaId,String hallId,String cineMovieNum,
                                                           String partnerPrice,String marketPrice,String activityPriceNum,String ticketNum,
                                                           String beforeTicketPrice,String totalDisprice,String payPrice,String seatTicketId,
                                                           String merTicketId,String merchandiseInfo,String appUserId,String preferPrice,
                                                           String globalPreferPrice,String globalCanBuyNum) {
        return getService().modifyOrderPrice(changePayType,reduceActivityId,playId,cinemaId,hallId,cineMovieNum, partnerPrice, marketPrice, activityPriceNum, ticketNum,
                beforeTicketPrice, totalDisprice, payPrice, seatTicketId, merTicketId,merchandiseInfo,appUserId,preferPrice,globalPreferPrice,globalCanBuyNum).compose(RxResultHelper.<LockSeatsBO>httpRusult());
    }

    /**
     * 查询订单列表
     */
    public static Observable<List<LockSeatsBO>> orderList(String orderType,String payStatus,int orderPage,String orderSize) {
        return getService().orserList(orderType, payStatus, orderPage,orderSize).compose(RxResultHelper.<List<LockSeatsBO>>httpRusult());
    }


    public static Observable<RefundBO>  refundinfo(String id) {
        return getService(). refundinfo(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<RefundBO>  refund(String id,String cinemaId) {
        return getService(). refund(id,cinemaId).compose(RxHelper.<RefundBO>httpRusult());
    }

    /**refund
     * 查询订单详情
     */
    public static Observable<OrderDetailBO> orderMessage(String id, String cinemaId) {
        return getService().orderMessage(id,cinemaId).compose(RxResultHelper.<OrderDetailBO>httpRusult());
    }


    /**
     * 查询单个订单详情
     */
    public static Observable<OrderBO> orderQuery(String appUserId ,String orderNum) {
        return getService().orderQuery(appUserId,orderNum).compose(RxResultHelper.<OrderBO>httpRusult());
    }


    /**
     * 检测是否有未完成订单
     */
    public static Observable<OrderNumBO> orderCheck() {
        return getService().orderCheck().compose(RxResultHelper.<OrderNumBO>httpRusult());
    }

    /**
     * 取消订单
     */
    public static Observable<OrderNumBO> orderCancle(String orderNum) {
        return getService().orderCancel(orderNum).compose(RxResultHelper.<OrderNumBO>httpRusult());
    }

    public static Observable<preferentialnumberBo> getsets(String cinemaId, String dxId) {
        return getService().getsets(cinemaId,dxId).compose(RxResultHelper.<preferentialnumberBo>httpRusult());
    }

    /**
     * 锁座(下单)
     */
    public static Observable<LockSeatsBO> lockSeats(String orderName, String seats, String seatId, String ticketOriginPrice, String ticketNum,
                                                    String cinemaNumber, String hallId, String playId, String cineMovieNum,String preferPrice,
                                                    String globalPreferPrice,String globalCanBuyNum) {
        return getService().lockSeats(orderName,seats,seatId,ticketOriginPrice,ticketNum,cinemaNumber,hallId,playId,cineMovieNum,preferPrice
                                      ,globalPreferPrice,globalCanBuyNum).compose(RxResultHelper.<LockSeatsBO>httpRusult());
    }

    /**
     * 支付宝支付
     */
    public static Observable<PayBO> payAlipay(String orderNum) {
        return getService().payAlipay(orderNum).compose(RxResultHelper.<PayBO>httpRusult());
    }

    /**
     * 积分商城支付宝支付
     */
    public static Observable<PayBO> payInterAlipay(String orderNum) {
        return getService().payInterAlipay(orderNum).compose(RxResultHelper.<PayBO>httpRusult());
    }

    /**
     * 积分商城支付宝支付
     */
    public static Observable<WXPayBO> payInterWechat(String orderNum) {
        return getService().payInterWechat(orderNum).compose(RxResultHelper.<WXPayBO>httpRusult());
    }


    /**
     * 微信支付
     */
    public static Observable<WXPayBO> payWeChat(String orderNum) {
        return getService().payWeChat(orderNum).compose(RxResultHelper.<WXPayBO>httpRusult());
    }

    /**
     * 会员卡支付
     */
    public static Observable<ResuBo> loadcardPay(String orderNum,String coupon) {
        return getService().loadcardPay(orderNum,coupon).compose(RxResultHelper.<ResuBo>httpRusult());
    }
    /**
     * 获取会员卡支付价格
     */
    public static Observable<PayCardBO> cardPayPrice(String orderNum, String card) {
        return getService().cardPayPrice(orderNum, card).compose(RxResultHelper.<PayCardBO>httpRusult());
    }

    /**
     * 会员卡支付
     */
    public static Observable<ResuBo> payCard(String orderNum, String pwd, String card) {
        return getService().payCard(orderNum, pwd, card).compose(RxResultHelper.<ResuBo>httpRusult());
    }


    /**
     * 获取好消息文章
     */
    public static Observable<List<HotWireBO>> hotWire(String articleType, String flpage, String cinemaId) {
        return getService().hotWire(articleType, flpage, pageNum, cinemaId).compose(RxResultHelper.<List<HotWireBO>>httpRusult());
    }
    /**
     * 获取收藏列表Collection of articles
     */
    public static Observable<List<HotWireBO>> articlesCollection(String pageNo) {
        return getService().articlesCollection(pageNo,pageNum).compose(RxResultHelper.<List<HotWireBO>>httpRusult());
    }
    /**
     * 获取充值记录列表
     */
    public static Observable<List<RechBo>> loadRecharge(String pageNo,String cardNum) {
        return getService().loadRecharge(cardNum,pageNo, "20").compose(RxResultHelper.<List<RechBo>>httpRusult());
    }
    /**loadcosumption
     * 获取消费金额
     */
    public static Observable<List<SumptionBo>> loadcosumption(String pageNo, String cardNum) {
        return getService().loadcosumption(cardNum,pageNo, "20").compose(RxResultHelper.<List<SumptionBo>>httpRusult());
    }
    /**loadcosumption
     * 获取单个电影评论
     */
    public static Observable<MoviesCommentBO> moviesComment(String userId, String movieId) {
        return getService().moviesComment(userId, movieId).compose(RxResultHelper.<MoviesCommentBO>httpRusult());
    }
    /**
     * 分享成功送积分
     */
    public static Observable<MoviesCommentBO> shareSuccess(String id,String appUserId) {
        return getService().shareSuccess(id, appUserId).compose(RxResultHelper.<MoviesCommentBO>httpRusult());
    }


    /**
     * 收藏电影
     */
    public static Observable<MoviesCommentBO> moviesShouCang(String userId, String movieId,
                                                             String collectStatus) {
        return getService().moviesShouCang(userId, movieId, collectStatus).compose(RxResultHelper.<MoviesCommentBO>httpRusult());
    }

    /**
     * 想看电影
     */
    public static Observable<MoviesCommentBO> moviesWantSee(String userId, String movieId,
                                                            String wantSee) {
        return getService().moviesWantSee(userId, movieId, wantSee).compose(RxResultHelper.<MoviesCommentBO>httpRusult());
    }

    /**
     * 提交评论
     */
    public static Observable<Object> moviesSunmitCom(String cinemaId,String userId, String movieId, String score,
                                                     String comment) {
        return getService().moviesSummitCom(cinemaId,userId, movieId, score, comment).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取游戏列表
     */
    public static Observable<List<GameBO>> gameList(String pageNo) {
        return getService().gameList(pageNum, pageNo).compose(RxResultHelper.<List<GameBO>>httpRusult());
    }

    /**
     * 获取有奖任务列表
     */
    public static Observable<HomeTopBean>getList(String pageNo) {
        return getService().getList( pageNo,pageNum).compose(RxHelper.<HomeTopBean>httpRusult());
    }
    /**
     * 提交反馈
     */
    public static Observable<String> submitFeed(String cinemaId,String phone, String suggestion) {
        return getService().submitFeed(cinemaId,phone, suggestion).compose(RxResultHelper.<String>httpRusult());
    }

    /**
     * 获取个人中心四个记录数量
     */
    public static Observable<UserBO> memberRecord(String appUserId) {
        return getService().memberRecords(appUserId).compose(RxResultHelper.<UserBO>httpRusult());
    }

    /**
     * 新版获取个人中心四个记录数量
     */
    public static Observable<NewMenberNum> memberNewRecords(String appUserId) {
        return getService().memberNewRecords(appUserId).compose(RxResultHelper.<NewMenberNum>httpRusult());
    }


    /**
     * 获取自己的电影评论记录
     */
    public static Observable<List<CommentBO>> personCommentList(String appUserId, String pageNo) {
        return getService().personCommentList(appUserId,pageNo,pageNum).compose(RxResultHelper.<List<CommentBO>>httpRusult());
    }


    public static Observable<List<RechatBo>> rechatgejine(String cinemaId) {
        return getService().rechatgejine(cinemaId).compose(RxResultHelper.<List<RechatBo>>httpRusult());
    }
    //支付宝
    public static Observable<PayBO> zhifubaopay(String cinemaId, Integer amountType, Integer amountId,String cardNum) {
        return getService().zhifubaopay(cinemaId,amountType,amountId,cardNum,0).compose(RxResultHelper.<PayBO>httpRusult());
    }
    //支付宝其他
    public static Observable<PayBO> zhifubaopayqita(String cinemaId, Integer amountType, Integer rechargeMoney,String cardNum) {
        return getService().zhifubaopayqita(cinemaId,amountType,rechargeMoney,cardNum,0).compose(RxResultHelper.<PayBO>httpRusult());
    }
    //微信
    public static Observable<WXPayBO> weixinpay(String cinemaId, Integer amountType, Integer amountId,String cardNum) {
        return getService().weixinpay(cinemaId,amountType,amountId,cardNum,0).compose(RxResultHelper.<WXPayBO>httpRusult());
    }
    //微信其他
    public static Observable<WXPayBO> weixinpayta(String cinemaId, Integer amountType, Integer rechargeMoney,String cardNum) {
        return getService().weixinpayta(cinemaId,amountType,rechargeMoney,cardNum,0).compose(RxResultHelper.<WXPayBO>httpRusult());
    }

    /**weixinpay
     * 获取自己观看电影记录
     */
    public static Observable<List<CommentBO>> personReadList(String appUserId, String pageNo) {
        return getService().personReadList(appUserId, pageNo, pageNum).compose(RxResultHelper.<List<CommentBO>>httpRusult());
    }

    /**
     * 获取自己想看电影列表
     */
    public static Observable<List<MoviesByCidBO>> personWantSeeList(String appUserId, String cinemaId, String pageNo) {
        return getService().personWantSeeList(appUserId, cinemaId,pageNo, pageNum).compose(RxResultHelper.<List<MoviesByCidBO>>httpRusult());
    }

    /**
     * 获取自己收藏电影记录
     */
    public static Observable<List<MoviesByCidBO>> personCollectList(String appUserId,String zzpage) {
        return getService().personCollectList(appUserId,zzpage,pageNum).compose(RxResultHelper.<List<MoviesByCidBO>>httpRusult());
    }

    /**
     * 分享电影
     */
    public static Observable<ShareBO> shareMovie(String movieId) {
        return getService().shareMovie(movieId).compose(RxResultHelper.<ShareBO>httpRusult());
    }
    /**
     * 电影评论列表
     */

    public static Observable<List<CriticBO>> criticMovie(Long movieId, Integer pageNo, Integer pageSize) {
        return getService().criticMovie(movieId,pageNo,pageSize).compose(RxResultHelper.<List<CriticBO>>httpRusult());
    }
    public static Observable<CriticBO> dianZan(Long Id) {
        return getService().dianZan(Id).compose(RxResultHelper.<CriticBO>httpRusult());
    }
    /**
     * 获取金币兑换商品
     */
    public static Observable<List<ShopBO>> creditsShop(String pageNo, String cinemaId) {
        return getService().creditsShop(pageNum, pageNo, cinemaId).compose(RxResultHelper.<List<ShopBO>>httpRusult());
    }

    /**
     * 获取金币兑换记录
     */
    public static Observable<List<ShopOrderBO>> creditsOrder() {
        return getService().creditsOrder().compose(RxResultHelper.<List<ShopOrderBO>>httpRusult());
    }

    /**
     * 我的约会
     */
    public static Observable<String> personSome() {
        return getService().personSome().compose(RxResultHelper.<String>httpRusult());
    }

    /**
     * 我的优惠券
     */
    public static Observable<List<UserCouponBO>> personCoupon(int pageNo, int pageSize,String cinemaId) {
        return getService().personCoupon(pageNo,pageSize,cinemaId).compose(RxResultHelper.<List<UserCouponBO>>httpRusult());
    }

    /**
     * 有效优惠券数量
     */
    public static Observable<CouponNumBo> personCouponNum(String cinemaId) {
        return getService().personCouponNum(cinemaId).compose(RxResultHelper.<CouponNumBo>httpRusult());
    }

    /**
     * 我的优惠券详情
     */
    public static Observable<CouponDetailBO> personCouponDetail(String id,String cinemaId) {
        return getService().personCouponDetail(id,cinemaId).compose(RxResultHelper.<CouponDetailBO>httpRusult());
    }

    /**
     * 添加优惠券
     */
    public static Observable<String> personAddCoupon() {
        return getService().personAddCoupon().compose(RxResultHelper.<String>httpRusult());
    }
    /**
     * 意见反馈列表
     */
    public static Observable<List<FeedBackListBO>> feedBackList() {
        return getService().feedBackList().compose(RxResultHelper.<List<FeedBackListBO>>httpRusult());
    }
    /**
     * 获取单个电影场次
     */
    public static Observable<SessionBO> movieSereen(String cinemaId, String movieId) {
        return getService().movieScreen(cinemaId, movieId).compose(RxResultHelper.<SessionBO>httpRusult());
    }

    /**
     * 检测版本更新
     */
    public static Observable<AppVersionBO> VersionCheck() {
        return getService().loadVersion().compose(RxResultHelper.<AppVersionBO>httpRusult());
    }

    /**
     * 获取优惠信息列表
     */
    public static Observable<List<FavourBO>> forvoreinfo() {
        return getService().favorinfo().compose(RxResultHelper.<List<FavourBO>>httpRusult());
    }

    /**
     * 获取消息列表
     */
    public static Observable<List<MessageBO>> getMessage(String appUserId,String cinemaId,int type,int messagePageNo,int systemType) {
        return getService().getMessage(appUserId,cinemaId,type,messagePageNo,systemType).compose(RxResultHelper.<List<MessageBO>>httpRusult());
    }

    /**
     * 消息已读
     */
    public static Observable<CommonBean> postRead(String id) {
        return getService().postRead(id).compose(RxHelper.<CommonBean>httpRusult());
    }

    /**
     * 卖品是否在售卖时段内
     */
    public static Observable<ResponseBody> getCanBuy(String cinemaId) {
        return getService().getCanBuy(cinemaId).compose(RxHelper.<ResponseBody>httpRusult());
    }


    /**
     * 座位图
     */
    public static Observable<ResponseBody> getSeats(String cinemaId,String playId) {
        return getService().getSeats(cinemaId,playId).compose(RxHelper.<ResponseBody>httpRusult());
    }

    /**
     * 刷新会员卡余额
     */
    public static Observable<ResponseBody> refreshNum(String cardNumber,String pwd) {
        return getService().refreshNum(cardNumber,pwd).compose(RxHelper.<ResponseBody>httpRusult());
    }
}
