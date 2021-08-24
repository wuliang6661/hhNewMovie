package com.myp.hhcinema.ui.Prizesreading;


import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class PrizesreadingContract {
    interface View extends BaseRequestView {


        void getTaskList(List<HomeTopBean.DataBo> gameBOs,int pageNo);
    }

    interface Presenter extends BasePresenter<View> {


        void loadTaskList(int pageNo);
    }
}
