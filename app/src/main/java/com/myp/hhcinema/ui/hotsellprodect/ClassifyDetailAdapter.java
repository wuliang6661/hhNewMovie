package com.myp.hhcinema.ui.hotsellprodect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
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
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.userlogin.LoginActivity;
import com.myp.hhcinema.util.SPUtils;
import com.myp.hhcinema.util.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import java.util.List;


public class ClassifyDetailAdapter extends RvAdapter<RightBean> {

    private int myPosition = -1;
    private boolean isOpen = false;
    private int goodsNum = 0;
    private SPUtils spUtils;
    private Context context;
    private List<RightBean> data;
    private int activityNum = -1;//优惠活动次数
    private boolean isFirstSelect = true;//第一次点击记录活动次数

    public ClassifyDetailAdapter(Context context, List<RightBean> list, RvListener listener) {
        super(context, list, listener);
        this.context = context;
        this.data = list;
        spUtils = new SPUtils("ProdectItemNum");
    }


    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_title : R.layout.item_classify_detail;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view, viewType, listener);
    }

    public class ClassifyHolder extends RvHolder<RightBean> {
        TextView txtName;
        ImageView ivAvatar;
        TextView tvTitle;
        ImageView ivAdd;
        RelativeLayout rlSelect;
        ImageView ivDecrease;
        TextView txtNum;
        TextView txtPrice;//结算价
        TextView txtNoPrice;//零售价
        TextView txtDes;//描述
        TextView personNum;

        public ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            switch (type) {
                case 0:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    txtName = (TextView) itemView.findViewById(R.id.txtName);
                    ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
                    ivAdd = (ImageView) itemView.findViewById(R.id.ivAdd);
                    rlSelect = (RelativeLayout) itemView.findViewById(R.id.rlSelect);
                    ivDecrease = (ImageView) itemView.findViewById(R.id.ivDecrease);
                    txtNum = (TextView) itemView.findViewById(R.id.txtNum);
                    txtPrice = itemView.findViewById(R.id.txtPrice);
                    txtNoPrice = itemView.findViewById(R.id.txtNoPrice);
                    txtDes = itemView.findViewById(R.id.txtDes);
                    personNum = itemView.findViewById(R.id.txtPersonNum);
                    break;
            }

        }

        @Override
        public void bindHolder(final RightBean sortBean, final int position) {
            int itemViewType = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    tvTitle.setText(sortBean.getName());
                    break;
                case 1:
                    if (sortBean.getPersonNum() == 0){
                        personNum.setVisibility(View.GONE);
                    }else if (sortBean.getPersonNum() == 1){
                        personNum.setVisibility(View.VISIBLE);
                        personNum.setText(String.format("%s","单人"));
                    }else if (sortBean.getPersonNum() == 2){
                        personNum.setVisibility(View.VISIBLE);
                        personNum.setText(String.format("%s","双人"));
                    }else {
                        personNum.setVisibility(View.VISIBLE);
                        personNum.setText(String.format("%s","多人"));
                    }
                    txtName.setText(sortBean.getName());
                    if (sortBean.getMerdesc() != null && sortBean.getMerdesc().equals("")){
                        txtDes.setVisibility(View.INVISIBLE);
                    }else {
                        txtDes.setVisibility(View.VISIBLE);
                        txtDes.setText(sortBean.getMerdesc());
                    }

                    if (activityNum == -1 ) {
                        if (sortBean.getActivityNumber() > 0){
                            txtPrice.setText(String.format("活动价:￥%s", sortBean.getActivityPrice()));
                        }else {
                            txtPrice.setText(String.format("￥ %s",sortBean.getPrice()));
                        }
                    }else {
                        if (activityNum > 0 && sortBean.getActivityNumber() > 0){
                            if (activityNum > sortBean.getActivityNumber()){
                                txtPrice.setText(String.format("￥%s", sortBean.getPrice()));
                            }else {
                                if (activityNum > 0 && activityNum > goodsNum) {
                                    txtPrice.setText(String.format("活动价:￥%s", sortBean.getActivityPrice()));
                                }else {
                                    txtPrice.setText(String.format("￥%s", sortBean.getPrice()));
                                }
                            }
                        }else {
                            txtPrice.setText(String.format("￥%s", sortBean.getPrice()));
                        }
                    }

                    if (sortBean.getListintPrice().equals("")){
                        txtNoPrice.setVisibility(View.GONE);
                    }else {
                        txtNoPrice.setVisibility(View.VISIBLE);
                        txtNoPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        txtNoPrice.setText(String.format("￥ %s",sortBean.getListintPrice()));
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (ScreenUtils.getScreenWidth()*0.32),
                            (int) (ScreenUtils.getScreenWidth()*0.32));
                    ivAvatar.setLayoutParams(params);
                    Glide.with(context)
                            .load(sortBean.getImgsrc())
                            .dontAnimate()
                            .placeholder(R.drawable.zhanwei1)
                            .into(ivAvatar);
                    if (spUtils.getInt(data.get(position).getId()) > 0){
                        rlSelect.setVisibility(View.VISIBLE);
                        txtNum.setText(String.format("%s", spUtils.getInt(data.get(position).getId())));
                    }else {
                        rlSelect.setVisibility(View.GONE);
                    }
                    ivAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                return;
                            }
                            if (MyApplication.isSuccess) {
                                if (activityNum == -1) {
                                    activityNum = sortBean.getActivityNumber();//优惠活动次数
                                }
                                if (activityNum >= 0) {
                                    if (activityNum - 1 >= 0) {
                                        activityNum = activityNum - 1;
                                    }
                                }
                                MyApplication.isSuccess = false;
                                ivAdd.setAnimation(getShowAnimation());
                                int num = spUtils.getInt(data.get(position).getId());
                                if (num != -1) {
                                    goodsNum = num;
                                    goodsNum++;
                                } else {
                                    goodsNum = 0;
                                    goodsNum++;
                                }
                                spUtils.put(data.get(position).getId(), goodsNum);
                                EventBus.getDefault().post(new MessageEvent("goodsNum", data.get(position).getId()));//发给HotSellActivity
                                myPosition = position;
                                isOpen = true;
                                notifyDataSetChanged();
                            }
                        }
                    });
                    ivDecrease.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MyApplication.isSuccess) {

                                if (activityNum + 1 <= sortBean.getActivityNumber()) {
                                    activityNum = activityNum + 1;
                                }

                                MyApplication.isSuccess = false;
                                ivDecrease.setAnimation(getHiddenAnimation());
                                int num = spUtils.getInt(data.get(position).getId());
                                if (num != -1) {
                                    goodsNum = num;
                                    goodsNum--;
                                    myPosition = position;
                                } else {
                                    goodsNum = 0;
                                    goodsNum--;
                                }
                                spUtils.put(data.get(position).getId(), goodsNum);
                                EventBus.getDefault().post(new MessageEvent("goodsNum", data.get(position).getId()));//发给HotSellActivity
                                isOpen = false;
                                notifyDataSetChanged();
                            }
                        }
                    });
                    break;
            }

        }
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

    public void clearSp(){
        if (spUtils != null) {
            spUtils.clear();
            notifyDataSetChanged();
        }
    }
}
