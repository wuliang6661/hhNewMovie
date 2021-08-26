package com.myp.hhcinema.ui.main;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.service.update.UpdateManager;
import com.myp.hhcinema.ui.main.home.HomeFragment;
import com.myp.hhcinema.ui.main.member.MemberFragment;
import com.myp.hhcinema.ui.moviesseltor.SeltormovieActivity;
import com.myp.hhcinema.util.AppManager;
import com.myp.hhcinema.util.CustomUpdateParser;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.util.baidumap.BaiduMapLoctionUtils;
import com.xuexiang.xupdate.XUpdate;
import com.xyz.tabitem.BottmTabItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import me.leolin.shortcutbadger.ShortcutBadger;
import rx.Subscriber;

/**
 * 主页面
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter>
        implements MainContract.View, View.OnClickListener {


    @Bind(R.id.home)
    BottmTabItem home;
    @Bind(R.id.haowan)
    BottmTabItem haowan;
    //    @Bind(R.id.prodect)
//    BottmTabItem prodect;
    @Bind(R.id.store)
    BottmTabItem store;
    @Bind(R.id.huiyuan)
    BottmTabItem huiyuan;

    HomeFragment homeFragment;
    //    PlayfulFragment playfulFragment;
    MemberFragment memberFragment;
    //    HotwireFragment hotwireFragment;
    PlayfulWebFragment playfulWebFragment;//互动
    StoreFragment storeFragment;//积分商城

    //    HotSellActivity hotSellFragment;
    BaiduMapLoctionUtils baiduMapLoctionUtils;

    UpdateManager updateManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
//        playfulFragment = new PlayfulFragment();
        memberFragment = new MemberFragment();
//        hotwireFragment = new HotwireFragment();//活动
        playfulWebFragment = new PlayfulWebFragment();//活动H5
//        hotSellFragment = new HotSellActivity();//卖品
        storeFragment = new StoreFragment();//商城
        goToFragment(homeFragment);

        home.setOnClickListener(this);
        haowan.setOnClickListener(this);
        store.setOnClickListener(this);
//        prodect.setOnClickListener(this);
        huiyuan.setOnClickListener(this);
        baiduMapLoctionUtils = new BaiduMapLoctionUtils();

//        updateManager = new UpdateManager(this, "main");   //检查更新
//        updateManager.checkUpdate();
        XUpdate.newBuild(this)
                .updateUrl(HttpInterface.URL + "/version/version.json")
                .updateParser(new CustomUpdateParser()) //设置自定义的版本更新解析器
                .update();

        EventBus.getDefault().register(this);
        loadCinemas();
        if (!MyApplication.spUtils.getBoolean("getLocationPermission")) {
            showPricessDialog();
        }else{
            LogUtils.showToast("请到设置-应用中开启定位权限，获取最近影院");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            MyApplication.badgeNum = 0;   //点击打开app时取消角标显示
            ShortcutBadger.removeCountOrThrow(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showPricessDialog() {
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
        TextView cancle = view.findViewById(R.id.off_commit);
        TextView commit = view.findViewById(R.id.commit);
        TextView title = view.findViewById(R.id.title);
        TextView message = view.findViewById(R.id.message);
        title.setText("需要获取定位、内存权限来获取您最近的影院");
        message.setText("是否获取？");
        cancle.setText("否");
        commit.setText("是");
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                MyApplication.spUtils.put("getLocationPermission", true);
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事件
                dialog.dismiss();
                getPermission();
            }
        });
        dialog.setView(view);
        dialog.show();
    }


    /**
     * 检查定位权限
     */
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            MyApplication.locateSuccess = false;
        } else {
            startLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {   //授权成功
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startLocation();
                MyApplication.locateSuccess = true;
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED
                    && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                Intent intent = new Intent(MainActivity.this, SeltormovieActivity.class);
                startActivityForResult(intent, 1);
                MyApplication.locateSuccess = false;
            }
        } else {
            loadDefaltCinemas();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1:
                CinemaBo cinemaBo = (CinemaBo) data.getSerializableExtra("ciname");
                if (cinemaBo != null) {
                    MyApplication.cinemaBo = cinemaBo;
                    MyApplication.spUtils.put("cinemaId", cinemaBo.getCinemaId());
                    homeFragment.setCinemaInfo();
                } else {
                    loadCinemas();
                }
                break;
            case 200:
//                updateManager.installApk();
                break;
        }
    }


    /**
     * 用户拒绝权限，重新申请
     */
    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission) ||
                super.shouldShowRequestPermissionRationale(permission);
    }


    /**
     * 定位方法调用
     */
    private void startLocation() {
        baiduMapLoctionUtils.startLocation(this, new BaiduMapLoctionUtils.BaiduLocationListener() {
            @Override
            public void getData(String lontitude, String latitude, String city) {
                baiduMapLoctionUtils.stopLocation();
                mPresenter.loadCinemaIds(city, lontitude, latitude);
            }

            @Override
            public void onEroorLocation() {
                if (MyApplication.spUtils.getString("cinemaId") != null) {
                    loadCinemas();
                } else {
                    Intent intent = new Intent(MainActivity.this, SeltormovieActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onData(final List<CinemaBo> cinemaIdBOs) {
        if (MyApplication.spUtils.getString("cinemaId") != null &&
                MyApplication.spUtils.getString("cinemaId").equals(cinemaIdBOs.get(0).getCinemaId())) {
            homeFragment.setCinemaNameStr(cinemaIdBOs.get(0));
        } else if (MyApplication.spUtils.getString("cinemaId") == null) {
            homeFragment.setCinemaNameStr(cinemaIdBOs.get(0));
        } else {
            LayoutInflater factory = LayoutInflater.from(this);//提示框
            final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
            TextView cancle = (TextView) view.findViewById(R.id.off_commit);
            TextView commit = (TextView) view.findViewById(R.id.commit);
            TextView title = view.findViewById(R.id.title);
            TextView message = view.findViewById(R.id.message);
            title.setText(String.format("检测到\"%s\"距离您较近", cinemaIdBOs.get(0).getCinemaName()));
            message.setText("是否切换？");
            cancle.setText("取消");
            commit.setText("切换");
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    loadCinemas();
                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //事件
                    dialog.dismiss();
                    homeFragment.setCinemaNameStr(cinemaIdBOs.get(0));
                    MyApplication.spUtils.put("cinemaId", cinemaIdBOs.get(0).getCinemaId());
                }
            });
            dialog.setView(view);
            dialog.show();
        }
    }


    private void loadCinemas() {
        HttpInterfaceIml.cinemaList("", "", "").subscribe(new Subscriber<List<CinemaBo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(List<CinemaBo> s) {
                if (MyApplication.spUtils.getString("cinemaId") != null) {
                    for (int i = 0; i < s.size(); i++) {
                        if (s.get(i).getCinemaId().equals(MyApplication.spUtils.getString("cinemaId"))) {
                            homeFragment.setCinemaNameStr(s.get(i));
                            break;
                        }
                    }
                } else {
                    homeFragment.setCinemaNameStr(s.get(0));
                }
            }
        });

    }

    /**
     * 定位失败获取默认第一个影院
     */
    private void loadDefaltCinemas() {
        HttpInterfaceIml.cinemaList("", "", "").subscribe(new Subscriber<List<CinemaBo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(List<CinemaBo> s) {
                if (s != null && s.size() > 0) {
                    if (MyApplication.spUtils.getString("cinemaId") != null) {
                        for (int i = 0; i < s.size(); i++) {
                            if (s.get(i).getCinemaId().equals(MyApplication.spUtils.getString("cinemaId"))) {
                                homeFragment.setCinemaNameStr(s.get(i));
                                break;
                            }
                        }
                    } else {
                        homeFragment.setCinemaNameStr(s.get(0));
                    }
                }
            }
        });

    }

    @Override
    public void onRequestError(String msg) {
//        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {
    }

    private Fragment mContent = null;

    /**
     * 修改显示的内容 不会重新加载
     **/
    public void goToFragment(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                if (mContent != null)
                    transaction.hide(mContent).add(R.id.fragment_container, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                else
                    transaction.add(R.id.fragment_container, to).commitAllowingStateLoss();
            } else {
                if (mContent != null)
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                else
                    transaction.show(to).commitAllowingStateLoss();
            }
            mContent = to;
        }
    }

    /**
     * 底部按钮点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home://首页
                goToFragment(homeFragment);
                home.setSelectState(true);
                haowan.setSelectState(false);
                store.setSelectState(false);
//                prodect.setSelectState(false);
                huiyuan.setSelectState(false);
                break;
            case R.id.haowan://互动H5页面
                goToFragment(playfulWebFragment);
                home.setSelectState(false);
                haowan.setSelectState(true);
                store.setSelectState(false);
//                prodect.setSelectState(false);
                huiyuan.setSelectState(false);
                break;
            case R.id.store://积分商城
                goToFragment(storeFragment);
                home.setSelectState(false);
                haowan.setSelectState(false);
                store.setSelectState(true);
//                prodect.setSelectState(false);
                huiyuan.setSelectState(false);
                break;
            case R.id.huiyuan://个人中心
                goToFragment(memberFragment);
                home.setSelectState(false);
                haowan.setSelectState(false);
                store.setSelectState(false);
                huiyuan.setSelectState(true);
//                prodect.setSelectState(false);
                break;
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    LogUtils.showToast("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    MyApplication.SESSIONID = null;
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 显示Fragment
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessageType().equals("showMain")) {
            if (messageEvent.getPassValue().equals("yes")) {
                goToFragment(homeFragment);
                home.setSelectState(true);
                haowan.setSelectState(false);
                store.setSelectState(false);
                huiyuan.setSelectState(false);
//                prodect.setSelectState(false);
            }
        } else if (messageEvent.getMessageType().equals("showstore")) {
            goToFragment(storeFragment);
            home.setSelectState(false);
            haowan.setSelectState(false);
            store.setSelectState(true);
            huiyuan.setSelectState(false);
//            prodect.setSelectState(false);
        } else if (messageEvent.getMessageType().equals("update")) {//Android8.0安装应用需要获取安装未知应用权限
            LayoutInflater factory = LayoutInflater.from(this);//提示框
            final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
            TextView cancle = (TextView) view.findViewById(R.id.off_commit);
            TextView commit = (TextView) view.findViewById(R.id.commit);
            TextView title = view.findViewById(R.id.title);
            TextView message = view.findViewById(R.id.message);
            cancle.setVisibility(View.GONE);
            commit.setText("立即前往设置");
            title.setText("继续安装需允许获取安装未知应用权限");
            message.setText("是否前往设置?");
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            commit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    //事件
                    dialog.dismiss();
                    startInstallPermissionSettingActivity(MainActivity.this);
                }
            });
            dialog.setView(view);
            dialog.show();
        }
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity) context).startActivityForResult(intent, 200);
    }
}