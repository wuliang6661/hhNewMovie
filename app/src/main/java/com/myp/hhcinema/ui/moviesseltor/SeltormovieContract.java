package com.myp.hhcinema.ui.moviesseltor;

import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SeltormovieContract {
    interface View extends BaseRequestView {

        void onData(List<CinemaBo> cinemaIdBOs);

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 获取影厅列表
         */
        void loadCinemaIds(String city,String location,String lacation);
    }
}
