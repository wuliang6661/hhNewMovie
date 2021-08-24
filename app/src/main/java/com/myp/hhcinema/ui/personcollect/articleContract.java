package com.myp.hhcinema.ui.personcollect;

import com.myp.hhcinema.entity.HotWireBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/20.
 */

public class articleContract {
    interface View extends BaseRequestView {

        void getCollection(List<HotWireBO> hotWireBOs, int pageNo);
    }

    interface Presenter extends BasePresenter<articleContract.View> {

        void loadCollection(int pageNo);
    }
}
