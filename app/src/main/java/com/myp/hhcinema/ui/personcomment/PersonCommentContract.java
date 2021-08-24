package com.myp.hhcinema.ui.personcomment;

import com.myp.hhcinema.entity.CommentBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonCommentContract {
    interface View extends BaseRequestView {

        void getCommentList(List<CommentBO> commentBOs ,int page);
    }

    interface Presenter extends BasePresenter<View> {

        void loadPersonComment(String appUserId, int page);
    }
}
