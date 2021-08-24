package com.myp.hhcinema.ui.personsetting;

import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.io.File;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonSettingContract {
    interface View extends BaseRequestView {

        void getUserImage(UserBO userBO);

        void getUserSex(UserBO userBO);

    }

    interface Presenter extends BasePresenter<View> {

        void updataUserImage(File fileName);

        void updateUserSex(int sex);

    }
}
