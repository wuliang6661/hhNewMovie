package com.myp.hhcinema.mvp;

/**
 * MVPPlugin
 * �wuliang
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}
