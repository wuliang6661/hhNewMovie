package com.myp.hhcinema.ui.personorder.ordermessage;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hedan.textdrawablelibrary.TextViewDrawable;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.entity.OrderDetailBO;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.applicationforrefund.applicationforrefund;
import com.myp.hhcinema.util.BigDecimalUtils;
import com.myp.hhcinema.util.CimemaUtils;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.ZxingUtils;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * MVPPlugin
 * 订单详情页面
 */

public class OrderMessageActivity extends MVPBaseActivity<OrderMessageContract.View,
        OrderMessagePresenter> implements OrderMessageContract.View, View.OnClickListener {
    @Bind(R.id.movies_img)
    ImageView movieImg;
    @Bind(R.id.movies_name)
    TextView moviesname;
    @Bind(R.id.movies_type)
    TextView moviestype;
    @Bind(R.id.movies_address)
    TextView moviesaddress;
    @Bind(R.id.movies_time)
    TextView moviestime;
    @Bind(R.id.movies_seat)
    TextView moviesSeat;
    @Bind(R.id.movies_num)
    TextView moviesnum;
    @Bind(R.id.collect_tickets2)
    TextView collectTickets2;//取票码
    @Bind(R.id.movies_price)
    TextView moviesPrice;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.phone_num)
    TextView phoneNum;
    @Bind(R.id.pay_time)
    TextView paytime;
    @Bind(R.id.add_ress)
    TextView address;
    @Bind(R.id.tuipiao)
    TextView tuipiao;
    @Bind(R.id.yingcheng)
    TextView yingcheng;
    @Bind(R.id.fenxiang_img)
    ImageView fenxiangimg;
    @Bind(R.id.call_phone)
    ImageView callphone;
    @Bind(R.id.layout_view)
    LinearLayout linear;
    @Bind(R.id.ivPrinted)
    ImageView ivPrinted;//已打印
    @Bind(R.id.ivGot)
    ImageView ivGot;//已领取
    @Bind(R.id.prodectList)
    RecyclerView prodectList;//小食列表
    @Bind(R.id.txtProdectCode)
    TextView txtProdectCode;//小食领取码
    @Bind(R.id.prodectCode)
    ImageView prodectCode;
    @Bind(R.id.ticketCode)
    ImageView ticketCode;
    @Bind(R.id.ivRefundTickets)
    ImageView ivRefundTickets;//已退票
    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.ivLine)
    ImageView ivLine;
    @Bind(R.id.llProdect)
    LinearLayout llProdect;
    @Bind(R.id.ivTicketCancle)
    ImageView ivTicketCancle;
    @Bind(R.id.ivProdectCancle)
    ImageView ivProdectCancle;
    @Bind(R.id.llTicketLayout)
    LinearLayout llTicketLayout;
    @Bind(R.id.LLTitle)
    LinearLayout LLTitle;
    @Bind(R.id.line1)
    ImageView line1;
    @Bind(R.id.commentContent)
    TextView commentContent;
    @Bind(R.id.moneyName)
    TextView moneyName;
    @Bind(R.id.collect_tickets1)
    TextView collect_tickets1;//序列号
    @Bind(R.id.txtProdectCode1)
    TextView txtProdectCode1;//卖品序列号
    @Bind(R.id.ticketOldPrice)
    TextView ticketOldPrice;//影票原价
    @Bind(R.id.ticketCutPrice)
    TextView ticketCutPrice;//影票优惠
    @Bind(R.id.prodectOldPrice)
    TextView prodectOldPrice;//卖品原价
    @Bind(R.id.prodectCutPrice)
    TextView prodectCutPrice;//卖品优惠
    @Bind(R.id.rlTicketOldPrice)
    RelativeLayout rlTicketOldPrice;
    @Bind(R.id.rlTicketCutPrice)
    RelativeLayout rlTicketCutPrice;
    @Bind(R.id.rlProdectOldPrice)
    RelativeLayout rlProdectOldPrice;
    @Bind(R.id.rlProdectCutPrice)
    RelativeLayout rlProdectCutPrice;
    @Bind(R.id.refundFee)
    TextView refundFee;
    @Bind(R.id.rlRefundFee)
    RelativeLayout rlRefundFee;
    @Bind(R.id.noticeName)
    TextView noticeName;
    @Bind(R.id.text1)
    TextViewDrawable text1;
    @Bind(R.id.text2)
    TextViewDrawable text2;
    @Bind(R.id.text3)
    TextViewDrawable text3;
    @Bind(R.id.refreshOrder)
    Button refreshOrder;

    private String orderDetailNum = "";
    private String id = "";
    private OrderDetailBO orderDetailBO;
    private String cinemaId;
    private List<OrderDetailBO.MerOrderBean.DetailsBean> prodectData = new ArrayList<>();
    private CommonAdapter<OrderDetailBO.MerOrderBean.DetailsBean> adapter;

    private boolean canClick = true;
    @Override
    protected int getLayout() {
        return R.layout.act_order_message;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("订单详情");
        orderDetailNum = getIntent().getExtras().getString("orderNum");
        id = getIntent().getStringExtra("id");
        cinemaId = getIntent().getStringExtra("cinemaId");
        showProgress("加载中...");
        mPresenter.quryOrderMessage(id, cinemaId);
        fenxiangimg.setOnClickListener(this);
        callphone.setOnClickListener(this);
        tuipiao.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        prodectList.setLayoutManager(layoutManager);
        refreshOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canClick){
                    showProgress("加载中...");
                    mPresenter.quryOrderMessage(id, cinemaId);
                    canClick = false;
                }
            }
        });
    }


    public static String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }


    private void setdata(final OrderDetailBO orderMessage) {
        if (orderMessage.getMerOrder() != null) {
            prodectData.clear();
            prodectData.addAll(orderMessage.getMerOrder().getDetails());
            setAdapter();
        }
        if (orderMessage.getMerOrder() != null) {
            llProdect.setVisibility(View.VISIBLE);
            ivLine.setVisibility(View.VISIBLE);

            if (orderMessage.getGoodsEwm() != null && !orderMessage.getGoodsEwm().isEmpty()) {
                Bitmap bitmap;
                if (orderMessage.getGoodsEwm().startsWith("http")) {
                    bitmap = ZxingUtils.createQRImage(orderMessage.getGoodsEwm(), prodectCode.getWidth(), prodectCode.getHeight());
                }else {
                    bitmap = ZxingUtils.createQRImage(HttpInterface.URL + orderMessage.getGoodsEwm(), prodectCode.getWidth(), prodectCode.getHeight());
                }
                prodectCode.setImageBitmap(bitmap);
                txtProdectCode.setText(orderMessage.getTicketFlag2());
                collectTickets2.setText(orderMessage.getTicketEwm());
            } else {
                prodectCode.setBackgroundResource(R.drawable.erweimshixiao);
            }

            txtProdectCode1.setText(orderMessage.getTicketFlag2());//序列号
            txtProdectCode.setText(orderMessage.getTicketFlag2());//验证码
            rlProdectOldPrice.setVisibility(View.VISIBLE);
            rlProdectCutPrice.setVisibility(View.VISIBLE);
            prodectOldPrice.setText(String.format("%s 元",
                    BigDecimalUtils.round(
                            String.valueOf(Math.abs(Double.valueOf(orderMessage.getMerOrder().getBeforeTicketPrice()))),
                            2)));//卖品原价
            prodectCutPrice.setText(String.format("-%s 元",
                    BigDecimalUtils.round(
                            String.valueOf(
                                    Math.abs(
                                            Double.valueOf(
                                                    BigDecimalUtils.sub(
                                                            orderMessage.getMerOrder().getBeforeTicketPrice(),
                                                            orderMessage.getMerOrder().getDisPrice(),
                                                            2)))),
                            2)));//卖品优惠价格 = 卖品原价 - 卖品实付
        } else {
            llProdect.setVisibility(View.GONE);
            ivLine.setVisibility(View.GONE);
            rlProdectOldPrice.setVisibility(View.GONE);
            rlProdectCutPrice.setVisibility(View.GONE);
        }
        if (orderMessage.getDxMovie() != null) {
            llTicketLayout.setVisibility(View.VISIBLE);
            LLTitle.setVisibility(View.VISIBLE);
            line1.setVisibility(View.VISIBLE);
            if (StringUtils.isEmpty(orderMessage.getDxMovie().getPicture())) {
                movieImg.setImageResource(R.color.act_bg01);
            } else {
                Picasso.with(this).load(orderMessage.getDxMovie().getPicture()).into(movieImg);
            }
            moviesname.setText(orderMessage.getDxMovie().getMovieName());
            if (orderMessage.getMovieLanguage() != null){
                moviestype.setText(orderMessage.getMovieLanguage() + " " + orderMessage.getMovieDimensional()+orderMessage.getMovieSize());
            }else {
                moviestype.setText(orderMessage.getDxMovie().getMovieLanguage() + " " + orderMessage.getDxMovie().getMovieDimensional());
            }
            moviesaddress.setText(String.format("%s %s", orderMessage.getCinemaName(), orderMessage.getHallName()));
            if (!StringUtils.isEmpty(orderMessage.getPlayName())) {
                String time = orderMessage.getPlayName().substring(0, orderMessage.getPlayName().length() - 3);
                moviestime.setText(time);
            } else {
                moviestime.setText("");
            }
            if (orderMessage.getSeats().contains("_")) {
                moviesSeat.setText(CimemaUtils.getSeats(orderMessage.getSeats()));
            } else {
                moviesSeat.setText(orderMessage.getSeats());
            }
            yingcheng.setText(orderMessage.getCinemaName());

            rlTicketOldPrice.setVisibility(View.VISIBLE);
            rlTicketCutPrice.setVisibility(View.VISIBLE);
            ticketOldPrice.setText(String.format("%s 元",
                    BigDecimalUtils.round(
                            String.valueOf(Math.abs(Double.valueOf(orderMessage.getBeforeTicketPrice()))),
                            2)));//影票原价
            ticketCutPrice.setText(String.format("-%s 元",
                    BigDecimalUtils.round(
                            String.valueOf(
                                    Math.abs(
                                            Double.valueOf(
                                                    BigDecimalUtils.sub(orderMessage.getBeforeTicketPrice(),
                                                    orderMessage.getTotalDisprice(),
                                                            2)))),
                            2)));//影票优惠 = 影票原价 - 影票实付

            //影票须知
            noticeName.setText("观影须知");
            text1.setText(getResources().getString(R.string.order_state1));
            text2.setText(getResources().getString(R.string.order_state2));
            text3.setText(getResources().getString(R.string.order_state3));
        } else {
            llTicketLayout.setVisibility(View.GONE);
            LLTitle.setVisibility(View.GONE);
            ivLine.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            rlTicketOldPrice.setVisibility(View.GONE);
            rlTicketCutPrice.setVisibility(View.GONE);
            yingcheng.setText(orderMessage.getCinemaName());

            //卖品须知
            noticeName.setText("卖品须知");
            text1.setText("请前往影城前台出示取货码并领取您的卖品。");
            text2.setText("卖品订单不支持退款。");
            text3.setVisibility(View.GONE);
        }
        orderNum.setText(orderMessage.getOrderNum());
        phoneNum.setText(settingphone(orderMessage.getDxAppUser().getMobile()));
        paytime.setText(orderMessage.getPayDate());
        if (orderMessage.getMemo() != null && !orderMessage.getMemo().equals("")) {
            commentContent.setText(orderMessage.getMemo());//备注
        } else {
            commentContent.setText("暂无备注");//备注
        }
        if (orderMessage.getRefundStatus() == null) {
            tuipiao.setVisibility(View.GONE);
            rlRefundFee.setVisibility(View.GONE);
        } else {
            if (orderMessage.getRefundStatus().equals("1")) {/* 已退票 */
                moneyName.setText("退款金额");
                moviesPrice.setText(String.format("-%s元", Math.abs(Double.valueOf(orderMessage.getPayPrice()))));
                moviesnum.setText(String.format("%s张票", Math.abs(orderMessage.getTicketNum())));
                tuipiao.setVisibility(View.GONE);
                ivRefundTickets.setVisibility(View.VISIBLE);
                ivGot.setVisibility(View.VISIBLE);
                ivPrinted.setVisibility(View.VISIBLE);
                ivTicketCancle.setVisibility(View.VISIBLE);
                ivProdectCancle.setVisibility(View.VISIBLE);
                ivGot.setImageDrawable(getResources().getDrawable(R.mipmap.refund));
                ivPrinted.setImageDrawable(getResources().getDrawable(R.mipmap.refund));

                rlRefundFee.setVisibility(View.VISIBLE);//手续费
                refundFee.setText(String.format("%s 元",orderMessage.getRefundHandFee()));

                txtProdectCode1.setText("xxxxxx");//序列号
                txtProdectCode.setText("xxxxxx");//验证码
            } else {
                rlRefundFee.setVisibility(View.GONE);//手续费
                moneyName.setText("实付金额");
                moviesPrice.setText(String.format("%s元", orderMessage.getPayPrice()));
                moviesnum.setText(String.format("%s张票", Math.abs(orderMessage.getTicketNum())));
                if (orderMessage.getCanRefund() == 1) {
                    tuipiao.setVisibility(View.VISIBLE);
                    tuipiao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", orderMessage);
                            bundle.putString("cinemaId", orderMessage.getCinema().getCinemaId());
                            bundle.putString("id", id);
                            gotoActivity(applicationforrefund.class, bundle, false);
                        }
                    });
                } else {
                    tuipiao.setVisibility(View.GONE);
                }
            }
        }
        address.setText(orderMessage.getCinema().getAddress());
        if (orderMessage.getQrCode() == null || orderMessage.getQrCode().isEmpty()) {
            if (orderMessage.getTicketEwm() != null && !orderMessage.getTicketEwm().isEmpty()) {
                Bitmap bitmap;
                if (orderMessage.getTicketEwm().startsWith("http")) {
                    bitmap = ZxingUtils.createQRImage(orderMessage.getTicketEwm(), ticketCode.getWidth(), ticketCode.getHeight());
                }else {
                    bitmap = ZxingUtils.createQRImage(HttpInterface.URL + orderMessage.getTicketEwm(), ticketCode.getWidth(), ticketCode.getHeight());
                }
                ticketCode.setImageBitmap(bitmap);
                refreshOrder.setVisibility(View.GONE);
            } else {
                ticketCode.setBackgroundResource(R.drawable.erweimshixiao);
                refreshOrder.setVisibility(View.VISIBLE);
            }
        } else {
            Bitmap bitmap;
            if (orderMessage.getQrCode() != null) {
                if (orderMessage.getQrCode().startsWith("http")) {
                    bitmap = ZxingUtils.createQRImage(orderMessage.getQrCode(), ticketCode.getWidth(), ticketCode.getHeight());
                } else {
                    bitmap = ZxingUtils.createQRImage(HttpInterface.URL + orderMessage.getQrCode(), ticketCode.getWidth(), ticketCode.getHeight());
                }
                ticketCode.setImageBitmap(bitmap);
                refreshOrder.setVisibility(View.GONE);
            }else {
                ticketCode.setBackgroundResource(R.drawable.erweimshixiao);
                refreshOrder.setVisibility(View.VISIBLE);
            }
            if (orderMessage.getRefundStatus().equals("1")) {/* 已退票 */
                collect_tickets1.setText("xxxxxx");//序列号
                collectTickets2.setText("xxxxxx");//验证码
                refreshOrder.setVisibility(View.GONE);
            }else {
                collect_tickets1.setText(orderMessage.getTicketFlag1());//序列号
                collectTickets2.setText(orderMessage.getTicketFlag2());//验证码
            }
        }
        fenxiangimg.setVisibility(View.GONE);
    }

    /**
     * 食品列表
     */
    private void setAdapter() {
        adapter = new CommonAdapter<OrderDetailBO.MerOrderBean.DetailsBean>(this, R.layout.orderdetail_prodect_item, prodectData) {
            @Override
            protected void convert(ViewHolder mholder, OrderDetailBO.MerOrderBean.DetailsBean detailsBean, int position) {
                if (detailsBean.getType() == 2) { // 2 套餐
                    if (detailsBean.getItemCombo() != null) {
                        mholder.setText(R.id.txtName, detailsBean.getItemCombo().getName());
                        mholder.setText(R.id.txtNumber, String.format("%s份", detailsBean.getNumber()));
                    }else {
                        mholder.setText(R.id.txtName, detailsBean.getMerchandise().getName());
                        mholder.setText(R.id.txtNumber, String.format("%s份", detailsBean.getNumber()));
                    }
                } else {
                    if (detailsBean.getMerchandise() != null) {
                        mholder.setText(R.id.txtName, detailsBean.getMerchandise().getName());
                        mholder.setText(R.id.txtNumber, String.format("%s份", detailsBean.getNumber()));
                    }else {
                        mholder.setText(R.id.txtName, detailsBean.getItemCombo().getName());
                        mholder.setText(R.id.txtNumber, String.format("%s份", detailsBean.getNumber()));
                    }
                }
            }
        };
        prodectList.setAdapter(adapter);
    }

    @Override
    public void onRequestError(String msg) {
        stopProgress();
        LogUtils.showToast(msg);
        canClick = true;
    }

    @Override
    public void onRequestEnd() {
        stopProgress();
    }

    @Override
    public void getOrderMessage(OrderDetailBO orderMessage) {
        stopProgress();
        canClick = true;
        this.orderDetailBO = orderMessage;
        setdata(orderMessage);
    }


    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + orderDetailBO.getCinema().getContact()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_phone:
                callPhone();
                break;
            case R.id.fenxiang_img:
//                logo = orderBO.getDxMovie().getPicture();
//                String content = orderBO.getDxMovie().getMovieName();
//                String url = orderMessage.getData().getShareUrl();
//                String title = "好友给你分享电影票";
//                ShareBO shareBO = new ShareBO(content,logo, url, title);
//                new ShareDialog(this, shareBO).showAtLocation(linear, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tuipiao:
//                showCancleDialog();
                break;
            default:
                break;
        }
    }
}
