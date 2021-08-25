package com.myp.hhcinema.ui.personorder;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.entity.LockSeatsBO;
import com.myp.hhcinema.entity.OrderBO;
import com.myp.hhcinema.mvp.MVPBaseFragment;
import com.myp.hhcinema.ui.personorder.ordermessage.OrderMessageActivity;
import com.myp.hhcinema.util.CimemaUtils;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.StringUtils;
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
 * Created by Administrator on 2018/2/11.已完成订单
 */

public class completed extends MVPBaseFragment<completedContract.View, completedPresenter>
        implements completedContract.View ,AdapterView.OnItemClickListener {
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.text_layout)
    TextView textlayout;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.none_layout)
    LinearLayout nonelayout;
    CommonAdapter<LockSeatsBO> adapter;
    private List<LockSeatsBO> data= new ArrayList<>();
    private int page = 1;

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
        mPresenter.loadOrderList( "0","1",page,"10");
        list.setOnItemClickListener(this);
        setPullRefresher();
        adapter();
    }
    private void adapter() {
        adapter = new CommonAdapter<LockSeatsBO>(getActivity(), R.layout.item_order, data) {
            @Override
            protected void convert(ViewHolder helper, LockSeatsBO item, int position) {
                helper.setText(R.id.movies_name, item.getDxMovie().getMovieName());
                if (item.getMovieLanguage() != null){
                    helper.setText(R.id.movies_type, item .getMovieLanguage() +
                            item.getMovieDimensional() + item .getMovieSize());
                }else {
                    helper.setText(R.id.movies_type, item.getDxMovie().getMovieLanguage() +
                            item.getDxMovie().getMovieDimensional());
                }
                helper.setText(R.id.movies_address, item.getCinemaName() + " " + item.getHallName());
                if (item.getSeats().contains("_")){
                    helper.setText(R.id.movies_seat, CimemaUtils.getSeats(item.getSeats()));
                }else {
                    helper.setText(R.id.movies_seat, item.getSeats());
                }
                helper.setText(R.id.movies_time, item.getPlayName().substring(0, item.getPlayName().length() - 3));
                if (3 == item.getPayStatus() || 1 == item.getPayStatus()) {   //已完成的票价
                    if(item.getRefundStatus() == 1){//已退票
                        helper.setVisible(R.id.biaoshi, true);
                        helper.setText(R.id.movies_num, String.valueOf(Math.abs(Integer.valueOf(item.getTicketNum()))));
                        String refundFee = String.valueOf(item.getRefundHandFee());
                        String totalFee = String.valueOf(Double.valueOf(item.getPayPrice()) * -1);
                        helper.setText(R.id.order_price, String.format("已退款：¥ %s   手续费：¥ %s",totalFee,refundFee));
                    }else {
                        helper.setVisible(R.id.biaoshi, false);
                        helper.setText(R.id.movies_num, String.valueOf(Math.abs(Integer.valueOf(item.getTicketNum()))));
                        helper.setText(R.id.order_price, "总价：¥" + item.getPayPrice());
                    }
                }
                if (StringUtils.isEmpty(item.getDxMovie().getPicture())) {
                    helper.setImageResource(R.id.movies_img, R.color.act_bg01);
                } else {
                    helper.setImageUrl(R.id.movies_img, item.getDxMovie().getPicture());
                }
            }
        };
    }

    private void setPullRefresher(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                mPresenter.loadOrderList( "0","1",page,"10");
                smartRefreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.loadOrderList( "0","1",page,"10");
                smartRefreshLayout.finishLoadmore(1000);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("orderNum", data.get(position).getOrderNum());
        bundle.putString("id",String.valueOf(data.get(position).getId()));
        bundle.putString("cinemaId",data.get(position).getCinemaId());
        gotoActivity(OrderMessageActivity.class, bundle, false);

    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void getOrderList(List<LockSeatsBO> orderList) {
        if(page==1){
            if(orderList.size()==0){
                smartRefreshLayout.setVisibility(View.GONE);
                nonelayout.setVisibility(View.VISIBLE);
                textlayout.setText("您当前没有已完成的订单");
            }else {
                data.clear();
                data.addAll(orderList);
                list.setAdapter(adapter);
            }

        }else {
            data.addAll(orderList);
            adapter.setmDatas(data);
        }

    }
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}