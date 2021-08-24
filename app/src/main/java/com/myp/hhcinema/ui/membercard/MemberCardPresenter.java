package com.myp.hhcinema.ui.membercard;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MemberCardPresenter extends BasePresenterImpl<MemberCardContract.View> implements MemberCardContract.Presenter{
    @Override
    public void loadCardUser(String cinemaId) {
        HttpInterfaceIml.cardUser(cinemaId).subscribe(new Subscriber<List<CardBO>>() {
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
            public void onNext(List<CardBO> s) {
                if (mView == null)
                    return;
                mView.getCardList(s);
            }
        });
    }
}
