package com.myp.hhcinema.ui.userregister;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View>
        implements RegisterContract.Presenter {

    @Override
    public void loadVersition(String phone,String verifycode) {
        HttpInterfaceIml.userVerification(phone, "register",verifycode).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                if (mView == null)
                    return;
                mView.getVersition(s);
            }
        });
    }

    @Override
    public void loadRegisterUser(String cinemaId,String phone, String password, String version, String sex) {
        HttpInterfaceIml.userRegister(cinemaId,phone, password, version, sex).subscribe(new Subscriber<UserBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(UserBO s) {
                if (mView == null)
                    return;
                mView.getUser(s);
            }
        });
    }
}
