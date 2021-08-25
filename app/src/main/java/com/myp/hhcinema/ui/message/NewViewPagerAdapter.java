package com.myp.hhcinema.ui.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.myp.hhcinema.R;

import java.util.List;


/**
 * Created by zhoutong on 2017/11/17
 */
public class NewViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles;
    private Context mcontext;
    private ImageView imageNotice;
    private int myPosition = -1;

    public NewViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments, Context context) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
        this.mcontext = context;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public View getTabView(int position) {
        View tabView = LayoutInflater.from(mcontext).inflate(R.layout.mytab_layout, null);
        TextView tabTitle = (TextView) tabView.findViewById(R.id.txtTitle);
        imageNotice = tabView.findViewById(R.id.imageNotice);
        imageNotice.setVisibility(View.GONE);
        tabTitle.setText(titles[position]);
        return tabView;
    }

}