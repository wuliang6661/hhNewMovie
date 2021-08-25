package com.myp.hhcinema.ui.prodectorder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.MerchandBO;
import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.PayResult;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.ResuBo;
import com.myp.hhcinema.entity.SubmitPrdectOrderBO;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.membercard.MemberCardActivity;
import com.myp.hhcinema.ui.orderconfrim.OrderSurcessActivity;
import com.myp.hhcinema.ui.pay.PayActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.wxapi.WXUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;


/**
 * Created by witness on 2018/11/19.
 *  确认小食订单
 */

public class ProdectOrderActivity extends MVPBaseActivity<ProdectOrderContract.View,
        ProdectOrderPresenter> implements ProdectOrderContract.View,
        AdapterView.OnItemClickListener,View.OnClickListener {

    @Bind(R.id.go_back)
    LinearLayout go_back;
//    @Bind(R.id.rlCommit)
//    RelativeLayout rlCommit;//确认订单
    @Bind(R.id.prodectList)
    RecyclerView prodectList;//商品列表
    @Bind(R.id.tvPrice)
    TextView tvPrice;//小食总价
    @Bind(R.id.activityPrice)
    TextView activityPrice;//满减活动优惠价格
    @Bind(R.id.imageType)
    ImageView imageType;//取餐方式图片
    @Bind(R.id.txtWayLine1)
    TextView txtWayLine1;//取餐方式第一行文字
    @Bind(R.id.txtWayLine2)
    TextView txtWayLine2;//取餐方式第二行文字
    @Bind(R.id.txtAddress)
    TextView txtAddress;
    @Bind(R.id.txtPhone)
    TextView txtPhone;//手机号
    @Bind(R.id.selectProdectCoupon)
    RelativeLayout selectProdectCoupon;
    @Bind(R.id.txtProdectCouponNum)
    TextView txtProdectCouponNum;
    @Bind(R.id.rlUpdatePhone)
    RelativeLayout rlUpdatePhone;
    @Bind(R.id.submit_button)
    RelativeLayout submit_button;
    @Bind(R.id.commentContent)
    TextView commentContent;
//    @Bind(R.id.decrease)
//    TextView decrease;//满减活动金额
    @Bind(R.id.txtDecreaseInstruction)
    TextView txtDecreaseInstruction;//满减活动
    @Bind(R.id.txtDes)
    TextView txtDes;
    @Bind(R.id.txtDesNum)
    TextView txtDesNum;
    @Bind(R.id.rlDecreaseActivity)
    RelativeLayout rlDecreaseActivity;
    @Bind(R.id.rlActivity)
    RelativeLayout rlActivity;
    @Bind(R.id.cardPayNum)
    TextView cardPayNum;//会员卡价格
    @Bind(R.id.AliPay)
    TextView AliPay;//支付宝支付价格
    @Bind(R.id.WechatPay)
    TextView WechatPay;//微信支付价格
    @Bind(R.id.rlCard)
    RelativeLayout rlCard;//会员卡支付
    @Bind(R.id.rlAliPay)
    RelativeLayout rlAliPay;//支付宝支付
    @Bind(R.id.rlWeChatPay)
    RelativeLayout rlWeChatPay;//微信支付
    @Bind(R.id.ivCardPay)
    ImageView ivCardPay;//会员卡
    @Bind(R.id.ivAliPay)
    ImageView ivAliPay;//支付宝
    @Bind(R.id.ivWechatPay)
    ImageView ivWechatPay;//微信
    @Bind(R.id.totalPrice)
    TextView totalPrice;//现价
    @Bind(R.id.OldPrice)
    TextView OldPrice;//原价
    @Bind(R.id.DecreaseSuccess)
    RelativeLayout DecreaseSuccess;//已满足满减条件
    @Bind(R.id.DecreaseSuccessInstruction)
    TextView DecreaseSuccessInstruction;
    @Bind(R.id.rlFrontDesk)
    RelativeLayout rlFrontDesk;//柜台自取
    @Bind(R.id.ivFrontDesk)
    ImageView ivFrontDesk;//柜台自取
    @Bind(R.id.rlSeat)
    RelativeLayout rlSeat;//送至座位
    @Bind(R.id.ivSeat)
    ImageView ivSeat;//送至座位
    @Bind(R.id.txtCouponName)
    TextView txtCouponName;
    @Bind(R.id.rlComment)
    RelativeLayout rlComment;


    private CommonAdapter<ProdectBO.DetailsBean> adapter;
    private List<ProdectBO.DetailsBean> data = new ArrayList<>();
    private ProdectBO prodectBO;
    private ProdectBO firstProdectBO;
    private ProdectBO changeProdectBO;
    private ArrayList<String> goodsId = new ArrayList<>();
    private SPUtils spUtils;
    private String merchandiseInfo="";
    private String phone = "";
    private String comment = "";
    private SubmitPrdectOrderBO submitPrdectOrderBO;
    private String merTicketId = "";
    private String payPrice;
    private String vipPrice;

    private int payType = 3;//支付方式，  1  会员卡  2  支付宝   3 微信
    private boolean isSubmit = false;//是否已经提交过支付

    private String sendAddress = "";//配送地址
    private int deliveryType = 0;//配送方式 0自提，1配送
    @Override
    protected int getLayout() {
        return R.layout.prodect_order_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("确认订单");
        spUtils = new SPUtils("ProdectItemNum");
        selectProdectCoupon.setOnClickListener(this);
//        rlCommit.setOnClickListener(this);
        rlUpdatePhone.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        rlCard.setOnClickListener(this);
        rlAliPay.setOnClickListener(this);
        rlWeChatPay.setOnClickListener(this);
        rlFrontDesk.setOnClickListener(this);
        rlSeat.setOnClickListener(this);
        rlComment.setOnClickListener(this);
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null) {
            prodectBO = (ProdectBO) bundle.getSerializable("ProdectBO");
            sendAddress = bundle.getString("sendAddress");
            deliveryType = bundle.getInt("deliveryType");
            firstProdectBO = prodectBO;
            changeProdectBO = prodectBO;
            goodsId = bundle.getStringArrayList("goodsId");
            setAdapter();
            data.addAll(prodectBO.getDetails());
        }
        if (deliveryType == 0){
            Glide.with(ProdectOrderActivity.this).load(R.mipmap.getdesk).into(imageType);
            txtWayLine1.setText("柜台自取");
            txtWayLine2.setText("请前往柜台，凭订单二维码领取");
            txtWayLine2.setTextColor(Color.parseColor("#878787"));
        }else {
            Glide.with(ProdectOrderActivity.this).load(R.mipmap.seat).into(imageType);
            txtWayLine1.setText("送至座位");
            txtWayLine2.setText("请确保您此时正在座位上");
            txtWayLine2.setTextColor(Color.parseColor("#D35458"));
            txtAddress.setText(sendAddress);
        }
        txtPhone.setText(MyApplication.user.getMobile());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        prodectList.setLayoutManager(linearLayoutManager);
        tvPrice.setText(String.format("%s元",prodectBO.getDisPrice()));
        AliPay.setText(String.format("%s",prodectBO.getDisPrice()));
        WechatPay.setText(String.format("%s",prodectBO.getDisPrice()));
        cardPayNum.setText(String.format("%s",prodectBO.getVipdisPrice()));
        payPrice = String.valueOf(prodectBO.getDisPrice());
        vipPrice = String.valueOf(prodectBO.getVipdisPrice());
        if (prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice() == 0 ){
            rlActivity.setVisibility(View.GONE);
        }else {
            rlActivity.setVisibility(View.VISIBLE);
            activityPrice.setText(String.format("-%s元", new DecimalFormat("0.00").format(prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice())));
        }
        if (prodectBO != null && prodectBO.getMerTicket() !=null){
            merTicketId = String.valueOf(prodectBO.getMerTicket().getId());
        }
        if (prodectBO.getMerTicket() != null && prodectBO.getMerTicket().getId() < -1){
            txtCouponName.setText("满减活动");
        }else {
            txtCouponName.setText("小食优惠券");
        }
        txtPhone.setText(String.valueOf(MyApplication.user.getMobile()));
        if (prodectBO.getMerTicketList() != null
                && prodectBO.getMerTicketList().size()>0){
            selectProdectCoupon.setVisibility(View.VISIBLE);
        }else {
            selectProdectCoupon.setVisibility(View.GONE);
        }
//        if (MyApplication.prodectWay == 0){
//            txtWayLine2.setVisibility(View.GONE);
//            txtWayLine1.setVisibility(View.VISIBLE);
//            txtWay.setText("柜台自取");
//        }else {
//            txtWayLine2.setVisibility(View.VISIBLE);
//            txtWayLine1.setVisibility(View.GONE);
//            txtWay.setText("送至影厅门口");
//            txtWayLine2.setText(MyApplication.prodectWayString);
//        }
        //设置默认为微信支付
        ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
        ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
        ivWechatPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
        totalPrice.setText(String.format("合计：%s",WechatPay.getText().toString()));
        if (payType != 1) {
            modifyPrice("", 1,false);
        }else {
            modifyPrice("", 2,false);
        }
    }

    private void modifyPrice(String id,int changePayType,boolean changeType){//1 非会员，2 会员。  changeType  是否是切换支付方式
        Gson gson=new Gson();
        List<MerchandBO> list = new ArrayList<>();
        for (int i=0;i<goodsId.size();i++) {
            if (spUtils.getInt(String.valueOf(goodsId.get(i))) != 0) {
                MerchandBO merchandBO = new MerchandBO(Long.parseLong(goodsId.get(i)),Long.parseLong(String.valueOf(spUtils.getInt(String.valueOf(goodsId.get(i))))));
                list.add(merchandBO);
            }
        }
        if (list.size()>0) {
            merchandiseInfo = gson.toJson(list);
            if (MyApplication.cinemaBo != null) {
                mPresenter.getOrderPrice(merchandiseInfo, id,String.valueOf(MyApplication.cinemaBo.getCinemaId()),String.valueOf(changePayType),changeType);
            }
         }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置食品列表数据
     */
    private void setAdapter() {
        adapter = new CommonAdapter<ProdectBO.DetailsBean>(this,
                R.layout.item_classify_detail, data) {
            @Override
            protected void convert(ViewHolder holder, ProdectBO.DetailsBean item, int position) {
                holder.getView(R.id.ivAdd).setVisibility(View.GONE);
                Glide.with(ProdectOrderActivity.this)
                        .load(item.getMerchandise().getImageUrl())
                        .asBitmap()
                        .centerCrop()
                        .dontAnimate()
                        .placeholder(R.drawable.zhanwei1)
                        .into((ImageView) holder.getView(R.id.ivAvatar));
                holder.setText(R.id.txtName,item.getMerchandise().getName());
                holder.getView(R.id.txtNoPrice).setVisibility(View.GONE);
                holder.setText(R.id.txtPrice,String.format("￥ %s",item.getMerchandise().getOnlinePrice()));
                holder.getView(R.id.rlSelectP).setVisibility(View.GONE);
                holder.getView(R.id.rlNum).setVisibility(View.VISIBLE);
                holder.setText(R.id.num,String.format("数量：%s",String.valueOf(item.getNumber())));
                if (item.getMerchandise().getMerdesc() != null && !item.getMerchandise().getMerdesc().equals("")){
                    holder.getView(R.id.txtDes).setVisibility(View.GONE);
                }else {
                    holder.getView(R.id.txtDes).setVisibility(View.VISIBLE);
                    holder.setText(R.id.txtDes,item.getMerchandise().getMerdesc());
                }
            }
        };
        prodectList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selectProdectCoupon:
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("tag","prodectSingle");
//                bundle2.putSerializable("prodectBO", changeProdectBO);
//                Intent intent2 = new Intent(this,SelectCoupon.class);
//                intent2.putExtras(bundle2);
//                startActivityForResult(intent2,1);
                showCouponDialog();
                break;
            case R.id.submit_button:
                if (payType == -1){
                    ToastUtils.showShortToast("请选择支付方式");
                    return;
                }
                if (deliveryType == 1) {//配送
                    LayoutInflater factory = LayoutInflater.from(this);//提示框
                    final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
                    TextView cancle = (TextView) view.findViewById(R.id.off_commit);
                    TextView commit = (TextView) view.findViewById(R.id.commit);
                    final TextView title = view.findViewById(R.id.title);
                    final TextView message = view.findViewById(R.id.message);
                    title.setText(String.format("确认送到%s%s?", MyApplication.cinemaBo.getCinemaName() ,sendAddress));
                    message.setText("");
                    cancle.setText("否");
                    commit.setText("是");
                    final AlertDialog dialog = new AlertDialog.Builder(this).create();
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //事件
                            dialog.dismiss();
                            if (payType == 1) {//会员卡支付,判断是否有会员卡
                                getCard();
                            } else {
                                submitOrder();
                            }
                        }
                    });
                    dialog.setView(view);
                    dialog.show();
                }else {
                    if (payType == 1) {//会员卡支付,判断是否有会员卡
                        getCard();
                    } else {
                        submitOrder();
                    }
                }
                break;
            case R.id.rlUpdatePhone:
