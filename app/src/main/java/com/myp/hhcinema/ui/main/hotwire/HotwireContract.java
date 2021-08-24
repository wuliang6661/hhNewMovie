package com.myp.hhcinema.ui.main.hotwire;

import com.myp.hhcinema.entity.HotWireBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HotwireContract {
    interface View extends BaseRequestView {

        void getHotWire(List<HotWireBO> hotWireBOs,int flpage,String articleType);
    }

    interface Presenter extends BasePresenter<View> {

        void loadHotWire(String articleType, int flpage, String cinemaId);
    }
}
