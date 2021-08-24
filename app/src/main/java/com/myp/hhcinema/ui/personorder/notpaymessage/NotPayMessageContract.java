package com.myp.hhcinema.ui.personorder.notpaymessage;

import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NotPayMessageContract {
    interface View extends BaseRequestView {

        void getOrderCancle(OrderNumBO orderNumBO);
    }

    interface  Presenter extends BasePresenter<View> {
        /**
         * 取消订单
         */
        void orderCancle(String orderNum);
    }
}
