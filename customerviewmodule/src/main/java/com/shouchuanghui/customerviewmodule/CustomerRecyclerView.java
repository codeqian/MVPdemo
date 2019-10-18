package com.shouchuanghui.customerviewmodule;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shouchuanghui.customerviewmodule.loadmore.MultiSwipeRefreshLayout;

/**
 * 自定义RecyclerView集成下拉刷新和上拉加载
 */

public class CustomerRecyclerView extends LinearLayout implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = CustomerRecyclerView.class.getSimpleName();

    private Context context;

    private MultiSwipeRefreshLayout srl;
    private RecyclerView rv;
    private ImageView picNoDataImageView;
    private OnDataChangeListener onDataChangeListener;
    private RecyclerView.LayoutManager layoutManager;
    private BaseQuickAdapter adapter;
    private int loadMorePageSize = 10;
    private boolean isPicNoDataImageViewVisible = true;

    private boolean loadmoreEnable = true;

    public CustomerRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setOrientation(LinearLayout.VERTICAL);

        srl = new MultiSwipeRefreshLayout(context);
        srl.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        srl.setOnRefreshListener(this);

        rv = new RecyclerView(context);
        rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rv.setVerticalScrollBarEnabled(true);
        if (layoutManager == null) {
            rv.setLayoutManager(new LinearLayoutManager(context));
        }

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        srl.addView(relativeLayout);
        srl.setSwipeableChildren(rv);
        relativeLayout.addView(rv);
        //添加暂无内容
        picNoDataImageView = new ImageView(context);
        picNoDataImageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        picNoDataImageView.setImageDrawable(getResources().getDrawable(R.drawable.pic_no_data));
        picNoDataImageView.setScaleType(ImageView.ScaleType.CENTER);
        picNoDataImageView.setVisibility(View.GONE);
        relativeLayout.addView(picNoDataImageView);
        addView(srl);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomerRecyclerView);
            boolean refreshEnable = array.getBoolean(R.styleable.CustomerRecyclerView_refresh_enable, true);
            srl.setEnabled(refreshEnable);

            loadmoreEnable = array.getBoolean(R.styleable.CustomerRecyclerView_loadmore_enable, true);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (onDataChangeListener != null && adapter.isLoadMoreEnable()) {
            onDataChangeListener.onLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        if (onDataChangeListener != null) {
            onDataChangeListener.onRefresh();
        }
    }

    /**
     * 设置能否自动刷新
     *
     * @param isRefresh
     */
    public void onRefreshEnable(boolean isRefresh) {
        srl.setEnabled(isRefresh);
    }

    /**
     * 设置SwipeRefreshLayout刷新动画
     *
     * @param refresh
     */
    public void setRefreshing(boolean refresh) {
        if (srl != null) {
            srl.setRefreshing(refresh);
        }
    }

    public boolean isRefreshEnable(boolean enable) {
        return srl.isEnabled();
    }

    /**
     * 设置下拉加载
     *
     * @param enable
     */
    public void setLoadMoreEnable(boolean enable) {
        if (adapter != null) {
            if (enable) {
                adapter.setOnLoadMoreListener(this);
            } else {
                adapter.setOnLoadMoreListener(null);
            }
            adapter.setEnableLoadMore(enable);
        } else {
            Log.d(TAG, "adapter == null");
        }
    }

    public boolean isLoadMoreEnable(boolean enable) {
        if (adapter != null) {
            return adapter.isLoadMoreEnable();
        } else {
            Log.d(TAG, "adapter == null");
            return false;
        }
    }

    public SwipeRefreshLayout getSrl() {
        return srl;
    }

    public void setSrl(MultiSwipeRefreshLayout srl) {
        this.srl = srl;
    }

    public OnDataChangeListener getOnDataChangeListener() {
        return onDataChangeListener;
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        rv.setLayoutManager(layoutManager);
    }

    public RecyclerView getRv() {
        return rv;
    }

    public void setRv(RecyclerView rv) {
        this.rv = rv;
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(final BaseQuickAdapter adapter) {
        if (this.adapter != null)
            this.adapter.unregisterAdapterDataObserver(adapterDataObserver);
        this.adapter = adapter;
        //动画
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        setLoadMoreEnable(loadmoreEnable);
        if (adapter.isLoadMoreEnable()) {
//            adapter.setPreLoadNumber(loadMorePageSize);
            adapter.setOnLoadMoreListener(this);
        }
        adapter.registerAdapterDataObserver(adapterDataObserver);
        rv.setAdapter(adapter);
    }

    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            isShowNoDataPicImageView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            isShowNoDataPicImageView();
        }
    };

    private void isShowNoDataPicImageView() {
        if (isPicNoDataImageViewVisible) {
            if (adapter.getData().size() > 0) {
                picNoDataImageView.setVisibility(View.GONE);
            } else {
                picNoDataImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    public int getLoadMorePageSize() {
        return loadMorePageSize;
    }

    public void setLoadMorePageSize(int loadMorePageSize) {
        this.loadMorePageSize = loadMorePageSize;
    }

    public boolean isPicNoDataImageViewVisible() {
        return isPicNoDataImageViewVisible;
    }

    public void setPicNoDataImageViewVisible(boolean picNoDataImageViewVisible) {
        isPicNoDataImageViewVisible = picNoDataImageViewVisible;
        if (isPicNoDataImageViewVisible) {
            picNoDataImageView.setVisibility(View.VISIBLE);
        } else {
            picNoDataImageView.setVisibility(View.GONE);
        }
    }
}
