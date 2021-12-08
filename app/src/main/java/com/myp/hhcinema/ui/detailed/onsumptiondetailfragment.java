package com.myp.hhcinema.ui.detailed;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.myp.hhcinema.R;
import com.myp.hhcinema.entity.SumptionBo;
import com.myp.hhcinema.mvp.MVPBaseFragment;
import com.myp.hhcinema.util.LogUtils;
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
 * Created by Administrator on 2018/1/28.
 * 消费明细界面
 */

public class onsumptiondetailfragment extends MVPBaseFragment<onsumptiondetailContract.View, onsumptiondetailPresenter>
        implements onsumptiondetailContract.View {
    private String cardNum;
    @Bind(R.id.list)
    ListView listview;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private ArrayList<SumptionBo> data = new ArrayList<>();
    private CommonAdapter<SumptionBo> adapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onsumptiondetaillayout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadcosumption(1, cardNum);
        setPullRefresher();
        adapter();
    }

    private void adapter() {
        adapter = new CommonAdapter<SumptionBo>(getActivity(),
                R.layout.item_onsumption, data) {
            @Override
            protected void convert(ViewHolder helper, SumptionBo item, int position) {
                helper.setText(R.id.shijian, item.getPayDate());
                helper.getView(R.id.card_num).setVisibility(View.VISIBLE);
                helper.setText(R.id.card_num, "共计" + item.getTicketNum() + "张");
                helper.setText(R.id.chongzhijine, item.getPayPrice() + "元");
                helper.setText(R.id.shijian, item.getPayDate());
                switch (item.getOrderType()) {
                    case "0": // 0 购票订单
                        if (item.getPayStatus() == 1) {
                            helper.setText(R.id.pay_title, "购票支付成功");
                        } else if (item.getPayStatus() == 3) {
                            helper.setText(R.id.pay_title, "购票退款成功");
                        }
                        break;
                    case "1":
                        helper.getView(R.id.card_num).setVisibility(View.GONE);
                        if (item.getPayStatus() == 1) {
                            helper.setText(R.id.pay_title, "卖品支付成功");
                        } else if (item.getPayStatus() == 3) {
                            helper.setText(R.id.pay_title, "卖品退款成功");
                        }
                        break;
                    case "2":
                        if (item.getPayStatus() == 1) {
                            helper.setText(R.id.pay_title, "支付成功");
                        } else if (item.getPayStatus() == 3) {
                            helper.setText(R.id.pay_title, "退款成功");
                        }
                        break;
                }
            }
        };
    }

    private void setPullRefresher() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.loadcosumption(1, cardNum);
                page = 1;
                smartRefreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.loadcosumption(page, cardNum);
                smartRefreshLayout.finishLoadmore(1000);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {
    }

    @Override
    public void getcosumption(List<SumptionBo> sumptionBo, int pages) {
        if (pages == 1) {
            data.clear();
            data.addAll(sumptionBo);
            listview.setAdapter(adapter);
        } else {
            data.addAll(sumptionBo);
            adapter.setmDatas(data);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        cardNum = ((detailed) activity).getTitles();
    }

}

