package com.myp.hhcinema.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.myp.hhcinema.util.LogUtils;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

// x5 init service
public class AdvanceLoadX5Service extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initX5();
    }

    private void initX5() {
        //  预加载X5内核
//        QbSdk.initX5Environment(getApplicationContext(), cb);
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
            //初始化完成回调
            LogUtils.E(" onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
        }
    };

}