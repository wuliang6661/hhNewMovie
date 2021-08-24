package com.myp.hhcinema.ui.main.home.nextmovies;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.MoviesSoonBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NextMoviesPresenter extends BasePresenterImpl<NextMoviesContract.View>
        implements NextMoviesContract.Presenter {


    @Override
    public void loadMoviesSoon(String cinemaId) {
        HttpInterfaceIml.moviesSoon(cinemaId).subscribe(new Subscriber<List<MoviesSoonBO>>() {
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
            public void onNext(List<MoviesSoonBO> s) {
                if (mView == null)
                    return;
                mView.getMoviesSoon(s);
            }
        });
    }
}
