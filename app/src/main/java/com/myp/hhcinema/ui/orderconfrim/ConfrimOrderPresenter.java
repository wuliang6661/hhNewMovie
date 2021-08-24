package com.myp.hhcinema.ui.orderconfrim;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.ConfirmPayBO;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ConfrimOrderPresenter extends BasePresenterImpl<ConfrimOrderContract.View>
        implements ConfrimOrderContract.Presenter {

    @Override
    public void loadSubmitOrder(String areaId,String changePayType,String reduceActivityId,String newCinemaId,String orderName,String seats,String seatId,String ticketOriginPrice,String ticketNum,
                                String cinemaNumber,String hallId,String playId,String cineMovieNum,String seatTicketId,
                                String merchandiseInfo,String merTicketId,String orderPhone,String memo,String preferPrice,
                                String globalPreferPrice,String globalCanBuyNum,String merDisprice,String merVipDisprice) {
        HttpInterfaceIml.orderSubmit( areaId,changePayType,reduceActivityId,newCinemaId,orderName, seats, seatId,ticketOriginPrice,ticketNum,cinemaNumber,hallId,
                playId,cineMovieNum,seatTicketId,merchandiseInfo,merTicketId,orderPhone,memo,
                preferPrice,globalPreferPrice,globalCanBuyNum,merDisprice,merVipDisprice).subscribe(new Subscriber<ConfirmPayBO>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(ConfirmPayBO s) {
                if (mView == null)
                    return;
                mView.getOrder(s);
            }
        });
    }

    @Override
    public void modifyOrderPrice(String changePayType, String reduceActivityId,String playId, String cinemaId,String hallId,String cineMovieNum, String partnerPrice, String marketPrice, String activityPriceNum, String ticketNum,
                                 String beforeTicketPrice, String totalDisprice, String payPrice, String seatTicketId,
                                 String merTicketId, String merchandiseInfo, String appUserId, String preferPrice,
                                 String globalPreferPrice, String globalCanBuyNum, final boolean payType,final boolean reduce) {
        HttpInterfaceIml.modifyOrderPrice( changePayType,reduceActivityId,playId,cinemaId,hallId,cineMovieNum,partnerPrice, marketPrice, activityPriceNum, ticketNum,
                beforeTicketPrice, totalDisprice, payPrice, seatTicketId,merTicketId, merchandiseInfo,appUserId,preferPrice,globalPreferPrice,globalCanBuyNum).subscribe(new Subscriber<LockSeatsBO>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(LockSeatsBO s) {
                if (mView == null)
                    return;
                mView.getModifyInfo(s,payType,reduce);
            }
        });
    }
}
