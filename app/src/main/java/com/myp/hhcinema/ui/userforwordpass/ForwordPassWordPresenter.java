package com.myp.hhcinema.ui.userforwordpass;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ForwordPassWordPresenter extends BasePresenterImpl<ForwordPassWordContract.View>
        implements ForwordPassWordContract.Presenter {

    @Override
    public void loadForWordPassWord(String passWord, String pwd) {
        HttpInterfaceIml.userUpdatePass(passWord, pwd).subscribe(new Subscriber<UserBO>() {
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
            public void onNext(UserBO userBO) {
                if (mView == null)
                    return;
                mView.getUserBo(userBO);
            }
        });
    }
}
