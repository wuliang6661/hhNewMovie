package com.myp.hhcinema.ui.pay.cardpay;

import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.PayCardBO;
import com.myp.hhcinema.entity.ResuBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CardPayContract {
    interface View extends BaseRequestView {

        /**
         * 会员卡列表
         *
         * @param cardBOs
         */
        void getCardList(List<CardBO> cardBOs);

        /**
         * 获取会员卡价格
         *
         * @param payCardBO
         */
        void getCardPrice(PayCardBO payCardBO);
        void getPayCard(ResuBo pay);

    }

    interface Presenter extends BasePresenter<View> {


        void loadCardUser(String cinemaId);


        /**
         * 获取会员卡支付价格
         *
         * @param orderNum
         * @param card
         */
        void loadCardPay(String orderNum, String card);


        /**
         * 会员卡支付
         *
         * @param orderNum
         * @param pwd
         */
        void payCard(String orderNum, String pwd, String card);

    }
}
