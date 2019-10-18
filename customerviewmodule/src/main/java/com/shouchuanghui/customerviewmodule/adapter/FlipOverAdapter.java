package com.shouchuanghui.customerviewmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shouchuanghui.customerviewmodule.BaseQuickAdapter;
import com.shouchuanghui.customerviewmodule.BaseViewHolder;
import com.shouchuanghui.customerviewmodule.R;

import java.util.List;

public class FlipOverAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public interface OnItemClickListener {
        void onPageClick(int page);
    }

    private int currentPage = 1;
    private OnItemClickListener onItemClickListener;

    public FlipOverAdapter(@Nullable List<String> data, OnItemClickListener onItemClickListener) {
        super(R.layout.item_flip_over, data);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String item, final int position) {
        TextView tvPage=viewHolder.getView(R.id.tvPage);

        tvPage.setText(item);
        if (currentPage - 1 == position) {
            tvPage.setTextColor(mContext.getResources().getColor(R.color.text_orange));
        } else {
            tvPage.setTextColor(mContext.getResources().getColor(R.color.text_black));
        }
        tvPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    currentPage = position + 1;
                    onItemClickListener.onPageClick(position + 1);
                }
            }
        });

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
