package com.myp.hhcinema.ui;

import android.os.Bundle;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.BaseWebActivity;
import com.tencent.smtt.sdk.WebView;


/**
 * Description : InfoActivity
 * 隐私政策
 *
 * @author WITNESS
 * @date 2020/8/24
 */
public class InfoActivity extends BaseWebActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("用户协议与隐私政策");
        goBack();
        webView = findViewById(R.id.webview);
        initWebView(webView);
        webView.loadUrl("http://hhyy.happydoit.com/yinsi/yinsi.htm");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }
}