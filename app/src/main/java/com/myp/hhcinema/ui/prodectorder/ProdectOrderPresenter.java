package com.myp.hhcinema.ui.prodectorder;


import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.SendGoodsBO;
import com.myp.hhcinema.entity.SubmitPrdectOrderBO;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ProdectOrderPresenter extends BasePresenterImpl<ProdectOrderContract.View>
        implements ProdectOrderContract.Presenter {

    @Override
    public void getOrderPrice(String merchandiseinfo, String merTicketId, String cinemaId, String changePayType, final boolean changeType) {
        HttpInterfaceIml.getOrderPrice(merchandiseinfo,merTicketId,cinemaId,changePayType).subscribe(new Subscriber<ProdectBO>() {
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
            public void onNext(ProdectBO s) {
                if (mView == null)
                    return;

                mView.getOrder(s,changeType);
            }
        });
    }

    @Override
    public void submitOrder(String merchandiseinfo,String memo,String cinemaNumber,String merTicketId,
                            String payPrice,String vipPayPrice,String cinemaId,int deliveryType,String address,String phone) {
        HttpInterfaceIml.submitProdectOrder(merchandiseinfo,memo,cinemaNumber,merTicketId,payPrice,vipPayPrice,cinemaId,deliveryType,address,phone).subscribe(new Subscriber<SubmitPrdectOrderBO>() {
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
            public void onNext(SubmitPrdectOrderBO s) {
                if (mView == null)
                    return;

                mView.getSubmitOrderResult(s);
            }
        });
    }


    @Override
    public void loadAliPay(String orderNum) {
        HttpInterfaceIml.payAlipay(orderNum).subscribe(new Subscriber<PayBO>() {
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
            public void onNext(PayBO s) {
                if (mView == null)
                    return;
                mView.getAliPay(s);
            }
        });
    }

    @Override
    public void loadWeChatPay(String orderNum) {
        HttpInterfaceIml.payWeChat(orderNum).subscribe(new Subscriber<WXPayBO>() {
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
            public void onNext(WXPayBO s) {
                if (mView == null)
                    return;
                mView.getWxPay(s);
            }
        });
    }

    @Override
    public void loadcardPay(String appUserId, String orderNum, String password, String timeStamp, String mac) {
//        HttpInterfaceIml.loadcardPay(appUserId,orderNum,password,timeStamp,mac).subscribe(new Subscriber<ResuBo>() {
//            @Override
//            public void onCompleted() {
//                if (mView == null)
//                    return;
//                mView.onRequestEnd();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                if (mView == null)
//                    return;
//                mView.onRequestError(e.getMessage());
//            }
//
//            @Override
//            public void onNext(ResuBo s) {
//                if (mView == null)
//                    return;
//                mView.getcardPay(s);
//            }
//        });
    }
}