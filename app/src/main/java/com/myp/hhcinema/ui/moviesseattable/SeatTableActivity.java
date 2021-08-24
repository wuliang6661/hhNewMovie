package com.myp.hhcinema.ui.moviesseattable;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.entity.MoviesSessionBO;
import com.myp.hhcinema.entity.OrderNumBO;
import com.myp.hhcinema.entity.SeatBean;
import com.myp.hhcinema.entity.aCinemaSeatTableBO;
import com.myp.hhcinema.entity.preferentialnumberBo;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.orderconfrim.ConfrimOrderActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.TimeUtils;
import com.myp.hhcinema.widget.seattable.SeatTable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.ResponseBody;

import static com.myp.hhcinema.widget.seattable.SeatTable.SEAT_TYPE_AVAILABLE;
import static com.myp.hhcinema.widget.seattable.SeatTable.SEAT_TYPE_NOT_AVAILABLE;
import static com.myp.hhcinema.widget.seattable.SeatTable.SEAT_TYPE_SELECTED;
import static com.myp.hhcinema.widget.seattable.SeatTable.SEAT_TYPE_SOLD;


/**
 * 选座购票界面
 */

public class SeatTableActivity extends MVPBaseActivity<SeatTableContract.View, SeatTablePresenter>
        implements SeatTableContract.View, View.OnClickListener {

    @Bind(R.id.seat_table)
    SeatTable seatTable;
    @Bind(R.id.movies_time)
    TextView moviesTime;
    @Bind(R.id.update_session)
    TextView updateSession;
    @Bind(R.id.cinema_address)
    TextView cinemaAddress;
    @Bind(R.id.seat1)
    TextView seat1;
    @Bind(R.id.seat2)
    TextView seat2;
    @Bind(R.id.seat3)
    TextView seat3;
    @Bind(R.id.seat4)
    TextView seat4;
    @Bind(R.id.seat5)
    TextView seat5;
    @Bind(R.id.seat6)
    TextView seat6;
    @Bind(R.id.seat11)
    TextView seat11;
    @Bind(R.id.seat22)
    TextView seat22;
    @Bind(R.id.seat33)
    TextView seat33;
    @Bind(R.id.seat44)
    TextView seat44;
    @Bind(R.id.seat55)
    TextView seat55;
    @Bind(R.id.seat66)
    TextView seat66;
    @Bind(R.id.lin_seat1)
    LinearLayout lin_seat1;
    @Bind(R.id.lin_seat2)
    LinearLayout lin_seat2;
    @Bind(R.id.lin_seat3)
    LinearLayout lin_seat3;
    @Bind(R.id.lin_seat4)
    LinearLayout lin_seat4;
    @Bind(R.id.lin_seat5)
    LinearLayout lin_seat5;
    @Bind(R.id.lin_seat6)
    LinearLayout lin_seat6;

    @Bind(R.id.order_price)
    TextView orderPrice;
    @Bind(R.id.submit_button)
    Button submitButton;
    @Bind(R.id.buttom_layout)
    LinearLayout buttomLayout;
    @Bind(R.id.x)
    TextView x;

    private Map<String, aCinemaSeatTableBO> setMap;   //座位取值简单
    private Map<String, aCinemaSeatTableBO> selector;   //选中的座位

    private List<Integer> noneRows;

    MoviesSessionBO sessionBO;    //场次bean
    MoviesByCidBO movies;   //当前选座的电影
    private double zongjia;
    private OrderNumBO isVip;
    private Integer preferentialnumber = 0;
    private List<aCinemaSeatTableBO> xss = new ArrayList<>();
    private LockSeatsBO lockSeatsBO;
    private double uploadTotalPrice;//当前借口需要穿给后台的参数ticketOriginPrice
    private int xValue;//座位图的行
    private int yValue;//座位图的列

    private List<aCinemaSeatTableBO> seatBeanList = new ArrayList<>();//新版座位图

    @Override
    protected int getLayout() {
        return R.layout.act_seat_table;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionBO = (MoviesSessionBO) getIntent().getExtras().getSerializable("session");
        movies = (MoviesByCidBO) getIntent().getExtras().getSerializable("movies");
        isVip = (OrderNumBO) getIntent().getExtras().getSerializable("isVip");
        goBack();
        setTitle(movies.getMovieName());
        moviesTime.setText(TimeUtils.string2Week(sessionBO.getStartTime()));
        LogUtils.I(sessionBO.getStartTime());
        LogUtils.I(TimeUtils.string2Week(sessionBO.getStartTime()));
        cinemaAddress.setText(MyApplication.cinemaBo.getCinemaName());
        selector = new HashMap<>();
        showProgress("加载中...");
//        mPresenter.loadSeatTables(MyApplication.cinemaBo.getCinemaId(),
//                sessionBO.getDxId(), sessionBO.getCineUpdateTime());
        mPresenter.loadSeats(MyApplication.cinemaBo.getCinemaId(),sessionBO.getDxId());
        seatTable.setScreenName(sessionBO.getHallName() + "银幕");//设置屏幕名称
        seatTable.setMaxSelected(6);//设置最多选中
        submitButton.setOnClickListener(this);
        updateSession.setOnClickListener(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取返回的座位信息
     * <p>
     * 下标从0开始，显示真实座位号需要加1
     */
    @Override
    public void getSeatData(List<aCinemaSeatTableBO> s) {
        packSeatData(s);
        xss = s;
        mPresenter.getsets(MyApplication.cinemaBo.getCinemaId(),sessionBO.getDxId());

    }

    /**
     * 新版座位图接口
     * @param responseBody
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public void getSeats(ResponseBody responseBody) throws IOException, JSONException {
        String s = new String(responseBody.bytes());
        JSONObject jsonObject = new JSONObject(s);
        int status = jsonObject.optInt("status");
        if (status == 1){
            seatBeanList.clear();
            seatBeanList.addAll(JSON.parseArray(jsonObject.optString("data"),aCinemaSeatTableBO.class));
            packSeatData(seatBeanList);
            xss = seatBeanList;
            x.setText("0");
//            mPresenter.getsets(MyApplication.cinemaBo.getCinemaId(),sessionBO.getDxId());
            seatTable.setSeatChecker(new SeatTable.SeatChecker() {

                @Override
                public boolean isValidSeat(int row, int column) {
                    row++;
                    column++;
                    aCinemaSeatTableBO seatTableBO = setMap.get(row + "&&" + column);
                    if (seatTableBO == null){
                        return false;
                    }
                    if ("0".equals(seatTableBO.getRowValue()) && "0".equals(seatTableBO.getColumnValue())) {
                        return false;
                    }
                    return true;

                }

                @Override
                public boolean isNotAvailable(int row, int column) {
                    row++;
                    column++;
                    aCinemaSeatTableBO seatTableBO = setMap.get(row + "&&" + column);
                    if ("damaged".equals(seatTableBO.getSeatStatus())) {
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean isSold(int row, int column) {
                    row++;
                    column++;
                    aCinemaSeatTableBO seatTableBO = setMap.get(row + "&&" + column);
                    if ("ok".equals(seatTableBO.getSeatStatus())) {
                        return false;
                    }
                    return true;
                }

                @Override
                public void checked(int row, int column) {
                    row++;
                    column++;
                    addSeatTables(row, column);
                }

                @Override
                public void unCheck(int row, int column) {
                    row++;
                    column++;
                    removeSeatTables(row, column);
                }

                @Override
                public int isFriends(int row, int column) {
                    row++;
                    column++;
                    aCinemaSeatTableBO seatTableBO = setMap.get(row + "&&" + column);
                    if (!StringUtils.isEmpty(seatTableBO.getType())) {
                        aCinemaSeatTableBO left = setMap.get(row + "&&" + (column - 1));
                        aCinemaSeatTableBO right = setMap.get(row + "&&" + (column + 1));
                        if (left == null) {
                            if (right == null) {
                                return 0;
                            }
                            if (right.getType().equals("L")){
                                return 2;
                            }
                            if (right.getType().equals("R")) {
                                return 1;
                            }
                            return 0;
                        }
                        if (left.getType().equals("R")) {
                            return 1;
                        }else {
                            if (right == null) {
                                return 0;
                            }
                            if (right.getType().equals("L")) {
                                return 2;
                            }
                            if (right.getType().equals("R")) {
                                return 1;
                            }
                        }
                    }
                    return 0;
                }

                @Override
                public boolean isEmpty(int row, int column) {
                    return true;
                }

                @Override
                public String[] checkedSeatTxt(int row, int column) {
                    return null;
                }

                @Override
                public boolean isNoneRow(int row) {
//                if (Collections.binarySearch(noneRows, row) >= 0) {
//                    return true;
//                }
                    return false;
                }

            });
//        int x = Integer.parseInt(xss.get(xss.size() - 1).getX());
//        int y = Integer.parseInt(xss.get(xss.size() - 1).getY());
            List<Integer> columnList = new ArrayList<>();//集合，存储所有seatList大小
            for (int i=0;i<xss.size();i++){
                columnList.add(Integer.parseInt(xss.get(i).getX()));
            }
            List<Integer> rowList = new ArrayList<>();//集合，存储所有seatList大小
            for (int i=0;i<xss.size();i++){
                rowList.add(Integer.parseInt(xss.get(i).getY()));
            }
            xValue = getBig(rowList);
            yValue = getBig(columnList);

            seatTable.setData(xValue, yValue);
        }
    }


    @Override
    public void getpreferentialnumberBo(preferentialnumberBo s) {
        preferentialnumber = s.getGlobalCanBuyNum();
        if (isVip.getIsVip() == 1){//会员
            if(s.getGlobalCanBuyNum()==null){
                x.setText("0");
            }else {
                x.setText(preferentialnumber+"");
            }
        }else {
            if(sessionBO.getLeftScreenLimitNum() != null){
                x.setText(String.valueOf(sessionBO.getLeftScreenLimitNum()));
            }else {
                x.setText("0");
            }
        }
    }


    /**
     * 获取最大的列数
     * @param list
     * @return
     */
    private int getBig(List<Integer> list){
        // 冒泡排序，查找到数据中列数最大的一个
        int NUM = list.size();
        int [] a = new int[NUM];
        for (int m=0;m<list.size()-1;m++){
            if (list.get(m) != null) {
                a[m] = list.get(m);
            }
        }
        for (int i=0;i<a.length-1;i++){
            for (int j=0;j<a.length-i-1;j++){
                if (a[j] > a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
        return a[a.length -1];
    }

    /**
     * 出始化空白行及座位重新封装
     */
    private void packSeatData(List<aCinemaSeatTableBO> s) {
        setMap = new HashMap<>();
        noneRows = new ArrayList<>();
        int xNum = 1;   //记录x
        aCinemaSeatTableBO bo;
        for (int i = 0; i < s.size(); i++) {
            bo = s.get(i);
            setMap.put(bo.getY() + "&&" + bo.getX(), bo);
//            if ((xNum + "").equals(bo.getX())) {
//                if ("0".equals(bo.getRowValue()) && "0".equals(bo.getColumnValue())) {   //走道
//                } else {
//                    xNum++;
//                }
//            } else if (Integer.parseInt(bo.getX()) > xNum) {
//                noneRows.add(xNum);
//                xNum++;
//            }
        }
    }

    AnimationSet animationSet;

    /**
     * 选中座位，界面变化
     */

    private void addSeatTables(int row, int column) {
        if (selector.size() == 0) {
            animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.share_pop_in);
            buttomLayout.startAnimation(animationSet);
            buttomLayout.setVisibility(View.VISIBLE);
        }
        aCinemaSeatTableBO seatTableBO = setMap.get(row + "&&" + column);
        selector.put(row + "&&" + column, seatTableBO);
        jisuan(selector.size());
        seatShow();
    }
    /**
     * 取消座位，界面控制
     */
    private void removeSeatTables(int row, int column) {
        selector.remove(row + "&&" + column);
        jisuan(selector.size());
        if (selector.size() == 0) {
            animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.share_pop_out);
            buttomLayout.startAnimation(animationSet);
            buttomLayout.setVisibility(View.GONE);
        }
        seatShow();
    }

    private void jisuan(int size) {
                if(isVip.getIsVip()==1){
                    if ( preferentialnumber==null||preferentialnumber<=0) {
                        double zong = (size)* Double.parseDouble(String.valueOf(sessionBO.getPreferPrice()));
                        BigDecimal bd=new BigDecimal(zong);
                        zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                        orderPrice.setText("¥" + zongjia);
                        //传给后台的价格
                        double zong2 = (size)* Double.parseDouble(String.valueOf(sessionBO.getPartnerPrice()));
                        BigDecimal bd2=new BigDecimal(zong2);
                        uploadTotalPrice =  bd2.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                    } else {
                        if(size>preferentialnumber){
                            double  youhuijia =preferentialnumber * Double.parseDouble(String.valueOf(sessionBO.getGlobalPreferPrice()));
                            double noyouhui = (size-preferentialnumber)* Double.parseDouble(String.valueOf(sessionBO.getPreferPrice()));
                            double zong = youhuijia+noyouhui;
                            BigDecimal bd=new BigDecimal(zong);
                            zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                            orderPrice.setText("¥" + zongjia);

                            //传给后台的价格
                            double  youhuijia2 =preferentialnumber * Double.parseDouble(String.valueOf(sessionBO.getPartnerPrice()));
                            double noyouhui2 = (size-preferentialnumber)* Double.parseDouble(String.valueOf(sessionBO.getPartnerPrice()));
                            double zong2 = youhuijia2+noyouhui2;
                            BigDecimal bd2=new BigDecimal(zong2);
                            uploadTotalPrice =  bd2.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                        }else {
                            double zong = (size)* Double.parseDouble(String.valueOf(sessionBO.getGlobalPreferPrice()));
                            BigDecimal bd=new BigDecimal(zong);
                            zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                            orderPrice.setText("¥" + zongjia);

                            //传给后台的价格
                            double zong2 = (size)* Double.parseDouble(String.valueOf(sessionBO.getPartnerPrice()));
                            BigDecimal bd2=new BigDecimal(zong2);
                            uploadTotalPrice =  bd2.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                }else {
                    if(sessionBO.getGlobalLeftNum()==null){
                        double zong = (size)* Double.parseDouble(sessionBO.getMarketPrice());
                        BigDecimal bd=new BigDecimal(zong);
                        zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                        orderPrice.setText("¥" + zongjia);
                        uploadTotalPrice = zongjia;
                    }else {
                        if(sessionBO.getPartnerPrice()==null){
                            double zong = (size)* Double.parseDouble(sessionBO.getMarketPrice());
                            BigDecimal bd=new BigDecimal(zong);
                            zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                            orderPrice.setText("¥" + zongjia);
                            uploadTotalPrice = zongjia;
                        }else {
                            if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                if(size>sessionBO.getLeftScreenLimitNum()){
                                    double  youhuijia =sessionBO.getLeftScreenLimitNum() * Double.parseDouble(sessionBO.getPartnerPrice());
                                    double noyouhui = (size-sessionBO.getLeftScreenLimitNum())* Double.parseDouble(sessionBO.getMarketPrice());
                                    double zong = youhuijia+noyouhui;
                                    BigDecimal bd=new BigDecimal(zong);
                                    zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                                    orderPrice.setText("¥" + zongjia);
                                    uploadTotalPrice = zongjia;

                                }else {
                                    double zong = (size)* Double.parseDouble(sessionBO.getPartnerPrice());
                                    BigDecimal bd=new BigDecimal(zong);
                                    zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                                    orderPrice.setText("¥" + zongjia);
                                    uploadTotalPrice = zongjia;
                                }
                            }else {
                                if(size>sessionBO.getGlobalLeftNum()){
                                    double  youhuijia =sessionBO.getGlobalLeftNum() * Double.parseDouble(sessionBO.getPartnerPrice());
                                    double noyouhui = (size-sessionBO.getGlobalLeftNum())* Double.parseDouble(sessionBO.getMarketPrice());
                                    double zong = youhuijia+noyouhui;
                                    BigDecimal bd=new BigDecimal(zong);
                                    zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                                    orderPrice.setText("¥" + zongjia);
                                    uploadTotalPrice = zongjia;
                                }else {
                                    double zong = (size)* Double.parseDouble(sessionBO.getPartnerPrice());
                                    BigDecimal bd=new BigDecimal(zong);
                                    zongjia =  bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
                                    orderPrice.setText("¥" + zongjia);
                                    uploadTotalPrice = zongjia;
                                }
                            }
                        }
                }

            }
        }

    /**
     * 设置底部座位显示
     */
    private void seatShow() {
        lin_seat1.setVisibility(View.INVISIBLE);
        lin_seat2.setVisibility(View.INVISIBLE);
        lin_seat3.setVisibility(View.INVISIBLE);
        lin_seat4.setVisibility(View.INVISIBLE);
        lin_seat5.setVisibility(View.INVISIBLE);
        lin_seat6.setVisibility(View.INVISIBLE);
        int i = 0;
        for (aCinemaSeatTableBO seatTableBO : selector.values()) {
            i++;
            switch (i) {
                case 1:
                    if(isVip.getIsVip()==1){
                        if (String.valueOf(preferentialnumber) == null && preferentialnumber<=0) {
                            seat11.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                        }else {
                            if(preferentialnumber>=1){
                                seat11.setText("惠：¥ " + String.valueOf(sessionBO.getGlobalPreferPrice()));
                            }else {
                                seat11.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                            }
                        }
                    }else {
                        if(sessionBO.getGlobalLeftNum()==null){
                            seat11.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                        }else {
                            if(sessionBO.getPartnerPrice()==null){
                                seat11.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                            }else {
                                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                    if(sessionBO.getLeftScreenLimitNum()>=1){
                                        seat11.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat11.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }else {
                                    if(sessionBO.getGlobalLeftNum()>=1){
                                        seat11.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat11.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }
                            }
                        }
                    }
                    seat1.setText(seatTableBO.getRowValue() + "排" + seatTableBO.getColumnValue() + "座");
                    lin_seat1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    if(isVip.getIsVip()==1){
                        if (String.valueOf(preferentialnumber) == null && preferentialnumber<=0) {
                            seat22.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                        }else {
                            if(preferentialnumber>=2){
                                seat22.setText("惠：¥ " + String.valueOf(sessionBO.getGlobalPreferPrice()));
                            }else {
                                seat22.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                            }
                        }
                    }else {
                        if(sessionBO.getGlobalLeftNum()==null){
                            seat22.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                        }else {
                            if(sessionBO.getPartnerPrice()==null){
                                seat22.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                            }else {
                                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                    if(sessionBO.getLeftScreenLimitNum()>=2){
                                        seat22.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat22.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }else {
                                    if(sessionBO.getGlobalLeftNum()>=2){
                                        seat22.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat22.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }
                            }
                        }
                    }
                    seat2.setText(seatTableBO.getRowValue() + "排" + seatTableBO.getColumnValue() + "座");
                    lin_seat2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    if(isVip.getIsVip()==1){
                        if (String.valueOf(preferentialnumber) == null && preferentialnumber<=0) {
                            seat33.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                        }else {
                            if(preferentialnumber>=3){
                                seat33.setText("惠：¥ " + String.valueOf(sessionBO.getGlobalPreferPrice()));
                            }else {
                                seat33.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                            }
                        }
                    }else {
                        if(sessionBO.getGlobalLeftNum()==null){
                            seat33.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                        }else {
                            if(sessionBO.getPartnerPrice()==null){
                                seat33.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                            }else {
                                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                    if(sessionBO.getLeftScreenLimitNum()>=3){
                                        seat33.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat33.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }else {
                                    if(sessionBO.getGlobalLeftNum()>=3){
                                        seat33.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat33.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }
                            }
                        }
                    }
                    seat3.setText(seatTableBO.getRowValue() + "排" + seatTableBO.getColumnValue() + "座");
                    lin_seat3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    if(isVip.getIsVip()==1){
                        if (String.valueOf(preferentialnumber) == null && preferentialnumber<=0) {
                            seat44.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                        }else {
                            if(preferentialnumber>=4){
                                seat44.setText("惠：¥ " + String.valueOf(sessionBO.getGlobalPreferPrice()));
                            }else {
                                seat44.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                            }
                        }
                    }else {
                        if(sessionBO.getGlobalLeftNum()==null){
                            seat44.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                        }else {
                            if(sessionBO.getPartnerPrice()==null){
                                seat44.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                            }else {
                                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                    if(sessionBO.getLeftScreenLimitNum()>=4){
                                        seat44.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat44.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }else {
                                    if(sessionBO.getGlobalLeftNum()>=4){
                                        seat44.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat44.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }
                            }
                        }
                    }
                    seat4.setText(seatTableBO.getRowValue() + "排" + seatTableBO.getColumnValue() + "座");
                    lin_seat4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    if(isVip.getIsVip()==1){
                        if (String.valueOf(preferentialnumber) == null && preferentialnumber<=0) {
                            seat55.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                        }else {
                            if(preferentialnumber>=4){
                                seat55.setText("惠：¥ " + String.valueOf(sessionBO.getGlobalPreferPrice()));
                            }else {
                                seat55.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                            }
                        }
                    }else {
                        if(sessionBO.getGlobalLeftNum()==null){
                            seat55.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                        }else {
                            if(sessionBO.getPartnerPrice()==null){
                                seat55.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                            }else {
                                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                    if(sessionBO.getLeftScreenLimitNum()>=4){
                                        seat55.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat55.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }else {
                                    if(sessionBO.getGlobalLeftNum()>=4){
                                        seat55.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat55.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }
                            }
                        }
                    }
                    seat5.setText(seatTableBO.getRowValue() + "排" + seatTableBO.getColumnValue() + "座");
                    lin_seat5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    if(isVip.getIsVip()==1){
                        if (String.valueOf(preferentialnumber) == null && preferentialnumber<=0) {
                            seat66.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                        }else {
                            if(preferentialnumber>=4){
                                seat66.setText("惠：¥ " + String.valueOf(sessionBO.getGlobalPreferPrice()));
                            }else {
                                seat66.setText("卡：¥ " + String.valueOf(sessionBO.getPreferPrice()));
                            }
                        }
                    }else {
                        if(sessionBO.getGlobalLeftNum()==null){
                            seat66.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                        }else {
                            if(sessionBO.getPartnerPrice()==null){
                                seat66.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                            }else {
                                if(sessionBO.getLeftScreenLimitNum()<sessionBO.getGlobalLeftNum()){
                                    if(sessionBO.getLeftScreenLimitNum()>=4){
                                        seat66.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat66.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }else {
                                    if(sessionBO.getGlobalLeftNum()>=4){
                                        seat66.setText("惠：¥ " + String.valueOf(sessionBO.getPartnerPrice()));
                                    }else {
                                        seat66.setText("原：¥ " + String.valueOf(sessionBO.getMarketPrice()));
                                    }
                                }
                            }
                        }
                    }
                    seat6.setText(seatTableBO.getRowValue() + "排" + seatTableBO.getColumnValue() + "座");
                    lin_seat6.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
//                issAvailable();
//                if (!isAvailable){
//                    ToastUtils.showShortToast("不要留下单个空座");
//                    return;
//                }
                showNoProgress("锁座中...");
                mPresenter.lockSeats(null,getSeats(),getSeatsId(),String.valueOf(uploadTotalPrice),String.valueOf(selector.size()),
                        MyApplication.cinemaBo.getCinemaNumber(),sessionBO.getHallId(),
                        sessionBO.getDxId(),sessionBO.getCineMovieNum(),String.valueOf(sessionBO.getPreferPrice()),
                        String.valueOf(sessionBO.getGlobalPreferPrice()),String.valueOf(sessionBO.getGlobalCanBuyNum()));
                break;
            case R.id.update_session:   //更换场次
                finish();
                break;
        }
    }


    /**
     * 获取选中的座位用1_1，2_2连起来
     */
    private String getSeats() {
        StringBuffer buffer = new StringBuffer();
        for (aCinemaSeatTableBO seatTableBO : selector.values()) {
            buffer.append(seatTableBO.getRowValue() + "_" + seatTableBO.getColumnValue() + ",");
        }
        return buffer.substring(0, buffer.length() - 1);
    }


    /**
     * 获取座位Id
     *
     * @return
     */
    private String getSeatsId() {
        StringBuffer buffer = new StringBuffer();
        for (aCinemaSeatTableBO seatTableBO : selector.values()) {
            buffer.append(seatTableBO.getCineSeatId() + ",");
        }
        return buffer.substring(0, buffer.length() - 1);
    }

    static boolean isAvailable = true;
    private void issAvailable(){
        for (aCinemaSeatTableBO seatTableBO : selector.values()) {//-1为当前位置
            int rowValue = Integer.valueOf(seatTableBO.getRowValue());
            int columnValue = Integer.valueOf(seatTableBO.getColumnValue());
            if (seatTable.getSeatType(rowValue,columnValue) == SEAT_TYPE_SELECTED) {
                setAvailable(rowValue, columnValue);
                if (!isAvailable){
                    break;
                }
            }else if (seatTable.getSeatType(rowValue,columnValue+1) == SEAT_TYPE_SELECTED){
                setAvailable(rowValue, columnValue + 1);
                if (!isAvailable){
                    break;
                }
            }else if (seatTable.getSeatType(rowValue,columnValue+2) == SEAT_TYPE_SELECTED){
                setAvailable(rowValue, columnValue + 2);
                if (!isAvailable){
                    break;
                }
            }else {
                setAvailable(rowValue, columnValue-1);
                if (!isAvailable){
                    break;
                }
            }
        }
    }

    private void setAvailable(int row,int column){
        if (seatTable.getSeatType(row,column - 1) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column-2) == SEAT_TYPE_SOLD){//当前位置左边第一个可用，第二个已售，有一个空位
            if (seatTable.getSeatType(row,column + 1) == SEAT_TYPE_SOLD
                    || seatTable.getSeatType(row,column + 1) == SEAT_TYPE_SELECTED){
                isAvailable = true;
            }else {
                isAvailable = false;
            }
        }else if (seatTable.getSeatType(row,column - 1) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row , column- 2) == SEAT_TYPE_SELECTED){//当前位置左边第一个可用，第二个已经选择，有一个空位
            isAvailable = false;
        }else if (seatTable.getSeatType(row,column - 1) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row , column- 2) == SEAT_TYPE_NOT_AVAILABLE){//当前位置左边第一个可用，第二个不可选择，有一个空位
            if (seatTable.getSeatType(row,column - 2) == SEAT_TYPE_SOLD
                    || seatTable.getSeatType(row , column- 2) == SEAT_TYPE_NOT_AVAILABLE
                    || seatTable.getSeatType(row , column- 2) == SEAT_TYPE_SELECTED){
                if (seatTable.getSeatType(row,column +1) == SEAT_TYPE_AVAILABLE
                        && seatTable.getSeatType(row,column +2) == SEAT_TYPE_SOLD){
                    isAvailable = false;
                }else if (seatTable.getSeatType(row,column +1) == SEAT_TYPE_AVAILABLE
                        && seatTable.getSeatType(row,column +2) == SEAT_TYPE_SELECTED){
                    isAvailable = false;
                }else if (seatTable.getSeatType(row,column -1) == SEAT_TYPE_AVAILABLE
                        && seatTable.getSeatType(row,column -2) == SEAT_TYPE_NOT_AVAILABLE
                        && seatTable.getSeatType(row,column +1) == SEAT_TYPE_SOLD){
                    isAvailable = true;
                }else if (seatTable.getSeatType(row,column -1) == SEAT_TYPE_AVAILABLE
                        && seatTable.getSeatType(row,column -2) == SEAT_TYPE_NOT_AVAILABLE
                        && seatTable.getSeatType(row,column +1) == SEAT_TYPE_AVAILABLE){
                    isAvailable = false;
                }else {
                    isAvailable = true;
                }
            }else {
                isAvailable = false;
            }
        }else if (seatTable.getSeatType(row ,column- 1) == SEAT_TYPE_SOLD){//当前位置左边第一个已售，没有空位
            if (seatTable.getSeatType(row ,column-1) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column) == SEAT_TYPE_SOLD ){
                isAvailable = false;
            }else if (seatTable.getSeatType(row,column-1) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column)== SEAT_TYPE_SELECTED ){
                isAvailable = false;
            }else {
                isAvailable = true;
            }
        }else if (seatTable.getSeatType(row,column- 1) == SEAT_TYPE_SELECTED){//当前位置左边第一个已选择，没有空位
            if (seatTable.getSeatType(row,column-2) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column - 3)== SEAT_TYPE_SOLD ){
                if (seatTable.getSeatType(row,column + 1) == SEAT_TYPE_NOT_AVAILABLE){
                    isAvailable = true;
                }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column + 2)== SEAT_TYPE_NOT_AVAILABLE ){
                    isAvailable = true;
                }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column + 2)== SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column + 3)== SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column + 4)== SEAT_TYPE_NOT_AVAILABLE ){
                    isAvailable = true;
                }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column + 2)== SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column + 3)== SEAT_TYPE_NOT_AVAILABLE){
                    isAvailable = true;
                }else {
                    isAvailable = false;
                }
            }else if (seatTable.getSeatType(row,column-2) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column-3)== SEAT_TYPE_SELECTED ){
                isAvailable = false;
            }else if (seatTable.getSeatType(row,column-2) == SEAT_TYPE_NOT_AVAILABLE){
                isAvailable = true;
            }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column + 2)== SEAT_TYPE_SOLD ){
                if (seatTable.getSeatType(row,column - 1) == SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column -2)== SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column -3)== SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column -4)== SEAT_TYPE_NOT_AVAILABLE){
                    isAvailable = true;
                }else if (seatTable.getSeatType(row,column - 1) == SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column -2)== SEAT_TYPE_SELECTED
                        && seatTable.getSeatType(row ,column -3)== SEAT_TYPE_NOT_AVAILABLE){
                    isAvailable = true;
                }else {
                    isAvailable = false;
                }
            }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column + 2)== SEAT_TYPE_SELECTED ){
                isAvailable = false;
            }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_AVAILABLE
                    && seatTable.getSeatType(row ,column + 2)== SEAT_TYPE_NOT_AVAILABLE ){
                isAvailable = false;
            }else if ((seatTable.getSeatType(row,column+1) == SEAT_TYPE_SELECTED)){
                isAvailable = true;
            }else {
                isAvailable = true;
            }
        }else if (seatTable.getSeatType(row,column-1 )== SEAT_TYPE_NOT_AVAILABLE){//当前座位的左边不可挑选
            isAvailable = true;
        }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column+2) == SEAT_TYPE_SOLD){//当前位置右边第一个可用，第二个已售，有一个空位
            isAvailable = false;
        }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column+2) == SEAT_TYPE_SELECTED){//当前位置右边第一个可用，第二个已选择，有一个空位
            isAvailable = false;
        }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column+2) == SEAT_TYPE_NOT_AVAILABLE){//当前位置右边第一个可用，第二个不可用，有一个空位
            isAvailable = false;
        }else if (seatTable.getSeatType(row,column+2) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column+3) == SEAT_TYPE_SOLD){//当前位置右边第二个可用，第三个已售，有一个空位
            isAvailable = true;
        }else if (seatTable.getSeatType(row,column+2) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column+3) == SEAT_TYPE_SELECTED){//当前位置右边第二个可用，第三个已选择，有一个空位
            isAvailable = true;
        }else if (seatTable.getSeatType(row,column+2) == SEAT_TYPE_AVAILABLE
                && seatTable.getSeatType(row,column+3) == SEAT_TYPE_NOT_AVAILABLE){//当前位置右边第二个可用，第三个不可用，有一个空位
            isAvailable = true;
        }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_SOLD){//当前位置右边第一个已售，没有空位
            isAvailable = true;
        }else if (seatTable.getSeatType(row,column+1) == SEAT_TYPE_SELECTED){//当前位置右边第一个已选择，没有空位
            isAvailable = true;
        }else if (seatTable.getSeatType(row,column+1)== SEAT_TYPE_NOT_AVAILABLE){//当前座位的右边第一个不可挑选
            isAvailable = true;
        }else {
            isAvailable = true;
        }
    }



    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
        mPresenter.loadSeatTables(MyApplication.cinemaBo.getCinemaId(),
                sessionBO.getDxId(), sessionBO.getCineUpdateTime());
    }

    @Override
    public void onRequestEnd() {
        stopProgress();
    }

    @Override
    public void getConfirmOrderInfo(LockSeatsBO lockSeatsBO) {
        this.lockSeatsBO = lockSeatsBO;
        MyApplication.orderBO = lockSeatsBO;
        MyApplication.selectedId = -1;//清空之前选择的优惠券
        Bundle bundle = new Bundle();
        bundle.putSerializable("LockSeatsBO", lockSeatsBO);
        bundle.putSerializable("MoviesSession", sessionBO);   //场次
        bundle.putSerializable("movies", movies);              //电影
        bundle.putInt("num", selector.size());                  //电影张数
        bundle.putString("seats", getSeats());
        bundle.putString("seatId", getSeatsId());
        bundle.putDouble("uploadTotalPrice",uploadTotalPrice);
        bundle.putInt("preferentialnumber", preferentialnumber);
        bundle.putString("areaId",seatBeanList.get(6).getAreaId());
        if (lockSeatsBO.getSeatTicket() != null) {
            bundle.putInt("ticketCouponId", lockSeatsBO.getSeatTicket().getId());
        }else {
            bundle.putInt("ticketCouponId", 0);
        }
        gotoActivity(ConfrimOrderActivity.class, bundle, false);
    }

    /**
     * 关闭
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){
        if (messageEvent.getMessageType().equals("SeatTebleActivity")){
            finish();
        }
    }
}
