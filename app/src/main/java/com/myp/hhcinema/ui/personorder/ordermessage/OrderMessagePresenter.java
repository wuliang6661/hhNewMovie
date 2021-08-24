package com.myp.hhcinema.ui.personorder.ordermessage;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.OrderDetailBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderMessagePresenter extends BasePresenterImpl<OrderMessageContract.View> implements OrderMessageContract.Presenter{

    @Override
    public void quryOrderMessage(String id, String cinemaId) {
        HttpInterfaceIml.orderMessage(id,cinemaId).subscribe(new Subscriber<OrderDetailBO>() {
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
            public void onNext(OrderDetailBO order) {
                if (mView == null)
                    return;
                mView.getOrderMessage(order);
            }
        });
    }
}
