package com.myp.hhcinema.ui.personread.persongrade;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonGradePresenter extends BasePresenterImpl<PersonGradeContract.View>
        implements PersonGradeContract.Presenter {

    @Override
    public void loadSubmitComment(String cinemaId,String appId, String movieId, String souce, String comment) {
        HttpInterfaceIml.moviesSunmitCom(cinemaId,appId, movieId, souce, comment).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.onRequestEnd();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null) {
                    return;
                }
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(Object s) {
                if (mView != null) {
                    mView.submitSurcess();
                }
            }
        });
    }
}
