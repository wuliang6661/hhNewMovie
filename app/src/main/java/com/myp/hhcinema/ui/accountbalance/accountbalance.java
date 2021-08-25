package com.myp.hhcinema.ui.accountbalance;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.ui.detailed.detailed;

import butterknife.Bind;
import butterknife.OnClick;

/**会员卡余额页面
 * Created by Administrator on 2018/1/24.
 */

public class accountbalance extends BaseActivity {





    @Bind(R.id.recharge_bu)
    RelativeLayout rechargeBu;
    @Bind(R.id.yu)
    TextView yu;
    @Bind(R.id.rlCard)
    RelativeLayout rlCard;

    private String cardcode;
    private String value;
    private TextView card;
    private String cardLevel;

    @Override
    protected int getLayout() {
        return R.layout.accountbalancetactivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("账户余额");
        card = (TextView)findViewById(R.id.card_num);
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("cardPrice");
            cardcode = intent.getStringExtra("getCardNumber");
            cardLevel = intent.getStringExtra("cardLevel");
            yu.setText(value);
            TextPaint paint = yu.getPaint();
            paint.setFakeBoldText(true);
            card.setText("NO." +cardcode);
            if (cardLevel.contains("至尊卡")){
                rlCard.setBackground(getResources().getDrawable(R.mipmap.vipcard));
            }else {
                rlCard.setBackground(getResources().getDrawable(R.mipmap.vipcard));
            }
        }

    }


    @OnClick(R.id.recharge_bu)
    public void onViewClicked() {
        Intent rechatge = new Intent(accountbalance.this, com.myp.hhcinema.ui.rechatge.rechatge.class);
        rechatge.putExtra("cardPrice", value);
        rechatge.putExtra("cardcode",  cardcode);
//        Log.d("支付宝充值", "固定金额 " + cardcode);
        startActivity(rechatge);
    }

    @OnClick({R.id.my_balance, R.id.my_mingxi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_balance:
                Intent rechatg = new Intent(accountbalance.this, detailed.class);
                rechatg.putExtra("mingxi", "1");
                rechatg.putExtra("cardcode", cardcode);
                startActivity(rechatg);
                break;
            case R.id.my_mingxi:
                Intent rechatge = new Intent(accountbalance.this, detailed.class);
                rechatge.putExtra("mingxi","2");
                rechatge.putExtra("cardcode", cardcode);
                startActivity(rechatge);
                break;
        }
    }
}

