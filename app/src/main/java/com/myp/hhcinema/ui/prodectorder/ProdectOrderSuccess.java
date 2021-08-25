package com.myp.hhcinema.ui.prodectorder;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.ui.personorder.ordermessage.OrderMessageActivity;
import com.myp.hhcinema.util.AppManager;
import com.myp.hhcinema.util.LogUtils;

import butterknife.Bind;
import rx.Subscriber;


/**
 * Created by Witness on 2019/1/25
 * Describe:食品订单支付成功
 */
public class ProdectOrderSuccess extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.movies_name)
    TextView moviesName;
    @Bind(R.id.movies_num)
    TextView moviesNum;
    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.back_home)
    Button back_home;
    @Bind(R.id.order_message)
    Button order_message;


    private String orderNum;
    private OrderBO orderBO;

    @Override
    protected int getLayout() {
        return R.layout.prodect_order_scress;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支付成功");
        orderNum = getIntent().getExtras().getString("order");
        goBack.setOnClickListener(this);
        back_home.setOnClickListener(this);
        order_message.setOnClickListener(this);
        MyApplication.selectedId = -1;//清空选择的优惠券
        if (MyApplication.user != null) {
            queryOrder(String.valueOf(MyApplication.user.getId()), orderNum);
        }
    }

    /**
     * 查询单个订单
     */
    private void queryOrder(String appUserId ,String orderNum) {
        HttpInterfaceIml.orderQuery(appUserId,orderNum).subscribe(new Subscriber<OrderBO>() {
            @Override
            public void onCompleted() {
                stopProgress();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.showToast(e.getMessage());
                stopProgress();
            }

            @Override
            public void onNext(OrderBO order) {
                orderBO = order;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                AppManager.getAppManager().goBackMain();
                break;
            case R.id.back_home:
                AppManager.getAppManager().goBackMain();
                break;
            case R.id.order_message:   //订单详情
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orderBO);
                bundle.putString("cinemaId", orderBO.getCinemaId());
                bundle.putString("id", String.valueOf(orderBO.getId()));
                gotoActivity(OrderMessageActivity.class, bundle, false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().goBackMain();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
