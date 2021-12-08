package com.myp.hhcinema.ui.orderconfrim;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.personorder.ordermessage.OrderMessageActivity;
import com.myp.hhcinema.util.AppManager;
import com.myp.hhcinema.util.CimemaUtils;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.StringUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by wuliang on 2017/6/2.
 * <p>
 * 订单支付成功
 */

public class OrderSurcessActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.movies_img)
    ImageView moviesImg;
    @Bind(R.id.movies_name)
    TextView moviesName;
    @Bind(R.id.movies_type)
    TextView moviesType;
    @Bind(R.id.movies_address)
    TextView moviesAddress;
    @Bind(R.id.movies_time)
    TextView moviesTime;
    @Bind(R.id.movies_seat)
    TextView moviesSeat;
    @Bind(R.id.movies_num)
    TextView moviesNum;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.order_price)
    TextView orderPrice;
    @Bind(R.id.back_home)
    Button backHome;
    @Bind(R.id.order_message)
    Button orderMessage;
    @Bind(R.id.go_back)
    LinearLayout goBack;
    @Bind(R.id.rlPrice)
    RelativeLayout rlPrice;
    @Bind(R.id.llTicket)
    LinearLayout llTicket;
    @Bind(R.id.llProdect)
    LinearLayout llProdect;//卖品订单
    @Bind(R.id.prodect_img)
    ImageView prodect_img;//卖品图片
    @Bind(R.id.prodectName)
    TextView prodectName;//卖品名称
    @Bind(R.id.cinemaName)
    TextView cinemaName;//卖品订单影城名称

    private OrderBO orderBO;

    private String tag;

    @Override
    protected int getLayout() {
        return R.layout.act_order_scress;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("支付成功");

        String orderNum = getIntent().getExtras().getString("order");
        tag = getIntent().getExtras().getString("tag");

        showProgress("加载中...");
        queryOrder(String.valueOf(MyApplication.user.getId()), orderNum);

        if (tag.equals("ticket")) {
            llTicket.setVisibility(View.VISIBLE);
            rlPrice.setVisibility(View.VISIBLE);
        } else {
            llTicket.setVisibility(View.GONE);
            rlPrice.setVisibility(View.GONE);
        }
        backHome.setOnClickListener(this);
        orderMessage.setOnClickListener(this);
        goBack.setOnClickListener(this);
        EventBus.getDefault().post(new MessageEvent("prodectClear", "yes"));//发给SortDetailFragment
    }

    /**
     * 设置界面数据
     */
    private void setData() {
        if (tag.equals("ticket")) {
            if (orderBO.getMerOrder() != null) {
                showNotice();
                llProdect.setVisibility(View.VISIBLE);
                if (orderBO.getMerOrder().getDetails().size() > 0) {
                    if (orderBO.getMerOrder().getDetails().get(0).getMerchandise() != null) {
                        Picasso.with(this).load(orderBO.getMerOrder().getDetails().get(0).getMerchandise().getImageUrl()).into(prodect_img);
                    } else {
                        Picasso.with(this).load(orderBO.getMerOrder().getDetails().get(0).getItemCombo().getImageUrl()).into(prodect_img);
                    }
                }
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < orderBO.getMerOrder().getDetails().size(); i++) {
                    if (orderBO.getMerOrder().getDetails().get(0).getMerchandise() != null) {
                        buf.append(orderBO.getMerOrder().getDetails().get(i).getMerchandise().getName()).append("+");
                    } else {
                        buf.append(orderBO.getMerOrder().getDetails().get(i).getItemCombo().getName()).append("+");
                    }
                }
                prodectName.setText(buf.deleteCharAt(buf.length() - 1));
                cinemaName.setText(orderBO.getCinemaName());
            } else {
                llProdect.setVisibility(View.GONE);
            }
            moviesName.setText(orderBO.getDxMovie().getMovieName());
            moviesAddress.setText(orderBO.getCinemaName() + " " + orderBO.getHallName());
            if (orderBO.getMovieLanguage() != null) {
                moviesType.setText(orderBO.getMovieDimensional() + " " + orderBO.getMovieLanguage() + orderBO.getMovieSize());
            } else {
                moviesType.setText(orderBO.getDxMovie().getMovieDimensional() + " " + orderBO.getDxMovie().getMovieLanguage());
            }
            if (!StringUtils.isEmpty(orderBO.getPlayName())) {
                String time = orderBO.getPlayName().substring(0, orderBO.getPlayName().length() - 3);
                moviesTime.setText(time);
            } else {
                moviesTime.setText("");
            }
            moviesSeat.setText(CimemaUtils.getSeats(orderBO.getSeats()));
            moviesNum.setText(String.valueOf(orderBO.getTicketNum()));
            orderPrice.setText("¥" + orderBO.getPayPrice());
            orderNum.setText("订单号：" + orderBO.getOrderNum());
            if (StringUtils.isEmpty(orderBO.getDxMovie().getPicture())) {
                moviesImg.setImageResource(R.color.act_bg01);
            } else {
                Picasso.with(this).load(orderBO.getDxMovie().getPicture()).into(moviesImg);
            }
        } else {
            if (orderBO.getMerOrder() != null) {
                showNotice();
                llProdect.setVisibility(View.VISIBLE);
                if (orderBO.getMerOrder().getDetails().size() > 0) {
                    if (orderBO.getMerOrder().getDetails().get(0).getMerchandise() != null) {
                        Picasso.with(this).load(orderBO.getMerOrder().getDetails().get(0).getMerchandise().getImageUrl()).into(prodect_img);
                    } else {
                        Picasso.with(this).load(orderBO.getMerOrder().getDetails().get(0).getItemCombo().getImageUrl()).into(prodect_img);
                    }
                }
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < orderBO.getMerOrder().getDetails().size(); i++) {
                    if (orderBO.getMerOrder().getDetails().get(0).getMerchandise() != null) {
                        buf.append(orderBO.getMerOrder().getDetails().get(i).getMerchandise().getName()).append("+");
                    } else {
                        buf.append(orderBO.getMerOrder().getDetails().get(i).getItemCombo().getName()).append("+");
                    }
                }
                prodectName.setText(buf.deleteCharAt(buf.length() - 1));
                cinemaName.setText(orderBO.getCinemaName());
                orderPrice.setText("¥" + orderBO.getPayPrice());
                orderNum.setText("订单号：" + orderBO.getOrderNum());
            } else {
                llProdect.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 查询单个订单
     */
    private void queryOrder(String appUserId, String orderNum) {
        HttpInterfaceIml.orderQuery(appUserId, orderNum).subscribe(new Subscriber<OrderBO>() {
            @Override
            public void onCompleted() {
                stopProgress();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.showToast(e.getMessage());
                e.printStackTrace();
                stopProgress();
            }

            @Override
            public void onNext(OrderBO order) {
                orderBO = order;
                setData();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_home:   //返回首页
            case R.id.go_back:
                AppManager.getAppManager().goBackMain();
                break;
            case R.id.order_message:   //订单详情
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orderBO);
                bundle.putString("cinemaId", orderBO.getCinemaId());
                bundle.putString("id", String.valueOf(orderBO.getId()));
                gotoActivity(OrderMessageActivity.class, bundle, false);
                break;
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

    private void showNotice() {
        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.dialog_order_pay, null);//这里必须是final的
        TextView title = view.findViewById(R.id.title);
        title.setText("您已成功购买卖品，请至影城前台小卖部领取");
        TextView message = view.findViewById(R.id.message);
        message.setVisibility(View.GONE);
        TextView cancle = (TextView) view.findViewById(R.id.off_commit);
        cancle.setVisibility(View.GONE);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        commit.setText("确定");
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
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
}
