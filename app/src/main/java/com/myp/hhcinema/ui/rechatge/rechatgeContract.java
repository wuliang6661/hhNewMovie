package com.myp.hhcinema.ui.rechatge;

import com.myp.hhcinema.entity.RechatBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */

public class rechatgeContract{
    interface View extends BaseRequestView {



        void getRechat(List<RechatBo> rechats);

    }

interface Presenter extends BasePresenter<View> {

    void rechatgejine(String cinemaId);

}
}