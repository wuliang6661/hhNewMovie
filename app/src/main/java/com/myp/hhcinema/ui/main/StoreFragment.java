package com.myp.hhcinema.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseFragment;
import com.myp.hhcinema.base.BaseWebFragment;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.entity.CardBgBO;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.entity.ThreeProdectBO;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.FragmentPaerAdapter;
import com.myp.hhcinema.ui.WebViewActivity;
import com.myp.hhcinema.ui.hotsellprodect.HotSellActivity;
import com.myp.hhcinema.ui.main.home.movieslist.MoviesListFragment;
import com.myp.hhcinema.ui.main.home.nextmovies.NextMoviesFragment;
import com.myp.hhcinema.ui.membercard.MemberCardActivity;
import com.myp.hhcinema.ui.userlogin.LoginActivity;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.widget.RoundImageView;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by witness
 * <p>
 * 积分商城
 */

public class StoreFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.txtMore)
    TextView txtMore;//查看全部食品
    @Bind(R.id.roundImage1)
    RoundImageView roundImage1;//第一个食品图片
    @Bind(R.id.txtPrice1)
    TextView txtPrice1;//第一个食品价格
    @Bind(R.id.roundImage2)
    RoundImageView roundImage2;//第二个食品图片
    @Bind(R.id.txtPrice2)
    TextView txtPrice2;//第二个食品价格
    @Bind(R.id.roundImage3)
    RoundImageView roundImage3;//第三个食品图片
    @Bind(R.id.txtPrice3)
    TextView txtPrice3;//第三个食品价格
    @Bind(R.id.txtCardDetail)
    TextView txtCardDetail;//查看会员卡详细信息
    @Bind(R.id.cardImage)
    RoundImageView cardImage;//会员卡图片
    @Bind(R.id.cardName)
    TextView cardName;//卡片名称
    @Bind(R.id.cardNum)
    TextView cardNum;//卡片金额
    @Bind(R.id.storeImage)
    RoundImageView storeImage;//积分商城图片
    @Bind(R.id.txtName1)
    TextView txtName1;
    @Bind(R.id.txtName2)
    TextView txtName2;
    @Bind(R.id.txtName3)
    TextView txtName3;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.ll2)
    LinearLayout ll2;
    @Bind(R.id.ll3)
    LinearLayout ll3;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goBack.setVisibility(View.GONE);
        title.setText("商城");
        initListener();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.cinemaBo != null) {
            getCardImage();
            getProdect(MyApplication.cinemaBo.getCinemaId());
            if (LocalConfiguration.isCanBuy == 0){
//                getCanBuy();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (MyApplication.cinemaBo != null) {
//                getCanBuy();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private void initListener(){
        txtMore.setOnClickListener(this);
        roundImage1.setOnClickListener(this);
        roundImage2.setOnClickListener(this);
        roundImage3.setOnClickListener(this);
        txtCardDetail.setOnClickListener(this);
        storeImage.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtMore://查看全部食品
            case R.id.roundImage1:
            case R.id.roundImage2:
            case R.id.roundImage3:
                if (MyApplication.cinemaBo != null) {
                    getCanBuy();
                }else {
                    ToastUtils.showShortToast("请先选择影院");
                }
                break;
            case R.id.txtCardDetail://查看会员卡详情
                if (MyApplication.isLogin == ConditionEnum.LOGIN) {
                    gotoActivity(MemberCardActivity.class, false);
                }else {
                    gotoActivity(LoginActivity.class,false);
                }
                break;
            case R.id.storeImage://积分商城
                if (MyApplication.isLogin == ConditionEnum.LOGIN) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title","积分商城");
                    if (MyApplication.cinemaBo != null) {
                        bundle.putString("url", HttpInterface.URL + "/api/pointsmall/change?appUserId=" + MyApplication.user.getId() + "&cinemaId=" +
                                MyApplication.cinemaBo.getCinemaId());
                    }else {
                        bundle.putString("url", HttpInterface.URL + "/api/pointsmall/change?appUserId=" + MyApplication.user.getId());
                    }
                    gotoActivity(WebViewActivity.class, bundle,false);
                }else {
                    gotoActivity(LoginActivity.class,false);
                }
                break;
                default:
                    break;
        }
    }

    /**
     *  获取会员卡背景图片
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
                Glide.with(getActivity()).load(s.getPicPath()).into(cardImage);
                cardName.setText(s.getEventTitle());
                cardNum.setText(String.format("￥%s",s.getEventMoney()));
                Glide.with(getActivity()).load(s.getPointsPic()).into(storeImage);
            }
        });
    }

    /**
     * 积分商城三个卖品
     * @param cinemaId
     */
    private void getProdect(String cinemaId){
        HttpInterfaceIml.getProdect(cinemaId).subscribe(new Subscriber<ThreeProdectBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(ThreeProdectBO s) {
                if (s != null && s.getSubList().size()>0) {
                    ll1.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(s.getSubList().get(0).getImageUrl()).into(roundImage1);
                    txtName1.setText(s.getSubList().get(0).getName());
                    txtPrice1.setText(String.format("￥%s", s.getSubList().get(0).getActivityPrice()));
                    if (s.getSubList().size() > 2) {
                        ll2.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(s.getSubList().get(1).getImageUrl()).into(roundImage2);
                        txtName2.setText(s.getSubList().get(1).getName());
                        txtPrice2.setText(String.format("￥%s", s.getSubList().get(1).getActivityPrice()));
                    }else {
                        ll2.setVisibility(View.INVISIBLE);
                    }
                    if (s.getSubList().size() >= 3) {
                        ll3.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(s.getSubList().get(2).getImageUrl()).into(roundImage3);
                        txtName3.setText(s.getSubList().get(2).getName());
                        txtPrice3.setText(String.format("￥%s", s.getSubList().get(2).getActivityPrice()));
                    }else {
                        ll3.setVisibility(View.INVISIBLE);
                    }
                }else {
                    ll1.setVisibility(View.INVISIBLE);
                    ll2.setVisibility(View.INVISIBLE);
                    ll3.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    /**
     *  判断卖品是否可以点击
     */
    private void getCanBuy(){
        HttpInterfaceIml.getCanBuy(MyApplication.cinemaBo.getCinemaId()).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String s = new String(responseBody.bytes());
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.optInt("status");
                    LocalConfiguration.isCanBuy = status;
                    if (status == 1){//正常
                        if (MyApplication.isLogin == ConditionEnum.LOGIN) {
                            gotoActivity(HotSellActivity.class, false);
                        }else {
                            gotoActivity(LoginActivity.class, false);

                        }
                    }else {
                        ToastUtils.showLongToast(jsonObject.optString("message"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessageType().equals("StoreFragment")) {
            if (MyApplication.cinemaBo != null) {
                getProdect(MyApplication.cinemaBo.getCinemaId());
                getCardImage();
            }
        }
    }
}
