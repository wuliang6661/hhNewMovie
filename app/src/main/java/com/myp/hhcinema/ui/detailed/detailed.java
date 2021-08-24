package com.myp.hhcinema.ui.detailed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**充值明细，充值记录
 * Created by Administrator on 2018/1/28.
 */

public class detailed extends BaseActivity {
    @Bind(R.id.movie)
    TextView movie;
    @Bind(R.id.article)
    TextView article;
    @Bind(R.id.frameLayout)
    FrameLayout fragment;
    private rechargefragment fg2;
    private onsumptiondetailfragment fg3;
    private String mingxi;
    private String cardcode;

    @Override
    protected int getLayout() {
        return R.layout.balancectivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("明细");
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            mingxi = intent.getStringExtra("mingxi");
            cardcode = intent.getStringExtra("cardcode");
        }
        if(mingxi.equals("1")){
            movie.setTextColor(Color.parseColor("#FFFFFF"));
            article.setTextColor(Color.parseColor("#CFAF79"));
            movie.setBackgroundColor(Color.parseColor("#CFAF79"));
            article.setBackgroundColor(Color.parseColor("#FFFFFF"));
            setChioceItem(1);
        }
        if(mingxi.equals("2")){
            movie.setTextColor(Color.parseColor("#CFAF79"));
            article.setTextColor(Color.parseColor("#FFFFFF"));
            article.setBackgroundColor(Color.parseColor("#CFAF79"));
            movie.setBackgroundColor(Color.parseColor("#FFFFFF"));
            setChioceItem(2);
        }
    }

    @OnClick({R.id.movie, R.id.article})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movie://充值明细
                movie.setTextColor(Color.parseColor("#FFFFFF"));
                article.setTextColor(Color.parseColor("#CFAF79"));
                movie.setBackgroundColor(Color.parseColor("#CFAF79"));
                article.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setChioceItem(1);
                break;
            case R.id.article://消费明细
                movie.setTextColor(Color.parseColor("#CFAF79"));
                article.setTextColor(Color.parseColor("#FFFFFF"));
                article.setBackgroundColor(Color.parseColor("#CFAF79"));
                movie.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setChioceItem(2);
                break;
        }
    }
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragments(fragmentTransaction);//隐藏所有fragment
        switch (index) {
            case 1:
                if (fg2 == null) {
                    fg2 = new rechargefragment();//充值明细
                    fragmentTransaction.add(R.id.frameLayout, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
                if (fg3 == null) {
                    fg3 = new onsumptiondetailfragment();//消费明细
                    fragmentTransaction.add(R.id.frameLayout, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;

        }

        fragmentTransaction.commit(); // 提交
    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }

    }
    public String getTitles(){
        return cardcode;
    }

}

