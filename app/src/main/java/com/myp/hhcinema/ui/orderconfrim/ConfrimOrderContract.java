package com.myp.hhcinema.ui.orderconfrim;

import com.myp.hhcinema.entity.ConfirmPayBO;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ConfrimOrderContract {
    interface View extends BaseRequestView {

        void getOrder(ConfirmPayBO orderBO);

        void getModifyInfo(LockSeatsBO modifyInfo,boolean payType,boolean reduce);
    }

    interface Presenter extends BasePresenter<View> {

        void loadSubmitOrder(String areaId,String changePayType,String reduceActivityId,String newCinemaId,String orderName,String seats,String seatId,String ticketOriginPrice,String ticketNum,
                             String cinemaNumber,String hallId,String playId,String cineMovieNum,String seatTicketId,
                             String merchandiseInfo,String merTicketId,String orderPhone,String memo,String preferPrice,
                             String globalPreferPrice,String globalCanBuyNum,String merDisprice,String merVipDisprice);

        /**
         * 修改订单价格
         * @param partnerPrice
         * @param marketPrice
         * @param activityPriceNum
         * @param ticketNum
         * @param beforeTicketPrice
         * @param totalDisprice
         * @param payPrice
         * @param seatTicketId
         * @param merchandiseInfo
         * @param appUserId
         */
        void modifyOrderPrice(String changePayType,String reduceActivityId,String playId,String cinemaId,String hallId,String cineMovieNum,String partnerPrice,String marketPrice,String activityPriceNum,String ticketNum,
                              String beforeTicketPrice,String totalDisprice,String payPrice,String seatTicketId,
                              String merTicketId,String merchandiseInfo,String appUserId,String preferPrice,
                              String globalPreferPrice,String globalCanBuyNum,boolean payType,boolean reduce);


    }
}