//                showSelectDialog("请输入手机号");
                editphone();
                break;
            case R.id.rlComment:
                showSelectDialog("请输入备注信息");
                break;
            case R.id.rlCard://会员卡支付
                if (payType == 2 || payType == 3) {
                    payType = 1;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    totalPrice.setText(String.format("合计：%s", cardPayNum.getText().toString()));
                    modifyPrice("",2,true);
                    showProgress("");
                }else {
                    payType = 1;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    totalPrice.setText(String.format("合计：%s", cardPayNum.getText().toString()));
                }
                break;
            case R.id.rlAliPay://支付宝支付
                if (payType == 1) {
                    payType = 2;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    totalPrice.setText(String.format("合计：%s", AliPay.getText().toString()));
                    modifyPrice("",1,true);
                    showProgress("");
                }else {
                    payType = 2;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    totalPrice.setText(String.format("合计：%s", AliPay.getText().toString()));
                }
                break;
            case R.id.rlWeChatPay://微信支付
                if (payType == 1) {
                    payType = 3;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    totalPrice.setText(String.format("合计：%s", WechatPay.getText().toString()));
                    modifyPrice("",1,true);
                    showProgress("");
                }else {
                    payType = 3;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    totalPrice.setText(String.format("合计：%s", WechatPay.getText().toString()));
                }
                break;
            case R.id.rlFrontDesk://柜台自取
                ivFrontDesk.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                ivSeat.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                break;
            case R.id.rlSeat://送至座位
                ivFrontDesk.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                ivSeat.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                break;
                default:
                    break;
        }
    }

    /**
     * 提交订单
     */
    private void submitOrder(){
        showProgress("提交订单中...");
        String myPhone;
        if (phone.equals("")){
            myPhone = MyApplication.user.getMobile();
        }else {
            myPhone = phone;
        }
        if (!isSubmit) {//是否已经提交过支付，如果提交过，就不走接口
            mPresenter.submitOrder(merchandiseInfo, comment,
                    MyApplication.cinemaBo.getCinemaNumber(), merTicketId, payPrice, vipPrice
                    , String.valueOf(MyApplication.cinemaBo.getCinemaId()),deliveryType,sendAddress,myPhone);
        }else {
            if (payType == 1){//会员卡支付
                Bundle bundle = new Bundle();
                bundle.putInt("confrim", 2);
                bundle.putSerializable("order", submitPrdectOrderBO);
                gotoActivity(PayActivity.class, bundle, true);
            }else if (payType == 2){//支付宝支付
                mPresenter.loadAliPay(submitPrdectOrderBO.getOrderNum());
            }else {
                mPresenter.loadWeChatPay(submitPrdectOrderBO.getOrderNum());
            }
            stopProgress();
        }
    }

    /**
     * 判断是否有会员卡
     */
    private void getCard(){
        String cinemaId;
        if (MyApplication.cinemaBo != null) {
            cinemaId = String.valueOf(MyApplication.cinemaBo.getCinemaId());
        }else {
            cinemaId = "";
        }
        HttpInterfaceIml.cardUser(cinemaId).subscribe(new Subscriber<List<CardBO>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(List<CardBO> s) {
                if (s != null && s.size()>0){
                    submitOrder();
                }else {
                    openCard();
                }
            }
        });
    }

    /**
     * 跳到开卡界面
     */
    private void openCard(){
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
        TextView cancle = (TextView) view.findViewById(R.id.off_commit);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView message = (TextView) view.findViewById(R.id.message);
        message.setText("是否去开通");
        title.setText("您暂无会员卡");
        cancle.setText("取消");
        commit.setText("确定");
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事件
                dialog.dismiss();
                gotoActivity(MemberCardActivity.class,false);
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                double disprice = 0;
                int id = 0;
                try {
                    disprice = data.getExtras().getDouble("discount");
                    id = data.getExtras().getInt("id");
                    if (disprice == -1 && id == -1){//取消优惠券
                        txtProdectCouponNum.setText("去选择");
                        if (firstProdectBO.getTotalPrice() - firstProdectBO.getBeforeTicketPrice() == 0 ){
                            rlActivity.setVisibility(View.GONE);
                        }else {
                            rlActivity.setVisibility(View.VISIBLE);
                            activityPrice.setText(String.format("-%s元", new DecimalFormat("0.00").format(firstProdectBO.getTotalPrice() - firstProdectBO.getBeforeTicketPrice())));
                        }
                    }else {
                        rlActivity.setVisibility(View.GONE);
                        txtProdectCouponNum.setText(String.format("-%s元", disprice));
                        activityPrice.setText("-0元");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if (payType != 1) {
                        modifyPrice("", 1,false);
                    }else {
                        modifyPrice("", 2,false);
                    }
//                    rlActivity.setVisibility(View.VISIBLE);
//                    txtProdectCouponNum.setText("去选择");
//                    if (prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice() == 0 ){
//                        rlActivity.setVisibility(View.GONE);
//                    }else {
//                        rlActivity.setVisibility(View.VISIBLE);
//                        activityPrice.setText(String.format("-%s元", new DecimalFormat("0.0").format(prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice())));
//                    }
                }

                if (id > 0) {
                    if (payType != 1) {
                        modifyPrice(String.valueOf(id), 1,false);
                    }else {
                        modifyPrice(String.valueOf(id), 2,false);
                    }
                    merTicketId = String.valueOf(id);
                }else {
                    if (id == -1) {
                        if (payType != 1) {
                            modifyPrice("0", 1,false);
                        }else {
                            modifyPrice("0", 2,false);
                        }
                        merTicketId = "0";
                    }else {
                        if (payType != 1) {
                            modifyPrice(String.valueOf(id), 1,false);
                        }else {
                            modifyPrice(String.valueOf(id), 2,false);
                        }
                        merTicketId = String.valueOf(id);
                    }
                }

                if (id < -1){
                    txtCouponName.setText("满减活动");
                }else {
                    txtCouponName.setText("小食优惠券");
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRequestError(String msg) {
        stopProgress();
        ToastUtils.showShortToast(msg);
        if (payType != 1) {
            modifyPrice(merTicketId, 1,false);
        }else {
            modifyPrice(merTicketId, 2,false);
        }
    }

    @Override
    public void onRequestEnd() {
        stopProgress();
    }

    @Override
    public void getOrder(ProdectBO prodectBO,boolean changeType) {
        this.prodectBO = prodectBO;
        if (changeType){//切换支付方式之后重新获取优惠券列表
            changeProdectBO  = prodectBO;
        }
        if (prodectBO.getMerTicket() != null) {
            txtProdectCouponNum.setText(String.format("-%s元", prodectBO.getMerTicket().getDxPlatTicket().getAmount()));
            MyApplication.selectedSingleProdectId = prodectBO.getMerTicket().getId();
        }else {
            txtProdectCouponNum.setText("去选择");
            MyApplication.selectedSingleProdectId = 0;
        }
        if (payType == 1) {//会员卡
            if (prodectBO.getTotalPrice() - prodectBO.getVipbeforeTicketPrice() == 0) {
                rlActivity.setVisibility(View.GONE);
            } else {
                rlActivity.setVisibility(View.VISIBLE);
                activityPrice.setText(String.format("-%s元", new DecimalFormat("0.00").format(prodectBO.getTotalPrice() - prodectBO.getVipbeforeTicketPrice())));
            }
        }else {
            if (prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice() == 0) {
                rlActivity.setVisibility(View.GONE);
            } else {
                rlActivity.setVisibility(View.VISIBLE);
                activityPrice.setText(String.format("-%s元", new DecimalFormat("0.00").format(prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice())));
            }
        }
        //优惠券
        if (prodectBO.getMerTicketList() != null
                && prodectBO.getMerTicketList().size()>0){
            selectProdectCoupon.setVisibility(View.VISIBLE);
        }else {
            selectProdectCoupon.setVisibility(View.GONE);
        }
        //满减活动
        if (prodectBO.getPartOne() != null && !prodectBO.getPartOne().equals("")){
            rlDecreaseActivity.setVisibility(View.VISIBLE);
            DecreaseSuccess.setVisibility(View.GONE);
            if (prodectBO.getPartOne() != null && prodectBO.getPartTwo() == null){
                DecreaseSuccess.setVisibility(View.VISIBLE);
                rlDecreaseActivity.setVisibility(View.GONE);
                DecreaseSuccessInstruction.setText(prodectBO.getPartOne());
            }else {
                DecreaseSuccess.setVisibility(View.GONE);
                rlDecreaseActivity.setVisibility(View.VISIBLE);
                txtDecreaseInstruction.setText(String.format("%s,再买",prodectBO.getPartOne()));
                txtDes.setText(String.format(" %s元,",new DecimalFormat("0.0").format(Double.valueOf(prodectBO.getPartTwo()))));
                txtDesNum.setText(String.format(" %s元",new DecimalFormat("0.0").format(Double.valueOf(prodectBO.getPartThree()))));
            }
        }else {
            DecreaseSuccess.setVisibility(View.GONE);
            rlDecreaseActivity.setVisibility(View.GONE);
        }
        AliPay.setText(String.format("%s元",prodectBO.getDisPrice()));
        WechatPay.setText(String.format("%s元",prodectBO.getDisPrice()));
        cardPayNum.setText(String.format("%s元",prodectBO.getVipdisPrice()));
        payPrice = String.valueOf(prodectBO.getDisPrice());
        vipPrice = String.valueOf(prodectBO.getVipdisPrice());
        if (payType == 1){//会员卡
            totalPrice.setText(String.format("合计：%s元",vipPrice));
            tvPrice.setText(String.format("%s元",vipPrice));
        }else {
            totalPrice.setText(String.format("合计：%s元",payPrice));
            tvPrice.setText(String.format("%s元",payPrice));
        }

//        if (prodectBO.getDisPrice() == 0){
//            txtPay.setText("立即0元支付");
//        }else {
//            txtPay.setText("立即支付");
//        }

//        payType = 3;
//        ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
//        ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
//        ivWechatPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
//        totalPrice.setText(String.format("合计：%s",WechatPay.getText().toString()));
    }

    @Override
    public void getSubmitOrderResult(SubmitPrdectOrderBO submitPrdectOrderBO) {
        this.submitPrdectOrderBO = submitPrdectOrderBO;
        isSubmit = true;
        if (payType == 1){//会员卡支付
            Bundle bundle = new Bundle();
            bundle.putInt("confrim", 2);
            bundle.putSerializable("order", submitPrdectOrderBO);
            gotoActivity(PayActivity.class, bundle, true);
        }else if (payType == 2){//支付宝支付
            mPresenter.loadAliPay(submitPrdectOrderBO.getOrderNum());
        }else {
            mPresenter.loadWeChatPay(submitPrdectOrderBO.getOrderNum());
        }
    }

    @Override
    public void getAliPay(PayBO orderInfo) {
        stopProgress();
        payAliPay(orderInfo.getAlipay());
    }

    @Override
    public void getWxPay(WXPayBO wxPayBO) {
        LocalConfiguration.orderNum = submitPrdectOrderBO.getOrderNum();
        WXUtils.payWX(wxPayBO);
        LocalConfiguration.ordertype = 1;
    }

    @Override
    public void getcardPay(ResuBo resuBo) {
        Bundle bundle = new Bundle();
        bundle.putString("order", submitPrdectOrderBO.getOrderNum());
        bundle.putString("tag","prodect");
        gotoActivity(OrderSurcessActivity.class, bundle, false);
    }

    /**
     * 备注
     */
    private void showSelectDialog(final String text) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.exchange_coupon_dialog, null);
        final RelativeLayout rlClose = (RelativeLayout) v.findViewById(R.id.rlClose);
        final EditText etEnter = (EditText) v.findViewById(R.id.etEnter);
        final RelativeLayout rlSubmit = (RelativeLayout) v.findViewById(R.id.rlSubmit);
        final TextView txtCo = v.findViewById(R.id.txtCo);
        txtCo.setVisibility(View.GONE);
        etEnter.setHint(text);
        etEnter.setHintTextColor(Color.parseColor("#878787"));
        builder.setView(v);
        builder.setCancelable(true);
        final Dialog noticeDialog = builder.create();
        noticeDialog.getWindow().setGravity(Gravity.CENTER);
        noticeDialog.setCancelable(false);
        noticeDialog.show();
        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive()){
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                noticeDialog.dismiss();
            }
        });
        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText  = etEnter.getText().toString();
                if (text.equals("请输入手机号")){
                    if (!TextUtils.isEmpty(inputText)) {
                        txtPhone.setText(inputText);
                        phone = inputText;
                        //隐藏软键盘
                        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if(imm.isActive()){
                            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        }
                        noticeDialog.dismiss();
                    }else {
                        ToastUtils.showShortToast("你还没输入新的手机号呢...");
                    }
                }else {
                    if (!TextUtils.isEmpty(inputText)) {
                        commentContent.setText(inputText);
                        comment = inputText;
                        //隐藏软键盘
                        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if(imm.isActive()){
                            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        }
                        noticeDialog.dismiss();
                    }else {
                        ToastUtils.showShortToast("你还没输入备注呢...");
                    }
                }
            }
        });
        noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(
                        getWindow().getDecorView().getWindowToken(), 0);
            }
        });
        WindowManager.LayoutParams layoutParams = noticeDialog.getWindow().getAttributes();
        layoutParams.width = (int)(ScreenUtils.getScreenWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        noticeDialog.getWindow().setAttributes(layoutParams);
    }

    private void editphone(){
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.editphone_layout, null);//这里必须是final的
        TextView cancle = (TextView) view.findViewById(R.id.off_commit);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        final EditText etEnter = view.findViewById(R.id.etEnter);
        etEnter.setHintTextColor(Color.parseColor("#878787"));
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
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
                //事件
                if (!TextUtils.isEmpty(etEnter.getText().toString())) {
                    txtPhone.setText(etEnter.getText().toString());
                    //隐藏软键盘
                    InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                    }
                    dialog.dismiss();
                }else {
                    ToastUtils.showShortToast("请输入手机号");
                }
            }
        });
        dialog.setView(view);
        dialog.show();
    }
    /**
     * 支付宝调起操作
     */
    private void payAliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ProdectOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 0x11;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝返回结果处理
     * <p>
     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                Bundle bundle = new Bundle();
                bundle.putString("tag","prodect");
                bundle.putString("order", submitPrdectOrderBO.getOrderNum());
                gotoActivity(OrderSurcessActivity.class, bundle, false);
            } else {
                LogUtils.showToast("支付失败");
            }
        }
    };

    /**
     * 关闭
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){
        if (messageEvent.getMessageType().equals("ProdectOrderActivity")){
            finish();
        }
    }


    /**
     * 选择优惠券
     */
    Dialog couponDialog;
    private void showCouponDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.select_coupon_dialog, null);
        final ListView list = v.findViewById(R.id.list);
        SmartRefreshLayout refreshLayout = v.findViewById(R.id.refreshLayout);
        LinearLayout none_layout = v.findViewById(R.id.none_layout);
        Button submitBtn = v.findViewById(R.id.submitBtn);
        if (changeProdectBO.getMerTicketList().size() > 0) {
            refreshLayout.setVisibility(View.VISIBLE);
            none_layout.setVisibility(View.GONE);
        } else {
            refreshLayout.setVisibility(View.GONE);
            none_layout.setVisibility(View.VISIBLE);
        }
        com.myp.hhcinema.widget.superadapter.CommonAdapter<ProdectBO.MerTicketListBean> adapterSingleProdect
                = new com.myp.hhcinema.widget.superadapter.CommonAdapter<ProdectBO.MerTicketListBean>(
                        this, R.layout.select_coupon_item, changeProdectBO.getMerTicketList()) {
            @Override
            protected void convert(com.myp.hhcinema.widget.superadapter.ViewHolder holder, final ProdectBO.MerTicketListBean item, int position) {
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
                    Glide.with(ProdectOrderActivity.this).load(R.mipmap.newdecrease).into((ImageView)holder.getView(R.id.ivSee));
                    holder.getView(R.id.txtNum).setVisibility(View.GONE);
                    holder.getView(R.id.txtDate).setVisibility(View.GONE);
                    holder.setText(R.id.txtDecrease, String.format("%s", item.getCinemaName()));
                }

                if (MyApplication.selectedSingleProdectId == item.getId()) {
                    holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                }

                holder.getView(R.id.itemBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCoupon(item.getId());
                        couponDialog.dismiss();
                    }
                });
            }
        };
        list.setAdapter(adapterSingleProdect);
        builder.setView(v);
        builder.setCancelable(true);
        couponDialog = builder.create();
        couponDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        couponDialog.getWindow().setGravity(Gravity.BOTTOM);
        couponDialog.setCancelable(false);
        couponDialog.show();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCoupon(-1);
                MyApplication.selectedSingleProdectId = -1;
                couponDialog.dismiss();
            }
        });

        WindowManager.LayoutParams layoutParams = couponDialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int)(ScreenUtils.getScreenWidth() * 1.07);
        couponDialog.getWindow().setAttributes(layoutParams);
    }

    private void setTextColor(TextView textView,String text,String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(colorSpan, 0, text.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 设置优惠券
     * @param id 优惠券id
     */
    private void setCoupon(int id){
        double disprice = 0;
        try {
            if (id == -1){//取消优惠券
                txtProdectCouponNum.setText("去选择");
                if (firstProdectBO.getTotalPrice() - firstProdectBO.getBeforeTicketPrice() == 0 ){
                    rlActivity.setVisibility(View.GONE);
                }else {
                    rlActivity.setVisibility(View.VISIBLE);
                    activityPrice.setText(String.format("-%s元", new DecimalFormat("0.00").format(firstProdectBO.getTotalPrice() - firstProdectBO.getBeforeTicketPrice())));
                }
            }else {
                rlActivity.setVisibility(View.GONE);
                txtProdectCouponNum.setText(String.format("-%s元", disprice));
                activityPrice.setText("-0元");
            }
        }catch (Exception e){
            e.printStackTrace();
            if (payType != 1) {
                modifyPrice("", 1,false);
            }else {
                modifyPrice("", 2,false);
            }
//                    rlActivity.setVisibility(View.VISIBLE);
//                    txtProdectCouponNum.setText("去选择");
//                    if (prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice() == 0 ){
//                        rlActivity.setVisibility(View.GONE);
//                    }else {
//                        rlActivity.setVisibility(View.VISIBLE);
//                        activityPrice.setText(String.format("-%s元", new DecimalFormat("0.0").format(prodectBO.getTotalPrice() - prodectBO.getBeforeTicketPrice())));
//                    }
        }

        if (id > 0) {
            if (payType != 1) {
                modifyPrice(String.valueOf(id), 1,false);
            }else {
                modifyPrice(String.valueOf(id), 2,false);
            }
            merTicketId = String.valueOf(id);
        }else {
            if (id == -1) {
                if (payType != 1) {
                    modifyPrice("0", 1,false);
                }else {
                    modifyPrice("0", 2,false);
                }
                merTicketId = "0";
            }else {
                if (payType != 1) {
                    modifyPrice(String.valueOf(id), 1,false);
                }else {
                    modifyPrice(String.valueOf(id), 2,false);
                }
                merTicketId = String.valueOf(id);
            }
        }

        if (id < -1){
            txtCouponName.setText("满减活动");
        }else {
            txtCouponName.setText("小食优惠券");
        }
    }

}
