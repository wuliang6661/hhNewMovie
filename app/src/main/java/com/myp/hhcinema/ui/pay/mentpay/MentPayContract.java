package com.myp.hhcinema.ui.pay.mentpay;

import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.ResuBo;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MentPayContract {
    interface View extends BaseRequestView {

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
        void loadcardPay(String orderNum,String coupon);

    }
}
