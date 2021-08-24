package com.myp.hhcinema.ui.applicationforrefund;

import com.myp.hhcinema.entity.RefundBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * Created by Administrator on 2018/2/5.
 */

public class tuipiaoshenqingContract {
        interface View extends BaseRequestView {
            void getrefundinfo(RefundBO refundBO);
            void getrefund(RefundBO orderMessage);

        }

        interface Presenter extends BasePresenter<View> {

            void refundinfo(String id );
            void refund(String id ,String cinemaId);

        }
    }

