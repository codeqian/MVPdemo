package com.shouchuanghui.customerviewmodule.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * @author huscarter.
 * @title
 * @description
 * @date 11/16/2015.
 */
public class SimplePagerAdapter extends PagerAdapter {
    private List<? extends View> list;
    private Activity activity;
    private static final String TAG = "SimplePagerAdapter";

    public SimplePagerAdapter(List<? extends View> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        (container).addView(list.get(position), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView(list.get(position));
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        //
    }

    @Override
    public Parcelable saveState() {
        //
        return null;
    }
}
