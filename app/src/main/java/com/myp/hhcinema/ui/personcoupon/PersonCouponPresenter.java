package com.myp.hhcinema.ui.personcoupon;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.CouponNumBo;
import com.myp.hhcinema.entity.UserCouponBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonCouponPresenter extends BasePresenterImpl<PersonCouponContract.View>
        implements PersonCouponContract.Presenter {

    @Override
    public void loadPersonCoupon(int pageNo, int pageSize,String cinemaId) {
        HttpInterfaceIml.personCoupon(pageNo,pageSize,cinemaId).subscribe(new Subscriber<List<UserCouponBO>>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(List<UserCouponBO> s) {
                if (mView == null)
                    return;
                mView.getCoupon(s);
            }
        });
    }

    @Override
    public void loadCanUseNum(String cinemaId) {
        HttpInterfaceIml.personCouponNum(cinemaId).subscribe(new Subscriber<CouponNumBo>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(CouponNumBo s) {
                if (mView == null)
                    return;
                mView.getCanUseNum(s);
            }
        });
    }
}
