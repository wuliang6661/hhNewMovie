package com.myp.hhcinema.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by wuliang on 2017/6/9.
 * <p>
 * 解决内部包含listView时滑动冲突
 */

public class SimpleSwipeRefreshLayout extends SwipeRefreshLayout {

    private View view;

    public SimpleSwipeRefreshLayout(Context context) {
        super(context);
    }

    public SimpleSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewGroup(View view) {
        this.view = view;
    }

    @Override
    public boolean canChildScrollUp() {
        if (view != null && view instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop());
        }
        return super.canChildScrollUp();
    }
}
