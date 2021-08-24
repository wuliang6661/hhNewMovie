package com.myp.hhcinema.ui.detailed;

import com.myp.hhcinema.entity.SumptionBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 */

public class onsumptiondetailContract {
    interface View extends BaseRequestView {
        void getcosumption(List<SumptionBo> sumptionBo,int pageNo);

    }

    interface Presenter extends BasePresenter<View> {

        void loadcosumption(int pageNo,String cardNum);
    }
}

