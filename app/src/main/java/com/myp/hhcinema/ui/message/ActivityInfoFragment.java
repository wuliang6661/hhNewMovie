package com.myp.hhcinema.ui.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myp.hhcinema.R;
import com.myp.hhcinema.api.HttpInterfaceIml;
import com.myp.hhcinema.base.BaseFragment;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CommonBean;
import com.myp.hhcinema.entity.MessageBO;
import com.myp.hhcinema.ui.WebViewActivity;
import com.myp.hhcinema.ui.foodorderlist.FoodOrderActivity;
import com.myp.hhcinema.ui.membercard.MemberCardActivity;
import com.myp.hhcinema.ui.personcoupon.PersonCouponActivity;
import com.myp.hhcinema.ui.personorder.PersonOrderActivity;
import com.myp.hhcinema.ui.prodectorder.ProdectOrderActivity;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.widget.superadapter.CommonAdapter;
import com.myp.hhcinema.widget.superadapter.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Witness on 2019/7/9
 * Describe: 活动资讯
 */
public class ActivityInfoFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    List<MessageBO> moviesList = new ArrayList<>();
    CommonAdapter<MessageBO> adapter;

    private int pageNo = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mymessage_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPullRefresher();
        list.setOnItemClickListener(this);
        if (MyApplication.user != null && MyApplication.cinemaBo != null) {
            getMessageList(MyApplication.user.getId(),MyApplication.cinemaBo.getCinemaId(),pageNo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (moviesList.get(position).getAdvisoryType()){/* 消息类型  0资讯 1退款相关 2充值 3优惠券相关 4影票相关 5卖品相关 */
            case 0:
                if (moviesList.get(position).getMessageLink() != null && !moviesList.get(position).getMessageLink().equals("")) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", moviesList.get(position).getMessageLink());
                    startActivity(intent);
                }
                break;
            case 1:

                break;
            case 2:
                startActivity(new Intent(getActivity(),MemberCardActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(),PersonCouponActivity.class));
                break;
            case 4:
                startActivity(new Intent(getActivity(),PersonOrderActivity.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), FoodOrderActivity.class));
                break;
            default:
                break;
        }

        postRead(position,String.valueOf(moviesList.get(position).getId()));
    }

    private void setPullRefresher(){
        smartRefreshLayout.isEnableLoadmore();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (MyApplication.user != null && MyApplication.cinemaBo != null) {
                    getMessageList(MyApplication.user.getId(),MyApplication.cinemaBo.getCinemaId(),pageNo);
                }
                smartRefreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo++;
                if (MyApplication.user != null && MyApplication.cinemaBo != null) {
                    getMessageList(MyApplication.user.getId(),MyApplication.cinemaBo.getCinemaId(),pageNo);
                }
                smartRefreshLayout.finishLoadmore(1000);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }


    /**
     * 设置列表数据
     */
    private void setAdapter() {
        adapter = new CommonAdapter<MessageBO>(getActivity(),
                R.layout.activity_info_layout, moviesList) {

            @Override
            protected void convert(ViewHolder helper, final MessageBO item, int position) {
                RelativeLayout rlMain = helper.getConvertView().findViewById(R.id.rlMain);
                ViewGroup.LayoutParams params = rlMain.getLayoutParams();
                params.height=(int) (ScreenUtils.getScreenWidth() * 0.32);
                rlMain.setLayoutParams(params);

                TextView title = helper.getConvertView().findViewById(R.id.title);
                TextView secondTitle = helper.getConvertView().findViewById(R.id.secondTitle);
                TextView date = helper.getConvertView().findViewById(R.id.date);

                helper.setText(R.id.title,item.getTitle());
                helper.setText(R.id.secondTitle,item.getDescription());
                helper.setText(R.id.date,item.getCreateTime());
                Glide.with(getActivity()).load(item.getConsultPicture()).into((ImageView)helper.getConvertView().findViewById(R.id.image));

                if (item.isViewSwitch()){/* 是否已读 */
                    title.setTextColor(Color.parseColor("#8F8F8F"));
                    secondTitle.setTextColor(Color.parseColor("#8F8F8F"));
                    date.setTextColor(Color.parseColor("#8F8F8F"));
                }else {
                    title.setTextColor(Color.parseColor("#2B2B2B"));
                    secondTitle.setTextColor(Color.parseColor("#2B2B2B"));
                    date.setTextColor(Color.parseColor("#2B2B2B"));
                }
            }
        };
        list.setAdapter(adapter);
    }

    /**
     *
     * @param appUserId //
     * @param cinemaId //
     * @param messagePageNo//页码
     */
    private void getMessageList(String appUserId,String cinemaId,int messagePageNo){
        HttpInterfaceIml.getMessage(appUserId,cinemaId,0,messagePageNo,2).subscribe(new Subscriber<List<MessageBO>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(List<MessageBO> s) {
                if(pageNo==1){
                    if(s.size()==0){
                        smartRefreshLayout.setVisibility(View.VISIBLE);
//                        none_layout.setVisibility(View.VISIBLE);
                    }else {
                        moviesList.clear();
                        moviesList.addAll(s);
                        smartRefreshLayout.setVisibility(View.VISIBLE);
                        setAdapter();
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    moviesList.addAll(s);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void postRead(final int position,String id){
        HttpInterfaceIml.postRead(id).subscribe(new Subscriber<CommonBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onNext(CommonBean s) {
                if(s.getStatus() == 1){
                    moviesList.get(position).setViewSwitch(true);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
