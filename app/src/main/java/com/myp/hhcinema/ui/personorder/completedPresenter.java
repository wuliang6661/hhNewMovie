package com.myp.hhcinema.ui.personorder;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2018/2/11.
 */

public class completedPresenter extends BasePresenterImpl<completedContract.View> implements completedContract.Presenter{

    @Override
    public void loadOrderList(String orderType,String payStatus,int orderPage,String orderSize) {
        HttpInterfaceIml.orderList(orderType, payStatus,orderPage,orderSize).subscribe(new Subscriber<List<LockSeatsBO>>() {
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
            public void onNext(List<LockSeatsBO> s) {
                if (mView == null)
                    return;
                mView.getOrderList(s);
            }
        });
    }
}

