package com.myp.hhcinema.ui.main.member;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meg7.widget.CircleImageView;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterface;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.config.ConditionEnum;
import com.myp.hhcinema.entity.NewMenberNum;
import com.myp.hhcinema.entity.UserBO;
import com.myp.hhcinema.mvp.MVPBaseFragment;
import com.myp.hhcinema.ui.SettingActivity;
import com.myp.hhcinema.ui.WebViewActivity;
import com.myp.hhcinema.ui.feedbacklist.FeedBackListActivity;

import com.myp.hhcinema.ui.foodorderlist.FoodOrderActivity;
import com.myp.hhcinema.ui.membercard.MemberCardActivity;
import com.myp.hhcinema.ui.message.MessageActivity;
import com.myp.hhcinema.ui.personcollect.PersonCollectActivity2;
import com.myp.hhcinema.ui.personcomment.PersonCommentActivity;
import com.myp.hhcinema.ui.personcoupon.PersonCouponActivity;
import com.myp.hhcinema.ui.personorder.PersonOrderActivity;
import com.myp.hhcinema.ui.personread.PersonReadActivity;
import com.myp.hhcinema.ui.personsetting.PersonSettingActivity;
import com.myp.hhcinema.ui.personsome.PersonSomeActivity;
import com.myp.hhcinema.ui.personwantsee.PersonWantSeeActivity;
import com.myp.hhcinema.ui.scoredetail.ScoreDetailActivity;
import com.myp.hhcinema.ui.userlogin.LoginActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.widget.pulltozoomview.PullToZoomScrollViewEx;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人中心fragment
 */

