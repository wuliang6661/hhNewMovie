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

import java.util.List;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by wuliang on 2017/6/6.
 * <p>
 * 后台接口统一
 */

public interface HttpInterface {
//      String URL = "http://huihe.happydoit.com";
       String URL = "https://hhyy.happydoit.com";//正式服

    /**
     * 登陆用户
     */
    @FormUrlEncoded
    @POST("api/appuser/login")
    Observable<BaseResult<UserBO>> userLogin(@Field("mobile") String mobile,
                                             @Field("password") String password, @Field("uuid") String uuid);


    /**
     * 广告
     */
    @POST("api/getAdPictures/detail")
    Observable<BaseResult<AdBO>> getAdImage();

    /**
     * 积分商城获取会员卡图片
     */
    @FormUrlEncoded
    @POST("api/mallSetting/getMallSetting")
    Observable<BaseResult<CardBgBO>> getCardImage(@Field("cinemaId") String cinemaId);

    /**
     * 积分商城获取卖品前三个信息
     */
    @FormUrlEncoded
    @POST("api/sellingGoods/detail")
    Observable<BaseResult<ThreeProdectBO>> getProdect(@Field("cinemaId") String cinemaId);


    @FormUrlEncoded
    @POST("api/appuser/social/login")
    Observable<threelandingBo> userLoginid(@Field("wxUserId") String wxUserId,
                                           @Field("wbUserId") String wbUserId,
                                           @Field("qqUserId") String qqUserId);
    /**
     * 退出登录
     */
    @POST("api/appuser/logout")
    Observable<CommonBean> userlogout();

    /**
     * 用户注册
     */
    @FormUrlEncoded
    @POST("api/appuser/register")
    Observable<BaseResult<UserBO>> userRegister(@Field("cinemaId") String cinemaId,
                                                @Field("mobile") String mobile,
                                                @Field("password") String password,
                                                @Field("verification") String verification,
                                                @Field("gender") String gender);

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("api/appuser/verification")
    Observable<BaseResult<String>> userVerfication(@Field("mobile") String mobile,
                                                   @Field("verificationType") String verificationType,
                                                   @Field("verifycode") String verifycode);

    /**
     * 获取图文验证码
     */
    @POST("api/appuser/verifyPath")
    Observable<BaseResult<PicVerificBO>> picVerfication();

    /**
     * 验证用户
     */
    @FormUrlEncoded
    @POST("api/appuser/verifuser")
    Observable<BaseResult<UserBO>> userVerifuser(@Field("mobile") String mobile,
                                                 @Field("verification") String verification);

    /**
     * 第三方登陆验证用户
     */
    @FormUrlEncoded
    @POST("api/appuser/bind/mobile")
    Observable<threelandingBo> phonebinding(@Field("header") String header,
                                            @Field("mobile") String mobile,
                                            @Field("wxUserId") String wxUserId,
                                            @Field("wbUserId") String wbUserId,
                                            @Field("qqUserId") String qqUserId,
                                            @Field("verification") String verification);
    /**
     * 第三方登陆验证用户
     * mobile,pwd,header,nickname,gender,wxUserId,wbUserId,qqUserId
     *
     */
    @FormUrlEncoded
    @POST("api/appuser/social/signup")
    Observable<threelandingBo> thirdregist(@Field("cinemaId") String cinemaId,
                                           @Field("mobile") String mobile,
                                           @Field("password") String password,
                                           @Field("header") String header,
                                           @Field("nickName") String nickName,
                                           @Field("gender") String gender,
                                           @Field("wxUserId") String wxUserId,
                                           @Field("wbUserId") String wbUserId,
                                           @Field("qqUserId") String qqUserId);
    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("api/appuser/update")
    Observable<BaseResult<UserBO>> userUpdatePass(@Field("password") String password, @Field("pwd") String pwd);

    /**
     * 上传头像(需要做cookile持久化，否则会清空后台cookie，导致后台状态变为未登录)
     */
    @Multipart
    @POST("api/appuser/update")
    Observable<BaseResult<UserBO>> uploadFile(@Part("headerImage\"; filename=\"avatar.jpg") RequestBody file);

