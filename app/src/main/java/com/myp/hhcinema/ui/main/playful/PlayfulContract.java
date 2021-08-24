package com.myp.hhcinema.ui.main.playful;

import com.myp.hhcinema.entity.LunBoBO;
import com.myp.hhcinema.entity.ShopBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PlayfulContract {
    interface View extends BaseRequestView {
        void getLunBo(List<LunBoBO> lunBoBOs);

        void getShopList(List<ShopBO> shops);

    }

    interface  Presenter extends BasePresenter<View> {
        void lunboList(String scoce,String cinemaId);
        void loadCreditsShop(String cinemaId, boolean isPageAdd);
    }
}
