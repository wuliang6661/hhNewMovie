package com.myp.hhcinema.ui.userregister;

import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterContract {
    interface View extends BaseRequestView {

        void getVersition(String version);

        void getUser(UserBO user);

    }

    interface Presenter extends BasePresenter<View> {

        void loadVersition(String phone,String verifycode);

        void loadRegisterUser(String cinemaId,String phone, String password, String version, String sex);

    }
}