    /**
     * 修改昵称
     */
    @FormUrlEncoded
    @POST("api/appuser/update")
    Observable<BaseResult<UserBO>> updateName(@Field("nickName") String nickName);

    /**
     * 修改性别
     */
    @FormUrlEncoded
    @POST("api/appuser/update")
    Observable<BaseResult<UserBO>> updateUserSex(@Field("gender") int sex);

    /**
     * 绑定会员
     */
    @FormUrlEncoded
    @POST("api/appuser/bindcard")
    Observable<BaseResult<CardBO>> cardBindUser(@Field("cinemaId") String cinemaId, @Field("card") String card,
                                                @Field("pwd") String pwd
                                                );

    /**
     * 解绑会员
     */
    @FormUrlEncoded
    @POST("api/dxInsider/unbind")
    Observable<CommonBean> unBindCard(@Field("cardNumber") String cardNumber);


    /**
     * 获取用户会员卡
     */
    @FormUrlEncoded
    @POST("api/appuser/usercard")
    Observable<BaseResult<List<CardBO>>> cardUser(@Field("cinemaId") String cinemaId);

    /**
     * 获取开通会员卡金额
     */
    @FormUrlEncoded
    @POST("api/member/card/registRule")
    Observable<BaseResult<OpenCardBO>> getCardPay(@Field("cinemaId") String cinemaId);

    /**
     * 开通会员卡(支付宝)
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/alipay")
    Observable<BaseResult<PayBO>> bindCard(@Field("openCard") String openCard, //传 1
                                            @Field("cinemaId") String cinemaId,
                                            @Field("cardNum") String cardNum,
                                            @Field("password") String password,
                                            @Field("username") String username,
                                            @Field("sex") String sex,//男   女
                                            @Field("birthday") String birthday,
                                            @Field("idcard") String idcard,
                                            @Field("rechargeMoney") String rechargeMoney);

    /**
     * 开通会员卡(微信)
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/wxpay")
    Observable<BaseResult<WXPayBO>> bindCardWechat(@Field("openCard") String openCard, //传 1
                                                   @Field("cinemaId") String cinemaId,
                                                   @Field("cardNum") String cardNum,
                                                   @Field("password") String password,
                                                   @Field("username") String username,
                                                   @Field("sex") String sex,//男   女
                                                   @Field("birthday") String birthday,
                                                   @Field("idcard") String idcard,
                                                   @Field("rechargeMoney") String rechargeMoney);

    /**
     * 获取影院列表信息
     */
    @FormUrlEncoded
    @POST("api/cinema/cinemas")
    Observable<BaseResult<List<CinemaBo>>> cinemaList(@Field("city") String city, @Field("longitude") String longitude,
                                                      @Field("latitude") String latitude);

    /**
     * 获取城市列表
     */
    @FormUrlEncoded
    @POST("api/cinema/cinemacity")
    Observable<BaseResult<List<String>>> cityList(@Field("source") String source);


    /**
     * 查询首页轮播图
     */
    @FormUrlEncoded
    @POST("api/banner/banners")
    Observable<BaseResult<List<LunBoBO>>> lunboList(@Field("source") String source,
                                                    @Field("cinemaId") String cinemaId);

    /**
     * 查询首页轮播图
     */

    @FormUrlEncoded
    @POST("api/new/banners")
    Observable<BaseResult<LunBoAndBO>> lunboandList(@Field("source") String source,
                                                    @Field("cinemaId") String cinemaId);
    /**LunBoAndBO
     * 获取正在热映影片列表
     */
    @FormUrlEncoded
    @POST("api/Movie/hotMovie")
    Observable<BaseResult<List<MoviesByCidBO>>> moviesHot(@Field("cinemaId") String cinameId);

