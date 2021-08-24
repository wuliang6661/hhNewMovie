package com.myp.hhcinema.ui.main.member;

import com.myp.hhcinema.entity.NewMenberNum;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MemberContract {
    interface View extends BaseRequestView {

        void getUser(UserBO userBO);

        void getNum(NewMenberNum newMenberNum);
    }

    interface Presenter extends BasePresenter<View> {

        void loadMemberRecord(String appUserId);

        void loadNum(String appUserId);
    }
}
