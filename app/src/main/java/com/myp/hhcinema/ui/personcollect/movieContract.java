package com.myp.hhcinema.ui.personcollect;

import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/20.
 */

public class movieContract {
    interface View extends BaseRequestView {

        void getCollectList(List<MoviesByCidBO> moviesByCidBOs,int page);
    }

    interface Presenter extends BasePresenter<View> {

        void loadCollectMovie(String appUserId,int zzpage);
    }
}