    /**
     * 获取即将热映影片列表
     */
    @FormUrlEncoded
    @POST("api/Movie/soonMovie")
    Observable<BaseResult<List<MoviesSoonBO>>> moviesSoon(@Field("cinemaId") String cinameId);

    /**
     * 获取场次信息
     */
    @FormUrlEncoded
    @POST("api/Movie/MoviesByCid")
    Observable<BaseResult<List<MoviesByCidBO>>> moviesByCid(@Field("cinemaId") String cinameId);

    /**
     * 获取热卖小食
     */
    @FormUrlEncoded
    @POST("api/merchandise/merchandiseList")
    Observable<BaseResult<List<HotSellBO>>> getHotSell(@Field("appUserId") String appUserId,
                                                       @Field("cinemaId") String cinemaCode);

    /**
     * 获取轮播图
     */
    @FormUrlEncoded
    @POST("api/banner/banners")
    Observable<BaseResult<List<HotSellBannerBO>>> getBanner(@Field("cinemaId") String cinemaCode,
                                                            @Field("source") String category);
    /**
     * 修改食品价格
     */
//    @FormUrlEncoded
//    @POST("api/order/countMerchaniseOrderPrice")
//    Observable<BaseResult<ProdectBO>> getOrderPrice(@Field("merchandiseInfo") String merchandiseinfo,
//                                                    @Field("merTicketId") String merTicketId,
//                                                    @Field("newCinemaId") String cinemaId);

    /**
     * 修改食品价格
     */
    @FormUrlEncoded
    @POST("api/order/newCountMerchaniseOrderPrice")
    Observable<BaseResult<ProdectBO>> getOrderPrice(@Field("merchandiseInfo") String merchandiseinfo,
                                                    @Field("merTicketId") String merTicketId,
                                                    @Field("newCinemaId") String cinemaId,
                                                    @Field("changePayType") String changePayType);

    /**
     * 卖品活动
     */
    @FormUrlEncoded
    @POST("api/merActivity/getMerActivity")
    Observable<BaseResult<ActivityBO>> getActivity(@Field("cinemaId") String cinemaId);


    /**
     * 卖品送至座位相关接口
     */
    @FormUrlEncoded
    @POST("api/order/sendGoodsParam")
    Observable<SendGoodsBO> getSendParam(@Field("cinemaId") String cinemaId,
                                         @Field("appUserId") String appUserId);


    /**
     * 提交小食订单
     */
    @FormUrlEncoded
    @POST("api/order/newSubmitMerchaniseOrder")
    Observable<BaseResult<SubmitPrdectOrderBO>> submitProdectOrder(@Field("merchandiseInfo") String merchandiseinfo,
                                                                   @Field("memo") String memo,
                                                                   @Field("cinemaNumber") String cinemaNumber,
                                                                   @Field("merTicketId") String merTicketId,
                                                                   @Field("payPrice") String payPrice,
                                                                   @Field("vipPayPrice") String vipPayPrice,
                                                                   @Field("newCinemaId") String cinemaId,
                                                                   @Field("deliveryType") int deliveryType,//配送方式  0自提，1配送
                                                                   @Field("address") String address,
                                                                   @Field("phone") String phone);

