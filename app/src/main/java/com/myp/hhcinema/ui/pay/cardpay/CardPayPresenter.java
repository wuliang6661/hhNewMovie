package com.myp.hhcinema.ui.pay.cardpay;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.PayCardBO;
import com.myp.hhcinema.entity.ResuBo;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CardPayPresenter extends BasePresenterImpl<CardPayContract.View>
        implements CardPayContract.Presenter {


    @Override
    public void loadCardUser(String cinemaId) {
        HttpInterfaceIml.cardUser(cinemaId).subscribe(new Subscriber<List<CardBO>>() {
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
            public void onNext(List<CardBO> s) {
                if (mView == null)
                    return;
                mView.getCardList(s);
            }
        });
    }

    @Override
    public void loadCardPay(final String orderNum, String card) {
        HttpInterfaceIml.cardPayPrice(orderNum, card).subscribe(new Subscriber<PayCardBO>() {
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
            public void onNext(PayCardBO s) {
                if (mView == null)
                    return;
                mView.getCardPrice(s);
            }
        });
    }

    @Override
    public void payCard(String orderNum, String pwd, String card) {
        HttpInterfaceIml.payCard(orderNum, pwd, card).subscribe(new Subscriber<ResuBo>() {
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
            public void onNext(ResuBo s) {
                if (mView == null)
                    return;
                mView.getPayCard(s);
            }
        });
    }
}
