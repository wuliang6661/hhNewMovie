package com.myp.hhcinema.ui.rechatge;

import android.util.Log;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.RechatBo;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2018/1/27.
 */

public class rechatgePresenter extends BasePresenterImpl<rechatgeContract.View>
        implements rechatgeContract.Presenter {
    @Override
    public void rechatgejine(String cinemaId) {
        HttpInterfaceIml.rechatgejine(cinemaId).subscribe(new Subscriber<List<RechatBo>>() {
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
            public void onNext(List<RechatBo> rechatBos) {
                Log.d("rechatgePresenter", "rechatgePresenter "+rechatBos);
                if (mView == null)
                    return;
                mView.getRechat(rechatBos);

            }



        });
    }




}
