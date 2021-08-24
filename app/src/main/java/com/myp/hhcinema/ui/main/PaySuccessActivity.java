package com.myp.hhcinema.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.base.BaseWebActivity;
import com.myp.hhcinema.base.MyApplication;
import com.tencent.smtt.sdk.WebView;
import butterknife.Bind;

/**
 * Created by Witness on 2019/3/18
 * Describe: 积分商城支付成功H5界面
 */
public class PaySuccessActivity extends BaseWebActivity {

    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;

    String url = HttpInterface.URL + "/api/paySuccess?";

    @Override
    protected int getLayout() {
        return R.layout.fra_playful_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack.setVisibility(View.GONE);
        title.setText("支付成功");

        initWebView(webview);
    }

    @Override
    public void onResume() {
        super.onResume();
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
