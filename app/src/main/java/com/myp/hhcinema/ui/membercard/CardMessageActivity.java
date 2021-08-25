package com.myp.hhcinema.ui.membercard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.ui.accountbalance.accountbalance;
import com.myp.hhcinema.ui.detailed.detailed;
import com.myp.hhcinema.ui.personorder.notpaymessage.NotPayMessageActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.MD5;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**会员卡详情
 * Created by Administrator on 2018/3/2.
 */
public class CardMessageActivity extends BaseActivity {
    @Bind(R.id.card_num)
    TextView cardNum;
    @Bind(R.id.card_price)
    TextView cardPrice;
    @Bind(R.id.card_jifen)
    TextView cardJifen;
    @Bind(R.id.card_juanNum)
    TextView cardJuanNum;
    @Bind(R.id.card_qrCode)
    RelativeLayout cardQrCode;
    @Bind(R.id.my_balance)
    RelativeLayout mybalance;
    @Bind(R.id.rlCard)
    RelativeLayout rlCard;
    @Bind(R.id.rlUnBind)
    RelativeLayout rlUnBind;
    @Bind(R.id.myBalanceDetail)
    RelativeLayout myBalanceDetail;
    @Bind(R.id.myCostDetail)
    RelativeLayout myCostDetail;
    @Bind(R.id.payBtn)
    RelativeLayout payBtn;

    private CardBO cardBO;

    private boolean showNum = false;//是否显示余额
    private String priceNum = "";

    @Override
    protected int getLayout() {
        return R.layout.fra_card_message;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("会员卡");
        cardBO = (CardBO) getIntent().getExtras().getSerializable("cardBO");
        Log.d("data.get(position)", " " + cardBO.getBalance());
        listener();
        setData();

    }

    private void listener() {
        mybalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String code;
//                if (cardBO.getBalance() != null) {
//                    BigDecimal bd = new BigDecimal(cardBO.getBalance());
//                    code = bd.toPlainString();
//                }else {
//                    code = "0";
//                }
//                Intent intent = new Intent(CardMessageActivity.this, accountbalance.class);
//                intent.putExtra("cardPrice", code);
//                intent.putExtra("getCardNumber", cardBO.getCardNumber());
//                intent.putExtra("cardLevel",cardBO.getCardLevel());
//                startActivity(intent);
                showDialog();
            }
        });
        rlUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        cardPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showNum){
                    showNum = false;
                    cardPrice.setText("******");
                    cardPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.eye_close),null,null,null);
                }else {
                    refreshDialog();
                }
            }
        });
        myBalanceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//充值明细
                Intent rechatg = new Intent(CardMessageActivity.this, detailed.class);
                rechatg.putExtra("mingxi", "1");
                rechatg.putExtra("cardcode", cardBO.getCardNumber());
                startActivity(rechatg);
            }
        });
        myCostDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//消费明细
                Intent rechatge = new Intent(CardMessageActivity.this, detailed.class);
                rechatge.putExtra("mingxi","2");
                rechatge.putExtra("cardcode", cardBO.getCardNumber());
                startActivity(rechatge);
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//马上充值
                Intent rechatge = new Intent(CardMessageActivity.this, com.myp.hhcinema.ui.rechatge.rechatge.class);
                rechatge.putExtra("cardPrice", priceNum);
                rechatge.putExtra("cardcode", cardBO.getCardNumber());
                startActivity(rechatge);
            }
        });
    }

    //    /**
//     * 设置页面数据显示
//     */
    private void setData() {
        if (cardBO.getBalance() != null) {
            BigDecimal bd = new BigDecimal(cardBO.getBalance());
            String code = bd.toPlainString();
            priceNum = code;
        }else {
            priceNum = "0";
        }
        cardNum.setText("NO." + cardBO.getCardNumber());
        cardJifen.setText(cardBO.getAvailableJifen());
        if (cardBO.getCardLevel().contains("至尊卡")){
            rlCard.setBackground(getResources().getDrawable(R.mipmap.vipcard));
        }else {
            rlCard.setBackground(getResources().getDrawable(R.mipmap.vipcard));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void unBind(String cardNumber){
        HttpInterfaceIml.unBindCard(cardNumber).subscribe(new Subscriber<CommonBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(CommonBean s) {
               if (s.getStatus() == 1){
                   ToastUtils.showShortToast("解绑成功");
                   dialog.dismiss();
                   finish();
               }
            }
        });
    }
    AlertDialog dialog;

    private void showDialog(){
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
        TextView cancle = (TextView) view.findViewById(R.id.off_commit);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = view.findViewById(R.id.message);
        title.setVisibility(View.GONE);
        message.setText("是否确认解绑?");
        cancle.setText("确定");
        commit.setText("取消");
        cancle.setTextColor(Color.parseColor("#e72e2d"));
        commit.setTextColor(Color.parseColor("#a4a4a4"));
        dialog = new AlertDialog.Builder(this).create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBind(cardBO.getCardNumber());
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事件
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }


    /**
     * 刷新余额
     */
    private void refreshDialog() {
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        View dia = factory.inflate(R.layout.dialog_card_pay, null);//这里必须是final的
        Button cancle =  dia.findViewById(R.id.pay_cancle);
        Button commit = dia.findViewById(R.id.pay_yes);
        TextView title = dia.findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText("请输入会员卡密码");
        cancle.setText("取消");
        commit.setText("确认");
        LinearLayout priceLayout = dia.findViewById(R.id.priceLayout);
        priceLayout.setVisibility(View.GONE);
        LinearLayout handFeeLayout = dia.findViewById(R.id.handFeeLayout);
        handFeeLayout.setVisibility(View.GONE);
        final EditText passWord = dia.findViewById(R.id.edit_password);
        passWord.setHint("请输入密码");
        dialog = new AlertDialog.Builder(this, R.style.AlertDialog).create();
        dialog.getWindow().setWindowAnimations(R.style.anim_menu_bottombar);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏软键盘
                InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive()){
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
                dialog.dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = passWord.getText().toString().trim();
                if (StringUtils.isEmpty(pwd)) {
                    LogUtils.showToast("请输入密码！");
                } else {
                    //隐藏软键盘
                    InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                    }
                    dialog.dismiss();
                    showProgress("加载中...");
                    refresh(cardBO.getCardNumber(),pwd);
                }
            }
        });
        dialog.setView(dia);
        dialog.show();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (ScreenUtils.getScreenWidth() * 0.78);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }


    /**
     * 刷新
     * @param cardNumber
     * @param pwd
     */
    private void refresh(String cardNumber,String pwd){
        HttpInterfaceIml.refreshNum(cardNumber,pwd).subscribe(new Subscriber<ResponseBody>() {
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
            public void onNext(ResponseBody responseBody) {
                try {
                    String s = new String(responseBody.bytes());
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.optInt("status");
                    if (status == 1) {
                        BigDecimal bd = new BigDecimal(jsonObject.optString("data"));
                        String code = bd.toPlainString();
                        cardPrice.setText(String.format("¥ %s",code));
                        cardPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.eye_open),null,null,null);
                        showNum = true;
                        priceNum = code;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
