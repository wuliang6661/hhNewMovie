package com.myp.hhcinema.ui.main;


import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    @Override
    public void loadCinemaIds(String city, String location, String lacation) {
        HttpInterfaceIml.cinemaList(city, location, lacation).subscribe(new Subscriber<List<CinemaBo>>() {
            @Override
            public void onCompleted() {
                if (mView == null)
                    return;
            }

            @Override
            public void onError(Throwable e) {
                if (mView == null)
                    return;
//                mView.onRequestError(e.getMessage());
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