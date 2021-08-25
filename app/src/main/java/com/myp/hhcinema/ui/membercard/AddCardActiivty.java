package com.myp.hhcinema.ui.membercard;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.CardBgBO;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.MD5;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.ToastUtils;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by wuliang on 2017/6/1.
 * <p>
 * 绑定会员卡
 */

public class AddCardActiivty extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.card_num_edit)
    EditText cardNumEdit;
    @Bind(R.id.password_edit)
    EditText passwordEdit;
    @Bind(R.id.submit_button)
    LinearLayout submitButton;
    @Bind(R.id.imageNone)
    ImageView imageNone;


    String cardNum;
    String pwd;
    String verification;


    @Override
    protected int getLayout() {
        return R.layout.act_add_card;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("绑定会员卡");
        submitButton.setOnClickListener(this);
        getCardImage();
    }
    @Override
    public void onClick(View v) {
        cardNum = cardNumEdit.getText().toString().trim();
        pwd = passwordEdit.getText().toString().trim();
        switch (v.getId()) {
            case R.id.submit_button:
                if (isCardNum()) {
                    submitButton.setEnabled(false);
                    showProgress("加载中...");
                    bingCard();
                }
                break;

        }
    }

    /**
     * 检测输入
     */
    private boolean isCardNum() {
        if (StringUtils.isEmpty(cardNum)) {
            LogUtils.showToast("请输入会员卡号!");
            return false;
        }
        if (StringUtils.isEmpty(pwd)) {
            LogUtils.showToast("请输入密码！");
            return false;
        }
        return true;
    }





    /**
     * 绑定会员卡
     */
    private void bingCard() {
        if (MyApplication.cinemaBo == null) {
            LogUtils.showToast("请先选择影院，绑定会员卡！");
            return;
        }
        HttpInterfaceIml.cardBindUser(MyApplication.cinemaBo.getCinemaId(), cardNum,
                pwd)//MD5.strToMd5Low32(pwd)
                .subscribe(new Subscriber<CardBO>() {
                    @Override
                    public void onCompleted() {
                        stopProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopProgress();
                        LogUtils.showToast(e.getMessage());
                        submitButton.setEnabled(true);
                    }

                    @Override
                    public void onNext(CardBO s) {
                        stopProgress();
                        submitButton.setEnabled(true);
                        LogUtils.showToast("绑定成功!");
                        Intent intent = new Intent();
                        setResult(1, intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *  获取背景图片
     */
    private void getCardImage(){
        HttpInterfaceIml.getCardImage(MyApplication.cinemaBo.getCinemaId()).subscribe(new Subscriber<CardBgBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(CardBgBO s) {
                if (s != null && s.getBindCardPic() != null) {
                    Glide.with(AddCardActiivty.this).load(s.getBindCardPic()).into(imageNone);
                }
            }
        });
    }
}
