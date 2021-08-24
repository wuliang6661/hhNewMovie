package com.myp.hhcinema.ui.selectorcity;

import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.entity.CityBO;
import com.myp.hhcinema.mvp.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SelectorCityPresenter extends BasePresenterImpl<SelectorCityContract.View>
        implements SelectorCityContract.Presenter {

    @Override
    public void loadCityList() {
        HttpInterfaceIml.cityList().subscribe(new Subscriber<List<String>>() {
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
            public void onNext(List<String> s) {
                if (mView != null) {
                    List<CityBO> cityBOs = new ArrayList<CityBO>();
                    for (int i = 0; i < s.size(); i++) {
                        cityBOs.add(new CityBO(s.get(i)));
                    }
                    mView.getCityList(cityBOs);
                }
            }
        });
    }
}
