package com.myp.hhcinema.ui.Prizesreading;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2018/1/24.
 */

public class PrizesreadingPresenter extends BasePresenterImpl<PrizesreadingContract.View>
        implements PrizesreadingContract.Presenter {

    private Object data;
    private ArrayList<HomeTopBean> list;


    @Override
    public void loadTaskList(final int pageNo) {
        HttpInterfaceIml.getList(pageNo+"").subscribe(new Subscriber<HomeTopBean>() {
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
            public void onNext(HomeTopBean s) {
                if (mView != null) {
                    mView.getTaskList(s.getData(),pageNo);
                }
            }
        });
    }
}

