package com.myp.hhcinema.ui.moviesseltor;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SeltormoviePresenter extends BasePresenterImpl<SeltormovieContract.View>
        implements SeltormovieContract.Presenter {


    @Override
    public void loadCinemaIds(String city, String location, String lacation) {
        HttpInterfaceIml.cinemaList(city, location, lacation).subscribe(new Subscriber<List<CinemaBo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(List<CinemaBo> s) {
                if (mView == null)
                    return;
                mView.onData(s);
            }
        });
    }
}
