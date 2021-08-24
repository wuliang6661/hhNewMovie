package com.myp.hhcinema.ui.hotsellprodect;

import com.myp.hhcinema.entity.ActivityBO;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.HotSellBO;
import com.myp.hhcinema.entity.HotSellBannerBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.SendGoodsBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * Created by Witness on 2019/1/3
 * Describe:
 */
public class HotSellContract {

    interface View extends BaseRequestView {
        void getHotSellData(List<HotSellBO> hotSellBO);

        void getBanners(List<HotSellBannerBO> hotSellBO);

        void getOrder(ProdectBO prodectBO);

        void getActivity(ActivityBO activityBO);

        void getSendParam(SendGoodsBO sendGoodsBO);
    }

    interface Presenter extends BasePresenter<View> {
        void loadHotSellData(String appUserId,String cinemaCode);

        void loadBanners(String cinemaCode, String category);

        void getOrderPrice(String merchandiseinfo, String merTicketId,String cinemaId,String changePayType);

        void loadActivity(String cinemaId);

        void loadSendParam(String cinemaId, String appUserId);
    }
}