    /**
     * 提交订单
     */
    @FormUrlEncoded
    @POST("api/new/order/newSubmitorder")
    Observable<BaseResult<ConfirmPayBO>> orderSubmit(@Field("sectionId") String areaId,
                                                     @Field("changePayType") String changePayType,//1 非会员，2 会员。
                                                     @Field("reduceActivityId") String reduceActivityId,//满减活动id
                                                     @Field("newCinemaId") String newCinemaId,
                                                     @Field("orderName") String orderName,
                                                     @Field("seats") String seats,
                                                     @Field("seatId") String seatId,
                                                     @Field("ticketOriginPrice") String ticketOriginPrice,
                                                     @Field("ticketNum") String ticketNum,
                                                     @Field("cinemaNumber") String cinemaNumber,
                                                     @Field("hallId") String hallId,
                                                     @Field("playId") String playId,
                                                     @Field("cineMovieNum") String cineMovieNum,
                                                     @Field("seatTicketId") String seatTicketId,
                                                     @Field("merchandiseInfo") String merchandiseInfo,
                                                     @Field("merTicketId") String merTicketId,
                                                     @Field("orderPhone") String orderPhone,
                                                     @Field("memo") String memo,
                                                     @Field("preferPrice") String preferPrice,
                                                     @Field("globalPreferPrice") String globalPreferPrice,
                                                     @Field("globalCanBuyNum") String globalCanBuyNum,
                                                     @Field("merDisprice") String merDisprice,
                                                     @Field("merVipDisprice") String merVipDisprice);
    /**
     * 修改订单价格
     */
//    @FormUrlEncoded
//    @POST("api/order/modifyOrderPrice")
//    Observable<BaseResult<LockSeatsBO>> modifyOrderPrice(@Field("changePayType") String changePayType,//1 非会员，2 会员。
//                                                         @Field("playId") String playId,
//                                                         @Field("newCinemaId") String cinemaId,
//                                                         @Field("partnerPrice") String partnerPrice,
//                                                         @Field("marketPrice") String marketPrice,
//                                                         @Field("activityPriceNum") String activityPriceNum,
//                                                         @Field("ticketNum") String ticketNum,
//                                                         @Field("beforeTicketPrice") String beforeTicketPrice,
//                                                         @Field("totalDisprice") String totalDisprice,
//                                                         @Field("payPrice") String payPrice,
//                                                         @Field("seatTicketId") String seatTicketId,
//                                                         @Field("merTicketId") String merTicketId,
//                                                         @Field("merchandiseInfo") String merchandiseInfo,
//                                                         @Field("appUserId") String appUserId,
//                                                         @Field("preferPrice") String preferPrice,
//                                                         @Field("globalPreferPrice") String globalPreferPrice,
//                                                         @Field("globalCanBuyNum") String globalCanBuyNum);

    /**
     * 修改订单价格
     */
    @FormUrlEncoded
    @POST("api/order/newModifyOrderPrice")
    Observable<BaseResult<LockSeatsBO>> modifyOrderPrice(@Field("changePayType") String changePayType,//1 非会员，2 会员。
                                                         @Field("reduceActivityId") String reduceActivityId,//满减活动id，不使用传0，默认空字符串
                                                         @Field("playId") String playId,
                                                         @Field("newCinemaId") String cinemaId,
                                                         @Field("hallId") String hallId,
                                                         @Field("cineMovieNum") String cineMovieNum,
                                                         @Field("partnerPrice") String partnerPrice,
                                                         @Field("marketPrice") String marketPrice,
                                                         @Field("activityPriceNum") String activityPriceNum,
                                                         @Field("ticketNum") String ticketNum,
                                                         @Field("beforeTicketPrice") String beforeTicketPrice,
                                                         @Field("totalDisprice") String totalDisprice,
                                                         @Field("payPrice") String payPrice,
                                                         @Field("seatTicketId") String seatTicketId,
                                                         @Field("merTicketId") String merTicketId,
                                                         @Field("merchandiseInfo") String merchandiseInfo,
                                                         @Field("appUserId") String appUserId,
                                                         @Field("preferPrice") String preferPrice,
                                                         @Field("globalPreferPrice") String globalPreferPrice,
                                                         @Field("globalCanBuyNum") String globalCanBuyNum);

    /**
     * 查询订单列表
     */
    @FormUrlEncoded
    @POST("api/order/orderlist")
    Observable<BaseResult<List<LockSeatsBO>>> orserList(@Field("orderType") String orderType,  //0电影票，1 小食
                                                    @Field("payStatus") String payStatus,  //0 未支付  1 已完成
                                                    @Field("orderPage") int orderPage,
                                                    @Field("orderSize") String orderSize);

    /**
     * 查询订单详情
     */
    @FormUrlEncoded
    @POST("api/order/detail")
    Observable<BaseResult<OrderDetailBO>> orderMessage(@Field("id") String id,  //0未完成订单，1已完成订单
                                           @Field("cinemaId") String cinemaId);


