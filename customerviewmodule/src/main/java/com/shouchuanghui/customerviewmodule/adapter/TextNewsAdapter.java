package com.shouchuanghui.customerviewmodule.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.BaseViewHolder;
import com.shouchuanghui.customerviewmodule.R;
import com.shouchuanghui.httpmodule.bean.common.ArticleBean;

import java.util.List;

/**
 * 帖子item
 */
public class TextNewsAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    private int currentPageIndex;

    public TextNewsAdapter(@Nullable List<ArticleBean> data) {
        super(R.layout.adapter_text_news, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ArticleBean item, int position) {
        TextView news_t=viewHolder.getView(R.id.news_t);
        news_t.setText(item.getTitle());
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }
}
