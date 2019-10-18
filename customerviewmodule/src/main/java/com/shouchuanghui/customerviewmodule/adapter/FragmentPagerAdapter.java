package com.shouchuanghui.customerviewmodule.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author mahuafeng
 * 分页Fragment
 * @title
 * @description
 * @date 6/9/22
 */

public class FragmentPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private final String TAG = FragmentPagerAdapter.class.getSimpleName();

    protected Context context;
    protected List<T> fragments;

    public FragmentPagerAdapter(FragmentManager fm, Context context, List<T> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public T getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
