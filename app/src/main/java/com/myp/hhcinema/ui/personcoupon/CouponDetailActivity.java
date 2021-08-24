package com.myp.hhcinema.ui.personcoupon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CouponDetailBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.util.AppManager;
import com.myp.hhcinema.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by witness on 2018/12/3.
 *  优惠券详情
 */

public class CouponDetailActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.youhui_type)
    TextView youhui_type;
    @Bind(R.id.txtRange)
    TextView txtRange;
    @Bind(R.id.youhui_time)
    TextView youhui_time;
    @Bind(R.id.txtNumYes)
    TextView txtNumYes;
    @Bind(R.id.movieRange)
    TextView movieRange;
    @Bind(R.id.rlGoMain)
    RelativeLayout rlGoMain;
    @Bind(R.id.ivMoney)
    ImageView ivMoney;
    @Bind(R.id.youhui_price)
    TextView youhui_price;
    @Bind(R.id.decribe)
    TextView decribe;
    @Bind(R.id.ivState)
    ImageView ivState;

    private String id;
    @Override
    protected int getLayout() {
        return R.layout.coupon_detail_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("我的优惠券");
        rlGoMain.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        showProgress("加载中");
        if (MyApplication.cinemaBo != null) {
            getMessage(id,MyApplication.cinemaBo.getCinemaId());
        }else {
            getMessage(id,"");
        }

    }


    private void getMessage(String id,String cinemaId){
        HttpInterfaceIml.personCouponDetail(id,cinemaId).subscribe(new Subscriber<CouponDetailBO>() {
            @Override
            public void onCompleted() {
                stopProgress();
            }

            @Override
            public void onError(Throwable e) {
                stopProgress();
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(CouponDetailBO s) {
                stopProgress();
                setData(s);
            }
        });
    }

    private void setData(CouponDetailBO couponDetailBO){
        if (couponDetailBO.getStatus() == 1){//0 可用  1 已使用  2 已过期
            ivState.setVisibility(View.VISIBLE);
            Glide.with(CouponDetailActivity.this).load(R.mipmap.coupon_used).into(ivState);
            txtNumYes.setTextColor(Color.parseColor("#BBBABA"));
            youhui_price.setTextColor(Color.parseColor("#BBBABA"));
            rlGoMain.setVisibility(View.GONE);
            if (couponDetailBO.getDxPlatTicket().getTicketType() == 3){
                Glide.with(CouponDetailActivity.this)
                        .load(R.mipmap.see_coupon_no)
                        .into(ivMoney);
                txtNumYes.setText("1");
                youhui_price.setText("张");
            }else {
                if (couponDetailBO.getDxPlatTicket().getPlatformType() == 2){
                    Glide.with(CouponDetailActivity.this)
                            .load(R.mipmap.see_coupon_no)
                            .into(ivMoney);
                    txtNumYes.setText("1");
                    youhui_price.setText("张");
                }else {
                    if (couponDetailBO.getDxPlatTicket().getName().contains("卖品")){
                        Glide.with(CouponDetailActivity.this)
                                .load(R.mipmap.prodectcoupon_no)
                                .into(ivMoney);
                    }else {
                        Glide.with(CouponDetailActivity.this)
                                .load(R.mipmap.money_coupon_no)
                                .into(ivMoney);
                    }
                    txtNumYes.setText(String.valueOf(couponDetailBO.getDxPlatTicket().getAmount()));
                    youhui_price.setText("元");
                }
            }
        }else if (couponDetailBO.getStatus() == 2){
            ivState.setVisibility(View.VISIBLE);
            Glide.with(CouponDetailActivity.this).load(R.mipmap.coupon_outtime).into(ivState);
            txtNumYes.setTextColor(Color.parseColor("#BBBABA"));
            youhui_price.setTextColor(Color.parseColor("#BBBABA"));
            rlGoMain.setVisibility(View.GONE);
            if (couponDetailBO.getDxPlatTicket().getTicketType() == 3){
                Glide.with(CouponDetailActivity.this)
                        .load(R.mipmap.see_coupon_no)
                        .into(ivMoney);
                txtNumYes.setText("1");
                youhui_price.setText("张");
            }else {
                if (couponDetailBO.getDxPlatTicket().getPlatformType() == 2){
                    Glide.with(CouponDetailActivity.this)
                            .load(R.mipmap.see_coupon_no)
                            .into(ivMoney);
                    txtNumYes.setText("1");
                    youhui_price.setText("张");
                }else {
                    if (couponDetailBO.getDxPlatTicket().getName().contains("卖品")){
                        Glide.with(CouponDetailActivity.this)
                                .load(R.mipmap.prodectcoupon_no)
                                .into(ivMoney);
                    }else {
                        Glide.with(CouponDetailActivity.this)
                                .load(R.mipmap.money_coupon_no)
                                .into(ivMoney);
                    }
                    txtNumYes.setText(String.valueOf(couponDetailBO.getDxPlatTicket().getAmount()));
                    youhui_price.setText("元");
                }
            }
        }else {
            ivState.setVisibility(View.GONE);
            txtNumYes.setTextColor(Color.parseColor("#E04F2E"));
            youhui_price.setTextColor(Color.parseColor("#eb5e3a"));
            rlGoMain.setVisibility(View.VISIBLE);
            if (couponDetailBO.getDxPlatTicket().getTicketType() == 3){
                Glide.with(CouponDetailActivity.this)
                        .load(R.mipmap.see_coupon_yes)
                        .into(ivMoney);
                txtNumYes.setText("1");
                youhui_price.setText("张");
            }else {
                if (couponDetailBO.getDxPlatTicket().getPlatformType() == 2) {
                    Glide.with(CouponDetailActivity.this)
                            .load(R.mipmap.see_coupon_yes)
                            .into(ivMoney);
                    txtNumYes.setText("1");
                    youhui_price.setText("张");
                } else {
                    if (couponDetailBO.getDxPlatTicket().getName().contains("卖品")){
                        Glide.with(CouponDetailActivity.this)
                                .load(R.mipmap.prodectcoupon_yes)
                                .into(ivMoney);
                    }else {
                        Glide.with(CouponDetailActivity.this)
                                .load(R.mipmap.money3)
                                .into(ivMoney);
                    }
                    txtNumYes.setText(String.valueOf(couponDetailBO.getDxPlatTicket().getAmount()));
                    youhui_price.setText("元");
                }
            }
        }
        youhui_type.setText(couponDetailBO.getDxPlatTicket().getName());
        youhui_time.setText(String.format("有效期：%s 至 %s",couponDetailBO.getStartTime().substring(0,10),
                couponDetailBO.getEndTime().substring(0,10)));
        decribe.setText(couponDetailBO.getDxPlatTicket().getTicketDesc());
        txtRange.setText(couponDetailBO.getCinemaName());
        movieRange.setText(couponDetailBO.getCinemaName());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlGoMain:
                AppManager.getAppManager().goBackMain();
                EventBus.getDefault().post(new MessageEvent("showMain", "yes"));
                break;
            default:
                break;
        }
    }
}
