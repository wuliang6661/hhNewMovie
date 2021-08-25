package com.myp.hhcinema.ui.moviesseltor;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CinemaBo;
import com.myp.hhcinema.entity.CityBO;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.ui.selectorcity.SelectorCityActivity;
import com.myp.hhcinema.util.LogUtils;
import com.myp.hhcinema.util.ToastUtils;
import com.myp.hhcinema.util.baidumap.BaiduMapLoctionUtils;
import com.myp.hhcinema.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.myp.hhcinema.widget.lgrecycleadapter.LGViewHolder;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;


/**
 * 选择影院界面
 */

public class SeltormovieActivity extends MVPBaseActivity<SeltormovieContract.View, SeltormoviePresenter>
        implements SeltormovieContract.View, View.OnClickListener {

    @Bind(R.id.recyle)
    RecyclerView recyle;
    @Bind(R.id.local_bg)
    LinearLayout localBg;
    @Bind(R.id.city_name)
    TextView cityName;

    @Override
    protected int getLayout() {
        return R.layout.act_select_movies;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setRightImage(R.drawable.guanbi, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyApplication.locateSuccess){
                    if (MyApplication.spUtils.getString("cinemaId") != null){
                        Intent intent = new Intent();
                        setResult(1, intent);
                        finish();
                    }else {
                        ToastUtils.showShortToast("请先选择影院");
                    }
                }else {
                    finish();
                }
            }
        });
        localBg.setOnClickListener(this);
        localBg.setVisibility(View.GONE);
        if (MyApplication.cityBO == null) {
            cityName.setText(BaiduMapLoctionUtils.city);
            mPresenter.loadCinemaIds("", BaiduMapLoctionUtils.lontitudeNum, BaiduMapLoctionUtils.latitude);
        } else {
            cityName.setText(MyApplication.cityBO.getCity());
            mPresenter.loadCinemaIds(MyApplication.cityBO.getCity(), BaiduMapLoctionUtils.lontitudeNum, BaiduMapLoctionUtils.latitude);
        }
    }

    /**
     * 初始化控件
     */
    @SuppressLint("WrongConstant")
    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置为横向布局
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        recyle.setLayoutManager(layoutManager);
        //设置增加或删除条目的动画
        recyle.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onData(final List<CinemaBo> cinemaIdBOs) {
        LGRecycleViewAdapter<CinemaBo> adapter = new LGRecycleViewAdapter<CinemaBo>(cinemaIdBOs) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_cinema_list_layout;
            }

            @Override
            public void convert(LGViewHolder holder, CinemaBo cinemaHallsBO, int position) {
                TextView cinemaName = (TextView) holder.getView(R.id.cinema_name);
                TextView cinemaAdress = (TextView) holder.getView(R.id.cinema_address);
                TextView cinemaLoc = (TextView) holder.getView(R.id.cinema_distance);
                cinemaName.setText(cinemaHallsBO.getCinemaName());
                cinemaAdress.setText(cinemaHallsBO.getAddress());
                double distance = Double.parseDouble(cinemaHallsBO.getDistance());
                if (distance < 1000) {
                    cinemaLoc.setText((int) distance + "m");
                } else {
                    DecimalFormat df = new DecimalFormat("#.00");
                    cinemaLoc.setText(df.format(distance / 1000) + "km");
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("ciname", cinemaIdBOs.get(position));
                setResult(1, intent);
                finish();
            }
        });
        recyle.setAdapter(adapter);
    }

    @Override
    public void onRequestError(String msg) {
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SelectorCityActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                CityBO cityBO = (CityBO) data.getSerializableExtra("city");
                MyApplication.cityBO = cityBO;
                cityName.setText(cityBO.getCity());
                mPresenter.loadCinemaIds(cityBO.getCity(), BaiduMapLoctionUtils.lontitudeNum, BaiduMapLoctionUtils.latitude);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!MyApplication.locateSuccess){
            if (MyApplication.spUtils.getString("cinemaId") != null){
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }else {
                ToastUtils.showShortToast("请先选择影院");
            }
        }else {
            finish();
        }
    }
}