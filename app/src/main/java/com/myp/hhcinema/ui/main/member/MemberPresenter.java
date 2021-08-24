package com.myp.hhcinema.ui.main.member;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.NewMenberNum;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MemberPresenter extends BasePresenterImpl<MemberContract.View>
        implements MemberContract.Presenter {

    @Override
    public void loadMemberRecord(String appUserId) {
        HttpInterfaceIml.memberRecord(appUserId).subscribe(new Subscriber<UserBO>() {
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
            public void onNext(UserBO s) {
                if (mView != null) {
                    mView.getUser(s);
                }
            }
        });
    }

    @Override
    public void loadNum(String appUserId) {
        HttpInterfaceIml.memberNewRecords(appUserId).subscribe(new Subscriber<NewMenberNum>() {
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
            public void onNext(NewMenberNum s) {
                if (mView != null) {
                    mView.getNum(s);
                }
            }
        });
    }
}
