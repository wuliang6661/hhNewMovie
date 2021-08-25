package com.myp.hhcinema.ui.hotsellprodect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.donkingliang.banner.CustomBanner;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.entity.ActivityBO;
import com.myp.hhcinema.entity.FatherData;
import com.myp.hhcinema.entity.HotSellBO;
import com.myp.hhcinema.entity.HotSellBannerBO;
import com.myp.hhcinema.entity.MerchandBO;
import com.myp.hhcinema.entity.ProdectBO;
import com.myp.hhcinema.entity.SendGoodsBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.WebViewActivity;
import com.myp.hhcinema.ui.moviesmessage.MoviesMessageActivity;
import com.myp.hhcinema.ui.prodectorder.ProdectOrderActivity;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.SoftInputUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.widget.VpSwipeRefreshLayout;
import com.myp.hhcinema.widget.viewpager.ViewPagerSlide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by witness on 2018/11/14.
 *  热卖小食
 */

public class HotSellActivity extends MVPBaseActivity<HotSellContract.View,HotSellPresenter>
                            implements HotSellContract.View,CheckListener,View.OnClickListener{

    @Bind(R.id.refresh_root)
    VpSwipeRefreshLayout refresh_root;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.banner)
    CustomBanner banner;
    @Bind(R.id.hotLocationCinema)
    TextView hotLocationCinema;//banner上面的影院名称
    @Bind(R.id.rlLocateDismiss)
    RelativeLayout rlLocateDismiss;//置顶显示的影院名称布局，banner显示的时候Gone，banner消失的时候Visible
    @Bind(R.id.txtLocationCinemaBelow)
    TextView txtLocationCinemaBelow;//置顶显示的影院名称
    @Bind(R.id.txtDes)
    TextView txtDes;
    @Bind(R.id.txtDesNum)
    TextView txtDesNum;
    @Bind(R.id.llProdect)
    LinearLayout llProdect;//商品布局
    @Bind(R.id.rvLeft)
    RecyclerView rvLeft;//左侧布局
    @Bind(R.id.flRight)
    FrameLayout flRight;//右侧布局
    @Bind(R.id.tvShopCarNum)
    TextView tvShopCarNum;//购物车数量
    @Bind(R.id.tvPrice)
    TextView tvPrice;//商品总价
    @Bind(R.id.llSure)
    LinearLayout llSure;//选好了
    @Bind(R.id.rlDecreaseActivity)
    RelativeLayout rlDecreaseActivity;
    @Bind(R.id.txtDecreaseInstruction)
    TextView txtDecreaseInstruction;
    @Bind(R.id.go_back)
    LinearLayout go_back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.OneDecreaseActivity)
    RelativeLayout OneDecreaseActivity;//单独描述
    @Bind(R.id.oneDecreaseInstruction)
    TextView oneDecreaseInstruction;
    @Bind(R.id.rlBanner)
    RelativeLayout rlBanner;

    private SortAdapter mSortAdapter;
    private SortDetailFragment mSortDetailFragment;
    private LinearLayoutManager mLinearLayoutManager;
    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;
    private SortBean mSortBean;
    private List<HotSellBO> hotSellBO = new ArrayList<>();
    private List<HotSellBannerBO> bannerBOList = new ArrayList<>();
    private SPUtils spUtils;
    private ArrayList<String> goodsId = new ArrayList<>();//选中的食品id
    private String merchandiseInfo = "";//视频id和number 的json
    private ProdectBO prodectBO;
    private boolean isVisible = false;//当前页面是否可见
    private boolean priceIsOk = false;//价格是否已经算好
    private boolean isIntent = false;//是否是从订单界面回来过

    @Override
    protected int getLayout() {
        return R.layout.hot_sell_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        title.setText("卖品");
//        showDialog("","");
    }


    private void initViews(){
        goBack();
        spUtils = new SPUtils("ProdectItemNum");
        go_back.setVisibility(View.VISIBLE);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvLeft.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvLeft.addItemDecoration(decoration);
        invitionSwipeRefresh(refresh_root);
        setListener();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        spUtils.clear();
        MyApplication.goodsId.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        spUtils.clear();
        MyApplication.goodsId.clear();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        spUtils.clear();
        MyApplication.goodsId.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.cinemaBo != null) {
            if (MyApplication.cinemaBo.getCinemaName() != null) {
                String name = MyApplication.cinemaBo.getCinemaName();
                txtLocationCinemaBelow.setText(name);
                hotLocationCinema.setText(name);
            }
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(!hidden){
//            if (MyApplication.cinemaBo != null) {
//                if (mSortDetailFragment != null) {
//                    mSortDetailFragment.clearData();
//                }
//                merchandiseInfo = "";
//                goodsId.clear();
//                tvShopCarNum.setVisibility(View.GONE);
//                tvPrice.setText(String.format("￥ %s", 0.0));
//                isVisible = true;
//                String name = MyApplication.cinemaBo.getCinemaName();
//                txtLocationCinemaBelow.setText(name);
//                hotLocationCinema.setText(name);
//
//            }
//        }else {
//            isVisible = false;
//        }
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            if (mSortDetailFragment != null) {
//                mSortDetailFragment.clearData();
//                tvShopCarNum.setVisibility(View.GONE);
//                tvPrice.setText(String.format("￥ %s", 0.0));
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSure:
                MyApplication.selectedId = -1;//清空之前选择的优惠券
                if (!merchandiseInfo.equals("")) {
                    if (priceIsOk || isIntent) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ProdectBO", prodectBO);
                        bundle.putStringArrayList("goodsId", goodsId);
                        bundle.putString("sendAddress",sendAddress);
                        bundle.putInt("deliveryType",0);
                        gotoActivity(ProdectOrderActivity.class, bundle, false);
                        priceIsOk = false;
                        isIntent = true;
                        MyApplication.isIntent = true;
                        MyApplication.priceIsOk = false;
                    }
                }else {
                    ToastUtils.showShortToast("你还没选择好吃的呢");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getHotSellData(List<HotSellBO> hotSellBO) {
        refresh_root.setRefreshing(false);
        if (hotSellBO.size() != 0) {
            rvLeft.setVisibility(View.VISIBLE);
            flRight.setVisibility(View.VISIBLE);
            llSure.setEnabled(true);
            this.hotSellBO = hotSellBO;
            MyApplication.hotSellBO = hotSellBO;
            List<HotSellBO> mHotSellBO = hotSellBO;
            for (int i=0;i<hotSellBO.size();i++){
                if (hotSellBO.get(i).getMerchandiseList() == null
                        || hotSellBO.get(i).getMerchandiseList().size() == 0){
                    mHotSellBO.remove(i);
                }
            }

            mSortAdapter = new SortAdapter(this, mHotSellBO, new RvListener() {
                @Override
                public void onItemClick(int id, int position) {
                    if (mSortDetailFragment != null) {
                        isMoved = true;
                        targetPosition = position;
                        setChecked(position, true);
                    }
                }
            });
            rvLeft.setAdapter(mSortAdapter);
            createFragment();
        }else {
            rvLeft.setVisibility(View.GONE);
            flRight.setVisibility(View.GONE);
            llSure.setEnabled(false);
            if (isVisible) {
                ToastUtils.showShortToast("该影城暂无商品");
            }
            mSortAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getBanners(List<HotSellBannerBO> hotSellBO) {
        refresh_root.setRefreshing(false);
        if (hotSellBO.size() != 0) {
            this.bannerBOList = hotSellBO;
            rlBanner.setVisibility(View.VISIBLE);
            setBannerAdapter();
        }else {
            this.bannerBOList = hotSellBO;
            rlBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void getOrder(ProdectBO prodectBO) {
        stopProgress();
        MyApplication.isSuccess = true;
        MyApplication.prodectBO = prodectBO;
        this.prodectBO = prodectBO;
        if (prodectBO != null) {
            if (prodectBO.getTotalNumber() > 0) {
                tvShopCarNum.setVisibility(View.VISIBLE);
                tvShopCarNum.setText(String.valueOf(prodectBO.getTotalNumber()));
                String price;
                if (prodectBO.getDisPrice() == prodectBO.getTotalPrice()){
                    tvPrice.setText(String.format("￥ %s", prodectBO.getDisPrice()));
                    price = String.format("￥ %s", prodectBO.getDisPrice());
                }else {
                    tvPrice.setText(String.format("￥ %s (已优惠: ￥ %s)",prodectBO.getDisPrice(),
                            new DecimalFormat("0.00").format(prodectBO.getTotalPrice() - prodectBO.getDisPrice())));
                    price = String.format("￥ %s (已优惠: ￥ %s)",prodectBO.getDisPrice(),
                            new DecimalFormat("0.00").format(prodectBO.getTotalPrice() - prodectBO.getDisPrice()));
                }
                EventBus.getDefault().post(new MessageEvent("num", String.valueOf(prodectBO.getTotalNumber())));//发给SortDetailFragment
                EventBus.getDefault().post(new MessageEvent("price", price));//发给SortDetailFragment
            } else {
                EventBus.getDefault().post(new MessageEvent("num", "0"));//发给SortDetailFragment
                EventBus.getDefault().post(new MessageEvent("price", String.format("￥ %s", 0.0)));//发给SortDetailFragment
                tvShopCarNum.setVisibility(View.GONE);
                tvPrice.setText(String.format("￥ %s", 0.0));
            }
            if (prodectBO.getPartOne() != null && !prodectBO.getPartOne().equals("")){
                if (prodectBO.getPartOne() != null && prodectBO.getPartTwo() == null){
                    rlDecreaseActivity.setVisibility(View.GONE);
                    oneDecreaseInstruction.setVisibility(View.VISIBLE);
                    oneDecreaseInstruction.setText(prodectBO.getPartOne());
                }else {
                    oneDecreaseInstruction.setVisibility(View.GONE);
                    rlDecreaseActivity.setVisibility(View.VISIBLE);
                    txtDecreaseInstruction.setText(String.format("%s,",prodectBO.getPartOne()));
                    txtDes.setText(String.format(" %s元,",new DecimalFormat("0.00").format(Double.valueOf(prodectBO.getPartTwo()))));
                    txtDesNum.setText(String.format(" %s元",new DecimalFormat("0.00").format(Double.valueOf(prodectBO.getPartThree()))));
                    MyApplication.partOne = prodectBO.getPartOne();
                    MyApplication.partTwo = prodectBO.getPartTwo();
                    MyApplication.partThree = prodectBO.getPartThree();
                    EventBus.getDefault().post(new MessageEvent("activityChanged", "yes"));//发给SortDetailFragment
                }
            }else {
                rlDecreaseActivity.setVisibility(View.GONE);
                oneDecreaseInstruction.setVisibility(View.GONE);
                MyApplication.partOne = "";
                MyApplication.partTwo = "";
                MyApplication.partThree = "";
                EventBus.getDefault().post(new MessageEvent("activityChanged", "no"));//发给SortDetailFragment
            }
            MyApplication.isOk = true;
            priceIsOk = true;
            MyApplication.priceIsOk = true;
        }
        mSortDetailFragment.refresh();
    }

    @Override
    public void getActivity(ActivityBO activityBO) {
//        if (activityBO.getName() != null && !activityBO.getName().equals("")){
//            OneDecreaseActivity.setVisibility(View.VISIBLE);
//            oneDecreaseInstruction.setText(activityBO.getName());
//        }else {
//            oneDecreaseInstruction.setVisibility(View.GONE);
//        }
    }

    private SendGoodsBO sendGoodsBO;
    @Override
    public void getSendParam(SendGoodsBO sendGoodsBO) {
        this.sendGoodsBO = sendGoodsBO;
        if (sendGoodsBO.getCode().equals("haveTicket")){
            data = Arrays.asList(sendGoodsBO.getData().getSeats().split(","));
//            showDialog(sendGoodsBO.getData().getMovieName(),
//                    sendGoodsBO.getData().getPlayTime()+","+sendGoodsBO.getData().getHallName(),
//                    sendGoodsBO.getData().getHallList());
        }else {
//            if (sendGoodsBO.getData().getHallList() != null) {
//                showDialog("", "", sendGoodsBO.getData().getHallList());
//            }
        }
    }

    @Override
    public void onRequestError(String msg) {
        refresh_root.setRefreshing(false);
        stopProgress();
        MyApplication.isSuccess = true;
        priceIsOk = true;
        MyApplication.priceIsOk = true;
        MyApplication.isOk = true;
    }

    @Override
    public void onRequestEnd() {
        stopProgress();
        refresh_root.setRefreshing(false);
        MyApplication.isSuccess = true;
        MyApplication.isOk = true;
    }

    private void initData(){
        showProgress("");
        if (MyApplication.isLogin == ConditionEnum.LOGIN) {
            if (MyApplication.cinemaBo.getCinemaId() != null && MyApplication.user != null) {
                mPresenter.loadHotSellData(MyApplication.user.getId(),MyApplication.cinemaBo.getCinemaId());
                mPresenter.loadBanners(MyApplication.cinemaBo.getCinemaId(), "merchandise");
                mPresenter.loadActivity(MyApplication.cinemaBo.getCinemaId());
                mPresenter.loadSendParam(MyApplication.cinemaBo.getCinemaId(),MyApplication.user.getId());
            }
        }
    }

    public void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mSortDetailFragment = new SortDetailFragment();
        mSortDetailFragment.setListener(this);
        fragmentTransaction.add(R.id.flRight, mSortDetailFragment);
        fragmentTransaction.commit();
    }

    private void setChecked(int position, boolean isLeft) {
        Log.d("p-------->", String.valueOf(position));
        if (isLeft) {
            mSortAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += hotSellBO.get(i).getMerchandiseList().size();
            }
            count += position;
            mSortDetailFragment.setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false;
            } else
                mSortAdapter.setCheckedPosition(position);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position);

    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = rvLeft.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - rvLeft.getHeight() / 2);
            rvLeft.smoothScrollBy(0, y);
        }

    }

    /**
     * 初始化下拉刷新控件
     */
    protected void invitionSwipeRefresh(SwipeRefreshLayout mSwipeLayout) {
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.white); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
    }

    private void setListener(){
        llSure.setOnClickListener(this);
        refresh_root.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset >= 0){
                    refresh_root.setEnabled(true);
                }else{
                    refresh_root.setEnabled(false);
                }
                if (verticalOffset < - 380){
                    rlLocateDismiss.setVisibility(View.VISIBLE);
                }else {
                    rlLocateDismiss.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }

    /**
     * 设置轮播图视图
     */
    private void setBannerAdapter() {
        banner.setPages(new CustomBanner.ViewCreator<HotSellBannerBO>() {
            @Override
            public View createView(Context context, int i) {
                ImageView imageView = new ImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int i, HotSellBannerBO lunBoBO) {
                Glide.with(context).load(lunBoBO.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .error(R.drawable.zhanwei2)
                        .into((ImageView) view);
            }
        }, bannerBOList);
        if (bannerBOList.size() == 1) {   //只有一张轮播图   不滚动
            banner.stopTurning();
        } else {
            banner.startTurning(4000);
            //设置轮播图的滚动速度
            banner.setScrollDuration(300);
        }
        //设置轮播图的点击事件
        banner.setOnPageClickListener(new CustomBanner.OnPageClickListener<HotSellBannerBO>() {
            @Override
            public void onPageClick(int position, HotSellBannerBO str) {
                if (1 == str.getPlayType()) {   //页面跳转
                    if (!StringUtils.isEmpty(str.getRedirectUrl())) {
                        Bundle bundle = new Bundle();
                        if (str.getRedirectUrl().contains("?")) {
                            bundle.putString("url", str.getRedirectUrl() + "&cinemaId=" + MyApplication.cinemaBo.getCinemaId());
                        }else {
                            bundle.putString("url", str.getRedirectUrl() + "?cinemaId=" + MyApplication.cinemaBo.getCinemaId());
                        }
                        gotoActivity(WebViewActivity.class, bundle, false);
                    }
                } else if (2 == str.getPlayType()) {   //电影详情界面
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("movie", str.getDxMovie());
                    gotoActivity(MoviesMessageActivity.class, bundle, false);
                }
            }
        });
    }

    /**
     * 显示购买数量,接收来自ClassifyDetailAdapter的消息
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){
        if (messageEvent.getMessageType().equals("goodsNum")){
            String id = String.valueOf(messageEvent.getPassValue());
            if (!goodsId.contains(id)) {
                goodsId.add(id);
            }
            if (!MyApplication.goodsId.contains(id)){
                MyApplication.goodsId.add(id);
            }
            MyApplication.isOk = false;
            modifyPrice();
        }
    }

    private void modifyPrice(){
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
                mPresenter.getOrderPrice(merchandiseInfo, "",String.valueOf(MyApplication.cinemaBo.getCinemaId()),"1");
            }
        }else {
            tvPrice.setText(String.format("￥ %s", 0));
            tvShopCarNum.setVisibility(View.GONE);
            EventBus.getDefault().post(new MessageEvent("num", "0"));//发给SortDetailFragment
            EventBus.getDefault().post(new MessageEvent("price", String.format("￥ %s", 0.0)));//发给SortDetailFragment
            merchandiseInfo = "";
            MyApplication.isSuccess = true;
            mSortDetailFragment.refresh();
            MyApplication.isOk = true;
            rlDecreaseActivity.setVisibility(View.GONE);
//            OneDecreaseActivity.setVisibility(View.VISIBLE);
            MyApplication.partOne = "";
            MyApplication.partTwo = "";
            MyApplication.partThree = "";
            EventBus.getDefault().post(new MessageEvent("activityChanged", "no"));//发给SortDetailFragment
        }
    }

    /**
     * 卖品送至座位
     */
    private String sendAddress = "";//配送地址
    private int deliveryType  = 0;//  配送方式  0自提，1配送
    private ViewPagerSlide viewPagerSlide;
    private TextView txtTitle;
    private View view1, view2, view3,view4,viewSeatSelect;
    private List<View> viewList = new ArrayList<>();//view数组
    private PagerAdapter pagerAdapter;
    private LinearLayout layoutMain;


    private RelativeLayout rlGet;//自取
    private ImageView imageSelect;//自取
    private RelativeLayout rlSend;//送至座位
    private ImageView imageSelectSend;//送至座位
    private TextView txtHallMes;
    private Button buttonOne;
    private boolean isGet = true;//是否柜台自取
    //已买电影票
    private TextView movieName;//名称
    private TextView movieMessage;//位置时间信息
    private RecyclerView recyclerView;//座位
    private RelativeLayout RlOthers;//其他座位
    private CommonAdapter<String> adapter;
    private List<String> data = new ArrayList<>();
    private Button buttonTwo;

    //手动输入
    private RelativeLayout rlSelectHall;//选择影厅
    private RelativeLayout rlSelectSeat;/* 选择座位 */
    private ExpandableListView expandableListView;
    private EditText seatName;//座位
    private EditText hallName;
    private ArrayList<FatherData> datas;
    private Button btnSureThree;

    private RecyclerView hallListView;
    private CommonAdapter<String> hallAdapter;

    private WheelView wheelView1,wheelView2;//座位选择
    private LinearLayout seatSelectLayout;

    private RelativeLayout rlLastStep;/* 上一步*/
    private RelativeLayout rlConfirm;/* 确认 */
    private String seatSelected = "";/* 选中的座位 */

    private void showDialog(final String name, String message,List<String> hallList) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(HotSellActivity.this,R.style.AlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(HotSellActivity.this);
        View v = inflater.inflate(R.layout.prodect_send_layout, null);
        viewPagerSlide = v.findViewById(R.id.viewPager);
        layoutMain = v.findViewById(R.id.layoutMain);

        txtTitle = v.findViewById(R.id.txtTitle);
        rlLastStep = v.findViewById(R.id.rlLastStep);
        rlConfirm = v.findViewById(R.id.rlConfirm);
        rlLastStep.setVisibility(View.GONE);
        rlConfirm.setVisibility(View.GONE);
        viewPagerSlide.setScanScroll(false);
        view1 = inflater.inflate(R.layout.send_layout_one,null);
        view2 = inflater.inflate(R.layout.send_layout_two,null);
        view3 = inflater.inflate(R.layout.send_layout_three,null);
        view4 = inflater.inflate(R.layout.send_layout_four,null);
        viewSeatSelect = inflater.inflate(R.layout.seat_select_layout,null);

        rlGet = view1.findViewById(R.id.rlGet);
        imageSelect = view1.findViewById(R.id.imageSelect);
        rlSend = view1.findViewById(R.id.rlSend);
        imageSelectSend = view1.findViewById(R.id.imageSelectSend);
        buttonOne = view1.findViewById(R.id.btnSure);
        txtHallMes = view1.findViewById(R.id.txtHallMes);

        movieName = view2.findViewById(R.id.movieName);
        movieMessage = view2.findViewById(R.id.movieMessage);
        recyclerView = view2.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buttonTwo = view2.findViewById(R.id.btnSuretwo);
        RlOthers = view2.findViewById(R.id.RlOthers);

        movieName.setText(name);
        movieMessage.setText(message);


        rlSelectHall = view3.findViewById(R.id.rlSelectHall);
        rlSelectSeat = view3.findViewById(R.id.rlSelectSeat);
        seatName = view3.findViewById(R.id.seatName);
        hallName = view3.findViewById(R.id.hallName);
        hallName.setHintTextColor(Color.parseColor("#878787"));
        seatName.setHintTextColor(Color.parseColor("#878787"));
        seatName.setEnabled(false);
        btnSureThree = view3.findViewById(R.id.btnSureThree);

        /* 座位选择 */
        wheelView1 = viewSeatSelect.findViewById(R.id.wheelview1);
        wheelView2 = viewSeatSelect.findViewById(R.id.wheelview2);
        seatSelectLayout = viewSeatSelect.findViewById(R.id.seatSelectLayout);
        wheelView1.setCyclic(false);
        wheelView2.setCyclic(false);

//        ViewGroup.LayoutParams params = wheelView2.getLayoutParams();
//        params.height=ScreenUtils.getScreenWidth();
//        wheelView2.setLayoutParams(params);

        final List<String> mOptionsItems1 = new ArrayList<>();
        for (int i=1;i<=30;i++) {
            mOptionsItems1.add(i+"排");
        }

        final List<String> mOptionsItems2 = new ArrayList<>();
        for (int i=1;i<=40;i++) {
            mOptionsItems2.add(i+"座");
        }

        wheelView1.setAdapter(new ArrayWheelAdapter(mOptionsItems1));
        wheelView2.setAdapter(new ArrayWheelAdapter(mOptionsItems2));
        final String[] selectOne = {"1排"};
        final String[] selectTwo = {"1座"};
        wheelView1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectOne[0] = "" + mOptionsItems1.get(index);
            }
        });

        wheelView2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectTwo[0] = "" + mOptionsItems2.get(index);
            }
        });

        rlLastStep .setOnClickListener(new View.OnClickListener() {/* 上一步 */
            @Override
            public void onClick(View v) {
                viewPagerSlide.setCurrentItem(0);
                txtTitle.setText("取餐方式");
                rlLastStep.setVisibility(View.GONE);
                rlConfirm.setVisibility(View.GONE);
            }
        });

        rlConfirm .setOnClickListener(new View.OnClickListener() {/* 确认 */
            @Override
            public void onClick(View v) {
                seatSelected = selectOne[0] + selectTwo[0];
                viewPagerSlide.setCurrentItem(2);
                txtTitle.setText("选择座次");
                seatName.setText(seatSelected);
                rlLastStep.setVisibility(View.VISIBLE);
                rlConfirm.setVisibility(View.GONE);
            }
        });

        seatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seatName!=null){
                    //设置可获得焦点
                    seatName.setFocusable(true);
                    seatName.setFocusableInTouchMode(true);
                    //请求获得焦点
                    seatName.requestFocus();
                    //调用系统输入法
                    InputMethodManager inputManager = (InputMethodManager) seatName
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(seatName, 0);
                }
            }
        });

        hallListView = view4.findViewById(R.id.hallList);
        hallListView.setLayoutManager(new LinearLayoutManager(this));

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(viewSeatSelect);

        setAdapter();//设置ViewPager适配器
        setRecyAdapter();//设置座位列表适配器
        sethallAdapter(hallList);//设置影厅列表

        rlGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGet = true;
                Glide.with(HotSellActivity.this).load(R.mipmap.item_select).into(imageSelect);
                Glide.with(HotSellActivity.this).load(R.mipmap.item_notselect).into(imageSelectSend);
                deliveryType = 0;
            }
        });

        rlSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGet = false;
                Glide.with(HotSellActivity.this).load(R.mipmap.item_notselect).into(imageSelect);
                Glide.with(HotSellActivity.this).load(R.mipmap.item_select).into(imageSelectSend);
                deliveryType = 1;
                if (name.equals("")){
                    viewPagerSlide.setCurrentItem(2);
                    txtTitle.setText("选择座次");
                }else {
                    viewPagerSlide.setCurrentItem(1);
                    txtTitle.setText("当前影片");
                }
                rlLastStep.setVisibility(View.VISIBLE);
                rlConfirm.setVisibility(View.GONE);
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {//第二个界面的确认按钮
            @Override
            public void onClick(View v) {
                if (!selecthallName.equals("")) {
                    txtHallMes.setText(String.format("%s,%s", sendGoodsBO.getData().getHallName(), selecthallName));
                    viewPagerSlide.setCurrentItem(0);
                    txtTitle.setText("取餐方式");
                    SoftInputUtils.closeSoftInput(HotSellActivity.this);
                    rlLastStep.setVisibility(View.GONE);
                    rlConfirm.setVisibility(View.GONE);
                    //隐藏软键盘
//                    InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if(imm.isActive()){
//                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//                    }

                }else {
                    ToastUtils.showShortToast("请选择座位");
                }
            }
        });

        RlOthers.setOnClickListener(new View.OnClickListener() {/* 其他座位 */
            @Override
            public void onClick(View v) {
                isGet = false;
                Glide.with(HotSellActivity.this).load(R.mipmap.item_notselect).into(imageSelect);
                Glide.with(HotSellActivity.this).load(R.mipmap.item_select).into(imageSelectSend);
                deliveryType = 1;
                viewPagerSlide.setCurrentItem(3);
                txtTitle.setText("选择座次");
                rlLastStep.setVisibility(View.VISIBLE);
                rlConfirm.setVisibility(View.GONE);
            }
        });


        rlSelectHall.setOnClickListener(new View.OnClickListener() {//第三个界面选择影厅
            @Override
            public void onClick(View v) {
                viewPagerSlide.setCurrentItem(3);
                txtTitle.setText("选择影厅");
                rlLastStep.setVisibility(View.GONE);
                rlConfirm.setVisibility(View.GONE);
            }
        });

        rlSelectSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerSlide.setCurrentItem(4);
                txtTitle.setText("选择座位");
                rlLastStep.setVisibility(View.GONE);
                rlConfirm.setVisibility(View.VISIBLE);
            }
        });


        btnSureThree.setOnClickListener(new View.OnClickListener() {//第三个界面的确认按钮
            @Override
            public void onClick(View v) {
                if (hallName.getText() != null && hallName.getText().toString().equals("选择影厅")){
                    ToastUtils.showShortToast("请选择影厅");
                    return;
                }
                if (seatName.getText() != null && seatName.getText().toString().equals("")){
                    ToastUtils.showShortToast("请选择座位");
                    return;
                }
                viewPagerSlide.setCurrentItem(0);
                txtTitle.setText("取餐方式");
                txtHallMes.setText(String.format("%s,%s",hallName.getText(),seatName.getText()));
                rlLastStep.setVisibility(View.GONE);
                rlConfirm.setVisibility(View.GONE);
            }
        });
        builder.setView(v);
        builder.setCancelable(true);
        final Dialog noticeDialog = builder.create();

        noticeDialog.getWindow().setGravity(Gravity.BOTTOM);
        noticeDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        noticeDialog.setCancelable(true);
        noticeDialog.show();
        noticeDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        noticeDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                deliveryType = 0;/* 自提 */
            }
        });
        buttonOne.setOnClickListener(new View.OnClickListener() {//第一个界面的确认按钮
            @Override
            public void onClick(View v) {
                if (deliveryType == 1 && !txtHallMes.getText().toString().contains("请选择您的")){
                    sendAddress = txtHallMes.getText().toString();
                    noticeDialog.dismiss();
                }else {
                    if (deliveryType == 0){
                        noticeDialog.dismiss();
                    }else {
                        ToastUtils.showShortToast("请选择影厅座位");
                    }
                }
            }
        });


        WindowManager.LayoutParams layoutParams = noticeDialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        noticeDialog.getWindow().setAttributes(layoutParams);
    }

    private void setAdapter(){
        pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));


                return viewList.get(position);
            }
        };

        viewPagerSlide.setAdapter(pagerAdapter);
        // 如果不设置，可能第三个页面以后就显示不出来了，因为offset就是默认值1了
        viewPagerSlide.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPagerSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                viewPagerSlide.requestLayout();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private int mPosition = -1;
    private String selecthallName = "";
    private void setRecyAdapter(){
        adapter = new CommonAdapter<String>(this,R.layout.seat_item_layout,data) {
            @Override
            protected void convert(ViewHolder holder, final String data, final int position) {
                holder.setText(R.id.txtName,data);
                if (mPosition == position){
                    Glide.with(HotSellActivity.this).load(R.mipmap.item_select).into((ImageView)holder.getView(R.id.imageSelect));
                }else {
                    Glide.with(HotSellActivity.this).load(R.mipmap.item_notselect).into((ImageView)holder.getView(R.id.imageSelect));
                }
                holder.getView(R.id.rlMain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPosition = position;
                        selecthallName = data;
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void sethallAdapter(final List<String> hallData){
        hallAdapter = new CommonAdapter<String>(this,R.layout.hall_item_layout,hallData) {
            @Override
            protected void convert(ViewHolder holder, String data, final int position) {
                holder.setText(R.id.hallName,hallData.get(position));
            }
        };
        hallListView.setAdapter(hallAdapter);
        hallAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                hallName.setText(hallData.get(position));
                viewPagerSlide.setCurrentItem(2);
                rlLastStep.setVisibility(View.VISIBLE);
                rlConfirm.setVisibility(View.GONE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
