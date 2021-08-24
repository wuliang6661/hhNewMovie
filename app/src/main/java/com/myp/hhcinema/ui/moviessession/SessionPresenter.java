package com.myp.hhcinema.ui.moviessession;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.FavourBO;
import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.entity.SessionBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SessionPresenter extends BasePresenterImpl<SessionContract.View>
        implements SessionContract.Presenter {


    @Override
    public void loadMoviesSession(String cinemaId, String movieId) {
        HttpInterfaceIml.movieSereen(cinemaId, movieId).subscribe(new Subscriber<SessionBO>() {
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
            public void onNext(SessionBO s) {
                if (mView == null)
                    return;
                mView.getData(s.getScreenPlanList());
            }
        });
    }

    @Override
    public void checkOrder() {
        HttpInterfaceIml.orderCheck().subscribe(new Subscriber<OrderNumBO>() {
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
            public void onNext(OrderNumBO orderNumBO) {
                if (mView == null)
                    return;
                mView.getCheckOrder(orderNumBO);
            }
        });
    }

    @Override
    public void orderCancle(String orderNum) {
        HttpInterfaceIml.orderCancle(orderNum).subscribe(new Subscriber<OrderNumBO>() {
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
            public void onNext(OrderNumBO orderNumBO) {
                if (mView == null)
                    return;
                mView.getOrderCancle(orderNumBO);
            }
        });
    }

    @Override
    public void forvoreInfo() {
        HttpInterfaceIml.forvoreinfo().subscribe(new Subscriber<List<FavourBO>>() {
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
            public void onNext(List<FavourBO> s) {
                if (mView == null)
                    return;
                mView.getFoverList(s);
            }
        });
    }
}
