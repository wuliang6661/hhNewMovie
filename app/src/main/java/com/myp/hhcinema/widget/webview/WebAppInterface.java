package com.myp.hhcinema.widget.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.PayResult;
import com.myp.hhcinema.entity.ShareBO;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.Prizesreading.Prizesreading;
import com.myp.hhcinema.ui.WebViewActivity;
import com.myp.hhcinema.ui.main.PaySuccessActivity;
import com.myp.hhcinema.ui.orderconfrim.OrderSurcessActivity;
import com.myp.hhcinema.ui.playcredits.PlayCreditsActivity;
import com.myp.hhcinema.ui.playgame.PlayGameActivity;
import com.myp.hhcinema.ui.userlogin.LoginActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.widget.ShareDialog;
import com.myp.hhcinema.wxapi.WXUtils;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import rx.Subscriber;

/**
 * Created by wuliang on 2017/4/11.
 * <p>
 * 此处存放所有Html页面js调用的方法
 */

public class WebAppInterface {

    private Activity mContext;
    private WebView webView;

    public WebAppInterface(Activity context, WebView webView) {
        mContext = context;
        this.webView = webView;
    }

    /**
     * 此方法为例
     */
    @JavascriptInterface
    public void showToast(String message) {
        LogUtils.showToast(message);
    }


    @JavascriptInterface
    public void logDebug(String logo) {
        LogUtils.I(logo);
    }

    /**
     * 打开一个新窗口
     */
    @JavascriptInterface
    public void newWindow(String url) {
        if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {  //未登录进入登陆
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivityForResult(intent, 1);
        } else if (MyApplication.isLogin == ConditionEnum.LOGIN) {   //已登录正式跳转
            if ("game".equals(url)) {    //跳转游戏页面
                Intent intent = new Intent(mContext, PlayGameActivity.class);
                mContext.startActivity(intent);
            } else if ("goods".equals(url)) {   //积分兑换
                Intent intent = new Intent(mContext, PlayCreditsActivity.class);
                mContext.startActivity(intent);
            }else if ("readTask".equals(url)) {   //有奖阅读Prizes for reading
                Log.d("进来了","有奖阅读");
                Intent intent = new Intent(mContext, Prizesreading.class);
                mContext.startActivity(intent);
            }else if ("mall".equals(url)){
                EventBus.getDefault().post(new MessageEvent("showstore", "yes"));
            } else {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                if (url.contains("http")){
                    intent.putExtra("url",  url);
                }else {
                    intent.putExtra("url", HttpInterface.URL + url);
                }
                Log.i("hwhwhwhwhwhwhw",url+"");
                mContext.startActivity(intent);
            }
        }
    }

    @JavascriptInterface
    public void goToMall(){
        if (MyApplication.isLogin == ConditionEnum.LOGIN) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("title","积分商城");
            if (MyApplication.cinemaBo != null) {
                intent.putExtra("url", HttpInterface.URL + "/api/pointsmall/change?appUserId=" + MyApplication.user.getId() + "&cinemaId=" +
                        MyApplication.cinemaBo.getCinemaId());
            }else {
                intent.putExtra("url", HttpInterface.URL + "/api/pointsmall/change?appUserId=" + MyApplication.user.getId());
            }
            mContext.startActivity(intent);
        }else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void newAlert(String msg) {
        show(msg);

    }
    private void show(String mag) {
        LayoutInflater factory = LayoutInflater.from(mContext);//提示框
        final View view = factory.inflate(R.layout.dialog_layout, null);//这里必须是final的
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        TextView texttt = (TextView) view.findViewById(R.id.texttt);
        texttt.setText(mag);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
        WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
    }
    /**
     * 分享
     */
    @JavascriptInterface
    public void setShare(String title, String content, String logo, String url) {
        ShareBO shareBO = new ShareBO(content, logo, url, title);
        Message message = new Message();
        message.obj = shareBO;
        message.what = 0x11;
        handler.sendMessage(message);
    }

    /**
     * 活动影城资讯分享
     */
    @JavascriptInterface
    public void shareArticle(String title, String description, String pic, String url,long id) {
        ShareBO shareBO = new ShareBO(description, pic, url, title);
        Message message = new Message();
        message.obj = shareBO;
        message.what = 0x11;
        handler.sendMessage(message);
    }


    /**
     * 约人看电影
     */
    @JavascriptInterface
    public void dating(String url) {
        LogUtils.showToast("敬请期待！");
    }

    /**
     * 支付
     */
    @JavascriptInterface
    public void integralPay (int payType,String orderNum) {
        if (payType == 1){//支付宝
            aliPay(orderNum);
        }else {
            wechatPay(orderNum);
        }
    }

    /**
     * 用户未登陆，去登陆
     */
    @JavascriptInterface
    public void goLogin(String str) {
        Message message = new Message();
        message.obj = str;
        message.what = 0x22;
        handler.sendMessage(message);
    }


    int i = 0;

    /**
     * 提示弹窗
     */
    @JavascriptInterface
    public void showAlert(String str) {
        i++;
        LogUtils.I("提示弹窗！" + i + "次");
        Message message = new Message();
        message.obj = str;
        message.what = 0x33;
        handler.sendMessage(message);
    }


    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void finishWeb() {
        mContext.finish();
    }


    private AlertView alertView;

    /**
     * 处理界面变化
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:    //分享
                    ShareBO shareBO = (ShareBO) msg.obj;
                    new ShareDialog(mContext, shareBO).showAtLocation(webView, Gravity.BOTTOM, 0, 0);
                    break;
                case 0x22:  //去登陆
                    String message = (String) msg.obj;
                    new AlertView("提示", message, null, new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivityForResult(intent, 1);
                        }
                    }).show();
                    break;
                case 0x33:  //提示弹窗显示
                    String str01 = (String) msg.obj;
                    if (alertView != null && alertView.isShowing()) {
                        return;
                    }
                    alertView = new AlertView("提示", str01, null, null, null, mContext, AlertView.Style.Alert, null);
                    alertView.setCancelable(true);
                    alertView.show();
                    break;
            }
        }
    };

    /**
     * 支付宝支付
     */
    private void aliPay(String orderNum){
        HttpInterfaceIml.payInterAlipay(orderNum).subscribe(new Subscriber<PayBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(PayBO s) {
                payAliPay(s.getAlipay());
            }
        });
    }

    /**
     * 微信支付
     */
    private void wechatPay(String orderNum){
        HttpInterfaceIml.payInterWechat(orderNum).subscribe(new Subscriber<WXPayBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(WXPayBO s) {
                WXUtils.payWX(s);
                LocalConfiguration.isVoucher = 3;
            }
        });
    }


    /**
     * 支付宝调起操作
     */
    private void payAliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 0x11;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝返回结果处理
     * <p>
     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                Intent intent = new Intent(mContext,PaySuccessActivity.class);
                mContext.startActivity(intent);
            } else {
                LogUtils.showToast("支付失败");
            }
        }
    };
}
