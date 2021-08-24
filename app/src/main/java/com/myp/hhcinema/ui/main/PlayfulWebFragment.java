package com.myp.hhcinema.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.base.BaseWebFragment;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.jpush.MessageEvent;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wuliang on 2017/6/22.
 * <p>
 * 活动H5页面
 */

public class PlayfulWebFragment extends BaseWebFragment {

    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;


    String url = HttpInterface.URL + "/api/Movie/fun?";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_playful_web, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goBack.setVisibility(View.GONE);
        title.setText("活动");

        initWebView(webview);

        if (MyApplication.cinemaBo != null) {
            if (MyApplication.user != null){
                webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId()
                        +"&appUserId"+MyApplication.user.getId());
            }else {
                webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId());
            }
        }else {
            webview.loadUrl(url);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
//        if (MyApplication.cinemaBo != null) {
//            if (MyApplication.user != null){
//                webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId()
//                        +"&appUserId"+MyApplication.user.getId());
//            }else {
//                webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId());
//            }
//        }else {
//            webview.loadUrl(url);
//        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden){
//            if (MyApplication.cinemaBo != null) {
//                if (MyApplication.user != null){
//                    webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId()
//                            +"&appUserId"+MyApplication.user.getId());
//                }else {
//                    webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId());
//                }
//            }else {
//                webview.loadUrl(url);
//            }
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessageType().equals("PlayfulWebFragment")) {
            if (MyApplication.cinemaBo != null) {
                if (MyApplication.user != null){
                    webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId()
                            +"&appUserId"+MyApplication.user.getId());
                }else {
                    webview.loadUrl(url + "cinemaId=" + MyApplication.cinemaBo.getCinemaId());
                }
            }else {
                webview.loadUrl(url);
            }
        }
    }
}
