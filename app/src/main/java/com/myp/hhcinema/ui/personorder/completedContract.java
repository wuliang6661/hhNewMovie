package com.myp.hhcinema.ui.personorder;

import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 */

public class completedContract {
    interface View extends BaseRequestView {

        void getOrderList(List<LockSeatsBO> orderList);
    }

    interface Presenter extends BasePresenter<completedContract.View> {

        void loadOrderList(String orderType,String payStatus,int orderPage,String orderSize);
    }
}
