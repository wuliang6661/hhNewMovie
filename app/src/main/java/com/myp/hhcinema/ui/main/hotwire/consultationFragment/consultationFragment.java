package com.myp.hhcinema.ui.main.hotwire.consultationFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.HotWireBO;
import com.myp.hhcinema.mvp.MVPBaseFragment;
import com.myp.hhcinema.ui.WebViewActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ScreenUtils;
import com.myp.hhcinema.util.SizeUtils;
import com.myp.hhcinema.util.StringUtils;
import com.myp.hhcinema.util.TimeUtils;
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

/**
 * Created by Administrator on 2018/2/11.
 */

public class consultationFragment  extends MVPBaseFragment<consultationContract.View, consultationPresenter>
        implements consultationContract.View ,AdapterView.OnItemClickListener {

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.none_layout)
    LinearLayout nonelayout;
    private int page = 1;
    private CommonAdapter<HotWireBO> adapter;
    private List<HotWireBO> data= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movielayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setOnItemClickListener(this);
        setPullRefresher();
        adapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.cinemaBo != null) {
            mPresenter.loadHotWire("1", 1, MyApplication.cinemaBo.getCinemaId());
        } else {
            mPresenter.loadHotWire("1", 1, null);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (MyApplication.cinemaBo != null) {
                mPresenter.loadHotWire("1", 1, MyApplication.cinemaBo.getCinemaId());
            } else {
                mPresenter.loadHotWire("1", 1, null);
            }
        }
    }

    private void adapter() {
        adapter = new CommonAdapter<HotWireBO>(getActivity(),
                R.layout.item_hotwrie, data) {
            @Override
            protected void convert(ViewHolder helper, HotWireBO item, int position) {
                LinearLayout itemLayout = helper.getView(R.id.item_layout);
                ViewGroup.LayoutParams params = itemLayout.getLayoutParams();
                int widthDb = ScreenUtils.getScreenWidth() - (SizeUtils.dp2px(11) * 2);
                params.width = widthDb;
                params.height = (int) (widthDb / 2 / 1.37f);
                itemLayout.setLayoutParams(params);
                helper.setText(R.id.hot_message, item.getTitle());
                helper.setText(R.id.hot_msg, item.getDescription());
                helper.setText(R.id.hot_time, TimeUtils.string2Pander(item.getPublishTime(), "yyyy-MM-dd"));
                if (StringUtils.isEmpty(item.getPic())) {
                    helper.setImageResource(R.id.hot_img, R.drawable.zhanwei2);
                } else {
                    helper.setImageUrl(R.id.hot_img, item.getPic());
                }
            }
        };
    }

    private void setPullRefresher(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (MyApplication.cinemaBo != null) {
                    mPresenter.loadHotWire("1", 1, MyApplication.cinemaBo.getCinemaId());
                } else {
                    mPresenter.loadHotWire("1", 1, null);
                }
                page=1;
                smartRefreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (MyApplication.cinemaBo != null) {
                    mPresenter.loadHotWire("1", page, MyApplication.cinemaBo.getCinemaId());
                } else {
                    mPresenter.loadHotWire("1", page, null);
                }
                smartRefreshLayout.finishLoadmore(1000);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("title", data.get(position).getTitle());
        bundle.putString("title", data.get(position).getTitle());
        bundle.putString("pic", data.get(position).getPic());
        bundle.putString("description", data.get(position).getDescription());
        bundle.putString("url", data.get(position).getArticleUrl()+"&flag=1");
        String yes = "yes";
        bundle.putString("fenxiang", yes);
        bundle.putString("back", "yes");
        gotoActivity(WebViewActivity.class, bundle, false);
    }

    @Override
    public void getHotWire(List<HotWireBO> hotWireBOs, int flpage, String articleType) {
        if(page==1){
            if(hotWireBOs.size()==0){
                smartRefreshLayout.setVisibility(View.GONE);
                nonelayout.setVisibility(View.VISIBLE);
            }else {
                data.clear();
                data.addAll(hotWireBOs);
                list.setAdapter(adapter);
            }

        }else {
            data.addAll(hotWireBOs);
            adapter.setmDatas(data);
        }
    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

