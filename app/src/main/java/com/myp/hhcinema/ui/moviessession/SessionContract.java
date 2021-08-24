package com.myp.hhcinema.ui.moviessession;

import com.myp.hhcinema.entity.FavourBO;
import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.entity.SessionBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SessionContract {
    interface View extends BaseRequestView {

        void getData(List<SessionBO.ScreenPlanListBo> sessionBOs);

        void getCheckOrder(OrderNumBO orderNumBO);

        void getOrderCancle(OrderNumBO orderNumBO);

        void getFoverList(List<FavourBO> favourBOs);

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取场次信息
         */
        void loadMoviesSession(String cinemaId, String movieId);

        /**
         * 检测是否有未完成订单
         */
        void checkOrder();

        /**
         * 取消订单
         */
        void orderCancle(String orderNum);

        /**
         * 获取优惠信息
         */
        void forvoreInfo();

    }
}
