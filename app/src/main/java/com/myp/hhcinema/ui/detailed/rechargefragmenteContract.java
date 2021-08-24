package com.myp.hhcinema.ui.detailed;

import com.myp.hhcinema.entity.RechBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 */

public class rechargefragmenteContract {
    interface View extends BaseRequestView {
        void getRecharge(List<RechBo> rechBo,int pageNo);

    }

    interface Presenter extends BasePresenter<View> {

        void loadRecharge(int pageNo,String cardNum);
    }
}