    /**
     * 查询订单列表
     */
    @FormUrlEncoded
    @POST("api/order/refund/info")
    Observable<RefundBO> refundinfo(@Field("id") String id //0未完成订单，1已完成订单
    );
    @FormUrlEncoded
    @POST("api/order/refund")
    Observable<RefundBO> refund(@Field("id") String id //0未完成订单，1已完成订单
                                ,@Field("cinemaId") String cinemaId
    );

//    @FormUrlEncoded
//    @POST("api/order/detail")
//    Call<requstBean> orderMessage(@Field("id") String id,  //0未完成订单，1已完成订单
//                                  @Field("cinemaId") String cinemaId);

    /**
     * 查询单个订单详情
     */
    @FormUrlEncoded
    @POST("api/order/queryorder")
    Observable<BaseResult<OrderBO>> orderQuery(@Field("appUserId") String appUserId ,
                                               @Field("orderNum") String orderNum);


    /**
     * 检测是否有未完成的订单
     */
    @POST("api/new/order/checkorder")
    Observable<BaseResult<OrderNumBO>> orderCheck();

    /**
     * 取消未完成订单
     */
    @FormUrlEncoded
    @POST("api/order/cancelorder")
    Observable<BaseResult<OrderNumBO>> orderCancel(@Field("orderNum") String orderNum);
    /**
     * 会员卡优惠数量
     */
    @FormUrlEncoded
    @POST("api/global/can/buy/num")
    Observable<BaseResult<preferentialnumberBo>> getsets(@Field("cinemaId") String cinemaId, @Field("playId") String playId);
    /**
     * 获取订单信息
     */
    @FormUrlEncoded
    @POST("api/order/newCountOrder")
    Observable<BaseResult<LockSeatsBO>> lockSeats (@Field("orderName") String orderName,
                                                   @Field("seats") String seats,       //拼接座位，例  1排1座，用“，”隔开\
                                                   @Field("seatId") String seatId,
                                                   @Field("ticketOriginPrice") String ticketOriginPrice,     //总价
                                                   @Field("ticketNum") String ticketNum,
                                                   @Field("cinemaNumber") String cinemaNumber,//广电总局影院唯一编码
                                                   @Field("hallId") String hallId,  //鼎新影厅id
                                                   @Field("playId") String playId, //鼎新场次id
                                                   @Field("cineMovieNum") String cineMovieNum,//广电总局规定的影片全国唯一编码);
                                                   @Field("preferPrice") String preferPrice,
                                                   @Field("globalPreferPrice") String globalPreferPrice,
                                                   @Field("globalCanBuyNum") String globalCanBuyNum);
    /**
     * 获取会员卡支付价格
     */
    @FormUrlEncoded
    @POST("api/order/cardprice")
    Observable<BaseResult<PayCardBO>> cardPayPrice(@Field("orderNum") String orderNum, @Field("card") String card);


    /**
     * 会员卡支付
     */
    @FormUrlEncoded
    @POST("api/order/card")
    Observable<BaseResult<ResuBo>> payCard(@Field("orderNum") String orderNum, @Field("pwd") String pwd,
                                           @Field("card") String card);

    /**
     * 支付宝支付
     */
    @FormUrlEncoded
    @POST("api/order/alipay")
    Observable<BaseResult<PayBO>> payAlipay(@Field("orderNum") String orderNum);


    /**
     * 积分商城支付宝支付
     */
    @FormUrlEncoded
    @POST("api/integralOrder/aLiPay")
    Observable<BaseResult<PayBO>> payInterAlipay(@Field("orderNum") String orderNum);

