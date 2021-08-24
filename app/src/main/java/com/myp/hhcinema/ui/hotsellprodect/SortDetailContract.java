package com.myp.hhcinema.ui.hotsellprodect;

import com.myp.hhcinema.entity.FeedBackListBO;
import com.myp.hhcinema.mvp.BasePresenter;
import com.myp.hhcinema.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SortDetailContract {
    interface View extends BaseRequestView {
        void getSortDetailListBO(List<FeedBackListBO> feedBackListBOs);
    }

    interface Presenter extends BasePresenter<View> {
        void loadSortDetail();
    }
}
