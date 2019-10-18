package com.shouchuanghui.commonmodule.activity;

import android.os.Bundle;
import android.os.Handler;

import com.shouchuanghui.commonmodule.R;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/10/17 0017.
 */

public abstract class BaseTitleRecyclerViewActivity extends BaseTitleActivity implements BaseTitleRecyclerViewCB{
    protected ActivityBaseTitleRecyclerviewBinding binding;
    protected BaseDataBindingAdapter adapter;
    protected BaseTitleRecyclerViewVM vm;

    @Override
    public int getContentViewId() {
        return R.layout.activity_base_title_recyclerview;
    }

    @Override
    public BaseTitleRecyclerViewVM initViewModel() {
        vm = initTitleRecyclerViewViewModel();
        return vm;
    }


    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityBaseTitleRecyclerviewBinding) baseViewBinding;
        adapter = initAdapter(vm.getList());
        binding.crv.setAdapter(adapter);

        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.initData(true);
            }

            @Override
            public void onLoadMore() {
                vm.initData(false);
            }
        });

        vm.initData(false);
    }

    public abstract BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel();
    public abstract BaseDataBindingAdapter initAdapter(List list);

    @Override
    public void refreshAdapter(final boolean isRefresh,final  List dataList) {
        if (isRefresh)
            binding.crv.setRefreshing(false);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataChangedAfterLoadMore(isRefresh, dataList);
            }
        });
    }
}
