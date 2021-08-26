package com.myp.hhcinema.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.util.DataCleanManager;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.SizeUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by wuliang on 2017/6/13.
 * <p>
 * 基本设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.log_out_btn)
    Button logOutBtn;
    @Bind(R.id.clean_app)
    RelativeLayout cleanApp;
    @Bind(R.id.about_layout)
    RelativeLayout aboutLayout;
    @Bind(R.id.clean_size)
    TextView cleanSize;
    @Bind(R.id.secretBtn)
    RelativeLayout secretBtn;
    @Bind(R.id.login_out)
            RelativeLayout zhuxiao;


    boolean isLogout = false;

    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("基本设置");

        if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {
            logOutBtn.setVisibility(View.GONE);
            zhuxiao.setVisibility(View.GONE);
        }
        try {
            cleanSize.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logOutBtn.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        cleanApp.setOnClickListener(this);
        secretBtn.setOnClickListener(this);
        zhuxiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out_btn:    //退出登录
                logOut();
                break;
            case R.id.about_layout:   //关于我们
                gotoActivity(AboutActivity.class, false);
                break;
            case R.id.clean_app:
                cleanApp();
                break;
            case R.id.secretBtn:
                Bundle bundle = new Bundle();
                bundle.putString("url", LocalConfiguration.YINSI_H5);
                gotoActivity(WebViewActivity.class, bundle, false);
                break;
            case R.id.login_out:
                showHearthWikiDialog();
                break;
        }
    }



    /**
     * 显示健康百科答题活动奖品弹窗
     */
    private void showHearthWikiDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_loginout, null);
        TextView cancle= view.findViewById(R.id.cancle);
        TextView commit = view.findViewById(R.id.commit);
        builder.setView(view);//设置login_layout为对话提示框
        builder.setCancelable(true);//设置为不可取消
        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        dialog.show();//显示Dialog对话框
        //此处设置位置窗体大小
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(SizeUtils.dp2px(250), ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 清除缓存
     */
    private void cleanApp() {
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
        TextView cancle = (TextView) view.findViewById(R.id.off_commit);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);
        title.setText("提示");
        message.setText("是否清除缓存？");
        cancle.setText("取消");
        commit.setText("确定");
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事件
                DataCleanManager.clearAllCache(SettingActivity.this);
                try {
                    cleanSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }


    /**
     * 退出登录
     */
    private void logOut() {
        HttpInterfaceIml.userLogout().subscribe(new Subscriber<CommonBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                isLogout = true;
                MyApplication.user = null;
                MyApplication.isLogin = ConditionEnum.NOLOGIN;
                MyApplication.spUtils.remove("uuid");
                MyApplication.SESSIONID = null;
                LogUtils.showToast("已退出登录");
                Intent intent = new Intent();
                intent.putExtra("isLogout", isLogout);
                setResult(2, intent);
                finish();
            }

            @Override
            public void onNext(CommonBean s) {
                if (s.getStatus() == 1) {
                    isLogout = true;
                    MyApplication.user = null;
                    MyApplication.isLogin = ConditionEnum.NOLOGIN;
                    MyApplication.spUtils.remove("uuid");
                    MyApplication.SESSIONID = null;
                    LogUtils.showToast("已退出登录");
                    Intent intent = new Intent();
                    intent.putExtra("isLogout", isLogout);
                    setResult(2, intent);
                    EventBus.getDefault().post(new MessageEvent("PlayfulWebFragment", "yes"));//发给StoreFragment
                    finish();
                }else {
                    isLogout = true;
                    MyApplication.user = null;
                    MyApplication.isLogin = ConditionEnum.NOLOGIN;
                    MyApplication.spUtils.remove("uuid");
                    MyApplication.SESSIONID = null;
                    LogUtils.showToast("已退出登录");
                    Intent intent = new Intent();
                    intent.putExtra("isLogout", isLogout);
                    setResult(2, intent);
                    finish();
                }
            }
        });
    }
}
