package com.myp.hhcinema.ui.membercard;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.CardBgBO;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.widget.MyscrollView;
import com.myp.hhcinema.widget.superadapter.CommonAdapter;
import com.myp.hhcinema.widget.superadapter.ViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;


/**会员卡列表
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MemberCardActivity extends MVPBaseActivity<MemberCardContract.View, MemberCardPresenter> implements
        MemberCardContract.View , AdapterView.OnItemClickListener, View.OnClickListener{

    @Bind(R.id.txt)
    TextView txt;
    @Bind(R.id.rlBind)
    RelativeLayout rlBind;
    @Bind(R.id.rlOpen)
    RelativeLayout rlOpen;
    @Bind(R.id.imageNone)
    ImageView imageNone;
    List<CardBO> data =  new ArrayList<>();
    private CommonAdapter<CardBO> adapter;
    private ListView list;
    private MyscrollView sc;


    @Override
    protected int getLayout() {
        return R.layout.fra_card_list1;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("会员卡");
        txt.setVisibility(View.GONE);
        list = (ListView)findViewById(R.id.lv);
        sc = findViewById(R.id.sc);
        rlBind.setOnClickListener(this);
        rlOpen.setOnClickListener(this);
        list.setOnItemClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String cinemaId;
        if (MyApplication.cinemaBo != null) {
            cinemaId = String.valueOf(MyApplication.cinemaBo.getCinemaId());
        }else {
            cinemaId = "";
        }
        mPresenter.loadCardUser(cinemaId);
        adapter();
    }

    private void adapter() {
        adapter = new CommonAdapter<CardBO>(this, R.layout.item_card_pay, data) {
            @Override
            protected void convert(ViewHolder helper, CardBO item, int position) {
                helper.getView(R.id.card_check).setVisibility(View.GONE);
                if (item.getBalance() != null) {
                    BigDecimal bd = new BigDecimal(item.getBalance());
                    String str = bd.toPlainString();
                    helper.setText(R.id.card_price, "¥ " + str);
                }else {
                    helper.setText(R.id.card_price, "¥ " + "0");
                }
                helper.setText(R.id.card_num, item.getCardNumber());
            }
        };
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardBO", data.get(position));
        Intent intent = new Intent(MemberCardActivity.this, CardMessageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                String cinemaId;
                if (MyApplication.cinemaBo != null) {
                    cinemaId = String.valueOf(MyApplication.cinemaBo.getCinemaId());
                }else {
                    cinemaId = "";
                }
                mPresenter.loadCardUser(cinemaId);
                break;
        }
    }
    @Override
    public void getCardList(List<CardBO> cardBOs) {
        if (cardBOs != null && cardBOs.size() != 0) {
            imageNone.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            data.clear();
            data.addAll(cardBOs);
            list.setAdapter(adapter);
        } else {
            list.setVisibility(View.GONE);
            imageNone.setVisibility(View.VISIBLE);
            getCardImage();
        }
    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlBind:
                Intent intent = new Intent(MemberCardActivity.this, AddCardActiivty.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlOpen://开通会员卡
                gotoActivity(BindCard.class,false);
                break;
                default:
                    break;
        }
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
                    Glide.with(MemberCardActivity.this).load(s.getBindCardPic()).into(imageNone);
                }
            }
        });
    }
}