    /**
     * 积分商城微信支付
     */
    @FormUrlEncoded
    @POST("api/integralOrder/weChat")
    Observable<BaseResult<WXPayBO>> payInterWechat(@Field("orderNum") String orderNum);



    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST("api/order/wechat")
    Observable<BaseResult<WXPayBO>> payWeChat(@Field("orderNum") String orderNum);
    /**
     * 会员卡支付
     */
    @FormUrlEncoded
    @POST("api/order/coupon/pay")
    Observable<BaseResult<ResuBo>> loadcardPay(@Field("orderNum") String orderNum,@Field("coupon") String coupon);

    /**
     * 获取好消息文章
     */
    @FormUrlEncoded
    @POST("api/article/articles")
    Observable<BaseResult<List<HotWireBO>>> hotWire(@Field("articleType") String articleType,
                                                    @Field("articlePage") String articlePage,
                                                    @Field("articleSize") String articleSize,
                                                    @Field("cinemaId") String cinemaId);
    /**
     * 获取收藏消息文章
     */
    @FormUrlEncoded
    @POST("api/appuser/keep/article/list")
    Observable<BaseResult<List<HotWireBO>>> articlesCollection(@Field("pageNo") String pageNo,
                                                    @Field("pageSize") String pageSize);
    /**
     * 获取充值记录
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/order/list")
    Observable<BaseResult<List<RechBo>>> loadRecharge( @Field("cardNum") String cardNum,
                                                       @Field("pageNo") String pageNo,
                                                      @Field("pageSize") String pageSize);
    /**
     * 消费明细
     */
    @FormUrlEncoded
    @POST("api/member/card/consumption/list")
    Observable<BaseResult<List<SumptionBo>>> loadcosumption(@Field("cardNum") String cardNum,
                                                            @Field("pageNo") String pageNo,
                                                            @Field("pageSize") String pageSize

    );
    /**loadcosumption
     * 获取单个电影评论
     */
    @FormUrlEncoded
    @POST("api/Comment/myComment")
    Observable<BaseResult<MoviesCommentBO>> moviesComment(@Field("appUserId") String appUserId,
                                                          @Field("movieId") String movieId);

    /**l
     * 分享成功送积分
     */
    @FormUrlEncoded
    @POST("api/pointRule/shareArticle")
    Observable<BaseResult<MoviesCommentBO>> shareSuccess(@Field("id") String id,
                                                         @Field("appUserId") String appUserId);


    /**
     * 收藏电影
     */
    @FormUrlEncoded
    @POST("api/Comment/saveCollect")
    Observable<BaseResult<MoviesCommentBO>> moviesShouCang(@Field("appUserId") String appUserId,
                                                           @Field("movieId") String movieId,
                                                           @Field("collectStatus") String collectStatus);  //收藏为1，不收藏为0

    /**
     * 想看电影
     */
    @FormUrlEncoded
    @POST("api/Comment/saveWantSee")
    Observable<BaseResult<MoviesCommentBO>> moviesWantSee(@Field("appUserId") String appUserId,
                                                          @Field("movieId") String movieId,
                                                          @Field("wantSee") String wantSee);  // 想看为1，不想看为0

    /**
     * 提交评论
     */
    @FormUrlEncoded
    @POST("api/Comment/saveComment")
    Observable<BaseResult<Object>> moviesSummitCom(@Field("cinemaId") String cinemaId,
                                                   @Field("appUserId") String appUserId,
                                                   @Field("movieId") String movieId,
                                                   @Field("score") String score,//分数
                                                   @Field("comment") String comment);   //评论

    /**
     * 获取游戏列表
     */
    @FormUrlEncoded
    @POST("api/game/games")
    Observable<BaseResult<List<GameBO>>> gameList(@Field("pageSize") String pageSize, @Field("pageNo") String pageNo);

    /**
     * 获取有奖任务列表
     */
    @FormUrlEncoded
    @POST("api/reward/article/list")
    Observable<HomeTopBean>getList(@Field("pageNo") String pageNo,
            @Field("pageSize") String pageSize );

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("api/suggestion/suggestions")
    Observable<BaseResult<String>> submitFeed(@Field("cinemaId") String cinemaId,
                                              @Field("phone") String phone,
                                              @Field("suggestion") String suggestion);


