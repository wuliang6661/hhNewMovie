package com.myp.hhcinema.ui.personwantsee;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonWantSeePresenter extends BasePresenterImpl<PersonWantSeeContract.View>
        implements PersonWantSeeContract.Presenter {

    @Override
    public void loadWandSeeMovie(String appUserId, String cinemaId, final int page) {
        HttpInterfaceIml.personWantSeeList(appUserId,cinemaId,page +"").subscribe(new Subscriber<List<MoviesByCidBO>>() {
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
            public void onNext(List<MoviesByCidBO> moviesByCidBOs) {
                if (mView != null) {
                    mView.getWantSeeMovie(moviesByCidBOs,page);
                }
            }
        });
    }
}
