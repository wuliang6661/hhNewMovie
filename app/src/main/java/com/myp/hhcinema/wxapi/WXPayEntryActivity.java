package com.myp.hhcinema.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.hotsellprodect.HotSellActivity;
import com.myp.hhcinema.ui.main.PaySuccessActivity;
import com.myp.hhcinema.ui.moviessession.SessionActivity;
import com.myp.hhcinema.ui.paysuccess.PaysuccessActivity;
import com.myp.hhcinema.ui.orderconfrim.OrderSurcessActivity;
import com.myp.hhcinema.ui.prodectorder.ProdectOrderSuccess;
import com.myp.hhcinema.util.LogUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wuliang on 2017/3/6.
 * <p>
 * 微信支付回调的activity类
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "com.myp.meiyuan.wxapi.WXPayEntryActivity";

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
        api = WXAPIFactory.createWXAPI(this, LocalConfiguration.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
        api.registerApp(LocalConfiguration.WEIXIN_APP_ID);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {

    }


    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;
        if (errCode == 0) {
            // 0成功 展示成功页面
            if(LocalConfiguration.isVoucher==1){
                Bundle bundle = new Bundle();
                bundle.putString("cardcode", LocalConfiguration.cardcode);
                bundle.putString("shouye", LocalConfiguration.isShouye);
                Intent intent = new Intent(this, PaysuccessActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                LocalConfiguration.isVoucher=-1;
            }else if (LocalConfiguration.isVoucher == 2){
                EventBus.getDefault().post(new MessageEvent("success", "yes"));//发送给BindCard
                LocalConfiguration.isVoucher=-1;
                finish();
            }else if (LocalConfiguration.isVoucher == 3){//积分商城H5支付
                Toast.makeText(this, "支付成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,PaySuccessActivity.class);
                startActivity(intent);
                LocalConfiguration.isVoucher=-1;
                finish();
            }else {
                if (LocalConfiguration.ordertype == 0) {//电影票订单
                    Bundle bundle = new Bundle();
                    bundle.putString("tag","ticket");
                    bundle.putString("order", LocalConfiguration.orderNum);
                    Intent intent = new Intent(this, OrderSurcessActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else {//食品订单
                    Bundle bundle = new Bundle();
                    bundle.putString("tag","prodect");
                    bundle.putString("order", LocalConfiguration.orderNum);
                    Intent intent = new Intent(this, OrderSurcessActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }

        } else if (errCode == -1) {
            //-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//            LogUtils.showToast("支付失败！");
            if (LocalConfiguration.ordertype == 0) {//电影票订单
                EventBus.getDefault().post(new MessageEvent("SeatTebleActivity", "yes"));//发给SeatTebleActivity
                EventBus.getDefault().post(new MessageEvent("ConfrimOrderActivity", "yes"));//发给ConfrimOrderActivity
                finish();
            }else if (LocalConfiguration.ordertype == 1){//食品订单
                EventBus.getDefault().post(new MessageEvent("ProdectOrderActivity", "yes"));//发给ProdectOrderActivity
                finish();
            }else {
                Toast.makeText(this, "支付失败！", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (errCode == -2) {
            //-2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
//            LogUtils.showToast("取消支付！");
            if (LocalConfiguration.ordertype == 0) {//电影票订单
                EventBus.getDefault().post(new MessageEvent("SeatTebleActivity", "yes"));//发给SessionActivity
                EventBus.getDefault().post(new MessageEvent("ConfrimOrderActivity", "yes"));//发给ConfrimOrderActivity
                finish();
            }else if (LocalConfiguration.ordertype == 1){//食品订单
                EventBus.getDefault().post(new MessageEvent("ProdectOrderActivity", "yes"));//发给ProdectOrderActivity
                finish();
            }else {
                Toast.makeText(this, "取消支付！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}