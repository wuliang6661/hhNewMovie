package com.myp.hhcinema.ui.main.hotwire.welfareFragment;

import com.myp.hhcinema.entity.HotWireBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11.
 */

public class welfareContract {
    interface View extends BaseRequestView {

        void getHotWire(List<HotWireBO> hotWireBOs, int flpage, String articleType);
    }

    interface Presenter extends BasePresenter<View> {

        void loadHotWire(String articleType, int flpage, String cinemaId);
    }
}
