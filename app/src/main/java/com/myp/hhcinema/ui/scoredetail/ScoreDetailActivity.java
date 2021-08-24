package com.myp.hhcinema.ui.scoredetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.BaseActivity;
import com.myp.hhcinema.entity.MoviesByCidBO;
import com.myp.hhcinema.widget.superadapter.CommonAdapter;
import com.myp.hhcinema.widget.superadapter.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;
import butterknife.Bind;

/**
 * Created by Witness on 2019/3/21
 * Describe: 积分详情
 */
public class ScoreDetailActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.header)
    LinearLayout header;

    List<MoviesByCidBO> moviesList;
    CommonAdapter<MoviesByCidBO> adapter;

    private TextView txtNum;

    @Override
    protected int getLayout() {
        return R.layout.list_swift_layout;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        header.setVisibility(View.VISIBLE);
        smartRefreshLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        setTitle("积分详情");
        goBack();
        initView();
        setPullRefresher();
    }

    private void initView(){
        View headView = LayoutInflater.from(this).inflate(R.layout.scorehead_layout, null);
        txtNum = headView.findViewById(R.id.txtNum);
        list.addHeaderView(headView);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void setPullRefresher(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                smartRefreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });

    }

    /**
     * 设置电影列表数据
     */
    private void setAdapter() {
        adapter = new CommonAdapter<MoviesByCidBO>(this,
                R.layout.score_item_layout, moviesList) {

            @Override
            protected void convert(ViewHolder helper, final MoviesByCidBO item, int position) {

            }
        };
        list.setAdapter(adapter);
    }
}
