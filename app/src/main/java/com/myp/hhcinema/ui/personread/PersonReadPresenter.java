package com.myp.hhcinema.ui.personread;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CommentBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonReadPresenter extends BasePresenterImpl<PersonReadContract.View>
        implements PersonReadContract.Presenter {

//    int page = 1;

    @Override
    public void loadPersonReadList(String appUserId, final int page) {
//        if (isPageAdd) {
//            page++;
//        } else {
//            page = 1;
//        }
        HttpInterfaceIml.personReadList(appUserId, page + "").subscribe(new Subscriber<List<CommentBO>>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.onRequestEnd();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.onRequestError(e.getMessage());
                }
            }

            @Override
            public void onNext(List<CommentBO> s) {
                if (mView != null) {
                    mView.getReadList(s,page);
                }
            }
        });
    }
}
