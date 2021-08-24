package com.myp.hhcinema.ui.phonecode2two;

import com.myp.hhcinema.entity.threelandingBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * Created by Administrator on 2018/1/14.
 */


public class phonecode2twoContract {
    interface View extends BaseRequestView {
        void getThirdregistBo(threelandingBo thirdregistBo);
    }

    interface Presenter extends BasePresenter<View> {
        void thirdregist(String cinemaId,String mobile,String pwd,String header,String nickname,String gender,String wxUserId,String wbUserId,String qqUserId);
    }
}