public class MemberFragment extends MVPBaseFragment<MemberContract.View, MemberPresenter>
        implements MemberContract.View, View.OnClickListener {

    @Bind(R.id.scollview)
    PullToZoomScrollViewEx scollview;

    /**
     * headler区域
     */
    private ImageView signIn;
    private CircleImageView personImg;
    private TextView personName;

    /**
     * 主要content区域
     */
    LinearLayout moviesComment, moviesRead, moviesWantSee, moviesCollect,myScore;
    TextView moviesCommentNum, moviesReadNum, moviesWantSeeNum, moviesCollectNum,score_num,txtScore;
    RelativeLayout personYuehui, yijianfankuiLayout, settingLayout,call_layout,myAward;
    RelativeLayout rlMovieTicket,rlProdect,rlMovieCoupon,rlCard,seenMovie,wantSee,myCollect;
    private ImageView zoomImage;
    private String points = "0";
    private TextView ticket_point,prodect_point,coupon_point;
    private RelativeLayout rlMessage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_member, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadViewForPullToZoomScrollView(scollview);
        setPullToZoomViewLayoutParams(scollview);
        initViews();

        if (MyApplication.user != null) {
            mPresenter.loadNum(MyApplication.user.getId());
        }
    }


    /**
     * 初始化控件
     */
    private void initViews() {
        settingLayout = (RelativeLayout) scollview.getPullRootView().findViewById(R.id.setting_layout);
        call_layout = (RelativeLayout) scollview.getPullRootView().findViewById(R.id.call_layout);
        moviesComment = (LinearLayout) scollview.getPullRootView().findViewById(R.id.movies_comment);
        moviesRead = (LinearLayout) scollview.getPullRootView().findViewById(R.id.movies_read);
        moviesWantSee = (LinearLayout) scollview.getPullRootView().findViewById(R.id.movies_wantsee);
        moviesCollect = (LinearLayout) scollview.getPullRootView().findViewById(R.id.movies_collect);
        myScore = (LinearLayout) scollview.getPullRootView().findViewById(R.id.myScore);
        score_num = (TextView) scollview.getPullRootView().findViewById(R.id.score_num);
        myAward = (RelativeLayout) scollview.getPullRootView().findViewById(R.id.myAward);
        personYuehui = (RelativeLayout) scollview.getPullRootView().findViewById(R.id.person_yuehui);
        moviesCollectNum = (TextView) scollview.getPullRootView().findViewById(R.id.movies_collect_num);
        moviesReadNum = (TextView) scollview.getPullRootView().findViewById(R.id.movies_read_num);
        moviesWantSeeNum = (TextView) scollview.getPullRootView().findViewById(R.id.movies_wantsee_num);
        moviesCommentNum = (TextView) scollview.getPullRootView().findViewById(R.id.movies_comment_num);
        yijianfankuiLayout = (RelativeLayout) scollview.getPullRootView().findViewById(R.id.yijianfankui_layout);
        rlMovieTicket = scollview.getPullRootView().findViewById(R.id.rlMovieTicket);
        rlProdect = scollview.getPullRootView().findViewById(R.id.rlProdect);
        rlMovieCoupon = scollview.getPullRootView().findViewById(R.id.rlMovieCoupon);
        rlCard = scollview.getPullRootView().findViewById(R.id.rlCard);
        seenMovie = scollview.getPullRootView().findViewById(R.id.seenMovie);
        wantSee = scollview.getPullRootView().findViewById(R.id.wantSee);
        myCollect = scollview.getPullRootView().findViewById(R.id.myCollect);
        ticket_point = scollview.getPullRootView().findViewById(R.id.ticket_point);
        coupon_point = scollview.getPullRootView().findViewById(R.id.coupon_point);
        prodect_point = scollview.getPullRootView().findViewById(R.id.prodect_point);

        signIn = (ImageView) scollview.getHeaderView().findViewById(R.id.sign_img);
        rlMessage = scollview.getHeaderView().findViewById(R.id.rlMessage);
        rlMessage.setOnClickListener(this);
        signIn.setVisibility(View.VISIBLE);
        personImg = (CircleImageView) scollview.getHeaderView().findViewById(R.id.person_img);
        personName = (TextView) scollview.getHeaderView().findViewById(R.id.person_name);
        txtScore = scollview.getHeaderView().findViewById(R.id.txtScore);
        txtScore.setText("积分");
        zoomImage = scollview.getZoomView().findViewById(R.id.imageView);

//        ViewGroup.LayoutParams params3 = zoomImage.getLayoutParams();
//        params3.height=(int) (ScreenUtils.getScreenWidth() * 0.7);
//        zoomImage.setLayoutParams(params3);

        moviesRead.setOnClickListener(this);
        moviesComment.setOnClickListener(this);
        moviesWantSee.setOnClickListener(this);
        moviesCollect.setOnClickListener(this);
        myScore.setOnClickListener(this);
        personYuehui.setOnClickListener(this);
        yijianfankuiLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        call_layout.setOnClickListener(this);
        personImg.setOnClickListener(this);
        personName.setOnClickListener(this);
        myAward.setOnClickListener(this);
        rlMovieTicket.setOnClickListener(this);
        rlProdect.setOnClickListener(this);
        rlMovieCoupon.setOnClickListener(this);
        rlCard.setOnClickListener(this);
        seenMovie.setOnClickListener(this);
        wantSee.setOnClickListener(this);
        myCollect.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_img:
            case R.id.person_name:
                if (MyApplication.isLogin == ConditionEnum.LOGIN) {   //已登录
                    Intent intent = new Intent(getActivity(), PersonSettingActivity.class);
                    startActivityForResult(intent, 1);
                } else if (MyApplication.isLogin == ConditionEnum.NOLOGIN) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.rlMessage://消息
                if (goLogin()) {
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.rlMovieTicket://电影票
                if (goLogin()) {
                    gotoActivity(PersonOrderActivity.class, false);
                }
                break;
            case R.id.rlProdect://卖品
                if (goLogin()) {
                    gotoActivity(FoodOrderActivity.class, false);
                }
                break;
            case R.id.rlMovieCoupon://优惠券
                if (goLogin()) {
                    gotoActivity(PersonCouponActivity.class, false);
                }
                break;
            case R.id.rlCard://会员卡
                if (goLogin()) {
                    gotoActivity(MemberCardActivity.class, false);
                }
                break;
            case R.id.seenMovie://看过
                if (goLogin()) {
                    gotoActivity(PersonReadActivity.class, false);
                }
                break;
            case R.id.wantSee://想看
                if (goLogin()) {
                    gotoActivity(PersonWantSeeActivity.class, false);
                }
                break;
            case R.id.myCollect://收藏
                if (goLogin()) {
                    gotoActivity(PersonCollectActivity2.class, false);
                }
                break;

            case R.id.movies_comment:   //影评
                if (goLogin()) {
                    gotoActivity(PersonCommentActivity.class, false);
                }
                break;
            case R.id.myScore://积分
                if (goLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", HttpInterface.URL + "/wx/user/point?appUserId=" + MyApplication.user.getId() + "&cinemaId=" + MyApplication.cinemaBo.getCinemaId()
                            + "&points=" + points);
                    gotoActivity(WebViewActivity.class,bundle,false);
                }
                break;
            case R.id.person_yuehui:   //我的约会
                if (goLogin()) {
                    gotoActivity(PersonSomeActivity.class, false);
                }
//                LogUtils.showToast("敬请期待！");
                break;
            case R.id.yijianfankui_layout:   //意见反馈
                if (goLogin()) {
                    gotoActivity(FeedBackListActivity.class, false);
                }
                break;
            case R.id.setting_layout:  //基本设置
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.call_layout://客服
                if (MyApplication.cinemaBo != null){
                    callPhone();
                }else {
                    ToastUtils.showShortToast("请先选择影院");
                }
                break;
            case R.id.myAward:
                if (goLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", HttpInterface.URL + "/view/my_prize?appUserId=" + MyApplication.user.getId());
                    gotoActivity(WebViewActivity.class,bundle,false);
                }
                break;
                default:
                    break;
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+MyApplication.cinemaBo.getContact()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                UserBO userBO = (UserBO) data.getSerializableExtra("user");
                setDatas(userBO);
                break;
            case 2:
                boolean isLogout = data.getBooleanExtra("isLogout", false);
                if (isLogout) {
                    personName.setText("点击登录或注册");
                    personImg.setImageResource(R.drawable.defalt_person_img);
                    moviesCommentNum.setText("0");
                    moviesWantSeeNum.setText("0");
                    moviesReadNum.setText("0");
                    moviesCollectNum.setText("0");
                    score_num.setText("0");
                    points = "0";
                    txtScore.setText("积分");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin == ConditionEnum.LOGIN) {
            setDatas(MyApplication.user);
            mPresenter.loadMemberRecord(MyApplication.user.getId());
        }
    }

    /**
     * 为界面设置值
     */
    private void setDatas(UserBO userBO) {
        if (!StringUtils.isEmpty(userBO.getHeader())) {
            Glide.with(getActivity()).load(userBO.getHeader()).into(personImg);
        }
        if (!StringUtils.isEmpty(userBO.getNickName())) {
            personName.setText(userBO.getNickName());
        } else {
            personName.setText(userBO.getMobile());
        }
        moviesCommentNum.setText(userBO.getCommentNum());
        moviesWantSeeNum.setText(userBO.getWantseeNum());
        moviesReadNum.setText(userBO.getWatchedNum());
        moviesCollectNum.setText(userBO.getCollectNum());
        points = userBO.getPoints();
        score_num.setText(userBO.getPoints());
        Log.d("sdfkasjdfkl", "setDatas: "+userBO.getCollectNum());
    }


    private void loadViewForPullToZoomScrollView(PullToZoomScrollViewEx scrollView) {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.member_header_layout, null);
        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.member_zoom_layout, null);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.member_content_layout, null);

        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

    // 设置头部的View的宽高。
    private void setPullToZoomViewLayoutParams(PullToZoomScrollViewEx scrollView) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,
                (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderViewSize(mScreenWidth, (int)(mScreenWidth * 0.61));
        scrollView.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void getUser(UserBO userBO) {
        setDatas(userBO);

    }

    @Override
    public void getNum(NewMenberNum newMenberNum) {
        if (newMenberNum != null){
            if (newMenberNum.getOrders() != 0){
                ticket_point.setVisibility(View.VISIBLE);
                ticket_point.setText(String.valueOf(newMenberNum.getOrders()));
            }else {
                ticket_point.setVisibility(View.GONE);
            }

            if (newMenberNum.getTickets() != 0){
                coupon_point.setVisibility(View.VISIBLE);
                coupon_point.setText(String.valueOf(newMenberNum.getTickets()));
            }else {
                coupon_point.setVisibility(View.GONE);
            }
            txtScore.setText(String.format("积分 %s", newMenberNum.getPoints()));
        }
    }
}