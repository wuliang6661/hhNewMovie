package com.myp.hhcinema.ui.membercard;

import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MemberCardContract {
    interface View extends BaseRequestView {
        void getCardList(List<CardBO> cardBOs);
    }

    interface Presenter extends BasePresenter<View> {
        void loadCardUser(String cinemaId);
    }
}
