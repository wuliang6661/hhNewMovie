package com.myp.hhcinema.ui.personcomment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.myp.hhcinema.R;
import com.myp.hhcinema.base.MyApplication;
import com.myp.hhcinema.entity.CommentBO;
import com.myp.hhcinema.mvp.MVPBaseActivity;
import com.myp.hhcinema.util.CimemaUtils;
import com.myp.hhcinema.util.LogUtils;
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
 * MVPPlugin
 * 我的影评界面
 */

public class PersonCommentActivity extends MVPBaseActivity<PersonCommentContract.View,
        PersonCommentPresenter> implements PersonCommentContract.View{
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.none_layout)
    LinearLayout nonelayout;
    @Bind(R.id.text_layout)
    TextView textlayout;


    private CommonAdapter<CommentBO> adapter;
    private List<CommentBO> data= new ArrayList<>();
    private int page = 1;


    @Override
    protected int getLayout() {
        return R.layout.list_personcomment;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitle("我的影评");
        mPresenter.loadPersonComment(MyApplication.user.getId(),1);
        showProgress("加载中...");
        setPullRefresher();
        adapter();


    }

    private void adapter() {
        adapter = new CommonAdapter<CommentBO>(this, R.layout.item_comment, data) {
            @Override
            protected void convert(ViewHolder viewHolder, CommentBO item, int position) {
                RatingBar ratingBar = viewHolder.getView(R.id.ratingbar);
                ratingBar.setStar(Float.parseFloat(item.getScore()) / 2);
                viewHolder.setText(R.id.comment_message, item.getComment());
                if (StringUtils.isEmpty(item.getPicture())) {
                    viewHolder.setImageResource(R.id.movies_img, R.color.act_bg01);
                } else {
                    viewHolder.setImageUrl(R.id.movies_img, item.getPicture());
                }
                viewHolder.setText(R.id.movies_name, item.getMovieName());
                viewHolder.setText(R.id.movies_message, item.getSummary());
                viewHolder.setText(R.id.movies_type, item.getMovieType());
                viewHolder.setText(R.id.comment_num, CimemaUtils.getComment(Float.parseFloat(item.getScore())));
                viewHolder.setText(R.id.comment_time, TimeUtils.getFriendlyTimeSpanByNow(item.getCreateTime()));
            }
        };
    }

    private void setPullRefresher(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.loadPersonComment(MyApplication.user.getId(),1);
                page=1;
                smartRefreshLayout.finishRefresh(1000);
                refreshlayout.finishRefresh(2000);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.loadPersonComment(MyApplication.user.getId(),page);
                smartRefreshLayout.finishLoadmore(1000);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }
    @Override
    public void getCommentList(List<CommentBO> commentBOs,int pages) {
        if(pages==1){
            if(commentBOs.size()==0){
                smartRefreshLayout.setVisibility(View.GONE);
                nonelayout.setVisibility(View.VISIBLE);
                textlayout.setText("您当前还没有影评哦");
            }else {
                data.clear();
                data.addAll(commentBOs);
                list.setAdapter(adapter);
            }
        }else {
            data.addAll(commentBOs);
            adapter.setmDatas(data);

        }

    }

    @Override
    public void onRequestError(String msg) {
        stopProgress();
        LogUtils.showToast(msg);
    }

    @Override
    public void onRequestEnd(){
        stopProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }



}
