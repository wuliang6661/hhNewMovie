package com.myp.hhcinema.ui.personorder.notpaymessage;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NotPayMessagePresenter extends BasePresenterImpl<NotPayMessageContract.View>
        implements NotPayMessageContract.Presenter {


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
}
