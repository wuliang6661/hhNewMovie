package com.myp.hhcinema.ui.foodorderlist;

import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Witness on 2019/1/22
 * Describe:
 */
public class FoodOrderContract {
    interface View extends BaseRequestView {

        void getOrderList(List<LockSeatsBO> orderList);
    }

    interface Presenter extends BasePresenter<View> {

        void loadOrderList(String orderType,String payStatus,int orderPage,String orderSize);
    }
}