    /**
     * 获取个人中心四个记录数量
     */
    @FormUrlEncoded
    @POST("api/Comment/myRecords")
    Observable<BaseResult<UserBO>> memberRecords(@Field("appUserId") String appUserId);


    /**
     * 新版获取个人中心数量
     */
    @FormUrlEncoded
    @POST("api/center/itemNumber")
    Observable<BaseResult<NewMenberNum>> memberNewRecords(@Field("appUserId") String appUserId);

    /**
     * 获取自己的电影评论记录
     */
    @FormUrlEncoded
    @POST("api/Comment/myCommentList")
    Observable<BaseResult<List<CommentBO>>> personCommentList(@Field("appUserId") String appUserId,
                                                              @Field("pageNo") String pageNo,
                                                              @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("api/member/card/recharge/amount")
    Observable<BaseResult<List<RechatBo>>> rechatgejine(@Field("cinemaId") String cinemaId);

    /**rechatgejine
     * 获取自己观看电影记录
     */
    @FormUrlEncoded
    @POST("api/Movie/myWatchedList")
    Observable<BaseResult<List<CommentBO>>> personReadList(@Field("appUserId") String appUserId,
                                                           @Field("pageNo") String pageNo,
                                                           @Field("pageSize") String pageSize);

    /**
     * weixinpay
     * 支付宝支付
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/alipay")
    Observable<BaseResult<PayBO>> zhifubaopay(@Field("cinemaId") String cinemaId,
                                              @Field("amountType") Integer amountType,
                                              @Field("amountId") Integer amountId,
                                              @Field("cardNum") String cardNum,
                                              @Field("app") Integer app
    );
    /**
     * weixinpay
     * 支付宝支付其他金额
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/alipay")
    Observable<BaseResult<PayBO>> zhifubaopayqita(@Field("cinemaId") String cinemaId,
                                                  @Field("amountType") Integer amountType,
                                                  @Field("rechargeMoney") Integer rechargeMoney,
                                                  @Field("cardNum") String cardNum,
                                                  @Field("app") Integer app);

    /**
     * weixinpay
     * 微信支付
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/wxpay")
    Observable<BaseResult<WXPayBO>> weixinpay(@Field("cinemaId") String cinemaId,
                                              @Field("amountType") Integer amountType,
                                              @Field("amountId") Integer amountId,
                                              @Field("cardNum") String cardNum,
                                              @Field("app") Integer app);
    /**
     * weixinpay
     * 微信支付其他金额
     */
    @FormUrlEncoded
    @POST("api/member/card/recharge/wxpay")
    Observable<BaseResult<WXPayBO>> weixinpayta(@Field("cinemaId") String cinemaId,
                                                @Field("amountType") Integer amountType,
                                                @Field("rechargeMoney") Integer rechargeMoney,
                                                @Field("cardNum") String cardNum,
                                                @Field("app") Integer app);
    /**
     * 获取自己想看电影列表
     */
    @FormUrlEncoded
    @POST("api/Movie/myWantSeeList")
    Observable<BaseResult<List<MoviesByCidBO>>> personWantSeeList(@Field("appUserId") String appUserId,
                                                                  @Field("cinemaId") String cinemaId,
                                                                  @Field("pageNo") String pageNo,
                                                                  @Field("pageSize") String pageSize);
    /**
     * 获取自己收藏电影记录
     */
    @FormUrlEncoded
    @POST("api/Movie/myCollectList")
    Observable<BaseResult<List<MoviesByCidBO>>> personCollectList(@Field("appUserId") String appUserId,
                                                                  @Field("pageNo") String pageNo,
                                                                  @Field("pageSize") String pageSize);
    /**


    /**
     * 分享电影
     */
    @FormUrlEncoded
    @POST("api/Movie/share")
    Observable<BaseResult<ShareBO>> shareMovie(@Field("movieId") String movieId);


    @FormUrlEncoded
    @POST("api/movie/comment/list")
    Observable<BaseResult<List<CriticBO>>> criticMovie(@Field("movieId") Long movieId,
                                                 @Field("pageNo") Integer pageNo,
                                                 @Field("pageSize") Integer pageSize);
    @FormUrlEncoded
    @POST("api/movie/comment/upvote")
    Observable<BaseResult<CriticBO>> dianZan(@Field("id") Long id);
    /**
     * 获取金币兑换的商品
     */
    @FormUrlEncoded
    @POST("api/game/exchanges")
    Observable<BaseResult<List<ShopBO>>> creditsShop(@Field("pageSize") String pageSize,
                                                     @Field("pageNo") String pageNo,
                                                     @Field("cinemaId") String cinemaId);

    /**
     * 金币兑换记录
     */
    @POST("api/game/pointorder/list")
    Observable<BaseResult<List<ShopOrderBO>>> creditsOrder();


    /**
     * 我的约会
     */
    @POST("api/appointment/appointments")
    Observable<BaseResult<String>> personSome();

    /**
     * 我的优惠券
     */
    @FormUrlEncoded
    @POST("api/ticket/userTickets")
    Observable<BaseResult<List<UserCouponBO>>> personCoupon(@Field("pageNo") int pageNo,
                                                            @Field("pageSize") int pageSize,
                                                            @Field("cinemaId") String cinemaId);
    /**
     * 有效优惠券数量
     */
    @FormUrlEncoded
    @POST("api/ticket/canUseNumber")
    Observable<BaseResult<CouponNumBo>> personCouponNum(@Field("cinemaId") String cinemaId);

    /**
     * 我的优惠券详情
     */
    @FormUrlEncoded
    @POST("api/ticket/ticketInfo")
    Observable<BaseResult<CouponDetailBO>> personCouponDetail(@Field("id") String id,
                                                              @Field("cinemaId") String cinemaId);

    /**
     * 添加优惠券
     */
    @POST("api/coupon/add")
    Observable<BaseResult<String>> personAddCoupon();

    /**
     * 意见反馈列表
     */
    @POST("api/suggestion/list")
    Observable<BaseResult<List<FeedBackListBO>>> feedBackList();
    /**feedBackList
     * 获取单个电影的场次
     */
    @FormUrlEncoded
    @POST("api/new/movie/screening")
    Observable<BaseResult<SessionBO>> movieScreen(@Field("cinemaId") String cinemaId,
                                                  @Field("movieId") String movieId);


    /**
     * 版本检测更新
     */
    @POST("/version/version.json")
    Observable<BaseResult<AppVersionBO>> loadVersion();


    /**
     * 获取场次上面的优惠信息列表
     */
    @POST("api/price/favorinfo/list")
    Observable<BaseResult<List<FavourBO>>> favorinfo();

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("api/message/list")
    Observable<BaseResult<List<MessageBO>>> getMessage(@Field("appUserId") String appUserId,
                                                       @Field("cinemaId") String cinemaId,
                                                       @Field("type") int type, // 0活动资讯 1系统消息
                                                       @Field("messagePageNo") int messagePageNo,
                                                       @Field("systemType") int systemType);//iOS传入1 Android传入2

    /**
     * 消息已读
     */
    @FormUrlEncoded
    @POST("api/update/push_message")
    Observable<CommonBean> postRead(@Field("id") String id);


    /**
     * 卖品是否在售卖时段内
     */
    @FormUrlEncoded
    @POST("api/merchandise/isOpenSale")
    Observable<ResponseBody> getCanBuy(@Field("cinemaId") String cinemaId);


    /**
     * 座位图
     */
    @FormUrlEncoded
    @POST("api/applets/movie/seat/map")
    Observable<ResponseBody> getSeats(@Field("cinemaId") String cinemaId,
                                      @Field("playId") String playId);


    /**
     * 刷新会员卡余额
     */
    @FormUrlEncoded
    @POST("api/appuser/updateCard")
    Observable<ResponseBody> refreshNum(@Field("cardNumber") String cardNumber,
                                        @Field("pwd") String pwd);
}

