package com.myp.hhcinema.ui.orderconfrim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.widget.superadapter.CommonAdapter;
import com.myp.hhcinema.widget.superadapter.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Witness on 2019/1/7
 * Describe:
 */
public class SelectCoupon extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.none_layout)
    LinearLayout nonelayout;
    @Bind(R.id.text_layout)
    TextView textlayout;
    @Bind(R.id.ivNone)
    ImageView ivNone;
    @Bind(R.id.rlCancle)
    RelativeLayout rlCancle;

    private List<LockSeatsBO.SeatTicketListBean> data = new ArrayList<>();
    private List<LockSeatsBO.MerOrderBean.MerTicketListBean> prodectData = new ArrayList<>();
    private List<ProdectBO.MerTicketListBean> singleData = new ArrayList<>();
    private double totalPrice;
    private String tag;
    @Override
    protected int getLayout() {
        return R.layout.list_personcomment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("选择优惠券");
        rlCancle.setVisibility(View.VISIBLE);
        rlCancle.setOnClickListener(this);
        list.setOnItemClickListener(this);
        list.setDividerHeight(1);
        setPullRefresher();
        ivNone.setImageDrawable(getResources().getDrawable(R.mipmap.no_coupon));
        textlayout.setText("亲，没有优惠券哦~");
        try {
            Bundle bundle = getIntent().getExtras();
            tag = bundle.getString("tag");
            if (tag.equals("ticket")) {
                rlCancle.setVisibility(View.VISIBLE);
                LockSeatsBO bean = (LockSeatsBO) bundle.getSerializable("LockSeatsBO");
                data.clear();
                data.addAll((bean.getSeatTicketList()));
                if (data.size() > 0) {
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    nonelayout.setVisibility(View.GONE);
                } else {
                    smartRefreshLayout.setVisibility(View.GONE);
                    nonelayout.setVisibility(View.VISIBLE);
                }
//                totalPrice = bundle.getDouble("totalMoney");
                totalPrice = Double.valueOf(bean.getPayPrice());
                setAdapter();
            }else if (tag.equals("prodectSingle")){//从单独的食品订单界面跳过来
                rlCancle.setVisibility(View.VISIBLE);
                ProdectBO prodectBO = (ProdectBO) bundle.getSerializable("prodectBO");
                singleData.addAll(prodectBO.getMerTicketList());
                if (singleData.size() > 0) {
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    nonelayout.setVisibility(View.GONE);
                    setSingleProdectAdapter();
                } else {
                    smartRefreshLayout.setVisibility(View.GONE);
                    nonelayout.setVisibility(View.VISIBLE);
                }
            }else {
                rlCancle.setVisibility(View.VISIBLE);
                LockSeatsBO bean = (LockSeatsBO) bundle.getSerializable("LockSeatsBO");
                prodectData.clear();
                prodectData.addAll((bean.getMerOrder().getMerTicketList()));
                if (prodectData.size() > 0) {
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    nonelayout.setVisibility(View.GONE);
                    setProdectAdapter();
                } else {
                    smartRefreshLayout.setVisibility(View.GONE);
                    nonelayout.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            smartRefreshLayout.setVisibility(View.GONE);
            nonelayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (tag.equals("ticket")) {
            Intent intent = new Intent();
            intent.putExtra("id", data.get(position).getId());
//            MyApplication.selectedId = data.get(position).getId();
            intent.putExtra("discount", data.get(position).getDxPlatTicket().getAmount());
            SelectCoupon.this.setResult(RESULT_OK, intent);
            SelectCoupon.this.finish();
        }else if (tag.equals("prodectSingle")){//从单独的食品订单界面跳过来
            Intent intent = new Intent();
            intent.putExtra("id", singleData.get(position).getId());
//            MyApplication.selectedId = singleData.get(position).getId();
            intent.putExtra("discount", singleData.get(position).getDxPlatTicket().getAmount());
            SelectCoupon.this.setResult(RESULT_OK, intent);
            SelectCoupon.this.finish();
        }else {
            Intent intent = new Intent();
            intent.putExtra("id", prodectData.get(position).getId());
//            MyApplication.selectedId = prodectData.get(position).getId();
            intent.putExtra("discount", prodectData.get(position).getDxPlatTicket().getAmount());
            SelectCoupon.this.setResult(RESULT_OK, intent);
            SelectCoupon.this.finish();
        }
    }

    private void setAdapter() {
        CommonAdapter<LockSeatsBO.SeatTicketListBean> adapter = new CommonAdapter<LockSeatsBO.SeatTicketListBean>(this, R.layout.select_coupon_item, data) {
            @Override
            protected void convert(ViewHolder holder, LockSeatsBO.SeatTicketListBean item, int position) {
                if (item.getType() == 1) {//普通优惠券
                    holder.getView(R.id.llNormal).setVisibility(View.VISIBLE);
                    holder.getView(R.id.txtDecrease).setVisibility(View.GONE);
                    holder.getView(R.id.txtDate).setVisibility(View.VISIBLE);
                    if (data.get(position).getDxPlatTicket().getTicketType() == 3) {
                        holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                        Glide.with(SelectCoupon.this).load(R.mipmap.see_coupon_yes).into((ImageView)holder.getView(R.id.ivSee));
                        holder.getView(R.id.txtNum).setVisibility(View.GONE);
                        holder.setText(R.id.txtRange, String.format("%s", data.get(position).getDxPlatTicket().getName()));
                    } else {
                        if (totalPrice > Double.valueOf(data.get(position).getDxPlatTicket().getAchieveMoney())) {
                            setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", data.get(position).getDxPlatTicket().getAmount()), "#E04F2E");
                        } else {
                            setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", data.get(position).getDxPlatTicket().getAmount()), "#AAAAAA");
                        }
                        holder.getView(R.id.ivSee).setVisibility(View.GONE);
                        holder.getView(R.id.txtNum).setVisibility(View.VISIBLE);
                        holder.setText(R.id.txtNum, String.format("￥ %s", data.get(position).getDxPlatTicket().getAmount()));
                        holder.setText(R.id.txtRange, String.format("满%s元可用", data.get(position).getDxPlatTicket().getAchieveMoney()));
                    }
                    holder.setText(R.id.txtDate, String.format("有效期 %s", data.get(position).getDxPlatTicket().getEndTime().substring(0, 10)));
                }else {//满减活动
                    holder.getView(R.id.llNormal).setVisibility(View.GONE);
                    holder.getView(R.id.txtDecrease).setVisibility(View.VISIBLE);
                    holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                    Glide.with(SelectCoupon.this).load(R.mipmap.newdecrease).into((ImageView)holder.getView(R.id.ivSee));
                    holder.getView(R.id.txtDate).setVisibility(View.GONE);
                    holder.setText(R.id.txtDecrease,item.getCinemaName());
                }

                if (MyApplication.selectedId == data.get(position).getId()) {
                    holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                }
            }
        };
        list.setAdapter(adapter);
    }

    private void setProdectAdapter(){
        CommonAdapter<LockSeatsBO.MerOrderBean.MerTicketListBean> adapterProdect = new CommonAdapter<LockSeatsBO.MerOrderBean.MerTicketListBean>(this, R.layout.select_coupon_item, prodectData) {
            @Override
            protected void convert(ViewHolder holder, LockSeatsBO.MerOrderBean.MerTicketListBean item, int position) {
                if (item.getType() == 1) {//普通优惠券
                    holder.getView(R.id.llNormal).setVisibility(View.VISIBLE);
                    holder.getView(R.id.txtDecrease).setVisibility(View.GONE);
                    holder.getView(R.id.ivSee).setVisibility(View.GONE);
                    holder.getView(R.id.txtRange).setVisibility(View.VISIBLE);
                    holder.getView(R.id.txtDate).setVisibility(View.VISIBLE);
                    setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", prodectData.get(position).getDxPlatTicket().getAmount()), "#E04F2E");
                    holder.setText(R.id.txtNum, String.format("￥ %s", prodectData.get(position).getDxPlatTicket().getAmount()));
                    holder.setText(R.id.txtRange, String.format("满%s元可用", prodectData.get(position).getDxPlatTicket().getAchieveMoney()));
                    holder.setText(R.id.txtDate, String.format("有效期 %s", prodectData.get(position).getDxPlatTicket().getEndTime().substring(0, 10)));
                }else {
                    holder.getView(R.id.llNormal).setVisibility(View.GONE);
                    holder.getView(R.id.txtDecrease).setVisibility(View.VISIBLE);
                    holder.getView(R.id.txtRange).setVisibility(View.GONE);
                    holder.getView(R.id.txtDate).setVisibility(View.GONE);
                    holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                    Glide.with(SelectCoupon.this).load(R.mipmap.newdecrease).into((ImageView)holder.getView(R.id.ivSee));
                    holder.setText(R.id.txtDecrease, String.format("%s", item.getCinemaName()));
                }
                if (MyApplication.selectedId == prodectData.get(position).getId()){
                    holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                }else {
                    holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                }

                if (MyApplication.selectedProdectId == prodectData.get(position).getId()) {
                    holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                }
            }
        };
        list.setAdapter(adapterProdect);
    }

    private void setSingleProdectAdapter(){
        CommonAdapter<ProdectBO.MerTicketListBean> adapterSingleProdect = new CommonAdapter<ProdectBO.MerTicketListBean>(this, R.layout.select_coupon_item, singleData) {
            @Override
            protected void convert(ViewHolder holder, ProdectBO.MerTicketListBean item, int position) {
                if (item.getType() == 1) {
                    holder.getView(R.id.llNormal).setVisibility(View.VISIBLE);
                    holder.getView(R.id.txtDecrease).setVisibility(View.GONE);
                    holder.getView(R.id.ivSee).setVisibility(View.GONE);
                    holder.getView(R.id.txtNum).setVisibility(View.VISIBLE);
                    holder.getView(R.id.txtDate).setVisibility(View.VISIBLE);
                    setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", item.getDxPlatTicket().getAmount()), "#E04F2E");
                    holder.setText(R.id.txtNum, String.format("￥ %s", item.getDxPlatTicket().getAmount()));
                    holder.setText(R.id.txtRange, String.format("满%s元可用", item.getDxPlatTicket().getAchieveMoney()));
                    holder.setText(R.id.txtDate, String.format("有效期 %s", item.getDxPlatTicket().getEndTime().substring(0, 10)));
                } else {
                    holder.getView(R.id.llNormal).setVisibility(View.GONE);
                    holder.getView(R.id.txtDecrease).setVisibility(View.VISIBLE);
                    holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                    Glide.with(SelectCoupon.this).load(R.mipmap.newdecrease).into((ImageView)holder.getView(R.id.ivSee));
                    holder.getView(R.id.txtNum).setVisibility(View.GONE);
                    holder.getView(R.id.txtDate).setVisibility(View.GONE);
                    holder.setText(R.id.txtDecrease, String.format("%s", item.getCinemaName()));
                }
                if (MyApplication.selectedId == singleData.get(position).getId()){
                    holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                }else {
                    holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                }
                if (MyApplication.selectedSingleProdectId == item.getId()) {
                    holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                }
            }
        };
        list.setAdapter(adapterSingleProdect);
    }
    private void setTextColor(TextView textView,String text,String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(colorSpan, 0, text.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }


    private void setPullRefresher(){
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlCancle://不使用优惠券
                Intent intent = new Intent();
                intent.putExtra("id", -1);
                MyApplication.selectedId = -1;
                intent.putExtra("discount", -1);
                SelectCoupon.this.setResult(RESULT_OK, intent);
                SelectCoupon.this.finish();
                break;
                default:
                    break;
        }
    }
}
