package com.myp.hhcinema.widget.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.StringUtils;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by wuliang on 2017/4/11.
 * <p>
 * 从名字上不难理解，这个类就像WebView的委托人一样，
 * 是帮助WebView处理各种通知和请求事件的，我们可以称他为WebView的“内政大臣”。
 */

public class WebClient extends WebViewClient {

    private Context mContext;
    private SVProgressHUD svProgressHUD;

    public WebClient(Context context) {
        mContext = context;
        svProgressHUD = new SVProgressHUD(context);
    }


    /**
     * 该方法在加载页面资源时会回调，每一个资源（比如图片）的加载都会调用一次。
     *
     * @param view
     * @param url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * 该方法在WebView开始加载页面且仅在Main frame loading（即整页加载）时回调，
     * 一次Main frame的加载只会回调该方法一次。我们可以在这个方法里设定开启一个加载的动画，告诉用户程序在等待网络的响应。
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        svProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
        super.onPageStarted(view, url, favicon);
    }


    /**
     * 该方法只在WebView完成一个页面加载时调用一次（同样也只在Main frame loading时调用），
     * 我们可以可以在此时关闭加载动画，进行其他操作。
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        if (svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
        super.onPageFinished(view, url);
    }

    /***
     * 该方法在web页面加载错误时回调，这些错误通常都是由无法与服务器正常连接引起的，最常见的就是网络问题。
     * 这个方法有两个地方需要注意：
     * 1.这个方法只在与服务器无法正常连接时调用，类似于服务器返回错误码的那种错误（即HTTP ERROR），
     * 该方法是不会回调的，因为你已经和服务器正常连接上了（全怪官方文档(︶^︶)）；
     * 2.这个方法是新版本的onReceivedError()方法，
     * 从API23开始引进，与旧方法onReceivedError(WebView view,int errorCode,String description,String failingUrl)
     * 不同的是，新方法在页面局部加载发生错误时也会被调用（比如页面里两个子Tab或者一张图片）。
     * 这就意味着该方法的调用频率可能会更加频繁，所以我们应该在该方法里执行尽量少的操作。
     *
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        LogUtils.E(error.getDescription() + "网页加载局部报错");
        super.onReceivedError(view, request, error);
    }


    /**
     * 上一个方法提到onReceivedError并不会在服务器返回错误码时被回调，
     * 那么当我们需要捕捉HTTP ERROR并进行相应操作时应该怎么办呢？API23便引入了该方法。
     * 当服务器返回一个HTTP ERROR并且它的status code>=400时，该方法便会回调。
     * 这个方法的作用域并不局限于Main Frame，任何资源的加载引发HTTP ERROR都会引起该方法的回调，
     * 所以我们也应该在该方法里执行尽量少的操作，只进行非常必要的错误处理等。
     *
     * @param view
     * @param request
     * @param errorResponse
     */
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        LogUtils.E(errorResponse.getStatusCode() + "网页加载报错" + errorResponse.getResponseHeaders());
//        position++;
//        if (position <= 3) {   //3次之内可以刷新,防止资源没加载出来
//            view.reload();
//        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

//    int position = 0;

    /**
     * 当WebView加载某个资源引发SSL错误时会回调该方法，这时WebView要么执行handler.cancel()取消加载，
     * 要么执行handler.proceed()方法继续加载（默认为cancel）
     * 。需要注意的是，这个决定可能会被保留并在将来再次遇到SSL错误时执行同样的操作。
     */
    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
        sslErrorHandler.proceed();  // 接受所有网站的证书
//        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    /**
     * 当WebView需要请求某个数据时，
     * 这个方法可以拦截该请求来告知app并且允许app本身返回一个数据来替代我们原本要加载的数据。
     * 比如你对web的某个js做了本地缓存，希望在加载该js时不再去请求服务器而是可以直接读取本地缓存的js，
     * 这个方法就可以帮助你完成这个需求。你可以写一些逻辑检测这个request，并返回相应的数据，
     * 你返回的数据就会被WebView使用，如果你返回null，WebView会继续向服务器请求。
     *
     * @param view
     * @param request
     * @return
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        syncCookie(request.getUrl().toString());   //同步各个H5的session
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        syncCookie(url);
        return super.shouldInterceptRequest(view, url);
    }

    /**
     * 哈~ 终于到了这个方法，在最开始的基础演示时我们用到了这个方法。
     * 从实践中我们知道，当我们没有给WebView提供WebViewClient时，
     * WebView如果要加载一个url会向ActivityManager寻求一个适合的处理者来加载该url（比如系统自带的浏览器），
     * 这通常是我们不想看到的。于是我们需要给WebView提供一个WebViewClient，
     * 并重写该方法返回true来告知WebView url的加载就在app中进行。这时便可以实现在app内访问网页。
     *
     * @param view
     * @param request
     * @return
     */
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.toString());
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String request) {
//        view.loadUrl(request);
        return false;
    }

    /**
     * 当WebView得页面Scale值发生改变时回调。
     *
     * @param view
     * @param oldScale
     * @param newScale
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    /**
     * 默认值为false，重写此方法并return true可以让我们在WebView内处理按键事件。
     *
     * @param view
     * @param event
     * @return
     */
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }
    private void syncCookie(String url) {
        if (StringUtils.isEmpty(MyApplication.SESSIONID)) {
            return;
        }
        try {
            CookieSyncManager.createInstance(mContext.getApplicationContext());//创建一个cookie管理器
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();// 移除以前的cookie
            cookieManager.removeAllCookie();
            cookieManager.setAcceptCookie(true);
            StringBuilder sbCookie = new StringBuilder();//创建一个拼接cookie的容器,为什么这么拼接，大家查阅一下http头Cookie的结构
            sbCookie.append("SESSION=" + MyApplication.SESSIONID);//拼接sessionId
            String cookieValue = sbCookie.toString();
            Log.d("webviewwebviewwebview", "weweweeeeeeeeeeeeeeeeee: "+cookieValue);
            cookieManager.setCookie(url, cookieValue);//为url设置cookie
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush();
            } else {
                CookieSyncManager.getInstance().sync();
            }
        } catch (Exception e) {
            LogUtils.E("Session加载出错!");
            e.printStackTrace();
        }
    }
}
