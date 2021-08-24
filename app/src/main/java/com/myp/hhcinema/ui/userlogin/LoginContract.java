package com.myp.hhcinema.ui.userlogin;

import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.entity.threelandingBo;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * Wuliang
 * <p>
 * 登陆页面的业务控制
 */

public class LoginContract {

    interface View extends BaseRequestView {

        void getUser(UserBO data);
        void getUserid(threelandingBo data,int style);

    }

    interface Presenter extends BasePresenter<View> {
        void loadUserLogin(String phone, String password);
        void userLoginid(String wxUserId,String wbUserId,String qqUserId,int style);


    }
}
