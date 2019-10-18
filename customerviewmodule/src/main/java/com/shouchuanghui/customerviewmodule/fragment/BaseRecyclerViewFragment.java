package com.shouchuanghui.customerviewmodule.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.shouchuanghui.commonmodule.R;
import com.shouchuanghui.commonmodule.base.BaseFragment;
import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.CustomerRecyclerView;
import com.shouchuanghui.customerviewmodule.OnDataChangeListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewFragment extends BaseFragment {
    private BaseQuickAdapter adapter;
    private CustomerRecyclerView crv;
    private List list = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.fragment_base_recyclerview;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        adapter = initAdapter(getList());

        crv=thisView.findViewById(R.id.crv);
        crv.setAdapter(adapter);

        crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                initData(true);
            }

            @Override
            public void onLoadMore() {
                initData(false);
            }
        });

        initData(true);
    }

    public abstract void initData(boolean isRefresh);

    public abstract BaseQuickAdapter initAdapter(List list);

    public void refreshAdapter(final boolean isRefresh,final List dataList) {
        if (isRefresh)
            crv.setRefreshing(false);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataChangedAfterLoadMore(isRefresh, dataList);
            }
        });
    }

    public void stopRefreshAnim() {
        crv.setRefreshing(false);
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
