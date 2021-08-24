package com.myp.hhcinema.ui.hotsellprodect;


import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.ActivityBO;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.HotSellBO;
import com.myp.hhcinema.entity.HotSellBannerBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.SendGoodsBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Witness on 2019/1/3
 * Describe:
 */
public class HotSellPresenter extends BasePresenterImpl<HotSellContract.View>
                                implements HotSellContract.Presenter{
    @Override
    public void loadActivity(String cinemaId) {
        HttpInterfaceIml.getActivity(cinemaId).subscribe(new Subscriber<ActivityBO>() {
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
            public void onNext(ActivityBO s) {
                if (mView == null)
                    return;

                mView.getActivity(s);
            }
        });
    }

    @Override
    public void loadSendParam(String cinemaId, String appUserId) {
        HttpInterfaceIml.getSendParam(cinemaId,appUserId).subscribe(new Subscriber<SendGoodsBO>() {
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
            public void onNext(SendGoodsBO s) {
                if (mView == null)
                    return;
                mView.getSendParam(s);
            }
        });
    }

    @Override
    public void loadHotSellData(String appUserId,String cinemaCode) {
        HttpInterfaceIml.getHotSell(appUserId,cinemaCode).subscribe(new Subscriber<List<HotSellBO>>() {
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
            public void onNext(List<HotSellBO> s) {
                if (mView == null)
                    return;

                mView.getHotSellData(s);
            }
        });
    }

    @Override
    public void loadBanners(String cinemaCode, String category) {
        HttpInterfaceIml.getBanner(cinemaCode,category).subscribe(new Subscriber<List<HotSellBannerBO>>() {
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
            public void onNext(List<HotSellBannerBO> s) {
                if (mView == null)
                    return;

                mView.getBanners(s);
            }
        });
    }

    @Override
    public void getOrderPrice(String merchandiseinfo,String merTicketId,String cinemaId,String changePayType) {
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

                mView.getOrder(s);
            }
        });
    }
}
