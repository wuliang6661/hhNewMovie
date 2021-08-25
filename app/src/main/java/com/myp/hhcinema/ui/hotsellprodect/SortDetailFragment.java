package com.myp.hhcinema.ui.hotsellprodect;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.entity.HotSellBO;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.prodectorder.ProdectOrderActivity;
import com.myp.hhcinema.ui.userlogin.LoginActivity;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SortDetailFragment extends BaseFragment<SortDetailPresenter, String> implements CheckListener{
    private RecyclerView mRv;
    private ClassifyDetailAdapter mAdapter;
    private GridLayoutManager mManager;
    private List<RightBean> mDatas = new ArrayList<>();
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;
    private View view;
    private List<HotSellBO> hotSellBOList;

    private String num="";
    private String price="";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort_detail;
    }

    @Override
    protected void initCustomView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
    }

    @Override
    protected void initListener() {
        EventBus.getDefault().register(this);
        mRv.addOnScrollListener(new RecyclerViewListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void clearData(){
        mAdapter.clearSp();
        mAdapter.notifyDataSetChanged();
    }

    public void refresh(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected SortDetailPresenter initPresenter() {
        showRightPage(1);
        mManager = new GridLayoutManager(mContext, 1);
        //通过isTitle的标志来判断是否是title
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? 1 : 1;
            }
        });
        mRv.setLayoutManager(mManager);
        mAdapter = new ClassifyDetailAdapter(mContext, mDatas, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                String content = "";
                switch (id) {
                    case R.id.root:
                        content = "title";
                        break;
                    case R.id.content:
                        content = "content";
                        break;

                }
//                Toast.makeText(getActivity(),mDatas.get(position).getName(),Toast.LENGTH_SHORT).show();
                if (MyApplication.isOk) {
                    showUpdateDialog(position);
                }
            }
        });

        mRv.setAdapter(mAdapter);
        mDecoration = new ItemHeaderDecoration(mContext, mDatas);
        mRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);
        initData();
        return new SortDetailPresenter();
    }

    @Override
    protected void getData() {

    }
    private void initData() {

        hotSellBOList = MyApplication.hotSellBO;
        for (int i = 0; i < hotSellBOList.size(); i++) {
            RightBean head = new RightBean(hotSellBOList.get(i).getItemClass().getName());
            //头部设置为true
            if (hotSellBOList.get(i).getMerchandiseList() != null && hotSellBOList.get(i).getMerchandiseList().size()>0) {
                head.setTitle(true);
                head.setTitleName(hotSellBOList.get(i).getItemClass().getName());
                head.setTag(String.valueOf(i));
                mDatas.add(head);
            }
            for (int j = 0; j < hotSellBOList.get(i).getMerchandiseList().size(); j++) {
                RightBean body = new RightBean(hotSellBOList.get(i).getMerchandiseList().get(j).getName());
                body.setTag(String.valueOf(i));
                String name = hotSellBOList.get(i).getItemClass().getName();
                body.setTitleName(name);
                body.setImgsrc(hotSellBOList.get(i).getMerchandiseList().get(j).getImageUrl());
                body.setPrice(String.valueOf(hotSellBOList.get(i).getMerchandiseList().get(j).getOnlinePrice()));
                if (hotSellBOList.get(i).getMerchandiseList().get(j).getOriginalPrice() == 0){
                    body.setListintPrice("");
                }else {
                    body.setListintPrice(String.valueOf(hotSellBOList.get(i).getMerchandiseList().get(j).getOriginalPrice()));
                }
                body.setId(String.valueOf(hotSellBOList.get(i).getMerchandiseList().get(j).getId()));
                body.setMerdesc(hotSellBOList.get(i).getMerchandiseList().get(j).getMerdesc());
                body.setActivityPrice(hotSellBOList.get(i).getMerchandiseList().get(j).getActivityPrice());
                body.setActivityNumber(hotSellBOList.get(i).getMerchandiseList().get(j).getActivityNumber());
                body.setPersonNum(hotSellBOList.get(i).getMerchandiseList().get(j).getPersonNum());
                mDatas.add(body);
            }

        }
        mAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);
    }

    public void setData(int n) {
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }

    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        Log.d("first--->", String.valueOf(firstItem));
        Log.d("last--->", String.valueOf(lastItem));
        if (n <= firstItem) {
            mRv.scrollToPosition(n);
        } else if (n <= lastItem) {
            Log.d("pos---->", String.valueOf(n) + "VS" + firstItem);
            int top = mRv.getChildAt(n - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            mRv.scrollBy(0, top);
        } else {
            mRv.scrollToPosition(n);
            move = true;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSure:
                if (!num.equals("")) {
                    if (MyApplication.priceIsOk || MyApplication.isIntent) {
                        if (MyApplication.prodectBO != null && MyApplication.goodsId.size()>0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ProdectBO", MyApplication.prodectBO);
                            bundle.putStringArrayList("goodsId", MyApplication.goodsId);
                            Intent intent = new Intent(getActivity(), ProdectOrderActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            noticeDialog.dismiss();
                            MyApplication.isIntent = true;
                            MyApplication.priceIsOk = false;
                        }else {
                            ToastUtils.showShortToast("你还没选择好吃的呢");
                        }
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
    public void refreshView(int code, String data) {

    }


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    mRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    mRv.scrollBy(0, top);
                }
            }
        }
    }

    /**
     * 清空卖品数量
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){
        if (messageEvent.getMessageType().equals("prodectClear")){
            mAdapter.clearSp();
        }
        if (messageEvent.getMessageType().equals("num")){
            if (messageEvent.getPassValue().equals("0")){
                if (tvShopCarNum != null) {
                    tvShopCarNum.setVisibility(View.GONE);
                }
                num = "";
            }else {
                if (tvShopCarNum != null) {
                    tvShopCarNum.setVisibility(View.VISIBLE);
                    tvShopCarNum.setText(String.valueOf(messageEvent.getPassValue()));
                }
                num = String.valueOf(messageEvent.getPassValue());
            }
        }
        if (messageEvent.getMessageType().equals("price")){
            if (tvPrice != null) {
                tvPrice.setText(String.valueOf(messageEvent.getPassValue()));
            }
            price = String.valueOf(messageEvent.getPassValue());
        }
        //卖品满减活动
        if (messageEvent.getMessageType().equals("activityChanged")){
            if (rlDecreaseActivity != null) {
                if (!MyApplication.partOne.equals("")) {
                    rlDecreaseActivity.setVisibility(View.VISIBLE);
                    txtDecreaseInstruction.setText(String.format("%s,", MyApplication.partOne));
                    txtDes.setText(String.format(" %s元,", MyApplication.partTwo));
                    txtDesNum.setText(String.format(" %s元", MyApplication.partThree));
                } else {
                    rlDecreaseActivity.setVisibility(View.GONE);
                }
            }
        }
    }

    private int goodsNum = 0;
    private SPUtils spUtils;
    private int activityNum = -1;//优惠活动次数
    private TextView tvShopCarNum;//购物车数量
    private TextView tvPrice;//价格
    private LinearLayout llSure;
    private Dialog noticeDialog;
    private RelativeLayout rlDecreaseActivity;//满减活动
    private TextView txtDecreaseInstruction;
    private TextView txtDes;
    private TextView txtDesNum;

    private void showUpdateDialog(final int position) {
        spUtils = new SPUtils("ProdectItemNum");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.myAlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.prodect_detail_dialog, null);
        ImageView ivClose = v.findViewById(R.id.close);
        final SelectableRoundedImageView prodectImage = v.findViewById(R.id.prodectImage);
        final ImageView send = v.findViewById(R.id.send);
        final TextView prodectName = v.findViewById(R.id.prodectName);
        final TextView prodectNum = v.findViewById(R.id.prodectNum);
        final TextView prodectPrice = v.findViewById(R.id.prodectPrice);
        final TextView prodectDecribe = v.findViewById(R.id.prodectDecribe);
        final ImageView ivDecrease = v.findViewById(R.id.ivDecrease);
        final ImageView ivAdd = v.findViewById(R.id.ivAdd);
        final TextView txtNum = v.findViewById(R.id.txtNum);
        final RelativeLayout rlSelect = v.findViewById(R.id.rlSelect);
        rlDecreaseActivity = v.findViewById(R.id.rlDecreaseActivity);
        txtDecreaseInstruction = v.findViewById(R.id.txtDecreaseInstruction);
        txtDes = v.findViewById(R.id.txtDes);
        txtDesNum = v.findViewById(R.id.txtDesNum);
        if (!MyApplication.partOne.equals("")){
            rlDecreaseActivity.setVisibility(View.VISIBLE);
            txtDecreaseInstruction.setText(String.format("%s,",MyApplication.partOne));
            txtDes.setText(String.format(" %s元,",MyApplication.partTwo));
            txtDesNum.setText(String.format(" %s元",MyApplication.partThree));
        }else {
            rlDecreaseActivity.setVisibility(View.GONE);
        }
        tvShopCarNum = v.findViewById(R.id.tvShopCarNum);
        tvPrice = v.findViewById(R.id.tvPrice);
        llSure = v.findViewById(R.id.llSure);
        llSure.setOnClickListener(this);

        if (spUtils.getInt(mDatas.get(position).getId()) > 0){
            rlSelect.setVisibility(View.VISIBLE);
            txtNum.setText(String.format("%s", spUtils.getInt(mDatas.get(position).getId())));
        }else {
            rlSelect.setVisibility(View.GONE);
        }
        if (!num.equals("")){
            tvShopCarNum.setVisibility(View.VISIBLE);
            tvShopCarNum.setText(num);
        }else {
            tvShopCarNum.setVisibility(View.GONE);
        }

        if (!price.equals("")) {
            tvPrice.setText(price);
        }

        ViewGroup.LayoutParams params3 = prodectImage.getLayoutParams();
        params3.height=ScreenUtils.getScreenWidth();
        prodectImage.setLayoutParams(params3);

        Glide.with(getActivity()).load(mDatas.get(position).getImgsrc()).into(prodectImage);
        prodectName.setText(mDatas.get(position).getName());
        prodectDecribe.setText(mDatas.get(position).getMerdesc());
        if (activityNum == -1 ) {
            if (mDatas.get(position).getActivityNumber() > 0){
                prodectPrice.setText(String.format("活动价:￥%s", mDatas.get(position).getActivityPrice()));
            }else {
                prodectPrice.setText(String.format("￥ %s",mDatas.get(position).getPrice()));
            }
        }else {
            if (activityNum > 0 && mDatas.get(position).getActivityNumber() > 0){
                if (activityNum > mDatas.get(position).getActivityNumber()){
                    prodectPrice.setText(String.format("￥%s", mDatas.get(position).getPrice()));
                }else {
                    if (activityNum >0 && activityNum > goodsNum) {
                        prodectPrice.setText(String.format("活动价:￥%s", mDatas.get(position).getActivityPrice()));
                    }else {
                        prodectPrice.setText(String.format("￥%s", mDatas.get(position).getPrice()));
                    }
                }
            }else {
                prodectPrice.setText(String.format("￥%s", mDatas.get(position).getPrice()));
            }
        }

        builder.setView(v);
        builder.setCancelable(true);
        noticeDialog = builder.create();
        noticeDialog.getWindow().setGravity(Gravity.BOTTOM);
        noticeDialog.show();

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (MyApplication.isSuccess) {
                    if (activityNum == -1) {
                        activityNum = mDatas.get(position).getActivityNumber();//优惠活动次数
                    }
                    if (activityNum >= 0) {
                        if (activityNum - 1 >= 0) {
                            activityNum = activityNum - 1;
                        }
                    }
                    MyApplication.isSuccess = false;
                    int num = spUtils.getInt(mDatas.get(position).getId());
                    if (num != -1) {
                        goodsNum = num;
                        goodsNum++;
                    } else {
                        goodsNum = 0;
                        goodsNum++;
                    }
                    spUtils.put(mDatas.get(position).getId(), goodsNum);
                    EventBus.getDefault().post(new MessageEvent("goodsNum", mDatas.get(position).getId()));//发给HotSellActivity

                    if (spUtils.getInt(mDatas.get(position).getId()) > 0){
                        rlSelect.setVisibility(View.VISIBLE);
                        txtNum.setText(String.format("%s", spUtils.getInt(mDatas.get(position).getId())));
                    }else {
                        rlSelect.setVisibility(View.GONE);
                    }
//                    myPosition = position;
//                    isOpen = true;
//                    notifyDataSetChanged();
                }
            }
        });
        ivDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isSuccess) {
                    if (activityNum + 1 <= mDatas.get(position).getActivityNumber()) {
                        activityNum = activityNum + 1;
                    }

                    MyApplication.isSuccess = false;
                    int num = spUtils.getInt(mDatas.get(position).getId());
                    if (num != -1) {
                        goodsNum = num;
                        goodsNum--;
//                        myPosition = position;
                    } else {
                        goodsNum = 0;
                        goodsNum--;
                    }
                    spUtils.put(mDatas.get(position).getId(), goodsNum);
                    EventBus.getDefault().post(new MessageEvent("goodsNum", mDatas.get(position).getId()));//发给HotSellActivity

                    if (spUtils.getInt(mDatas.get(position).getId()) > 0){
                        rlSelect.setVisibility(View.VISIBLE);
                        txtNum.setText(String.format("%s", spUtils.getInt(mDatas.get(position).getId())));
                    }else {
                        rlSelect.setVisibility(View.GONE);
                    }
//                    isOpen = false;
//                    notifyDataSetChanged();
                }
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeDialog.dismiss();
            }
        });

        noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        WindowManager.LayoutParams layoutParams = noticeDialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) (ScreenUtils.getScreenWidth() * 1.45);
        noticeDialog.getWindow().setAttributes(layoutParams);
//        noticeDialog.getWindow().setWindowAnimations(R.style.bottomOut);
    }

    //显示减号的动画
    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
    //隐藏减号的动画
    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}
