package com.myp.hhcinema.ui.main.home;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.BaseFragment;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.jpush.MessageEvent;
import com.myp.hhcinema.ui.FragmentPaerAdapter;
import com.myp.hhcinema.ui.InfoActivity;
import com.myp.hhcinema.ui.main.home.movieslist.MoviesListFragment;
import com.myp.hhcinema.ui.main.home.nextmovies.NextMoviesFragment;
import com.myp.hhcinema.ui.message.MessageActivity;
import com.myp.hhcinema.ui.moviesseltor.SeltormovieActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.viewpager.CustomViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页的fragment
 */

public class HomeFragment extends BaseFragment implements
        View.OnClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.loc_layout)
    LinearLayout locLayout;
    @Bind(R.id.now_showing)
    RadioButton nowShowing;
    @Bind(R.id.next_showing)
    RadioButton nextShowing;
    @Bind(R.id.radio_layout)
    RadioGroup radioLayout;
    @Bind(R.id.hedler_layout)
    RelativeLayout hedlerLayout;
    @Bind(R.id.viewpager)
    CustomViewPager viewpager;

    @Bind(R.id.cinema_name)
    TextView cinemaName;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.rlMessage)
    RelativeLayout rlMessage;//消息
    @Bind(R.id.messageNum)
    TextView messageNum;//消息数量


    MoviesListFragment listFragment;
    NextMoviesFragment nextMoviesFragment;
    List<Fragment> list;

    CinemaBo cinemaIdBOs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listFragment = new MoviesListFragment();
        nextMoviesFragment = new NextMoviesFragment();
        list = new ArrayList<>();
        list.add(listFragment);
        list.add(nextMoviesFragment);
        FragmentPaerAdapter adapter = new FragmentPaerAdapter(getFragmentManager(), list);
        viewpager.setAdapter(adapter);
        viewpager.setScanScroll(false);
        setListener();
        if (MyApplication.spUtils.getString("read") == null
                || !MyApplication.spUtils.getString("read").equals("yes")) {
            infoDialog();
        }
    }

    /**
     * 设置监听
     */
    private void setListener() {
        locLayout.setOnClickListener(this);
        rlMessage.setOnClickListener(this);
        viewpager.setOnPageChangeListener(this);
        radioLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.now_showing:
                        nowShowing.setTextColor(Color.parseColor("#373737"));
                        nextShowing.setTextColor(Color.parseColor("#DBB177"));
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.next_showing:
                        nowShowing.setTextColor(Color.parseColor("#373737"));
                        nextShowing.setTextColor(Color.parseColor("#DBB177"));
                        viewpager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    public void setCinemaInfo(){
        cinemaName.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        cinemaName.setText(MyApplication.cinemaBo.getCinemaName());
        listFragment.setCinemaBo(MyApplication.cinemaBo);
        if (nextMoviesFragment != null) {
            nextMoviesFragment.setCinemaBo(MyApplication.cinemaBo);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loc_layout:   //定位地址
                Intent intent = new Intent(getActivity(), SeltormovieActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rlMessage:  //消息
                gotoActivity(MessageActivity.class,false);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                cinemaName.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                CinemaBo cinemaBo = (CinemaBo) data.getSerializableExtra("ciname");
                if (cinemaBo != null) {
                    cinemaName.setText(cinemaBo.getCinemaName());
                    MyApplication.spUtils.put("cinemaId", cinemaBo.getCinemaId());
                    MyApplication.cinemaBo = cinemaBo;
                    listFragment.setCinemaBo(cinemaBo);
                    if (nextMoviesFragment != null) {
                        nextMoviesFragment.setCinemaBo(cinemaBo);
                    }
                    EventBus.getDefault().post(new MessageEvent("StoreFragment", "yes"));//发给StoreFragment
                    EventBus.getDefault().post(new MessageEvent("PlayfulWebFragment", "yes"));//发给StoreFragment
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                nowShowing.setChecked(true);
                nowShowing.setTextColor(Color.parseColor("#373737"));
                nextShowing.setTextColor(Color.parseColor("#DBB177"));
                break;
            case 1:
                nowShowing.setTextColor(Color.parseColor("#DBB177"));
                nextShowing.setTextColor(Color.parseColor("#373737"));
                nextShowing.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void setCinemaNameStr(CinemaBo cinemaNameStr) {
        this.cinemaIdBOs = cinemaNameStr;
        if (cinemaIdBOs != null) {
            cinemaName.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            cinemaName.setText(cinemaNameStr.getCinemaName());
            MyApplication.cinemaBo = cinemaNameStr;
            listFragment.setCinemaBo(cinemaNameStr);
            if (nextMoviesFragment != null) {
                nextMoviesFragment.setCinemaBo(cinemaNameStr);
            }
        } else {
            cinemaName.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            cinemaName.setText("选择城市");
            LogUtils.showToast("当前城市暂无影院信息！");
        }
    }


    /**
     *  协议
     */
    private void infoDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialog);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.information_dialog_layout, null);
        TextView cancle = v.findViewById(R.id.cancle);
        TextView sure = v.findViewById(R.id.sure);
        TextView txt = v.findViewById(R.id.txt2);

        // SpannableStringBuilder 用法
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder();
        spannableBuilder.append(txt.getText().toString());
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
//                设定的是span超链接的文本颜色，而不是点击后的颜色
                ds.setColor(Color.parseColor("#009FFF"));
                ds.setUnderlineText(false);    //去除超链接的下划线
                ds.clearShadowLayer();//清除阴影
            }

        };
        spannableBuilder.setSpan(clickableSpan, 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置部分文字点击事件
        ClickableSpan clickableSpan2 = new ClickableSpan() {//隐私声明
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
//                设定的是span超链接的文本颜色，而不是点击后的颜色
                ds.setColor(Color.parseColor("#009FFF"));
                ds.setUnderlineText(false);    //去除超链接的下划线
                ds.clearShadowLayer();//清除阴影
            }

        };
        spannableBuilder.setSpan(clickableSpan2, 16, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt.setText(spannableBuilder);
        txt.setHighlightColor(getResources().getColor(android.R.color.transparent));//点击后的背景颜色，Android4.0以上默认是淡绿色，低版本的是黄色
        txt.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setView(v);
        builder.setCancelable(true);
        final Dialog noticeDialog = builder.create();
        noticeDialog.getWindow().setGravity(Gravity.CENTER);
        noticeDialog.setCancelable(false);
        noticeDialog.show();

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeDialog.dismiss();
                System.exit(0);
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeDialog.dismiss();
                MyApplication.spUtils.put("read", "yes");
            }
        });

        WindowManager.LayoutParams layoutParams = noticeDialog.getWindow().getAttributes();
        layoutParams.width = (int)(ScreenUtils.getScreenWidth()*0.75);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        noticeDialog.getWindow().setAttributes(layoutParams);
    }
}
