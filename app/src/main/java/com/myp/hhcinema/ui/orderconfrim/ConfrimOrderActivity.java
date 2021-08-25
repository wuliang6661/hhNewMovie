package com.myp.hhcinema.ui.orderconfrim;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hedan.textdrawablelibrary.TextViewDrawable;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.LocalConfiguration;
import com.myp.hhcinema.entity.CardBO;
import com.myp.hhcinema.entity.ConfirmPayBO;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.MerchandBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.entity.MoviesSessionBO;
import com.myp.hhcinema.entity.PayBO;
import com.myp.hhcinema.entity.PayResult;
import com.myp.hhcinema.entity.WXPayBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.membercard.MemberCardActivity;
import com.myp.hhcinema.ui.pay.PayActivity;
import com.myp.hhcinema.util.AppManager;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.MathExtend;
import com.myp.hhcinema.util.RegexUtils;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.TimeUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.wxapi.WXUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;


/**
 * 确认订单页面
 */

public class ConfrimOrderActivity extends MVPBaseActivity<ConfrimOrderContract.View,
        ConfrimOrderPresenter> implements ConfrimOrderContract.View, View.OnClickListener {


    @Bind(R.id.movies_address)
    TextView orderAddress;
    @Bind(R.id.movies_time)
    TextView orderTime;
    @Bind(R.id.movies_seat)
    TextView seatText;
    @Bind(R.id.order_price)
    TextView orderPrice;
    @Bind(R.id.submit_button)
    RelativeLayout submitButton;
    @Bind(R.id.movies_num)
    TextView moviesnum;
    @Bind(R.id.movies_name)
    TextView moviesName;
    @Bind(R.id.selectCoupon)
    RelativeLayout selectCoupon;//选择优惠券
    @Bind(R.id.txtCouponNum)
    TextView txtCouponNum;//优惠金额
    @Bind(R.id.prodectList)
    RecyclerView prodectList;//小食列表
    @Bind(R.id.selectProdectCoupon)
    RelativeLayout selectProdectCoupon;//选择小食优惠券
    @Bind(R.id.txtProdectCouponNum)
    TextView txtProdectCouponNum;//小食优惠券金额
    @Bind(R.id.ProdectPrice)
    TextView ProdectPrice;//小食总价
    @Bind(R.id.rlActivity)
    RelativeLayout rlActivity;//食品优惠活动
    @Bind(R.id.activityPrice)
    TextView activityPrice;//食品满减活动金额
    @Bind(R.id.phoneNumber)
    TextView phoneNumber;
    @Bind(R.id.rlUpdatePhone)
    RelativeLayout rlUpdatePhone;
    @Bind(R.id.rlComment)
    RelativeLayout rlComment;
    @Bind(R.id.tComment)
    TextView tComment;
    @Bind(R.id.rlProdect)
    RelativeLayout rlProdect;
    @Bind(R.id.rlProdectPrice)
    RelativeLayout rlProdectPrice;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.textFinal)
    TextViewDrawable textFinal;
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
    TextView totalPrice;//支付总价
    @Bind(R.id.rlDecreaseActivity)
    RelativeLayout rlDecreaseActivity;//满减活动
    @Bind(R.id.txtDecreaseInstruction)
    TextView txtDecreaseInstruction;//活动描述
    @Bind(R.id.txtDes)
    TextView txtDes;//再买多少
    @Bind(R.id.txtDesNum)
    TextView txtDesNum;//可减多少
    @Bind(R.id.DecreaseSuccess)
    RelativeLayout DecreaseSuccess;//已满足满减活动条件
    @Bind(R.id.DecreaseSuccessInstruction)
    TextView DecreaseSuccessInstruction;//活动描述
    @Bind(R.id.selectDecreaseActivity)
    RelativeLayout selectDecreaseActivity;//满减活动
    @Bind(R.id.txtDecreaseNum)
    TextView txtDecreaseNum;//满减活动金额


    private MoviesByCidBO movies;
    private MoviesSessionBO sessionBO;
    private int ticketNum;
    private String seatId;
    private String seats;
    private int size;
    private double zongjia;
    private int preferentialnumber;

    CountDownTimer timer;

    private ConfirmPayBO orderBO;
    private LockSeatsBO lockSeatsBO;
    private LockSeatsBO modifyInfo;
    private LockSeatsBO changeBO;//改变支付方式之后的数据
    private LockSeatsBO notChangeNum;//修改满减和选择优惠券的时候存的信息

    private CommonAdapter<LockSeatsBO.ComboListBean> adapter;
    private List<LockSeatsBO.ComboListBean> data = new ArrayList<>();
    private SPUtils spUtils;
    private String  merchandiseInfo = "";//选中的食品信息

    private  int ticketCouponId = 0;//电影票优惠券id
    private  int prodectCouponId = 0;//食品优惠券id

    private String newPhoneNumber = "";
    private String comment = "";//备注
    private double uploadTotalPrice;//修改订单传的总价

    private int mTicketCouponId = 0;//默认选择的优惠券
    private boolean isSuccess = true;//是否请求成功

    private int payType = 3;//支付方式，  1  会员卡  2  支付宝   3 微信
    private boolean isSubmit = false;//是否已经提交过支付

    private String reduceActivityId = "";//满减活动id，默认空字符串，不使用传0
    private boolean isUseDecrease = false;//是否使用满减活动


    private int couponType = 1;//1 影票  2 卖品
    @Override
    protected int getLayout() {
        return R.layout.act_confrim_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("确认订单");
        spUtils = new SPUtils("ConfirmOrder");
        getBundlen();
        inittion();
        submitButton.setOnClickListener(this);
        selectCoupon.setOnClickListener(this);
        selectProdectCoupon.setOnClickListener(this);
        rlUpdatePhone.setOnClickListener(this);
        rlComment.setOnClickListener(this);
        rlCard.setOnClickListener(this);
        rlAliPay.setOnClickListener(this);
        rlWeChatPay.setOnClickListener(this);
        selectDecreaseActivity.setOnClickListener(this);
        String content="下单即代表你同意《汇和时代影业会员服务协议》";
        SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#68AEEA"));
        stringBuilder.setSpan(foregroundColorSpan,8,stringBuilder.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFinal.setText(stringBuilder);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        spUtils.clear();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 获取传递过来的参数
     */
    private void getBundlen() {
        Bundle bundle = getIntent().getExtras();
        movies = (MoviesByCidBO) bundle.getSerializable("movies");
        sessionBO = (MoviesSessionBO) bundle.getSerializable("MoviesSession");
        lockSeatsBO = (LockSeatsBO) bundle.getSerializable("LockSeatsBO");
        modifyInfo = lockSeatsBO;
        changeBO = lockSeatsBO;
        notChangeNum = lockSeatsBO;
        ticketNum = bundle.getInt("num", 0);
        seatId = bundle.getString("seatId", "");
        seats = bundle.getString("seats", "");
        preferentialnumber= bundle.getInt("preferentialnumber", 0);
        uploadTotalPrice = bundle.getDouble("uploadTotalPrice");
        mTicketCouponId = bundle.getInt("ticketCouponId");
        ticketCouponId = mTicketCouponId;
        MyApplication.selectedId = mTicketCouponId;
    }


    /**
     * 初始化界面显示
     */
    private void inittion() {
        moviesName.setText(movies.getMovieName());
        orderAddress.setText(MyApplication.cinemaBo.getCinemaName());
        orderTime.setText(sessionBO.getStartTime());
        String[] seat = seats.split(",");
        size = seat.length;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < seat.length; i++) {
            buffer.append(seat[i].split("_")[0] + "排" + seat[i].split("_")[1] + "座 ");
        }
        seatText.setText(buffer.toString());
        seatText.setText(String.format("%s %s",sessionBO.getHallName(),buffer.toString()));
        moviesnum.setText(String.format("%s张",String.valueOf(ticketNum)));
        orderPrice.setText(String.format("%s",modifyInfo.getTotalDisprice()));
        if(sessionBO.getGlobalLeftNum()==null){
            double zong = (ticketNum)* Double.parseDouble(sessionBO.getMarketPrice());
            BigDecimal bd=new BigDecimal(zong);
            zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        }else {
            if(sessionBO.getPartnerPrice()==null){
                double zong = (ticketNum)* Double.parseDouble(sessionBO.getMarketPrice());
                BigDecimal bd=new BigDecimal(zong);
                zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
            }else {
                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                    if(ticketNum>sessionBO.getLeftScreenLimitNum()){
                        double  youhuijia =sessionBO.getLeftScreenLimitNum() * Double.parseDouble(sessionBO.getPartnerPrice());
                        double noyouhui = (ticketNum-sessionBO.getLeftScreenLimitNum())* Double.parseDouble(sessionBO.getMarketPrice());
                        double zong = youhuijia+noyouhui;
                        BigDecimal bd=new BigDecimal(zong);
                        zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }else {
                        double zong = (ticketNum)* Double.parseDouble(sessionBO.getPartnerPrice());
                        BigDecimal bd=new BigDecimal(zong);
                        zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                }else {
                    if(ticketNum>sessionBO.getGlobalLeftNum()){
                        double  youhuijia =sessionBO.getGlobalLeftNum() * Double.parseDouble(sessionBO.getPartnerPrice());
                        double noyouhui = (ticketNum-sessionBO.getGlobalLeftNum())* Double.parseDouble(sessionBO.getMarketPrice());
                        double zong = youhuijia+noyouhui;
                        BigDecimal bd=new BigDecimal(zong);
                        zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }else {
                        double zong = (ticketNum)* Double.parseDouble(sessionBO.getPartnerPrice());
                        BigDecimal bd=new BigDecimal(zong);
                        zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                }


            }
        }
//        txtCouponNum.setText(String.format("-%s元",MathExtend.subtract(modifyInfo.getBeforeTicketPrice(),
//                modifyInfo.getTotalDisprice()) ));

        phoneNumber.setText(String.format("%s",MyApplication.user.getMobile()));
        AliPay.setText(String.format("￥ %s",modifyInfo.getPayPrice()));
        WechatPay.setText(String.format("￥ %s",modifyInfo.getPayPrice()));
        cardPayNum.setText(String.format("￥ %s",modifyInfo.getVipPayPrice()));

        if (lockSeatsBO.getSeatTicketList() != null && lockSeatsBO.getSeatTicketList().size()>0){
            selectCoupon.setVisibility(View.VISIBLE);
        }else {
            selectCoupon.setVisibility(View.GONE);
        }
        if (modifyInfo.getMerOrder() != null
                && modifyInfo.getMerOrder().getMerTicketList().size()>0){
            selectProdectCoupon.setVisibility(View.VISIBLE);
        }else {
            selectProdectCoupon.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        prodectList.setLayoutManager(layoutManager);
        data.clear();
        data.addAll(modifyInfo.getComboList());
        if (data.size() > 0) {
            prodectList.setVisibility(View.VISIBLE);
            rlProdect.setVisibility(View.VISIBLE);
            rlProdectPrice.setVisibility(View.VISIBLE);
            rlActivity.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            setAdapter();
        }else {
            prodectList.setVisibility(View.GONE);
            rlProdect.setVisibility(View.GONE);
            rlProdectPrice.setVisibility(View.GONE);
            rlActivity.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
        }
        //满减活动描述
        if (modifyInfo.getPartOne() != null && !modifyInfo.getPartOne().equals("")){
            rlDecreaseActivity.setVisibility(View.VISIBLE);
            txtDecreaseInstruction.setText(String.format("%s,再买",modifyInfo.getPartOne()));
            txtDes.setText(String.format("%s元",modifyInfo.getPartTwo()));
            txtDesNum.setText(String.format("%s元",modifyInfo.getPartThree()));
        }else {
            rlDecreaseActivity.setVisibility(View.GONE);
        }
        //满减活动优惠选择
        if (notChangeNum.getDxReduceActivity() != null){
            isUseDecrease = true;
            reduceActivityId = notChangeNum.getDxReduceActivity().getId();
            selectDecreaseActivity.setVisibility(View.VISIBLE);
            txtDecreaseNum.setText(String.format("-%s元",notChangeNum.getDxReduceActivity().getReduceMoney()));
            ticketCouponId = 0;//如果有满减活动，不使用默认选择的优惠券
            selectCoupon.setEnabled(false);
            txtCouponNum.setTextColor(Color.parseColor("#ABABAB"));
            txtCouponNum.setText("不能和优惠活动叠加使用");
            selectProdectCoupon.setEnabled(false);
            txtProdectCouponNum.setTextColor(Color.parseColor("#ABABAB"));
            txtProdectCouponNum.setText("不能和优惠活动叠加使用");
        }else {
            isUseDecrease = false;
            reduceActivityId = "";
            selectDecreaseActivity.setVisibility(View.GONE);
            ticketCouponId = mTicketCouponId;//如果没有满减活动，使用默认选择的优惠券
            selectCoupon.setEnabled(true);
            txtCouponNum.setTextColor(Color.parseColor("#D35458"));
            if (Double.valueOf(MathExtend.subtract(modifyInfo.getBeforeTicketPrice(),
                    modifyInfo.getTotalDisprice())) > 0){
                txtCouponNum.setText(String.format("-%s元",MathExtend.subtract(modifyInfo.getBeforeTicketPrice(),
                        modifyInfo.getTotalDisprice()) ));
            }else {
                txtCouponNum.setText("去使用");
            }
            selectProdectCoupon.setEnabled(true);
            txtProdectCouponNum.setTextColor(Color.parseColor("#D35458"));
            txtProdectCouponNum.setText("去使用");
        }

        //设置微信支付默认选中
        ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
        ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
        ivWechatPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
        totalPrice.setText(String.format("合计：%s",WechatPay.getText().toString()));
    }

    /**
     * 小食列表
     */
    private int goodsNum = 0;
    private int activityNum = -1;//活动次数
    private List<String> goodsId = new ArrayList<>();
    private void setAdapter(){
        adapter = new CommonAdapter<LockSeatsBO.ComboListBean>(this,R.layout.item_classify_detail,data) {
            @Override
            protected void convert(final ViewHolder holder, LockSeatsBO.ComboListBean merchandiseListBean, final int position) {
                holder.setText(R.id.txtName,data.get(position).getName());
                Glide.with(ConfrimOrderActivity.this)
                        .load(data.get(position).getImageUrl())
                        .asBitmap()
                        .centerCrop()
                        .dontAnimate()
                        .placeholder(R.drawable.zhanwei1)
                        .into((ImageView)holder.getView(R.id.ivAvatar));
                TextView txtNoPrice = holder.getConvertView().findViewById(R.id.txtNoPrice);
                txtNoPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                txtNoPrice.setText(String.format("￥ %s",data.get(position).getOriginalPrice()));
                holder.getView(R.id.line).setVisibility(View.GONE);
                if (merchandiseListBean.getMerdesc() != null && merchandiseListBean.getMerdesc().equals("")){
                    holder.getView(R.id.txtDes).setVisibility(View.INVISIBLE);
                }else {
                    holder.getView(R.id.txtDes).setVisibility(View.VISIBLE);
                    holder.setText(R.id.txtDes,merchandiseListBean.getMerdesc());
                }

                if (merchandiseListBean.getPersonNum() == 0){
                    holder.getView(R.id.txtPersonNum).setVisibility(View.GONE);
                }else if (merchandiseListBean.getPersonNum() == 1){
                    holder.getView(R.id.txtPersonNum).setVisibility(View.VISIBLE);
                    holder.setText(R.id.txtPersonNum,String.format("%s","单人"));
                }else if (merchandiseListBean.getPersonNum() == 2){
                    holder.getView(R.id.txtPersonNum).setVisibility(View.VISIBLE);
                    holder.setText(R.id.txtPersonNum,String.format("%s","双人"));
                }else {
                    holder.getView(R.id.txtPersonNum).setVisibility(View.VISIBLE);
                    holder.setText(R.id.txtPersonNum,String.format("%s","多人"));
                }
                if (activityNum == -1 ) {
                    if (data.get(position).getActivityNumber() > 0){
                        holder.setText(R.id.txtPrice,String.format("活动价：￥ %s", data.get(position).getActivityPrice()));
                    }else {
                        holder.setText(R.id.txtPrice, String.format("￥ %s", data.get(position).getSalePrice()));
                    }
                }else {
                    if (activityNum > 0 && data.get(position).getActivityNumber() > 0){
                        if (activityNum > data.get(position).getActivityNumber()){
                            holder.setText(R.id.txtPrice, String.format("￥ %s", data.get(position).getSalePrice()));
                        }else {
                            if (activityNum >0 && activityNum > goodsNum) {
                                holder.setText(R.id.txtPrice,String.format("活动价：￥ %s", data.get(position).getActivityPrice()));
                            }else {
                                holder.setText(R.id.txtPrice, String.format("￥ %s", data.get(position).getSalePrice()));
                            }
                        }
                    }else {
                        holder.setText(R.id.txtPrice, String.format("￥ %s", data.get(position).getSalePrice()));
                    }
                }

                if (goodsId.contains(String.valueOf(data.get(position).getId())) &&
                        spUtils.getInt(String.valueOf(data.get(position).getId())) == 0){
                    goodsId.remove(String.valueOf(data.get(position).getId()));
                }
                if (spUtils.getInt(String.valueOf(data.get(position).getId())) > 0){
                    holder.getView(R.id.rlSelect).setVisibility(View.VISIBLE);
                    holder.setText(R.id.txtNum,String.format("%s", spUtils.getInt(String.valueOf(data.get(position).getId()))));
                    int num2;
                    Double totalSinglePrice;
                    Double singlePrice = 0.0;
                    Double totlePrice = 0.0;
                    for (int i=0;i<goodsId.size();i++){
                        num2 = spUtils.getInt(goodsId.get(i));
                        for (int j=0;j<data.size();j++){
                            if (goodsId.get(i).equals(String.valueOf(data.get(j).getId()))){
                                singlePrice = Double.valueOf(data.get(j).getSalePrice());
                            }
                        }
                        totalSinglePrice = new BigDecimal(MathExtend.multiply(singlePrice,Double.valueOf(String.valueOf(num2)))).doubleValue();
                        totlePrice = MathExtend.add(totlePrice,totalSinglePrice);
                    }
                }else {
                    holder.getView(R.id.rlSelect).setVisibility(View.GONE);
                    if (goodsId.size() == 0){
                        ProdectPrice.setText(String.format("%s", 0));
                        AliPay.setText(String.format("￥ %s",modifyInfo.getPayPrice()));
                        WechatPay.setText(String.format("￥ %s",modifyInfo.getPayPrice()));
                        cardPayNum.setText(String.format("￥ %s",modifyInfo.getVipPayPrice()));
                    }
                    isSuccess = true;
                }

                holder.getView(R.id.ivAdd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSuccess) {
                            isSuccess = false;
                            int num = spUtils.getInt(String.valueOf(data.get(position).getId()));
                            if (num != -1) {
                                goodsNum = num;
                                goodsNum++;
                            } else {
                                goodsNum = 0;
                                goodsNum++;
                            }
                            spUtils.put(String.valueOf(data.get(position).getId()), goodsNum);
                            if (!goodsId.contains(String.valueOf(data.get(position).getId()))) {
                                goodsId.add(String.valueOf(data.get(position).getId()));
                            }
                            if (activityNum == -1) {
                                activityNum = data.get(position).getActivityNumber();
                            }
                            if (activityNum >= 0) {
                                if (activityNum - 1 >= 0) {
                                    activityNum = activityNum - 1;
                                }
                            }
                            if (payType == 1) {//会员
                                modifyPrice("2",false,true);
                            }else {
                                modifyPrice("1",false,true);
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
                holder.getView(R.id.ivDecrease).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSuccess) {
                            isSuccess  = false;
                            int num = spUtils.getInt(String.valueOf(data.get(position).getId()));
                            if (num != -1) {
                                goodsNum = num;
                                goodsNum--;
                            } else {
                                goodsNum = 0;
                                goodsNum--;
                            }
                            spUtils.put(String.valueOf(data.get(position).getId()), goodsNum);
                            if (activityNum + 1 <= data.get(position).getActivityNumber()) {
                                activityNum = activityNum + 1;
                            }
                            if (payType == 1) {//会员
                                modifyPrice("2",false,true);
                            }else {
                                modifyPrice("1",false,true);
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        prodectList.setAdapter(adapter);
    }

    private void modifyPrice(String changePayType,boolean changePaytype,boolean payType){//changePaytype 1 非会员，2 会员。  是否切换会员非会员支付,payType 是否是切换支付方式
        isUseDecrease = true;//增加卖品或者修改支付方式的时候要重新设定优惠券
        Gson gson=new Gson();
        List<MerchandBO> list = new ArrayList<>();
        for (int i=0;i<goodsId.size();i++) {
            if (spUtils.getInt(String.valueOf(goodsId.get(i))) != 0) {
                MerchandBO merchandBO = new MerchandBO(Long.parseLong(goodsId.get(i)),Long.parseLong(String.valueOf(spUtils.getInt(String.valueOf(goodsId.get(i))))));
                list.add(merchandBO);
            }
        }
        String ticketId;
        String prodectId;

        if (ticketCouponId > 0){
            ticketId = String.valueOf(ticketCouponId);
        }else {
            ticketId = "0";
        }
        if (prodectCouponId > 0){
            prodectId = String.valueOf(prodectCouponId);
        }else {
            prodectId = "0";
        }
//        if (list.size()>=0) {
//            selectProdectCoupon.setEnabled(true);
            if (list.size() > 0) {
                merchandiseInfo = gson.toJson(list);
            }else {
                merchandiseInfo = "";
            }
            String activityPriceNum;
            if (lockSeatsBO.getActivityPriceNum() == null){
                activityPriceNum = "";
            }else {
                activityPriceNum = lockSeatsBO.getActivityPriceNum();
            }
            //修改订单价格
        if (MyApplication.cinemaBo != null) {
                if (changePaytype) {//切换为会员卡支付,优惠券id不传
                    mPresenter.modifyOrderPrice(changePayType,"",sessionBO.getDxId(),String.valueOf(MyApplication.cinemaBo.getCinemaId()),sessionBO.getHallId(),sessionBO.getCineMovieNum(), lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum,
                            String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                            "", "", merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                            String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()),payType,false);
                }else {
                    mPresenter.modifyOrderPrice(changePayType,"", sessionBO.getDxId(),String.valueOf(MyApplication.cinemaBo.getCinemaId()), sessionBO.getHallId(),sessionBO.getCineMovieNum(),lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum,
                            String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                            "", "", merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                            String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()),payType,false);
                }
        }
//        }else {
//            selectProdectCoupon.setEnabled(false);
//            txtProdectCouponNum.setText("-0元");
//        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                AppManager.getAppManager().goBackMain();
                break;
            case R.id.submit_button:
                if (payType == -1){
                    ToastUtils.showShortToast("请选择支付方式");
                    return;
                }
                if (payType == 1){//会员卡支付,判断是否有会员卡
                    getCard();
                }else {
                    submitOrder("1");
                }
                break;
            case R.id.call_phone:
                callPhone();
                break;
            case R.id.selectDecreaseActivity://满减活动
                if (notChangeNum.getDxReduceActivity() != null) {
                    showDialog(notChangeNum.getDxReduceActivity().getName(),notChangeNum.getDxReduceActivity().getId(),
                            notChangeNum.getDxReduceActivity().getReduceMoney());
                }else {
                    ToastUtils.showShortToast("暂无满减活动");
                }
                break;
            case R.id.selectCoupon://选择优惠券
//                Bundle bundle = new Bundle();
//                bundle.putString("tag","ticket");
//                bundle.putSerializable("LockSeatsBO", changeBO);
//                bundle.putDouble("totalMoney",zongjia);
//                Intent intent = new Intent(this,SelectCoupon.class);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,1);
                couponType = 1;
                showCouponDialog();
                break;
            case R.id.selectProdectCoupon://选择食品优惠券
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("tag","prodect");
//                bundle2.putSerializable("LockSeatsBO", modifyInfo);
//                Intent intent2 = new Intent(this,SelectCoupon.class);
//                intent2.putExtras(bundle2);
//                startActivityForResult(intent2,2);
                couponType = 2;
                showCouponDialog();
                break;
            case R.id.rlUpdatePhone:
                showSelectDialog("请输入手机号");
                break;
            case R.id.rlComment://备注
                showSelectDialog("请输入备注");
                break;
            case R.id.rlCard://会员卡支付
                if (payType == 2 || payType == 3) {
                    payType = 1;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    totalPrice.setText(String.format("合计：%s", cardPayNum.getText().toString()));
                    reduceActivityId = "";
                    modifyPrice("2",true,true);
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
                    reduceActivityId = "";
                    modifyPrice("1",true,true);
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
                    reduceActivityId = "";
                    modifyPrice("1",true,true);
                    showProgress("");
                }else {
                    payType = 3;
                    ivCardPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivAliPay.setImageDrawable(getResources().getDrawable(R.drawable.image_oval_paynormal));
                    ivWechatPay.setImageDrawable(getResources().getDrawable(R.mipmap.payselect));
                    totalPrice.setText(String.format("合计：%s", WechatPay.getText().toString()));
                }
                break;
                default:
                    break;
        }
    }

    /**
     * 满减活动弹窗
     */
    AlertDialog dialog;
    private void showDecreaseDialog(String name, final String id, final String money){
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
        TextView cancle = (TextView) view.findViewById(R.id.off_commit);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        TextView message = view.findViewById(R.id.message);
        TextView title = view.findViewById(R.id.title);
        title.setText(name);
        message.setVisibility(View.GONE);
        cancle.setText("不使用");
        commit.setText("使用");
        dialog = new AlertDialog.Builder(this).create();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reduceActivityId = "0";
                isUseDecrease = false;
                ticketCouponId = MyApplication.selectedId;
                prodectCouponId = MyApplication.selectedProdectId ;
                txtDecreaseNum.setText("去使用");
                selectCoupon.setEnabled(true);
                selectProdectCoupon.setEnabled(true);
                txtCouponNum.setTextColor(Color.parseColor("#D35458"));
                txtCouponNum.setText("去使用");
                txtProdectCouponNum.setTextColor(Color.parseColor("#D35458"));
                txtProdectCouponNum.setText("去使用");
                modifyOrder();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //事件
                dialog.dismiss();
                reduceActivityId = id;
                isUseDecrease = true;
                ticketCouponId = 0;
                prodectCouponId = 0;
                txtDecreaseNum.setText(String.format("-%s",money));
                selectCoupon.setEnabled(false);
                selectProdectCoupon.setEnabled(false);
                txtCouponNum.setTextColor(Color.parseColor("#ABABAB"));
                txtCouponNum.setText("不能和优惠活动叠加使用");
                txtProdectCouponNum.setTextColor(Color.parseColor("#ABABAB"));
                txtProdectCouponNum.setText("不能和优惠活动叠加使用");
                modifyOrder();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    /**
     * 优惠活动
     */
    private boolean isUse = true;//是否使用优惠活动
    private void showDialog(String name,final String id, final String money) {
        isUse  = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.decrease_layout, null);
        ImageView ivClose =  v.findViewById(R.id.ivClose);
        final RelativeLayout rlUse =  v.findViewById(R.id.rlUse);//使用
        final RelativeLayout rlNotUse =  v.findViewById(R.id.rlNotUse);//不使用
        final Button btnSure =  v.findViewById(R.id.btnSure);//确定
        final TextView txtDecrease = v.findViewById(R.id.txtDecrease);
        final ImageView used = v.findViewById(R.id.used);
        final ImageView doNotUsed = v.findViewById(R.id.doNotUsed);
        txtDecrease.setText(name);
        builder.setView(v);
        builder.setCancelable(true);
        final Dialog noticeDialog = builder.create();
        noticeDialog.getWindow().setGravity(Gravity.BOTTOM);
        noticeDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        noticeDialog.setCancelable(false);
        noticeDialog.show();
        rlUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUse = true;
                Glide.with(ConfrimOrderActivity.this).load(R.mipmap.item_select).into(used);
                Glide.with(ConfrimOrderActivity.this).load(R.mipmap.item_notselect).into(doNotUsed);
            }
        });

        rlNotUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUse = false;
                Glide.with(ConfrimOrderActivity.this).load(R.mipmap.item_select).into(doNotUsed);
                Glide.with(ConfrimOrderActivity.this).load(R.mipmap.item_notselect).into(used);
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUse){
                    noticeDialog.dismiss();
                    reduceActivityId = id;
                    isUseDecrease = true;
                    ticketCouponId = 0;
                    prodectCouponId = 0;
                    txtDecreaseNum.setText(String.format("-%s",money));
                    selectCoupon.setEnabled(false);
                    selectProdectCoupon.setEnabled(false);
                    txtCouponNum.setTextColor(Color.parseColor("#ABABAB"));
                    txtCouponNum.setText("不能和优惠活动叠加使用");
                    txtProdectCouponNum.setTextColor(Color.parseColor("#ABABAB"));
                    txtProdectCouponNum.setText("不能和优惠活动叠加使用");
                    modifyOrder();
                }else {
                    noticeDialog.dismiss();
                    reduceActivityId = "0";
                    isUseDecrease = false;
                    ticketCouponId = MyApplication.selectedId;
                    prodectCouponId = MyApplication.selectedProdectId ;
                    txtDecreaseNum.setText("去使用");
                    selectCoupon.setEnabled(true);
                    selectProdectCoupon.setEnabled(true);
                    txtCouponNum.setTextColor(Color.parseColor("#D35458"));
                    txtCouponNum.setText("去使用");
                    txtProdectCouponNum.setTextColor(Color.parseColor("#D35458"));
                    txtProdectCouponNum.setText("去使用");
                    modifyOrder();
                }
            }
        });
        WindowManager.LayoutParams layoutParams = noticeDialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        noticeDialog.getWindow().setAttributes(layoutParams);
    }

    /**
     * 修改价格
     */
    private void modifyOrder() {
        String ticketId;
        String prodectId;
        if (ticketCouponId > 0) {
            ticketId = String.valueOf(ticketCouponId);
        } else {
            ticketId = "";
        }
        if (prodectCouponId > 0) {
            prodectId = String.valueOf(prodectCouponId);
            rlActivity.setVisibility(View.GONE);
        } else {
            prodectId = "";
            if (modifyInfo.getMerOrder() != null) {
                if (Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                        modifyInfo.getMerOrder().getBeforeTicketPrice() == 0) {
                    rlActivity.setVisibility(View.GONE);
                } else {
                    rlActivity.setVisibility(View.VISIBLE);
                    activityPrice.setText(String.format("-%s元", Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                            modifyInfo.getMerOrder().getBeforeTicketPrice()));
                }
            }
        }
        String activityPriceNum2;
        if (lockSeatsBO.getActivityPriceNum() == null) {
            activityPriceNum2 = "";
        } else {
            activityPriceNum2 = lockSeatsBO.getActivityPriceNum();
        }
        if (MyApplication.cinemaBo != null) {
            String changePayType;
            if (payType == 1) {
                changePayType = "2";
            } else {
                changePayType = "1";
            }
            mPresenter.modifyOrderPrice(changePayType, reduceActivityId, sessionBO.getDxId(), MyApplication.cinemaBo.getCinemaId(), sessionBO.getHallId(), sessionBO.getCineMovieNum(), lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum2,
                    String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                    ticketId, prodectId, merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                    String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()), false,true);
        }
    }


    /**
     * 提交订单
     */
    private void submitOrder(String changePayType){//1 非会员，2 会员。
        showNoProgress("提交订单中...");
        String ticketId;
        String prodectId;
        String myPhone;
        if (ticketCouponId > 0) {//电影票优惠券
            ticketId = String.valueOf(ticketCouponId);
        } else {
            ticketId = "0";
        }
        if (prodectCouponId > 0) {//卖品优惠券
            prodectId = String.valueOf(prodectCouponId);
        } else {
            prodectId = "0";
        }
        if (newPhoneNumber.equals("")) {//电话号码
            myPhone = MyApplication.user.getMobile();
        } else {
            myPhone = newPhoneNumber;
        }
        if (!isSubmit) {//是否已经提交过支付，如果提交过，就不走接口
            if (modifyInfo.getMerOrder() != null) {//卖品不为空
                mPresenter.loadSubmitOrder(getIntent().getStringExtra("areaId"),changePayType,reduceActivityId,String.valueOf(MyApplication.cinemaBo.getCinemaId()),
                        null, seats, seatId, zongjia + "", ticketNum + "",
                        MyApplication.cinemaBo.getCinemaNumber(), sessionBO.getHallId(),
                        sessionBO.getDxId(), sessionBO.getCineMovieNum(), ticketId, merchandiseInfo, prodectId, myPhone, comment, String.valueOf(sessionBO.getPreferPrice()),
                        String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()),
                        modifyInfo.getMerOrder().getDisPrice(), modifyInfo.getMerOrder().getVipdisPrice());
            } else {
                mPresenter.loadSubmitOrder(getIntent().getStringExtra("areaId"),changePayType,reduceActivityId,String.valueOf(MyApplication.cinemaBo.getCinemaId()),
                        null, seats, seatId, zongjia + "", ticketNum + "",
                        MyApplication.cinemaBo.getCinemaNumber(), sessionBO.getHallId(),
                        sessionBO.getDxId(), sessionBO.getCineMovieNum(), ticketId, merchandiseInfo, prodectId, myPhone, comment, String.valueOf(sessionBO.getPreferPrice()),
                        String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()),
                        "", "");
            }
        } else {
            if (payType == 1) {//会员卡支付
                Bundle bundle = new Bundle();
                bundle.putSerializable("lockSeatsBO", modifyInfo);
                bundle.putInt("confrim", 1);
                bundle.putInt("size", size);
                bundle.putString("price", modifyInfo.getPayPrice());
                gotoActivity(PayActivity.class, bundle, true);
            } else if (payType == 2) {//支付宝支付
                payAli(orderBO.getOrderNum());
            } else {
                payWechat(orderBO.getOrderNum());
            }
            stopProgress();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().goBackMain();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+MyApplication.cinemaBo.getContact()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * 检测电话是否输入
     */
//    private boolean isEditPhone() {
//        userPhone = phoneEdit.getText().toString().trim();
//        if (StringUtils.isEmpty(userPhone)) {
//            LogUtils.showToast("请输入联系电话！");
//            return false;
//        }
//        if (!RegexUtils.isMobileExact(userPhone)) {
//            LogUtils.showToast("请输入正确的联系电话！");
//            return false;
//        }
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String ticketId;
        String prodectId;
        switch (requestCode) {
            case 1:
                int disprice = 0;
                try {
                    disprice = data.getExtras().getInt("discount");
                    ticketCouponId = data.getExtras().getInt("id");

                    if (ticketCouponId > 0) {
                        ticketId = String.valueOf(ticketCouponId);
                    } else {
                        ticketId = "0";
                    }
                    if (prodectCouponId > 0) {
                        prodectId = String.valueOf(prodectCouponId);
                        rlActivity.setVisibility(View.GONE);
                    } else {
                        prodectId = "0";
                        if (modifyInfo.getMerOrder() != null) {
                            if (Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                                    modifyInfo.getMerOrder().getBeforeTicketPrice() == 0) {
                                rlActivity.setVisibility(View.GONE);
                            } else {
                                rlActivity.setVisibility(View.VISIBLE);
                                activityPrice.setText(String.format("-%s元", Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                                        modifyInfo.getMerOrder().getBeforeTicketPrice()));
                            }
                        }
                    }
                    String activityPriceNum;
                    if (lockSeatsBO.getActivityPriceNum() == null) {
                        activityPriceNum = "";
                    } else {
                        activityPriceNum = lockSeatsBO.getActivityPriceNum();
                    }
                    if (MyApplication.cinemaBo != null) {
                        String changePayType;
                        if (payType == 1) {
                            changePayType = "2";
                        } else {
                            changePayType = "1";
                        }
                        mPresenter.modifyOrderPrice(changePayType, reduceActivityId, sessionBO.getDxId(), MyApplication.cinemaBo.getCinemaId(), sessionBO.getHallId(), sessionBO.getCineMovieNum()
                                , lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum,
                                String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                                ticketId, prodectId, merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                                String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()), false, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                int disprice2 = 0;
                try {
                    disprice2 = data.getExtras().getInt("discount");
                    prodectCouponId = data.getExtras().getInt("id");

                    if (ticketCouponId > 0) {
                        ticketId = String.valueOf(ticketCouponId);
                    } else {
                        ticketId = "0";
                    }
                    if (prodectCouponId > 0) {
                        prodectId = String.valueOf(prodectCouponId);
                        activityPrice.setText("-0元");
                        rlActivity.setVisibility(View.GONE);
                    } else {
                        prodectId = "0";
                        txtProdectCouponNum.setText("去使用");
                        if (modifyInfo.getMerOrder() != null) {
                            if (Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                                    modifyInfo.getMerOrder().getBeforeTicketPrice() == 0) {
                                rlActivity.setVisibility(View.GONE);
                            } else {
                                rlActivity.setVisibility(View.VISIBLE);
                                activityPrice.setText(String.format("-%s元", Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                                        modifyInfo.getMerOrder().getBeforeTicketPrice()));
                            }
                        } else {
                            rlActivity.setVisibility(View.GONE);
                        }
                    }
                    String activityPriceNum2;
                    if (lockSeatsBO.getActivityPriceNum() == null) {
                        activityPriceNum2 = "";
                    } else {
                        activityPriceNum2 = lockSeatsBO.getActivityPriceNum();
                    }
                    if (MyApplication.cinemaBo != null) {
                        String changePayType;
                        if (payType == 1) {
                            changePayType = "2";
                        } else {
                            changePayType = "1";
                        }
                        mPresenter.modifyOrderPrice(changePayType, reduceActivityId, sessionBO.getDxId(), MyApplication.cinemaBo.getCinemaId(), sessionBO.getHallId(), sessionBO.getCineMovieNum(), lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum2,
                                String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                                ticketId, prodectId, merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                                String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()), false, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getModifyInfo(LockSeatsBO modifyInfo,boolean mypayType,boolean reduce) {
        this.modifyInfo = modifyInfo;
        stopProgress();
        isSuccess = true;
        if (mypayType) {
            changeBO = modifyInfo;
            if (changeBO.getSeatTicketList() != null && changeBO.getSeatTicketList().size()>0){
                selectCoupon.setVisibility(View.VISIBLE);
            }else {
                selectCoupon.setVisibility(View.GONE);
            }
        }

        if (!reduce){//如果是切换优惠券，卖品优惠去和满减活动，满减活动的信息就不再返回
            notChangeNum = modifyInfo;
        }

        //满减活动优惠选择
        if (isUseDecrease) {//不使用满减活动
            if (notChangeNum.getDxReduceActivity() != null) {
                isUseDecrease = true;
                reduceActivityId = notChangeNum.getDxReduceActivity().getId();
                txtDecreaseNum.setText(String.format("-%s元", notChangeNum.getDxReduceActivity().getReduceMoney()));
                selectDecreaseActivity.setVisibility(View.VISIBLE);
                ticketCouponId = 0;//如果有满减活动，不使用默认选择的优惠券
                selectCoupon.setEnabled(false);
                txtCouponNum.setTextColor(Color.parseColor("#ABABAB"));
                txtCouponNum.setText("不能和优惠活动叠加使用");
                selectProdectCoupon.setEnabled(false);
                txtProdectCouponNum.setTextColor(Color.parseColor("#ABABAB"));
                txtProdectCouponNum.setText("不能和优惠活动叠加使用");
            } else {
                isUseDecrease = false;
                selectDecreaseActivity.setVisibility(View.GONE);
                reduceActivityId = "";
                ticketCouponId = mTicketCouponId;//如果没有满减活动，使用默认选择的优惠券
                selectCoupon.setEnabled(true);
                txtCouponNum.setTextColor(Color.parseColor("#D35458"));
                if (Double.valueOf(MathExtend.subtract(modifyInfo.getBeforeTicketPrice(),
                        modifyInfo.getTotalDisprice())) > 0){
                    txtCouponNum.setText(String.format("-%s元",MathExtend.subtract(modifyInfo.getBeforeTicketPrice(),
                            modifyInfo.getTotalDisprice()) ));
                }else {
                    txtCouponNum.setText("去使用");
                }
                selectProdectCoupon.setEnabled(true);
                txtProdectCouponNum.setTextColor(Color.parseColor("#D35458"));
                txtProdectCouponNum.setText("去使用");
            }
        }

        MyApplication.orderBO = modifyInfo;
        if (modifyInfo.getSeatTicket() != null) {
            MyApplication.selectedId =modifyInfo.getSeatTicket().getId();
        }
        orderPrice.setText(String.format("%s", modifyInfo.getTotalDisprice()));
        AliPay.setText(String.format("￥ %s",modifyInfo.getPayPrice()));
        WechatPay.setText(String.format("￥ %s",modifyInfo.getPayPrice()));
        cardPayNum.setText(String.format("￥ %s",modifyInfo.getVipPayPrice()));
        if (payType != -1) {
            if (payType == 1) {
                totalPrice.setText(String.format("合计：￥ %s", modifyInfo.getVipPayPrice()));
            } else {
                totalPrice.setText(String.format("合计：￥ %s", modifyInfo.getPayPrice()));
            }
        }
        if (!isUseDecrease){
            if (Double.valueOf(modifyInfo.getBeforeTicketPrice()) - Double.valueOf(modifyInfo.getTotalDisprice()) == 0) {
                txtCouponNum.setText("去使用");
            } else {
                txtCouponNum.setText(String.format("-%s元", MathExtend.subtract(modifyInfo.getBeforeTicketPrice(),
                        modifyInfo.getTotalDisprice())));
            }
        }

        if (modifyInfo.getMerOrder() != null) {
            ProdectPrice.setText(String.format("%s", modifyInfo.getMerOrder().getDisPrice()));
            if (!isUseDecrease) {//不使用满减活动才会设置优惠券
                if (modifyInfo.getMerOrder().getMerTicket() != null) {
                    txtProdectCouponNum.setText(String.format("-%s元", modifyInfo.getMerOrder().getMerTicket().getDxPlatTicket().getAmount()));
                    prodectCouponId = modifyInfo.getMerOrder().getMerTicket().getId();
                    MyApplication.selectedProdectId = prodectCouponId;
                } else {
                    prodectCouponId = 0;
                    txtProdectCouponNum.setText("去使用");
                    MyApplication.selectedProdectId = 0;
                }
            }
            if (Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                    modifyInfo.getMerOrder().getBeforeTicketPrice() == 0 ){
                rlActivity.setVisibility(View.GONE);
            }else {
                rlActivity.setVisibility(View.VISIBLE);
                activityPrice.setText(String.format("-%s元",new DecimalFormat("0.00").format(Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                        modifyInfo.getMerOrder().getBeforeTicketPrice())));
            }
        }else {
            rlActivity.setVisibility(View.GONE);
            activityPrice.setText("-0元");
//            txtProdectCouponNum.setText("去使用");
        }

        if (modifyInfo.getMerOrder() != null){
            if(modifyInfo.getMerOrder().getMerTicketList() != null &&
                    modifyInfo.getMerOrder().getMerTicketList().size()>0) {
                selectProdectCoupon.setVisibility(View.VISIBLE);
            }else {
                selectProdectCoupon.setVisibility(View.GONE);
            }
        }else {
            selectProdectCoupon.setVisibility(View.GONE);
        }
        //满减活动
        if (modifyInfo.getPartOne() != null && !modifyInfo.getPartOne().equals("")){
            rlDecreaseActivity.setVisibility(View.VISIBLE);
            txtDecreaseInstruction.setText(String.format("%s,再买",modifyInfo.getPartOne()));
            txtDes.setText(String.format("%s元",modifyInfo.getPartTwo()));
            txtDesNum.setText(String.format("%s元",modifyInfo.getPartThree()));
        }else {
            rlDecreaseActivity.setVisibility(View.GONE);
        }
    }

    /**
     * 备注
     */
    private void showSelectDialog(final String tag) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.exchange_coupon_dialog, null);
        final RelativeLayout rlClose = (RelativeLayout) v.findViewById(R.id.rlClose);
        final EditText etEnter = (EditText) v.findViewById(R.id.etEnter);
        final RelativeLayout rlSubmit = (RelativeLayout) v.findViewById(R.id.rlSubmit);
        final TextView txtCo = v.findViewById(R.id.txtCo);
        txtCo.setVisibility(View.GONE);
        etEnter.setHint(tag);
        etEnter.setHintTextColor(Color.parseColor("#878787"));
        builder.setView(v);
        builder.setCancelable(true);
        final Dialog noticeDialog = builder.create();
        noticeDialog.getWindow().setGravity(Gravity.BOTTOM);
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
                String etInput  = etEnter.getText().toString();
                String time = String.valueOf(TimeUtils.getNowTimeMills());
                if (!TextUtils.isEmpty(etInput)) {
                    //隐藏软键盘
                    InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (tag.equals("请输入备注")){
                        tComment.setText(etInput);
                        comment = etInput;
                        noticeDialog.dismiss();
                    }else {
                        if (RegexUtils.isMobileExact(etInput)) {
                            newPhoneNumber = etInput;
                            phoneNumber.setText(newPhoneNumber);
                            noticeDialog.dismiss();
                        }else {
                            ToastUtils.showShortToast("请输入正确的手机号");
                        }
                    }
                }else {
                    if (tag.equals("请输入备注")){
                        ToastUtils.showShortToast("你还没输入备注呢...");
                    }else {
                        ToastUtils.showShortToast("你还没输入手机号呢...");
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
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        noticeDialog.getWindow().setAttributes(layoutParams);
    }




    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
        stopProgress();
        if (payType == 1){
            modifyPrice("2",false,false);
        }else {
            modifyPrice("1",false,false);
        }
        isSuccess = true;
        isSubmit = false;
    }

    @Override
    public void onRequestEnd() {
        stopProgress();
        isSuccess = true;
    }

    @Override
    public void getOrder(ConfirmPayBO orderBO) {
        MyApplication.confirmPayBO = orderBO;
        this.orderBO = orderBO;
        isSubmit = true;
        if (payType == 1){//会员卡支付
            Bundle bundle = new Bundle();
//        bundle.putSerializable("order", orderBO);
            bundle.putSerializable("lockSeatsBO", modifyInfo);
            bundle.putInt("confrim", 1);
            bundle.putInt("size", size);
            bundle.putString("price", modifyInfo.getPayPrice());
            gotoActivity(PayActivity.class, bundle, true);
        }else if (payType == 2){//支付宝支付
            payAli(orderBO.getOrderNum());
        }else {
            payWechat(orderBO.getOrderNum());
        }
    }

    /**
     * 支付宝支付
     * @param orderNum
     */
    private void payAli(String orderNum){
        HttpInterfaceIml.payAlipay(orderNum).subscribe(new Subscriber<PayBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(PayBO s) {
                payAliPay(s.getAlipay());
            }
        });
    }

    /**
     * 微信支付
     * @param orderNum
     */
    private void payWechat(String orderNum){
        HttpInterfaceIml.payWeChat(orderNum).subscribe(new Subscriber<WXPayBO>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
              ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(WXPayBO s) {
                LocalConfiguration.orderNum = orderBO.getOrderNum();
                WXUtils.payWX(s);
                LocalConfiguration.ordertype = 0;
            }
        });
    }
    /**
     * 支付宝调起操作
     */
    private void payAliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfrimOrderActivity.this);
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
                bundle.putString("tag","ticket");
                bundle.putString("order", orderBO.getOrderNum());
                gotoActivity(OrderSurcessActivity.class, bundle, false);
            } else {
                finish();
                EventBus.getDefault().post(new MessageEvent("SeatTebleActivity", "yes"));//发给SessionActivity
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
        if (messageEvent.getMessageType().equals("ConfrimOrderActivity")){
            finish();
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
                    submitOrder("2");
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
        if (couponType == 1) {//影票
            if (changeBO.getSeatTicketList().size() > 0) {
                refreshLayout.setVisibility(View.VISIBLE);
                none_layout.setVisibility(View.GONE);
            } else {
                refreshLayout.setVisibility(View.GONE);
                none_layout.setVisibility(View.VISIBLE);
            }
            com.myp.hhcinema.widget.superadapter.CommonAdapter<LockSeatsBO.SeatTicketListBean> adapter =
                    new com.myp.hhcinema.widget.superadapter.CommonAdapter<LockSeatsBO.SeatTicketListBean>(
                            this, R.layout.select_coupon_item, changeBO.getSeatTicketList()) {
                        @Override
                        protected void convert(com.myp.hhcinema.widget.superadapter.ViewHolder holder,
                                               final LockSeatsBO.SeatTicketListBean item, int position) {
                            if (item.getType() == 1) {//普通优惠券
                                holder.getView(R.id.llNormal).setVisibility(View.VISIBLE);
                                holder.getView(R.id.txtDecrease).setVisibility(View.GONE);
                                holder.getView(R.id.txtDate).setVisibility(View.VISIBLE);
                                if (item.getDxPlatTicket().getTicketType() == 3) {
                                    holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                                    Glide.with(ConfrimOrderActivity.this).load(R.mipmap.see_coupon_yes).into((ImageView) holder.getView(R.id.ivSee));
                                    holder.getView(R.id.txtNum).setVisibility(View.GONE);
                                    holder.setText(R.id.txtRange, String.format("%s", item.getDxPlatTicket().getName()));
                                } else {
                                    if (Double.parseDouble(changeBO.getPayPrice()) > Double.parseDouble(item.getDxPlatTicket().getAchieveMoney())) {
                                        setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", item.getDxPlatTicket().getAmount()), "#E04F2E");
                                    } else {
                                        setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", item.getDxPlatTicket().getAmount()), "#AAAAAA");
                                    }
                                    holder.getView(R.id.ivSee).setVisibility(View.GONE);
                                    holder.getView(R.id.txtNum).setVisibility(View.VISIBLE);
                                    holder.setText(R.id.txtNum, String.format("￥ %s", item.getDxPlatTicket().getAmount()));
                                    holder.setText(R.id.txtRange, String.format("满%s元可用", item.getDxPlatTicket().getAchieveMoney()));
                                }
                                holder.setText(R.id.txtDate, String.format("有效期 %s", item.getDxPlatTicket().getEndTime().substring(0, 10)));
                            } else {//满减活动
                                holder.getView(R.id.llNormal).setVisibility(View.GONE);
                                holder.getView(R.id.txtDecrease).setVisibility(View.VISIBLE);
                                holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                                Glide.with(ConfrimOrderActivity.this).load(R.mipmap.newdecrease).into((ImageView) holder.getView(R.id.ivSee));
                                holder.getView(R.id.txtDate).setVisibility(View.GONE);
                                holder.setText(R.id.txtDecrease, item.getCinemaName());
                            }

                            if (MyApplication.selectedId == item.getId()) {
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
            list.setAdapter(adapter);
        }else {//卖品
            if (modifyInfo.getMerOrder().getMerTicketList().size() > 0) {
                refreshLayout.setVisibility(View.VISIBLE);
                none_layout.setVisibility(View.GONE);
            } else {
                refreshLayout.setVisibility(View.GONE);
                none_layout.setVisibility(View.VISIBLE);
            }
            com.myp.hhcinema.widget.superadapter.CommonAdapter<LockSeatsBO.MerOrderBean.MerTicketListBean> adapterProdect
                    = new com.myp.hhcinema.widget.superadapter.CommonAdapter<LockSeatsBO.MerOrderBean.MerTicketListBean>(
                            this, R.layout.select_coupon_item, modifyInfo.getMerOrder().getMerTicketList()) {
                @Override
                protected void convert(com.myp.hhcinema.widget.superadapter.ViewHolder holder,
                                       final LockSeatsBO.MerOrderBean.MerTicketListBean item, int position) {
                    if (item.getType() == 1) {//普通优惠券
                        holder.getView(R.id.llNormal).setVisibility(View.VISIBLE);
                        holder.getView(R.id.txtDecrease).setVisibility(View.GONE);
                        holder.getView(R.id.ivSee).setVisibility(View.GONE);
                        holder.getView(R.id.txtRange).setVisibility(View.VISIBLE);
                        holder.getView(R.id.txtDate).setVisibility(View.VISIBLE);
                        setTextColor((TextView) holder.getView(R.id.txtNum), String.format("￥ %s", item.getDxPlatTicket().getAmount()), "#E04F2E");
                        holder.setText(R.id.txtNum, String.format("￥ %s", item.getDxPlatTicket().getAmount()));
                        holder.setText(R.id.txtRange, String.format("满%s元可用", item.getDxPlatTicket().getAchieveMoney()));
                        holder.setText(R.id.txtDate, String.format("有效期 %s", item.getDxPlatTicket().getEndTime().substring(0, 10)));
                    }else {
                        holder.getView(R.id.llNormal).setVisibility(View.GONE);
                        holder.getView(R.id.txtDecrease).setVisibility(View.VISIBLE);
                        holder.getView(R.id.txtRange).setVisibility(View.GONE);
                        holder.getView(R.id.txtDate).setVisibility(View.GONE);
                        holder.getView(R.id.ivSee).setVisibility(View.VISIBLE);
                        Glide.with(ConfrimOrderActivity.this).load(R.mipmap.newdecrease).into((ImageView)holder.getView(R.id.ivSee));
                        holder.setText(R.id.txtDecrease, String.format("%s", item.getCinemaName()));
                    }

                    if (MyApplication.selectedProdectId == item.getId()) {
                        holder.getView(R.id.ivSelected).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.ivSelected).setVisibility(View.GONE);
                    }

                    holder.getView(R.id.itemBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setProductCoupon(item.getId());
                            couponDialog.dismiss();
                        }
                    });
                }
            };
            list.setAdapter(adapterProdect);

        }
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
                if (couponType == 1) {//影票
                    setCoupon(-1);
                    MyApplication.selectedId = -1;
                }else {
                    setProductCoupon(-1);
                    MyApplication.selectedProdectId = -1;
                }
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
        String ticketId;
        String prodectId;
        try {
            ticketCouponId = id;

            if (ticketCouponId > 0){
                ticketId = String.valueOf(ticketCouponId);
            }else {
                ticketId = "0";
            }
            if (prodectCouponId > 0){
                prodectId = String.valueOf(prodectCouponId);
                rlActivity.setVisibility(View.GONE);
            }else {
                prodectId = "0";
                if (modifyInfo.getMerOrder() != null) {
                    if (Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                            modifyInfo.getMerOrder().getBeforeTicketPrice() == 0) {
                        rlActivity.setVisibility(View.GONE);
                    } else {
                        rlActivity.setVisibility(View.VISIBLE);
                        activityPrice.setText(String.format("-%s元", Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                                modifyInfo.getMerOrder().getBeforeTicketPrice()));
                    }
                }
            }
            String activityPriceNum;
            if (lockSeatsBO.getActivityPriceNum() == null){
                activityPriceNum = "";
            }else {
                activityPriceNum = lockSeatsBO.getActivityPriceNum();
            }
            if (MyApplication.cinemaBo != null) {
                String changePayType;
                if (payType == 1){
                    changePayType = "2";
                }else {
                    changePayType = "1";
                }
                mPresenter.modifyOrderPrice(changePayType,reduceActivityId,sessionBO.getDxId(),MyApplication.cinemaBo.getCinemaId(),sessionBO.getHallId(),sessionBO.getCineMovieNum()
                        ,lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum,
                        String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                        ticketId, prodectId, merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                        String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()),false,true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 卖品优惠券
     * @param id
     */
    private void setProductCoupon(int id){
        String ticketId;
        String prodectId;
        try {
            prodectCouponId = id;

            if (ticketCouponId > 0){
                ticketId = String.valueOf(ticketCouponId);
            }else {
                ticketId = "0";
            }
            if (prodectCouponId > 0){
                prodectId = String.valueOf(prodectCouponId);
                activityPrice.setText("-0元");
                rlActivity.setVisibility(View.GONE);
            }else {
                prodectId = "0";
                txtProdectCouponNum.setText("去使用");
                if (modifyInfo.getMerOrder() != null) {
                    if (Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                            modifyInfo.getMerOrder().getBeforeTicketPrice() == 0) {
                        rlActivity.setVisibility(View.GONE);
                    } else {
                        rlActivity.setVisibility(View.VISIBLE);
                        activityPrice.setText(String.format("-%s元", Double.valueOf(modifyInfo.getMerOrder().getTotalPrice()) -
                                modifyInfo.getMerOrder().getBeforeTicketPrice()));
                    }
                }else {
                    rlActivity.setVisibility(View.GONE);
                }
            }
            String activityPriceNum2;
            if (lockSeatsBO.getActivityPriceNum() == null){
                activityPriceNum2 = "";
            }else {
                activityPriceNum2 = lockSeatsBO.getActivityPriceNum();
            }
            if (MyApplication.cinemaBo != null) {
                String changePayType;
                if (payType == 1){
                    changePayType = "2";
                }else {
                    changePayType = "1";
                }
                mPresenter.modifyOrderPrice(changePayType,reduceActivityId,sessionBO.getDxId(),MyApplication.cinemaBo.getCinemaId(),sessionBO.getHallId(),sessionBO.getCineMovieNum(),lockSeatsBO.getPartnerPrice(), lockSeatsBO.getMarketPrice(), activityPriceNum2,
                        String.valueOf(ticketNum), lockSeatsBO.getBeforeTicketPrice(), lockSeatsBO.getTotalDisprice(), modifyInfo.getPayPrice(),
                        ticketId, prodectId, merchandiseInfo, String.valueOf(MyApplication.user.getId()), String.valueOf(sessionBO.getPreferPrice()),
                        String.valueOf(sessionBO.getGlobalPreferPrice()), String.valueOf(sessionBO.getGlobalCanBuyNum()),false,true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}