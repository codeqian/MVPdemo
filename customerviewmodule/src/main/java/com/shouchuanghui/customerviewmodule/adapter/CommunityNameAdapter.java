package com.shouchuanghui.customerviewmodule.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.BaseViewHolder;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.httpmodule.bean.common.CommunityInfoBean;

import java.util.List;

/**
 * 帖子item
 */
public class CommunityNameAdapter extends BaseQuickAdapter<CommunityInfoBean, BaseViewHolder> {
    private int currentPageIndex;

    public CommunityNameAdapter(@Nullable List<CommunityInfoBean> data) {
        super(R.layout.adapter_community_name, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CommunityInfoBean item, int position) {
        TextView name_t=viewHolder.getView(R.id.name_t);
        TextView adr_t=viewHolder.getView(R.id.adr_t);
        name_t.setText(item.getCommunity_name());
        adr_t.setText(item.getCommunity_address());
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }
}
