package com.myp.hhcinema.ui.personread.persongrade;

import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonGradeContract {
    interface View extends BaseRequestView {

        void submitSurcess();
    }

    interface Presenter extends BasePresenter<View> {

        void loadSubmitComment(String cinemaId,String appId, String movieId, String souce, String comment);
    }
}
