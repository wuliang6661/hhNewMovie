package com.myp.hhcinema.ui.personread;

import com.myp.hhcinema.entity.CommentBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonReadContract {
    interface View extends BaseRequestView {

        void getReadList(List<CommentBO> commentBOs,int page);
    }

    interface Presenter extends BasePresenter<View> {

        void loadPersonReadList(String appUserId, int page);
    }
}
