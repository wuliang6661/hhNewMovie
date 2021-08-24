package com.myp.hhcinema.ui.selectorcity;

import com.myp.hhcinema.entity.CityBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SelectorCityContract {
    interface View extends BaseRequestView {

        void getCityList(List<CityBO> cityBOs);
    }

    interface Presenter extends BasePresenter<View> {


        void loadCityList();

    }
}
