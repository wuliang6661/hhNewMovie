package com.myp.hhcinema.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.BaseActivity;

/**
 * Created by wuliang on 2017/5/4.
 * <p>
 * 程序报错进入的页面
 */

public class CustomErrorActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.custom_error_act;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
