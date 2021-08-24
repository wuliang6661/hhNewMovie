package com.myp.hhcinema.ui.main.home.movieslist;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.LunBoAndBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoviesListPresenter extends BasePresenterImpl<MoviesListContract.View>
        implements MoviesListContract.Presenter {


    @Override
    public void moviesHot(String cinemaId) {
        HttpInterfaceIml.moviesHot(cinemaId).subscribe(new Subscriber<List<MoviesByCidBO>>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(List<MoviesByCidBO> s) {
                if (mView == null)
                    return;

                mView.getMoviesHot(s);
            }
        });
    }

    @Override
    public void lunboList(String souce, String cinemaId) {
        HttpInterfaceIml.lunboandList(souce, cinemaId).subscribe(new Subscriber<LunBoAndBO>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(LunBoAndBO lunBoAndBOs) {
                if (mView == null)
                    return;
                mView.getLunBo(lunBoAndBOs);
            }
        });
    }
}
