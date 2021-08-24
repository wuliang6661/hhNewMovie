package com.myp.hhcinema.ui.personsetting.personupdate;

import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonUpdateContract {
    interface View extends BaseRequestView {

        void getData(UserBO userBO);

    }

    interface Presenter extends BasePresenter<View> {

        void updatePassWord(String oldPass, String newPass);


        void updateName(String name);

    }
}
