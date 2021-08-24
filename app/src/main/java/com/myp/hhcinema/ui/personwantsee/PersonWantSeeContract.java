package com.myp.hhcinema.ui.personwantsee;

import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonWantSeeContract {
    interface View extends BaseRequestView {

        void getWantSeeMovie(List<MoviesByCidBO> moviesByCidBOs,int page);
    }

    interface Presenter extends BasePresenter<View> {


        void loadWandSeeMovie(String appUserId, String cinemaId,int page);

    }
}
