package com.myp.hhcinema.ui.main.home.movieslist;

import com.myp.hhcinema.entity.LunBoAndBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoviesListContract {
    interface View extends BaseRequestView {

        void getMoviesHot(List<MoviesByCidBO> moviesHotBO);

        void getLunBo(LunBoAndBO lunBoAndBOs);

    }

    interface Presenter extends BasePresenter<View> {

        void moviesHot(String cinemaId);

        void lunboList(String scoce,String cinemaId);
    }
}
