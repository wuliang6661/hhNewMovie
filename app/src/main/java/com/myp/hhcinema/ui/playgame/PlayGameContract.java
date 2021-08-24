package com.myp.hhcinema.ui.playgame;

import com.myp.hhcinema.entity.GameBO;
import com.myp.hhcinema.entity.LunBoBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PlayGameContract {
    interface View extends BaseRequestView {

        void getLunBo(List<LunBoBO> lunBoBOs);

        void getGameList(List<GameBO> gameBOs);
    }

    interface Presenter extends BasePresenter<View> {

        void lunboList(String scoce, String cinemaId);

        void loadGameList(int pageNo);
    }
}
