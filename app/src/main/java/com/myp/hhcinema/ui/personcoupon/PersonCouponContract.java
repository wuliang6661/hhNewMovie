package com.myp.hhcinema.ui.personcoupon;

import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.CouponNumBo;
import com.myp.hhcinema.entity.UserCouponBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonCouponContract {
    interface View extends BaseRequestView {

        void getCoupon(List<UserCouponBO> couponList);

        void getCanUseNum(CouponNumBo couponNumBo);

    }

    interface Presenter extends BasePresenter<View> {

        void loadPersonCoupon(int pageNo,int pageSize,String cinemaId);

        void loadCanUseNum(String cinemaId);
    }
}
