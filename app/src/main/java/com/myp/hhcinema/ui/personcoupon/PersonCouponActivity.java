package com.myp.hhcinema.ui.personcoupon;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.CouponNumBo;
import com.myp.hhcinema.entity.UserCouponBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.hotsellprodect.HotSellActivity;
import com.myp.hhcinema.util.AppManager;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.widget.superadapter.CommonAdapter;
import com.myp.hhcinema.widget.superadapter.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * MVPPlugin
 * 我的优惠券页面
 */

public class PersonCouponActivity extends MVPBaseActivity<PersonCouponContract.View,
        PersonCouponPresenter> implements PersonCouponContract.View, View.OnClickListener,AdapterView.OnItemClickListener {

    @Bind(R.id.txtCouponNum)
    TextView txtCouponNum;//优惠券数量
    @Bind(R.id.rlAddCoupon)
    RelativeLayout rlAddCoupon;//添加优惠券
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.none_layout)
    LinearLayout none_layout;

    private int page = 1;
    private CommonAdapter<UserCouponBO> adapter;

    private List<UserCouponBO> data= new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.act_coupon;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("我的优惠券");

        rlAddCoupon.setOnClickListener(this);
        list.setOnItemClickListener(this);
        none_layout.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        if (MyApplication.cinemaBo != null) {
            mPresenter.loadPersonCoupon(page, 10,MyApplication.cinemaBo.getCinemaId());
            mPresenter.loadCanUseNum(MyApplication.cinemaBo.getCinemaId());
        }

        setPullRefresher();
    }

    private void setPullRefresher(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                if (MyApplication.cinemaBo != null) {
                    mPresenter.loadPersonCoupon(page, 10,MyApplication.cinemaBo.getCinemaId());
                }
                refreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (MyApplication.cinemaBo != null) {
                    mPresenter.loadPersonCoupon(page, 10,MyApplication.cinemaBo.getCinemaId());
                }
                refreshLayout.finishLoadmore(1000);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAddCoupon:  //添加优惠券

                break;
            case R.id.txtCouponNum:

                break;
                default:
                    break;
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        adapter = new CommonAdapter<UserCouponBO>(this, R.layout.item_youhuijuan, data) {
            @Override
            protected void convert(ViewHolder holder, final UserCouponBO item, int position) {
//                viewHolder.setText(R.id.movies_name, item.getMovieName());
                holder.setText(R.id.txtRange,item.getCinemaName());
                if (item.getStatus() == 0){ //0 可用  1 已使用  2 已过期
                    holder.getView(R.id.coupon_bg_yes).setVisibility(View.VISIBLE);
                    holder.getView(R.id.coupon_bg_no).setVisibility(View.GONE);
                    setTextColor((TextView)holder.getView(R.id.youhui_type),item.getDxPlatTicket().getName(),"#312927");
                    holder.setText(R.id.youhui_time,String.format("有效期：%s 至 %s",item.getStartTime().substring(0,10),
                            item.getEndTime().substring(0,10)));
                    if (item.getDxPlatTicket().getTicketType() == 3){
                        holder.setText(R.id.txtNumYes,String.format("%s",1));
                        holder.setText(R.id.youhui_price,"张");
                        if (String.valueOf(Double.valueOf(item.getDxPlatTicket().getFixedPayMoney())) != null
                                && item.getDxPlatTicket().getFixedPayMoney() == 0 ) {
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_yes)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }else {
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_yes)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }
                    }else {
                        if (item.getDxPlatTicket().getPlatformType() == 2){//自己添加的优惠券
                            holder.setText(R.id.txtNumYes,String.format("%s",1));
                            holder.setText(R.id.youhui_price,"张");
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_yes)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }else {
                            holder.setText(R.id.txtNumYes, String.format("%s", item.getDxPlatTicket().getAmount()));
                            holder.setText(R.id.youhui_price, "元");
                            if (item.getDxPlatTicket().getName().contains("卖品")){
                                Glide.with(PersonCouponActivity.this)
                                        .load(R.mipmap.prodectcoupon_yes)
                                        .into((ImageView) holder.getView(R.id.ivType));
                            }else {
                                Glide.with(PersonCouponActivity.this)
                                        .load(R.mipmap.money3)
                                        .into((ImageView) holder.getView(R.id.ivType));
                            }
                        }
                    }
                }else if (item.getStatus() == 1){
                    holder.getView(R.id.coupon_bg_yes).setVisibility(View.GONE);
                    holder.getView(R.id.coupon_bg_no).setVisibility(View.VISIBLE);
                    setTextColor((TextView)holder.getView(R.id.youhui_type),item.getDxPlatTicket().getName(),"#888888");
                    holder.setText(R.id.youhui_time,String.format("有效期：%s 至 %s",item.getStartTime().substring(0,10),
                            item.getEndTime().substring(0,10)));
                    if (item.getDxPlatTicket().getTicketType() == 3){//观影券
                        holder.setText(R.id.txtNumYes,String.format("%s",1));
                        holder.setText(R.id.youhui_price,"张");
                        holder.setText(R.id.txtNumNo,String.format("%s",1));
                        holder.setText(R.id.youhui_price_yes,"张");
                        if (String.valueOf(Double.valueOf(item.getDxPlatTicket().getFixedPayMoney())) != null
                                && item.getDxPlatTicket().getFixedPayMoney() == 0 ) {
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_no)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }else {
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_no)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }
                    }else {
                        if (item.getDxPlatTicket().getPlatformType() == 2){
                            holder.setText(R.id.txtNumYes,String.format("%s",1));
                            holder.setText(R.id.youhui_price,"张");
                            holder.setText(R.id.txtNumNo,String.format("%s",1));
                            holder.setText(R.id.youhui_price_yes,"张");
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_no)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }else {
                            holder.setText(R.id.txtNumYes, String.format("%s", item.getDxPlatTicket().getAmount()));
                            holder.setText(R.id.youhui_price, "元");
                            holder.setText(R.id.txtNumNo, String.format("%s", item.getDxPlatTicket().getAmount()));
                            holder.setText(R.id.youhui_price_yes, "元");
                            if (item.getDxPlatTicket().getName().contains("卖品")){
                                Glide.with(PersonCouponActivity.this)
                                        .load(R.mipmap.prodectcoupon_no)
                                        .into((ImageView) holder.getView(R.id.ivType));
                            }else {
                                Glide.with(PersonCouponActivity.this)
                                        .load(R.mipmap.money_coupon_no)
                                        .into((ImageView) holder.getView(R.id.ivType));
                            }
                        }
                    }
                    Glide.with(PersonCouponActivity.this)
                            .load(R.mipmap.coupon_used)
                            .into((ImageView)holder.getView(R.id.ivState2));

                }else {
                    holder.getView(R.id.coupon_bg_yes).setVisibility(View.GONE);
                    holder.getView(R.id.coupon_bg_no).setVisibility(View.VISIBLE);
                    setTextColor((TextView)holder.getView(R.id.youhui_type),item.getDxPlatTicket().getName(),"#888888");
                    holder.setText(R.id.youhui_time,String.format("有效期：%s 至 %s",item.getStartTime().substring(0,10),
                            item.getEndTime().substring(0,10)));
                    if (item.getDxPlatTicket().getTicketType() == 3){
                        holder.setText(R.id.txtNumYes,String.format("%s",1));
                        holder.setText(R.id.youhui_price,"张");
                        holder.setText(R.id.txtNumNo,String.format("%s",1));
                        holder.setText(R.id.youhui_price_yes,"张");
                        if (String.valueOf(Double.valueOf(item.getDxPlatTicket().getFixedPayMoney())) != null
                                && item.getDxPlatTicket().getFixedPayMoney() == 0 ) {
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_no)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }else {
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_no)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }
                    }else {
                        if (item.getDxPlatTicket().getPlatformType() == 2){
                            holder.setText(R.id.txtNumYes,String.format("%s",1));
                            holder.setText(R.id.youhui_price,"张");
                            holder.setText(R.id.txtNumNo,String.format("%s",1));
                            holder.setText(R.id.youhui_price_yes,"张");
                            Glide.with(PersonCouponActivity.this)
                                    .load(R.mipmap.see_coupon_no)
                                    .into((ImageView) holder.getView(R.id.ivType));
                        }else {
                            holder.setText(R.id.txtNumYes, String.format("%s", item.getDxPlatTicket().getAmount()));
                            holder.setText(R.id.youhui_price, "元");
                            holder.setText(R.id.txtNumNo, String.format("%s", item.getDxPlatTicket().getAmount()));
                            holder.setText(R.id.youhui_price_yes, "元");
                            if (item.getDxPlatTicket().getName().contains("卖品")){
                                Glide.with(PersonCouponActivity.this)
                                        .load(R.mipmap.prodectcoupon_no)
                                        .into((ImageView) holder.getView(R.id.ivType));
                            }else {
                                Glide.with(PersonCouponActivity.this)
                                        .load(R.mipmap.money_coupon_no)
                                        .into((ImageView) holder.getView(R.id.ivType));
                            }
                        }
                    }
                    Glide.with(PersonCouponActivity.this)
                            .load(R.mipmap.coupon_used)
                            .into((ImageView)holder.getView(R.id.ivState2));
                }
                holder.getView(R.id.buyTickets).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppManager.getAppManager().goBackMain();
                        if (item.getDxPlatTicket().getName().contains("卖品")){
                            gotoActivity(HotSellActivity.class,true);
                        }else {
                            EventBus.getDefault().post(new MessageEvent("showMain", "yes"));
                        }
                    }
                });

            }
        };
        list.setAdapter(adapter);
    }

    private void setTextColor(TextView textView,String text,String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(colorSpan, 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("id",String.valueOf(data.get(position).getId()));
        gotoActivity(CouponDetailActivity.class,bundle,false);
    }

    @Override
    public void getCoupon(List<UserCouponBO> userCouponBOS) {
        stopProgress();
        if(page==1){
            if(userCouponBOS.size()==0){
                refreshLayout.setVisibility(View.GONE);
                none_layout.setVisibility(View.VISIBLE);
            }else {
                data.clear();
                data.addAll(userCouponBOS);
                refreshLayout.setVisibility(View.VISIBLE);
                setAdapter();
                adapter.notifyDataSetChanged();
            }
        }else {
            data.addAll(userCouponBOS);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCanUseNum(CouponNumBo couponNumBo) {
        if (couponNumBo != null){
            txtCouponNum.setText(String.format("共%s张优惠券",couponNumBo.getCanUseNumber()));
        }else {
            txtCouponNum.setText(String.format("共%s张优惠券",0));
        }
    }

    @Override
    public void onRequestError(String msg) {
        stopProgress();
//        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {
        stopProgress();
    }

}
