package com.myp.hhcinema.ui.prodectorder;


import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.ResuBo;
import com.myp.hhcinema.entity.SendGoodsBO;
import com.myp.hhcinema.entity.SubmitPrdectOrderBO;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ProdectOrderContract {
    interface View extends BaseRequestView {

        void getOrder(ProdectBO prodectBO,boolean changeType);

        void getSubmitOrderResult(SubmitPrdectOrderBO submitPrdectOrderBO);

        /**
         * 获取支付宝返回
         *
         * @param orderInfo
         */
        void getAliPay(PayBO orderInfo);

        /**
         * 获取微信返回
         */
        void getWxPay(WXPayBO wxPayBO);
        /**
         * 获取会员卡返回
         */
        void getcardPay(ResuBo resuBo);

    }

    interface Presenter extends BasePresenter<View> {


        void getOrderPrice(String merchandiseinfo,String merTicketId,String cinemaId,String changePayType,boolean changeType);

        void submitOrder(String merchandiseinfo,String memo,
                         String cinemaNumber,String merTicketId,
                         String payPrice,String vipPayPrice,String cinemaId,int deliveryType,String address,String phone);

        /**
         * 支付宝支付
         *
         * @param orderNum
         */
        void loadAliPay(String orderNum);

        /**
         * 微信支付
         */
        void loadWeChatPay(String orderNum);

        /**
         * 会员卡支付
         */
        void loadcardPay(String appUserId, String orderNum, String password, String timeStamp, String mac);
    }
}